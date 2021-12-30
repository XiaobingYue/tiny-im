package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.netty.protocol.resp.OnlineListResp;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class OnlineUserListHandler extends AbstractHandler {

    @Override
    public void exec(Channel channel, JSONObject req) {
        OnlineListResp onlineListResp = OnlineListResp.builder().userList(SessionUtil.onlineList()).type(1).build();
        TextWebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(onlineListResp));
        channel.writeAndFlush(frame);
    }
}
