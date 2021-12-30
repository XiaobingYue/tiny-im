package com.yxb.tinylove.netty.attribute;

import com.yxb.tinylove.common.bean.Session;
import io.netty.util.AttributeKey;

/**
 * @author yuexba
 */
public class Attributes {

    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
