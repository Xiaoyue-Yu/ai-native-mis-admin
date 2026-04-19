-- V6__init_sys_log.sql
-- 操作日志表

CREATE TABLE IF NOT EXISTS sys_log (
    id           BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    title        VARCHAR(100)  DEFAULT '' COMMENT '操作标题',
    business_type TINYINT      NOT NULL DEFAULT 0 COMMENT '业务类型 0其他 1新增 2修改 3删除',
    method       VARCHAR(200)  DEFAULT '' COMMENT '方法名称',
    request_method VARCHAR(10) DEFAULT '' COMMENT '请求方式',
    operator_name VARCHAR(50)  DEFAULT '' COMMENT '操作人',
    operator_type TINYINT      DEFAULT 0 COMMENT '操作人类型 0其他 1后台用户',
    oper_url     VARCHAR(500)  DEFAULT '' COMMENT '请求URL',
    oper_ip      VARCHAR(50)   DEFAULT '' COMMENT '主机地址',
    oper_param   TEXT          COMMENT '请求参数',
    oper_result  TEXT          COMMENT '返回参数',
    status       TINYINT      NOT NULL DEFAULT 1 COMMENT '操作状态 0异常 1正常',
    error_msg    TEXT          COMMENT '错误消息',
    cost_time    BIGINT        DEFAULT 0 COMMENT '耗时(ms)',
    create_time  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_sys_log_business_type (business_type),
    KEY idx_sys_log_operator (operator_name),
    KEY idx_sys_log_create_time (create_time),
    KEY idx_sys_log_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';
