package com.yxb.tinylove.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yxb.tinylove.common.bean.LoginReq;
import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.Md5Util;
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

    private static final Cache<Long, User> USER_CACHE = Caffeine.newBuilder().maximumSize(10000).build();

    @Override
    public Session login(LoginReq loginReq) {
        User user = this.queryByUsername(loginReq.getUsername());
        if (user.getPassword().equals(Md5Util.md5(loginReq.getPassword()))) {
            Session session = SessionUtil.getSession(user);
            SessionUtil.saveSession(session);
            return session;
        } else {
            throw new ServiceException("用户名或密码不正确");
        }
    }

    @Override
    public User queryByUsername(String username) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username).eq(User::getState, 1));
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        return user;
    }

    @Override
    public User queryById(Long userId) {
        return USER_CACHE.get(userId, this::getById);
    }
}
