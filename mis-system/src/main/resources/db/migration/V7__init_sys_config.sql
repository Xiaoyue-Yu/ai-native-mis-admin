-- V7__init_sys_config.sql
-- 系统配置表

CREATE TABLE IF NOT EXISTS sys_config (
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    config_name  VARCHAR(100) NOT NULL COMMENT '参数名称',
    config_key   VARCHAR(100) NOT NULL COMMENT '参数键名',
    config_value VARCHAR(500) NOT NULL COMMENT '参数键值',
    config_type  TINYINT      NOT NULL DEFAULT 1 COMMENT '系统内置 1是 0否',
    remark       VARCHAR(500) DEFAULT '' COMMENT '备注',
    create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by    VARCHAR(64)  DEFAULT '' COMMENT '创建人',
    update_by    VARCHAR(64)  DEFAULT '' COMMENT '更新人',
    deleted      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_sys_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

-- 初始配置数据
INSERT INTO sys_config (config_name, config_key, config_value, config_type, remark, create_time, update_time) VALUES
('主站标题', 'sys.index.title', 'MIS系统管理平台', 1, '系统首页标题', NOW(), NOW()),
('用户初始密码', 'sys.user.initPassword', '123456', 1, '新用户默认密码', NOW(), NOW()),
('账号自动注册', 'sys.account.register', 'false', 1, '是否开启自动注册', NOW(), NOW()),
('验证码开关', 'sys.captcha.enabled', 'true', 1, '登录是否开启验证码', NOW(), NOW()),
('用户密码最小长度', 'sys.password.minLength', '8', 1, '密码最小长度限制', NOW(), NOW()),
('用户密码最大长度', 'sys.password.maxLength', '20', 1, '密码最大长度限制', NOW(), NOW());
