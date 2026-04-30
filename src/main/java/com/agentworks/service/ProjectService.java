package com.agentworks.service;

import com.agentworks.domain.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProjectService extends IService<Project> {

    Project createProject(Project project);

    List<Project> listByUserId(Long userId);
}
