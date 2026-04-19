-- V8__init_relation_tables.sql
-- 关联表

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id      BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (id),
    KEY idx_sys_user_role_user_id (user_id),
    KEY idx_sys_user_role_role_id (role_id),
    UNIQUE KEY uk_sys_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id      BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (id),
    KEY idx_sys_role_menu_role_id (role_id),
    KEY idx_sys_role_menu_menu_id (menu_id),
    UNIQUE KEY uk_sys_role_menu (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 角色部门关联表(数据范围)
CREATE TABLE IF NOT EXISTS sys_role_dept (
    id      BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    PRIMARY KEY (id),
    KEY idx_sys_role_dept_role_id (role_id),
    KEY idx_sys_role_dept_dept_id (dept_id),
    UNIQUE KEY uk_sys_role_dept (role_id, dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门关联表';

-- 初始关联数据: 管理员拥有超级管理员角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 超级管理员角色拥有所有菜单权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE deleted = 0;
