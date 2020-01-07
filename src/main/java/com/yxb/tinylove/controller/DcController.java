package com.yxb.tinylove.controller;

import com.yxb.tinylove.common.bean.Result;
import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author yxb
 * @since 2020/1/6
 */
@RestController
public class DcController {

    @Autowired
    private IUserService userService;

    @GetMapping("/dc")
    public Result<List<User>> dc() {
        List<User> list = userService.list();
        return Result.success(list);
    }
}
