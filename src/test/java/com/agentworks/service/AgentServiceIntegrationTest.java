package com.agentworks.service;

import com.agentworks.common.enums.AgentRole;
import com.agentworks.common.enums.AgentStatus;
import com.agentworks.domain.entity.Agent;
import com.agentworks.domain.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class AgentServiceIntegrationTest {

    @Autowired
    private AgentService agentService;

    @Autowired
    private ProjectService projectService;

    @Test
    void should_create_agent_with_default_status() {
        Project project = new Project();
        project.setUserId(1L);
        project.setName("AgentWorks");
        Project savedProject = projectService.createProject(project);

        Agent agent = new Agent();
        agent.setProjectId(savedProject.getId());
        agent.setName("Java后端工程师");
        agent.setRole(AgentRole.BACKEND);
        agent.setSystemPrompt("负责后端开发");

        Agent saved = agentService.createAgent(agent);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStatus()).isEqualTo(AgentStatus.IDLE);
    }

    @Test
    void should_reject_agent_creation_when_project_missing() {
        Agent agent = new Agent();
        agent.setProjectId(9999L);
        agent.setName("运维工程师");
        agent.setRole(AgentRole.DEVOPS);

        assertThatThrownBy(() -> agentService.createAgent(agent))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("project does not exist");
    }

    @Test
    void should_list_agents_by_project_id() {
        Project projectA = new Project();
        projectA.setUserId(7L);
        projectA.setName("A");
        Project savedProjectA = projectService.createProject(projectA);

        Project projectB = new Project();
        projectB.setUserId(7L);
        projectB.setName("B");
        Project savedProjectB = projectService.createProject(projectB);

        Agent agentA = new Agent();
        agentA.setProjectId(savedProjectA.getId());
        agentA.setName("PM");
        agentA.setRole(AgentRole.PM);
        agentService.createAgent(agentA);

        Agent agentB = new Agent();
        agentB.setProjectId(savedProjectA.getId());
        agentB.setName("Backend");
        agentB.setRole(AgentRole.BACKEND);
        agentService.createAgent(agentB);

        Agent agentC = new Agent();
        agentC.setProjectId(savedProjectB.getId());
        agentC.setName("Frontend");
        agentC.setRole(AgentRole.FRONTEND);
        agentService.createAgent(agentC);

        List<Agent> agents = agentService.listByProjectId(savedProjectA.getId());

        assertThat(agents).hasSize(2);
        assertThat(agents).extracting(Agent::getProjectId).containsOnly(savedProjectA.getId());
    }
}
