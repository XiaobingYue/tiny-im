package com.yxb.tinylove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 人员主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员名称
     */
    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    @TableField("password")
    private String password;

    @TableField("phone_num")
    private String phoneNum;

    @TableField("gender")
    private Integer gender;
    /**
     * 人员头像地址
     */
    @TableField("avatar_url")
    private String avatarUrl;

    private Integer state;

    private Date createTime;

    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
