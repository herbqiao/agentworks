package com.agentworks.controller;

import com.agentworks.common.Result;
import com.agentworks.controller.request.AgentCreateRequest;
import com.agentworks.domain.entity.Agent;
import com.agentworks.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agents")
@Tag(name = "智能体管理")
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    @Operation(summary = "在指定项目下添加智能体成员")
    public Result<Agent> createAgent(@Valid @RequestBody AgentCreateRequest request) {
        Agent agent = new Agent();
        agent.setProjectId(request.getProjectId());
        agent.setName(request.getName());
        agent.setRole(request.getRole());
        agent.setSystemPrompt(request.getSystemPrompt());
        agent.setModelConfig(request.getModelConfig());
        return Result.success(agentService.createAgent(agent));
    }
}
