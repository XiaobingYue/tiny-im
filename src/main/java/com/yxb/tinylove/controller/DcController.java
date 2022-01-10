package com.yxb.tinylove.controller;

import com.yxb.tinylove.common.bean.Result;
import com.yxb.tinylove.config.queue.MsgQueueHandler;
import com.yxb.tinylove.dao.UserMapper;
import com.yxb.tinylove.domain.Msg;
import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @author yxb
 * @since 2020/1/6
 */
@RestController
@Slf4j
public class DcController {

    @Autowired
    private MsgQueueHandler msgQueueHandler;

    @GetMapping("/public/put")
    public Result dc(String msg) {
        msgQueueHandler.put(Msg.builder().msg(msg).userId(1L).toUserId(2L).updateTime(new Date()).type(1).build());
        return Result.success();
    }
}
