package com.agentworks.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectCreateRequest {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotBlank(message = "项目名称不能为空")
    private String name;

    private String description;
    private String gitRepoUrl;
    private String techStack;
}
