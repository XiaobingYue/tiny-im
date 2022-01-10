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

    private Long userId;

    private String msg;

    private String username;

    private String nickname;

    private Integer avatarIndex;

    private String avatar;

    private String time;

    private Integer type;

    private String toUsername;

    private Long toUserId;

    private String toNickname;

    /**
     * 消息类型， 1-个人消息 2-群消息
     */
    private Integer msgType;

    private Long groupId;
}
