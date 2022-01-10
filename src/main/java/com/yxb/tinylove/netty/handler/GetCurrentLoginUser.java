package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.netty.protocol.resp.LoginUser;
import com.yxb.tinylove.service.MsgService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Slf4j
@Component
public class GetCurrentLoginUser extends AbstractHandler {

    @Autowired
    private MsgService msgService;

    @Override
    public void exec(Channel channel, JSONObject req) {
        String token = req.getString("token");
        Session session = SessionUtil.getSession(channel);
        if (Objects.nonNull(session)) {
          log.warn("该用户已登录:{}", session.getUsername());
            SessionUtil.addOnlineUser(session, channel);
            LoginUser loginUser = LoginUser.builder().loginUser(session).type(3).build();
            // 返回用户信息
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(loginUser)));
        } else {
            session = SessionUtil.getSession(token);
            if (Objects.isNull(session)) {
                // 未登录
                LoginUser loginUser = LoginUser.builder().loginUser(null).type(3).build();
                // 返回用户信息
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(loginUser)));
                return;
            }
            SessionUtil.addOnlineUser(session, channel);
            LoginUser loginUser = LoginUser.builder().loginUser(session).type(3).build();
            // 返回用户信息
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(loginUser)));
            loginUser.setType(4);
            TextWebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(loginUser));
            // 上线通知
            SessionUtil.send2All(frame);
        }
    }
}
