package com.agentworks.service.impl;

import com.agentworks.common.enums.ProjectStatus;
import com.agentworks.domain.entity.Project;
import com.agentworks.mapper.ProjectMapper;
import com.agentworks.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public Project createProject(Project project) {
        Assert.notNull(project, "project must not be null");
        Assert.notNull(project.getUserId(), "userId must not be null");
        Assert.hasText(project.getName(), "name must not be blank");
        if (project.getStatus() == null) {
            project.setStatus(ProjectStatus.INIT);
        }
        save(project);
        return getById(project.getId());
    }

    @Override
    public List<Project> listByUserId(Long userId) {
        Assert.notNull(userId, "userId must not be null");
        return lambdaQuery()
                .eq(Project::getUserId, userId)
                .orderByDesc(Project::getId)
                .list();
    }
}
