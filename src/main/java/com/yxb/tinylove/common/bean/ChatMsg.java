package com.yxb.tinylove.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yxb
 * @since 2022/1/10
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg {

    private Long userId;

    private String username;

    private String nickName;

    private Long toUserId;

    private String toUserName;

    private String toNickName;

    private Integer type;

    private boolean self;

    private Integer avatarIndex;

    private String time;

    private String msg;
}
