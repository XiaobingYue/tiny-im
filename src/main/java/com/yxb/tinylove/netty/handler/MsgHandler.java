package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.netty.protocol.req.MsgRequest;
import com.yxb.tinylove.netty.protocol.resp.MsgResp;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class MsgHandler extends AbstractHandler {

    @Override
    public void exec(Channel channel, JSONObject req) {
        MsgRequest msgRequest = JSON.toJavaObject(req, MsgRequest.class);
        Session session = SessionUtil.getSession(channel);
        Channel toChannel = SessionUtil.getChannel(msgRequest.getToUserId());
        MsgResp msgResp = MsgResp.builder().msg(msgRequest.getMessage()).userId(session.getUserId()).username(session.getUsername()).time("").build();
        toChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgResp)));
    }
}
