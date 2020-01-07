package com.yxb.tinylove.service.impl;

import com.yxb.tinylove.domain.User;
import com.yxb.tinylove.dao.UserMapper;
import com.yxb.tinylove.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
