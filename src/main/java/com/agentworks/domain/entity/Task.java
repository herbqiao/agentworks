package com.agentworks.domain.entity;

import com.agentworks.common.enums.TaskStatus;
import com.agentworks.common.enums.TaskType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("aw_task")
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long projectId;
    private Long agentId;
    private Long parentTaskId;
    private TaskType type;
    private String inputContent;
    private String outputContent;
    private TaskStatus status;
}
