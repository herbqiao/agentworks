package com.agentworks.controller;

import com.agentworks.common.Result;
import com.agentworks.domain.entity.Project;
import com.agentworks.controller.request.ProjectCreateRequest;
import com.agentworks.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
@Tag(name = "项目管理")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "创建项目")
    public Result<Project> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        Project project = new Project();
        project.setUserId(request.getUserId());
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setGitRepoUrl(request.getGitRepoUrl());
        project.setTechStack(request.getTechStack());
        return Result.success(projectService.createProject(project));
    }

    @GetMapping
    @Operation(summary = "获取我的项目列表")
    public Result<List<Project>> listMyProjects(@RequestParam Long userId) {
        return Result.success(projectService.listByUserId(userId));
    }
}
