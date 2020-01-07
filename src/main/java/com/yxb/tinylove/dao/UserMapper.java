package com.yxb.tinylove.dao;

import com.yxb.tinylove.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yxb
 * @since 2020-01-07
 */
public interface UserMapper extends BaseMapper<User> {

    User queryById(@Param("id") String id);

}
