package com.yxb.tinylove.common.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author yuexba
 */
@Data
@Builder
public class Session {

    private String userId;

    private String username;
}
