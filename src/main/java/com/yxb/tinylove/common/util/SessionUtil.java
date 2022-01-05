package com.yxb.tinylove.common.util;

import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.netty.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class SessionUtil {

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static void addOnlineUser(Session session, Channel channel) {
        channel.attr(Attributes.SESSION).set(session);
        channelMap.put(session.getUserId(), channel);
        globalGroup.add(channel);
    }

    public static void delOnlineUser(Channel channel) {
        channelMap.remove(getSession(channel).getUserId());
        channel.attr(Attributes.SESSION).set(null);
        globalGroup.remove(channel);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static List<Session> onlineList() {
        return channelMap.values().stream().map(SessionUtil::getSession).collect(Collectors.toList());
    }

    public static List<Session> onlineListExceptSelf(Channel channel) {
        Session selfSession = getSession(channel);
        return channelMap.values().stream().map(SessionUtil::getSession).filter(session -> !session.getUserId().equals(selfSession.getUserId())).collect(Collectors.toList());
    }


    public static Channel getChannel(String userId) {
        return channelMap.get(userId);
    }


    public static void send2All(TextWebSocketFrame frame) {
        globalGroup.writeAndFlush(frame);
    }
}
