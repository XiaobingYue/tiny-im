package com.yxb.tinylove.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxb.tinylove.domain.Msg;

import java.util.List;

/**
 * @author yxb
 * @since 2022/1/10
 */
public interface MsgService extends IService<Msg> {

    List<Msg> queryLatestMsgByUserIds(List<Long> idList, Long userId);
}
