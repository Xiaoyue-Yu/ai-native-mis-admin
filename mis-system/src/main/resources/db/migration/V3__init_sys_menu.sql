-- V3__init_sys_menu.sql
-- 菜单管理表

CREATE TABLE IF NOT EXISTS sys_menu (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_id   BIGINT       NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    menu_name   VARCHAR(50)  NOT NULL COMMENT '菜单名称',
    menu_type   TINYINT      NOT NULL COMMENT '类型 1目录 2菜单 3按钮',
    path        VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    component   VARCHAR(200) DEFAULT '' COMMENT '组件路径',
    perms       VARCHAR(100) DEFAULT '' COMMENT '权限标识',
    icon        VARCHAR(100) DEFAULT '' COMMENT '菜单图标',
    sort_order  INT          NOT NULL DEFAULT 0 COMMENT '排序',
    visible     TINYINT      NOT NULL DEFAULT 1 COMMENT '是否显示 0隐藏 1显示',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    is_cache    TINYINT      NOT NULL DEFAULT 0 COMMENT '是否缓存 0否 1是',
    is_frame    TINYINT      NOT NULL DEFAULT 0 COMMENT '是否外链 0否 1是',
    remark      VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_sys_menu_parent_id (parent_id),
    KEY idx_sys_menu_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 初始菜单数据
INSERT INTO sys_menu (parent_id, menu_name, menu_type, path, component, perms, icon, sort_order, create_time, update_time) VALUES
(0, '系统管理', 1, '/system', NULL, '', 'setting', 1, NOW(), NOW()),
(1, '用户管理', 2, '/system/user', 'system/user/index', 'sys:user:list', 'user', 1, NOW(), NOW()),
(2, '用户查询', 3, '', NULL, 'sys:user:list', '', 1, NOW(), NOW()),
(2, '用户新增', 3, '', NULL, 'sys:user:add', '', 2, NOW(), NOW()),
(2, '用户编辑', 3, '', NULL, 'sys:user:edit', '', 3, NOW(), NOW()),
(2, '用户删除', 3, '', NULL, 'sys:user:delete', '', 4, NOW(), NOW()),
(1, '角色管理', 2, '/system/role', 'system/role/index', 'sys:role:list', 'peoples', 2, NOW(), NOW()),
(1, '菜单管理', 2, '/system/menu', 'system/menu/index', 'sys:menu:list', 'tree-table', 3, NOW(), NOW()),
(1, '部门管理', 2, '/system/dept', 'system/dept/index', 'sys:dept:list', 'tree', 4, NOW(), NOW());
