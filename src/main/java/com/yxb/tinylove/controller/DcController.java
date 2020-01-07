package com.yxb.tinylove.controller;

import com.yxb.tinylove.common.bean.Result;
import com.yxb.tinylove.dao.UserMapper;
import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author yxb
 * @since 2020/1/6
 */
@RestController
@Slf4j
public class DcController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/dc")
    public Result<List<User>> dc() {
        List<User> list = userService.list();
        User user = userMapper.queryById("1");
        log.debug(user.getName());
        return Result.success(list);
    }
}
