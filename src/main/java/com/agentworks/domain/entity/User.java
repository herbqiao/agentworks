package com.agentworks.domain.entity;

import com.agentworks.common.enums.UserStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("aw_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String passwordHash;
    private String email;
    private String avatar;
    private UserStatus status;
}
