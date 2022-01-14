package com.yxb.tinylove.common.bean;

import com.yxb.tinylove.common.util.DateUtil;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.domain.Msg;
import com.yxb.tinylove.domain.User;
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

    private String nickname;

    private Long toUserId;

    private String toUserName;

    private String toNickname;

    private Integer type;

    private boolean self;

    private String avatarIndex;

    private String time;

    private String msg;

    public static ChatMsg buildChat(Msg msg, Session fromSession, Session loginSession) {
        boolean self = msg.getUserId().equals(loginSession.getUserId());
        return ChatMsg.builder()
                .userId(self ? loginSession.getUserId() : fromSession.getUserId())
                .username(self ? loginSession.getUsername() : fromSession.getUsername())
                .nickname(self ? loginSession.getNickname() : fromSession.getNickname())
                .toUserId(self ? fromSession.getUserId() : loginSession.getUserId())
                .toUserName(self ? fromSession.getUsername() : loginSession.getUsername())
                .toNickname(self ? fromSession.getNickname() : loginSession.getNickname())
                .avatarIndex(self ? loginSession.getAvatarIndex() : fromSession.getAvatarIndex())
                .self(self)
                .msg(msg.getMsg())
                .time(DateUtil.format(msg.getCreateTime(), DateUtil.PATTERN_M2S))
                .type(msg.getType())
                .build();
    }

    public static ChatMsg buildGroupChat(Msg msg, User user, Session loginSession) {
        return ChatMsg.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatarIndex(user.getAvatarUrl())
                .self(msg.getUserId().equals(loginSession.getUserId()))
                .msg(msg.getMsg())
                .time(DateUtil.format(msg.getCreateTime(), DateUtil.PATTERN_M2S))
                .type(msg.getType())
                .build();
    }
}
