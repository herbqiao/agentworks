package com.agentworks.domain.entity;

import com.agentworks.common.enums.ProjectStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("aw_project")
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private String name;
    private String description;
    private String gitRepoUrl;
    private ProjectStatus status;
    private String techStack;
}
