package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.netty.protocol.resp.LoginUser;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class GetCurrentLoginUser extends AbstractHandler {
    @Override
    public void exec(Channel channel, JSONObject req) {
        LoginUser loginUser = LoginUser.builder().type(3).loginUser(SessionUtil.getSession(channel)).build();
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(loginUser)));
    }
}
