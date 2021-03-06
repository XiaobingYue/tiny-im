package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.bean.ChatMsg;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.DateUtil;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.domain.Msg;
import com.yxb.tinylove.netty.protocol.resp.OnlineListResp;
import com.yxb.tinylove.service.MsgService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Component
public class OnlineUserListHandler extends AbstractHandler {

    @Autowired
    private MsgService msgService;

    @Override
    public void exec(Channel channel, JSONObject req) {
        List<Session> sessions = SessionUtil.onlineListExceptSelf(channel);
        Session session = SessionUtil.getSession(channel);
        List<Long> idList = sessions.stream().map(Session::getUserId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(idList)) {
            // 好友聊天消息
            List<Msg> msgList = msgService.queryMsgByUserIds(idList, session.getUserId());
            Map<Long, List<Msg>> msgMap = msgList.stream().collect(Collectors.groupingBy(Msg::getUserId));
            List<Msg> selfSendList = msgMap.get(session.getUserId());
            if (Objects.nonNull(selfSendList)) {
                Map<Long, List<Msg>> selfMsgMap = selfSendList.stream().collect(Collectors.groupingBy(Msg::getToUserId));
                selfMsgMap.forEach((userId, list) -> {
                    if (msgMap.get(userId) == null) {
                        msgMap.put(userId, list);
                    } else {
                        msgMap.get(userId).addAll(list);

                    }
                });
            }
            sessions.forEach(s -> {
                Long userId = s.getUserId();
                List<Msg> list = msgMap.get(userId);
                if (!CollectionUtils.isEmpty(list)) {
                    List<ChatMsg> chatMsgList = list.stream().sorted(Comparator.comparing(Msg::getCreateTime))
                            .map(msg -> ChatMsg.buildChat(msg, s, session)).collect(Collectors.toList());
                    s.setChatMessageList(chatMsgList);
                }
            });
        }
        Map<Long, List<ChatMsg>> chatMsgMap = msgService.queryMsgByGroupIds(Collections.singletonList(0L), session);
        sessions.add(0, Session.builder().userId(0L)
                .username("chatRoom")
                .nickname("聊天大厅")
                .avatarIndex("chatRoom")
                .chatMessageList(chatMsgMap.get(0L)).build());
        OnlineListResp onlineListResp = OnlineListResp.builder().userList(sessions).type(1).build();
        TextWebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(onlineListResp));
        channel.writeAndFlush(frame);
    }
}
