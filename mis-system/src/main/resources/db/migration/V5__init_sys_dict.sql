-- V5__init_sys_dict.sql
-- 数据字典表

CREATE TABLE IF NOT EXISTS sys_dict_type (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    dict_name   VARCHAR(100) NOT NULL COMMENT '字典名称',
    dict_type   VARCHAR(100) NOT NULL COMMENT '字典类型',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    remark      VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

CREATE TABLE IF NOT EXISTS sys_dict_data (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    dict_type   VARCHAR(100) NOT NULL COMMENT '字典类型',
    dict_label  VARCHAR(100) NOT NULL COMMENT '字典标签',
    dict_value  VARCHAR(100) NOT NULL COMMENT '字典值',
    dict_sort   INT          NOT NULL DEFAULT 0 COMMENT '排序',
    css_class   VARCHAR(100) DEFAULT '' COMMENT '样式属性',
    list_class  VARCHAR(100) DEFAULT '' COMMENT '表格回显样式',
    is_default  TINYINT      NOT NULL DEFAULT 0 COMMENT '是否默认 0否 1是',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    remark      VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_sys_dict_data_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 初始字典数据
INSERT INTO sys_dict_type (dict_name, dict_type, create_time, update_time) VALUES
('用户状态', 'sys_user_status', NOW(), NOW()),
('性别', 'sys_user_sex', NOW(), NOW()),
('菜单类型', 'sys_menu_type', NOW(), NOW()),
('是否状态', 'sys_yes_no', NOW(), NOW());

INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, list_class, is_default, create_time, update_time) VALUES
('sys_user_status', '启用', '1', 1, 'success', 1, NOW(), NOW()),
('sys_user_status', '禁用', '0', 2, 'danger', 0, NOW(), NOW()),
('sys_user_sex', '男', '1', 1, '', 0, NOW(), NOW()),
('sys_user_sex', '女', '2', 2, '', 0, NOW(), NOW()),
('sys_user_sex', '未知', '0', 3, 'info', 1, NOW(), NOW()),
('sys_yes_no', '是', '1', 1, 'success', 0, NOW(), NOW()),
('sys_yes_no', '否', '0', 2, 'danger', 0, NOW(), NOW());
