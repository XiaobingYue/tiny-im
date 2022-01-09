package com.yxb.tinylove.netty.protocol.req;

import lombok.Data;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Data
public class MsgRequest {

    private String message;

    private Long toUserId;
}
