-- V4__init_sys_dept.sql
-- 部门管理表

CREATE TABLE IF NOT EXISTS sys_dept (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_id   BIGINT       NOT NULL DEFAULT 0 COMMENT '父部门ID',
    dept_name   VARCHAR(50)  NOT NULL COMMENT '部门名称',
    dept_code   VARCHAR(50)  DEFAULT '' COMMENT '部门编码',
    leader      VARCHAR(50)  DEFAULT '' COMMENT '负责人',
    phone       VARCHAR(20)  DEFAULT '' COMMENT '联系电话',
    email       VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    sort_order  INT          NOT NULL DEFAULT 0 COMMENT '排序',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1启用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by   VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by   VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted     TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    KEY idx_sys_dept_parent_id (parent_id),
    KEY idx_sys_dept_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 初始部门数据
INSERT INTO sys_dept (parent_id, dept_name, dept_code, leader, sort_order, create_time, update_time) VALUES
(0, '总公司', 'HQ', '王总', 1, NOW(), NOW()),
(1, '技术部', 'TECH', '李经理', 1, NOW(), NOW()),
(1, '市场部', 'MKT', '张经理', 2, NOW(), NOW()),
(1, '财务部', 'FIN', '赵经理', 3, NOW(), NOW()),
(2, '前端组', 'FE', '刘主任', 1, NOW(), NOW()),
(2, '后端组', 'BE', '陈主任', 2, NOW(), NOW());
