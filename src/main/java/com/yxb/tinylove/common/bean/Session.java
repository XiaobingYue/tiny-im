package com.yxb.tinylove.common.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yuexba
 */
@Data
@Builder
public class Session {

    private String userId;

    private String username;

    private Integer avatarIndex;

    private List<String> chatMessageList;
}
