package com.agentworks.service;

import com.agentworks.domain.entity.Agent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AgentService extends IService<Agent> {

    Agent createAgent(Agent agent);

    List<Agent> listByProjectId(Long projectId);
}
