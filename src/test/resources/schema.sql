DROP TABLE IF EXISTS aw_execution_log;
DROP TABLE IF EXISTS aw_task;
DROP TABLE IF EXISTS aw_agent;
DROP TABLE IF EXISTS aw_project;
DROP TABLE IF EXISTS aw_user;

CREATE TABLE aw_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(128) NOT NULL,
    avatar VARCHAR(255),
    status VARCHAR(32) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    create_by BIGINT NOT NULL,
    update_by BIGINT NOT NULL,
    is_deleted TINYINT NOT NULL
);

CREATE TABLE aw_project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(512),
    git_repo_url VARCHAR(255),
    status VARCHAR(32) NOT NULL,
    tech_stack VARCHAR(1024),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    create_by BIGINT NOT NULL,
    update_by BIGINT NOT NULL,
    is_deleted TINYINT NOT NULL
);

CREATE TABLE aw_agent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    name VARCHAR(128) NOT NULL,
    role VARCHAR(32) NOT NULL,
    system_prompt CLOB,
    model_config VARCHAR(1024),
    status VARCHAR(32) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    create_by BIGINT NOT NULL,
    update_by BIGINT NOT NULL,
    is_deleted TINYINT NOT NULL
);

CREATE TABLE aw_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    agent_id BIGINT,
    parent_task_id BIGINT,
    type VARCHAR(32) NOT NULL,
    input_content CLOB NOT NULL,
    output_content CLOB,
    status VARCHAR(32) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    create_by BIGINT NOT NULL,
    update_by BIGINT NOT NULL,
    is_deleted TINYINT NOT NULL
);

CREATE TABLE aw_execution_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    agent_id BIGINT,
    role VARCHAR(32) NOT NULL,
    content CLOB NOT NULL,
    token_count INT NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    create_by BIGINT NOT NULL,
    update_by BIGINT NOT NULL,
    is_deleted TINYINT NOT NULL
);
