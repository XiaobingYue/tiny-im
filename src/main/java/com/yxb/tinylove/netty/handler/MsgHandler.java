package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.DateUtil;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.netty.protocol.req.MsgRequest;
import com.yxb.tinylove.netty.protocol.resp.MsgResp;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class MsgHandler extends AbstractHandler {

    @Override
    public void exec(Channel channel, JSONObject req) {
        MsgRequest msgRequest = JSON.toJavaObject(req, MsgRequest.class);
        String toUserId = msgRequest.getToUserId();
        Session session = SessionUtil.getSession(channel);
        if ("chatRoom".equals(toUserId)) {
            // 群发
            MsgResp msgResp = MsgResp.builder().msg(msgRequest.getMessage().replaceAll("\\n", "<br/>"))
                    .userId(session.getUserId())
                    .username(session.getUsername())
                    .time(DateUtil.format(new Date(), DateUtil.PATTERN_M2S))
                    .type(2)
                    .msgType(2)
                    .avatarIndex(session.getAvatarIndex())
                    .groupId(msgRequest.getToUserId())
                    .build();
            SessionUtil.send2All(new TextWebSocketFrame(JSON.toJSONString(msgResp)));
        } else {
            Channel toChannel = SessionUtil.getChannel(toUserId);
            Session toSession = SessionUtil.getSession(toChannel);
            MsgResp msgResp = MsgResp.builder().msg(msgRequest.getMessage().replaceAll("\\n", "<br/>"))
                    .userId(session.getUserId())
                    .username(session.getUsername())
                    .toUserId(toSession.getUserId())
                    .toUsername(toSession.getUsername())
                    .time(DateUtil.format(new Date(), DateUtil.PATTERN_M2S))
                    .type(2)
                    .msgType(1)
                    .avatarIndex(session.getAvatarIndex())
                    .build();
            toChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgResp)));
        }
    }
}
