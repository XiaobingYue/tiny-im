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

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            List<Msg> msgList = msgService.queryLatestMsgByUserIds(idList, session.getUserId());
            Map<Long, List<Msg>> msgMap = msgList.stream().collect(Collectors.groupingBy(Msg::getUserId));
            List<Msg> selfSendList = msgMap.get(session.getUserId());
            if (Objects.nonNull(selfSendList)) {
                Map<Long, List<Msg>> selfMsgMap = selfSendList.stream().collect(Collectors.groupingBy(Msg::getToUserId));
                selfMsgMap.forEach((userId, list) -> msgMap.get(userId).addAll(list));
            }
            sessions.forEach(s -> {
                Long userId = s.getUserId();
                List<ChatMsg> chatMsgList = msgMap.get(userId).stream().sorted(Comparator.comparing(Msg::getCreateTime))
                        .map(msg -> ChatMsg.builder().userId(s.getUserId()).username(s.getUsername()).nickName(s.getNickname())
                                .toUserId(session.getUserId()).toUserName(session.getUsername()).toNickName(session.getNickname())
                                .avatarIndex(s.getAvatarIndex()).self(msg.getUserId().equals(session.getUserId())).msg(msg.getMsg())
                                .time(DateUtil.format(msg.getCreateTime(), DateUtil.PATTERN_M2S)).type(msg.getType()).build()).collect(Collectors.toList());
                s.setChatMessageList(chatMsgList);
            });
        }
        OnlineListResp onlineListResp = OnlineListResp.builder().userList(sessions).type(1).build();
        TextWebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(onlineListResp));
        channel.writeAndFlush(frame);
    }
}
