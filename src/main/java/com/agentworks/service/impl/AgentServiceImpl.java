package com.agentworks.service.impl;

import com.agentworks.common.enums.AgentStatus;
import com.agentworks.domain.entity.Agent;
import com.agentworks.mapper.AgentMapper;
import com.agentworks.mapper.ProjectMapper;
import com.agentworks.service.AgentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {

    private final ProjectMapper projectMapper;

    public AgentServiceImpl(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public Agent createAgent(Agent agent) {
        Assert.notNull(agent, "agent must not be null");
        Assert.notNull(agent.getProjectId(), "projectId must not be null");
        Assert.hasText(agent.getName(), "name must not be blank");
        Assert.notNull(agent.getRole(), "role must not be null");
        if (projectMapper.selectById(agent.getProjectId()) == null) {
            throw new IllegalArgumentException("project does not exist");
        }
        if (agent.getStatus() == null) {
            agent.setStatus(AgentStatus.IDLE);
        }
        save(agent);
        return getById(agent.getId());
    }

    @Override
    public List<Agent> listByProjectId(Long projectId) {
        Assert.notNull(projectId, "projectId must not be null");
        return lambdaQuery()
                .eq(Agent::getProjectId, projectId)
                .orderByDesc(Agent::getId)
                .list();
    }
}
