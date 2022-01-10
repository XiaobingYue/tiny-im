package com.yxb.tinylove.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxb.tinylove.common.util.DateUtil;
import com.yxb.tinylove.dao.MsgMapper;
import com.yxb.tinylove.domain.Msg;
import com.yxb.tinylove.service.MsgService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author yxb
 * @since 2022/1/10
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {
    @Override
    public List<Msg> queryLatestMsgByUserIds(List<Long> idList, Long userId) {
        Date lastOnceDate = DateUtil.getLastOnceDate(new Date(), 7);
        return this.list(Wrappers.<Msg>lambdaQuery()
                .and(wrapper -> wrapper.in(Msg::getUserId, idList).eq(Msg::getToUserId, userId))
                .or(wrapper -> wrapper.eq(Msg::getUserId, userId).in(Msg::getToUserId, idList))
                .ge(Msg::getCreateTime, lastOnceDate)
                .orderByAsc(Msg::getCreateTime));
    }
}
