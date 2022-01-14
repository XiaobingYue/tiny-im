package com.yxb.tinylove.service;

import com.yxb.tinylove.common.bean.LoginReq;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxb
 * @since 2020-01-07
 */
public interface IUserService extends IService<User> {

    Session login(LoginReq loginReq);

    User queryByUsername(String username);

    User queryById(Long userId);
}
