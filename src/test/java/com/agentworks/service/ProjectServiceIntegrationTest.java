package com.agentworks.service;

import com.agentworks.common.enums.ProjectStatus;
import com.agentworks.domain.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @Test
    void should_create_project_with_default_status() {
        Project project = new Project();
        project.setUserId(1L);
        project.setName("AgentWorks");
        project.setDescription("AI agent platform");
        project.setTechStack("[\"Java\",\"MySQL\"]");

        Project saved = projectService.createProject(project);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStatus()).isEqualTo(ProjectStatus.INIT);
    }

    @Test
    void should_list_projects_by_user_id() {
        Project projectA = new Project();
        projectA.setUserId(101L);
        projectA.setName("Project-A");
        projectService.createProject(projectA);

        Project projectB = new Project();
        projectB.setUserId(101L);
        projectB.setName("Project-B");
        projectService.createProject(projectB);

        Project projectC = new Project();
        projectC.setUserId(202L);
        projectC.setName("Project-C");
        projectService.createProject(projectC);

        List<Project> projects = projectService.listByUserId(101L);

        assertThat(projects).hasSize(2);
        assertThat(projects).extracting(Project::getUserId).containsOnly(101L);
    }
}
