package com.yxb.tinylove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yxb
 * @since 2022/1/10
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_msg")
public class Msg extends Model<User> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long toUserId;

    private String msg;

    private Integer type;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
