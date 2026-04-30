package com.agentworks.controller.request;

import com.agentworks.common.enums.AgentRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgentCreateRequest {

    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @NotBlank(message = "智能体名称不能为空")
    private String name;

    @NotNull(message = "智能体角色不能为空")
    private AgentRole role;

    private String systemPrompt;
    private String modelConfig;
}
