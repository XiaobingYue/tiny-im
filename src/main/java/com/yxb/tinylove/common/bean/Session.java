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

    private Long userId;

    private String username;

    private String nickname;

    private Gender gender;

    private String phoneNum;

    private Integer avatarIndex;

    private String avatarUrl;

    private String token;

    private List<String> chatMessageList;
}
