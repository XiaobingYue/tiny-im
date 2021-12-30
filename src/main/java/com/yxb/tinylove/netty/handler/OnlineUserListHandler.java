package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class OnlineUserListHandler extends AbstractHandler {

    @Override
    public void exec(Channel channel, JSONObject req) {
        String onlineUser = JSON.toJSONString(SessionUtil.onlineList());
        TextWebSocketFrame frame = new TextWebSocketFrame(onlineUser);
        channel.writeAndFlush(frame);
    }
}
