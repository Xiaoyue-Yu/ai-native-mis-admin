-- V1__init_sys_user.sql
-- 用户管理表

CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username    VARCHAR(50)  NOT NULL COMMENT '用户名',
    password    VARCHAR(200) NOT NULL COMMENT '密码(BCrypt加密)',
    nickname    VARCHAR(50)  DEFAULT '' COMMENT '昵称',
    email       VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    phone       VARCHAR(20)  DEFAULT '' COMMENT '手机号',
    avatar      VARCHAR(500) DEFAULT '' COMMENT '头像地址',
    sex         TINYINT      DEFAULT 0 COMMENT '性别 0未知 1男 2女',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    dept_id     BIGINT       DEFAULT NULL COMMENT '部门ID',
    remark      VARCHAR(500) DEFAULT '' COMMENT '备注',
    login_ip    VARCHAR(50)  DEFAULT '' COMMENT '最后登录IP',
    login_time  DATETIME     DEFAULT NULL COMMENT '最后登录时间',
    pwd_update_time DATETIME DEFAULT NULL COMMENT '密码最后更新时间',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_user_username (username),
    KEY idx_sys_user_dept_id (dept_id),
    KEY idx_sys_user_status (status),
    KEY idx_sys_user_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 初始管理员账号
INSERT INTO sys_user (username, password, nickname, status, create_time, update_time)
VALUES ('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '超级管理员', 1, NOW(), NOW());
