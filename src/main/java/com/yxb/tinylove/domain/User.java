package com.yxb.tinylove.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxb
 * @since 2020-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 人员主键
     */
    @TableId("id")
    private String id;

    /**
     * 人员名称
     */
    @TableField("name")
    private String name;

    /**
     * 人员头像地址
     */
    @TableField("avatar_url")
    private String avatarUrl;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
