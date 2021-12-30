package com.yxb.tinylove.common.util;

import java.util.UUID;

/**
 * @author yxb
 * @since 2021/12/30
 */
public class UUIDUtil {

    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
