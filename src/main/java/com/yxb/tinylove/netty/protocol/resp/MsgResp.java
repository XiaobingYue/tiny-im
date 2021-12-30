package com.yxb.tinylove.netty.protocol.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Data
@Builder
public class MsgResp {

    private String userId;

    private String msg;

    private String username;

    private String avatar;

    private String time;
}
