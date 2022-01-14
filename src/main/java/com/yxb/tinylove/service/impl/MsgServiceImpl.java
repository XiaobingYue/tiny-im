package com.yxb.tinylove.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxb.tinylove.common.bean.ChatMsg;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.dao.MsgMapper;
import com.yxb.tinylove.domain.Msg;
import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.service.IUserService;
import com.yxb.tinylove.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yxb
 * @since 2022/1/10
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {

    @Autowired
    private IUserService userService;

    @Override
    public List<Msg> queryMsgByUserIds(List<Long> idList, Long userId) {
        return this.list(Wrappers.<Msg>lambdaQuery()
                .eq(Msg::getState, 1)
                .and(wrapper -> wrapper.in(Msg::getUserId, idList).eq(Msg::getToUserId, userId))
                .or(wrapper -> wrapper.eq(Msg::getUserId, userId).in(Msg::getToUserId, idList)));
    }

    @Override
    public Map<Long, List<ChatMsg>> queryMsgByGroupIds(List<Long> groupIds, Session loginSession) {
        List<Msg> list = this.list(Wrappers.<Msg>lambdaQuery()
                .eq(Msg::getState, 1)
                .in(Msg::getToUserId, groupIds));
        Map<Long, User> userMap = new HashMap<>(10);
        list.forEach(msg -> userMap.put(msg.getUserId(), userService.queryById(msg.getUserId())));
        Map<Long, List<Msg>> msgMap = list.stream().collect(Collectors.groupingBy(Msg::getToUserId));
        Map<Long, List<ChatMsg>> chatMsgMap = new HashMap<>(msgMap.size());
        msgMap.forEach((groupId, msgList) -> {
            List<ChatMsg> chatMsgList = msgList.stream().sorted(Comparator.comparing(Msg::getCreateTime))
                    .map(msg -> ChatMsg.buildGroupChat(msg, userMap.get(msg.getUserId()), loginSession)).collect(Collectors.toList());
            chatMsgMap.put(groupId, chatMsgList);
        });
        return chatMsgMap;
    }

    @Override
    public List<ChatMsg> queryMsgByUserId(Long fromId) {
        Session session = SessionUtil.getSession();
        List<Msg> msgList = this.list(Wrappers.<Msg>lambdaQuery()
                .eq(Msg::getState, 1)
                .and(wrapper -> wrapper.eq(Msg::getUserId, fromId).eq(Msg::getToUserId, session.getUserId()))
                .or(wrapper -> wrapper.eq(Msg::getUserId, session.getUserId()).eq(Msg::getToUserId, fromId)));
        //User user = userService.queryById(fromId);
        Session fromSession = SessionUtil.getSession(SessionUtil.getChannel(fromId));
        return msgList.stream().sorted(Comparator.comparing(Msg::getCreateTime))
                .map(msg -> ChatMsg.buildChat(msg, fromSession, session))
                .collect(Collectors.toList());
    }
}
