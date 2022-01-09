package com.yxb.tinylove.netty.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author yuexba
 */
public abstract class AbstractHandler {

    public abstract void exec(Channel channel, JSONObject req);
}
