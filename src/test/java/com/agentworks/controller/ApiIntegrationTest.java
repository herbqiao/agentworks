package com.agentworks.controller;

import com.agentworks.common.enums.ProjectStatus;
import com.agentworks.domain.entity.Project;
import com.agentworks.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;

    @Test
    void should_create_project_via_api() throws Exception {
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "name": "AgentWorks",
                                  "description": "AI agent platform",
                                  "gitRepoUrl": "https://example.com/repo.git",
                                  "techStack": "[\\"Java\\",\\"MySQL\\"]"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("success"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.status").value("INIT"));
    }

    @Test
    void should_list_projects_via_api() throws Exception {
        Project project = new Project();
        project.setUserId(9L);
        project.setName("AgentWorks");
        project.setStatus(ProjectStatus.RUNNING);
        projectService.createProject(project);

        mockMvc.perform(get("/api/projects")
                        .param("userId", "9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].name").value("AgentWorks"))
                .andExpect(jsonPath("$.data[0].status").value("RUNNING"));
    }

    @Test
    void should_add_agent_via_api() throws Exception {
        Project project = new Project();
        project.setUserId(2L);
        project.setName("AgentWorks");
        Project savedProject = projectService.createProject(project);

        mockMvc.perform(post("/api/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "projectId": %d,
                                  "name": "Java后端工程师",
                                  "role": "BACKEND",
                                  "systemPrompt": "负责后端开发",
                                  "modelConfig": "{\\"temperature\\":0.1}"
                                }
                                """.formatted(savedProject.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.projectId").value(savedProject.getId()))
                .andExpect(jsonPath("$.data.status").value("IDLE"));
    }

    @Test
    void should_return_validation_error_when_project_name_blank() throws Exception {
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "name": " "
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("项目名称不能为空"));
    }
}
