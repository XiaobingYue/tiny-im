package com.yxb.tinylove.netty.protocol.resp;

import com.yxb.tinylove.common.bean.Session;
import lombok.Builder;
import lombok.Data;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Data
@Builder
public class LoginUser {

    private Integer type;

    private Session loginUser;
}
