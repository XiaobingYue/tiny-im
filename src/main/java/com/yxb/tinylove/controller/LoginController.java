package com.yxb.tinylove.controller;

import com.yxb.tinylove.common.bean.LoginReq;
import com.yxb.tinylove.common.bean.Result;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yxb
 * @since 2022.1.7
 */

@RestController
@RequestMapping("/admin/public")
public class LoginController {

    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    public Result<Session> login(@RequestBody LoginReq loginReq) {
        Session session = userService.login(loginReq);
        return Result.success(session);
    }
}
