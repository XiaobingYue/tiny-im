package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author yxb
 * @since 2022.1.5
 */
public class PingHandler extends AbstractHandler {
    @Override
    public void exec(Channel channel, JSONObject req) {
        channel.writeAndFlush(new TextWebSocketFrame("pong"));
    }
}