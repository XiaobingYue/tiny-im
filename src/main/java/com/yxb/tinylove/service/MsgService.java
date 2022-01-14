package com.yxb.tinylove.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxb.tinylove.common.bean.ChatMsg;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.domain.Msg;

import java.util.List;
import java.util.Map;

/**
 * @author yxb
 * @since 2022/1/10
 */
public interface MsgService extends IService<Msg> {

    List<Msg> queryMsgByUserIds(List<Long> idList, Long userId);

    Map<Long, List<ChatMsg>> queryMsgByGroupIds(List<Long> groupIds, Session loginSession);

    List<ChatMsg> queryMsgByUserId(Long fromId);
}
