package com.yxb.tinylove.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yxb.tinylove.common.bean.LoginReq;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.dao.UserMapper;
import com.yxb.tinylove.exception.ServiceException;
import com.yxb.tinylove.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxb
 * @since 2020-01-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Session login(LoginReq loginReq) {
        User user = this.queryByUsername(loginReq.getUsername());
        Session session = SessionUtil.getSession(user);
        SessionUtil.saveSession(session);
        return session;
    }

    @Override
    public User queryByUsername(String username) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username).eq(User::getState, 1));
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        return user;
    }
}
