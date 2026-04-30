package com.agentworks.domain.entity;

import com.agentworks.common.enums.AgentRole;
import com.agentworks.common.enums.AgentStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("aw_agent")
@EqualsAndHashCode(callSuper = true)
public class Agent extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long projectId;
    private String name;
    private AgentRole role;
    private String systemPrompt;
    private String modelConfig;
    private AgentStatus status;
}
