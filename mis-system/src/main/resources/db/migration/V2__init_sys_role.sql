-- V2__init_sys_role.sql
-- 角色管理表

CREATE TABLE IF NOT EXISTS sys_role (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role_name   VARCHAR(50)  NOT NULL COMMENT '角色名称',
    role_key    VARCHAR(50)  NOT NULL COMMENT '角色标识',
    sort_order  INT          NOT NULL DEFAULT 0 COMMENT '排序',
    data_scope  TINYINT      NOT NULL DEFAULT 1 COMMENT '数据范围 1全部 2本部门 3本部门及下级 4仅本人 5自定义',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    remark      VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_role_key (role_key),
    KEY idx_sys_role_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 初始角色数据
INSERT INTO sys_role (role_name, role_key, sort_order, data_scope, status, create_time, update_time) VALUES
('超级管理员', 'admin', 1, 1, 1, NOW(), NOW()),
('普通管理员', 'common', 2, 2, 1, NOW(), NOW()),
('普通用户', 'user', 3, 4, 1, NOW(), NOW());
