package com.agentworks.domain.entity;

import com.agentworks.common.enums.ExecutionRole;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("aw_execution_log")
@EqualsAndHashCode(callSuper = true)
public class ExecutionLog extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long taskId;
    private Long agentId;
    private ExecutionRole role;
    private String content;
    private Integer tokenCount;
}
