package com.yxb.tinylove.controller;

import com.yxb.tinylove.common.bean.ChatMsg;
import com.yxb.tinylove.common.bean.Result;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yxb
 * @since 2022/1/12
 */

@RestController
@RequestMapping("/admin/msg")
public class MsgController {

    @Autowired
    private MsgService msgService;


    @GetMapping("/chatMsg")
    public Result<List<ChatMsg>> queryChatMsgList(Long id, Integer type) {
        if (type == 1) {
            return Result.success(msgService.queryMsgByUserId(id));
        } else {
            Map<Long, List<ChatMsg>> chatMap = msgService.queryMsgByGroupIds(Collections.singletonList(id), SessionUtil.getSession());
            return Result.success(chatMap.get(id));
        }
    }
}
