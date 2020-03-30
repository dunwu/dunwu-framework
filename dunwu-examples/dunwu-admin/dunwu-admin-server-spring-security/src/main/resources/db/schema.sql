SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- 用户信息
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id       INT(20)         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    username VARCHAR(30)     NOT NULL COMMENT '用户名',
    password VARCHAR(60)     NOT NULL COMMENT '密码',
    sex      INT(1) UNSIGNED COMMENT '性别',
    avatar   VARCHAR(100) DEFAULT '' COMMENT '头像',
    email    VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    mobile   VARCHAR(20)  DEFAULT '' COMMENT '手机号',
    status   INT(1) UNSIGNED NOT NULL COMMENT '状态，0 为有效，1 为无效',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username(username),
    KEY key_email(email),
    KEY key_mobile(mobile)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='用户信息'
    ROW_FORMAT = DYNAMIC;

-- 角色信息
DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role (
    id     INT(20)         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    code   VARCHAR(30)     NOT NULL COMMENT '角色编码',
    name   VARCHAR(30)     NOT NULL COMMENT '角色名',
    status INT(1) UNSIGNED NOT NULL COMMENT '状态，0 为有效，1 为无效',
    notes  TEXT COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code(code)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='角色信息'
    ROW_FORMAT = DYNAMIC;

-- 权限信息
DROP TABLE IF EXISTS t_permission;
CREATE TABLE t_permission (
    id         INT(20)         NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    name       VARCHAR(30)     NOT NULL COMMENT '权限名称',
    expression VARCHAR(100)    NOT NULL COMMENT '权限表达式',
    type       VARCHAR(30)     NOT NULL COMMENT '权限类型',
    status     INT(1) UNSIGNED NOT NULL COMMENT '状态，0 为有效，1 为无效',
    notes      TEXT COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_expression(expression)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='权限信息'
    ROW_FORMAT = DYNAMIC;

-- 用户和角色关联信息
DROP TABLE IF EXISTS t_user_role;
CREATE TABLE t_user_role (
    id      INT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id INT(20) NOT NULL COMMENT '用户ID',
    role_id INT(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role(user_id, role_id)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='用户和角色关联信息'
    ROW_FORMAT = DYNAMIC;

-- 角色和权限关联信息
DROP TABLE IF EXISTS t_role_permission;
CREATE TABLE t_role_permission (
    id            INT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    role_id       INT(20) NOT NULL COMMENT '角色ID',
    permission_id INT(20) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_permission(role_id, permission_id)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='角色和权限关联信息'
    ROW_FORMAT = DYNAMIC;

-- 菜单信息
DROP TABLE IF EXISTS t_menu;
CREATE TABLE t_menu (
    id        INT(20)         NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    parent_id INT(20)      DEFAULT 0 COMMENT '父菜单ID',
    url       VARCHAR(200) DEFAULT NULL COMMENT '菜单URL',
    name      VARCHAR(30)     NOT NULL COMMENT '菜单名称',
    perms     VARCHAR(100)    NOT NULL COMMENT '权限表达式',
    type      VARCHAR(30)     NOT NULL COMMENT '菜单类型',
    icon      VARCHAR(50)  DEFAULT NULL COMMENT '菜单图标',
    power     INT(3)       DEFAULT 0 COMMENT '菜单权重',
    status    INT(1) UNSIGNED NOT NULL COMMENT '状态，0 为有效，1 为无效',
    notes     TEXT COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_menu_url(url)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='菜单信息'
    ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS persistent_logins;
CREATE TABLE persistent_logins (
    username  VARCHAR(64) NOT NULL,
    series    VARCHAR(64) NOT NULL,
    token     VARCHAR(64) NOT NULL,
    last_used TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (series)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='持久化登录'
    ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS file;
CREATE TABLE file (
    id           INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    file_name    VARCHAR(128)        NOT NULL COMMENT '实际文件名',
    namespace    VARCHAR(32)  DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    tag          VARCHAR(32)  DEFAULT '' COMMENT '标签。供业务系统使用',
    origin_name  VARCHAR(128)        NOT NULL COMMENT '源文件名',
    size         BIGINT(32) UNSIGNED NOT NULL COMMENT '文件大小',
    extension    VARCHAR(32)         NOT NULL COMMENT '文件扩展名',
    content_type VARCHAR(32)         NOT NULL COMMENT '文件实际类型',
    store_type   VARCHAR(128) DEFAULT '' COMMENT '文件存储服务类型',
    store_url    VARCHAR(128) DEFAULT '' COMMENT '文件存储路径',
    access_url   VARCHAR(160)        NOT NULL COMMENT '文件访问路径',
    update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_file_name(file_name),
    UNIQUE KEY uk_access_url(access_url),
    UNIQUE KEY uk_keys(origin_name, tag, namespace)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='文件信息表';

DROP TABLE IF EXISTS file_content;
CREATE TABLE file_content (
    id        INT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    file_name VARCHAR(128) NOT NULL COMMENT '实际文件名',
    content   BLOB         NOT NULL COMMENT '文件内容',
    PRIMARY KEY (id),
    UNIQUE KEY uk_file_name(file_name)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='文件内容表';

-- 调度信息表
DROP TABLE IF EXISTS scheduler;
CREATE TABLE scheduler (
    id              INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    version         INT(32)                      DEFAULT 0 COMMENT '版本号',
    scheduler_name  VARCHAR(32)                  DEFAULT '' COMMENT '调度器名',
    trigger_group   VARCHAR(32)         NOT NULL COMMENT '触发器组',
    trigger_name    VARCHAR(32)         NOT NULL COMMENT '触发器名称',
    job_group       VARCHAR(32)         NOT NULL COMMENT '任务组',
    job_name        VARCHAR(32)         NOT NULL COMMENT '任务名',
    bean_name       VARCHAR(256)                 DEFAULT '' COMMENT 'Bean名称',
    bean_type       VARCHAR(256)                 DEFAULT '' COMMENT 'Bean类型',
    method_name     VARCHAR(128)                 DEFAULT '' COMMENT '方法名称',
    method_params   VARCHAR(256)                 DEFAULT '' COMMENT '方法参数（JSON 形式）',
    trigger_type    INT(2)                       DEFAULT 0 COMMENT '触发类型',
    invoke_type     INT(2)                       DEFAULT 0 COMMENT '调用类型',
    begin_time      DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '触发开始时间',
    end_time        DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '触发结束时间',
    repeat_interval INT(20)                      DEFAULT 0 COMMENT '重复调度间隔',
    repeat_count    INT(3)                       DEFAULT 0 COMMENT '重复调度次数',
    cron_expression VARCHAR(64)                  DEFAULT '' COMMENT 'CRON 表达式',
    status          INT(1) UNSIGNED     NOT NULL DEFAULT '0' COMMENT '状态',
    note            VARCHAR(100)                 DEFAULT '' COMMENT '备注',
    create_time     DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_trigger_group_name(trigger_group, trigger_name)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='调度信息表';

DROP TABLE IF EXISTS template;
CREATE TABLE template (
    id          INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name        VARCHAR(128)        NOT NULL COMMENT '模板名',
    namespace   VARCHAR(32)                  DEFAULT 'default' COMMENT '命名空间',
    tag         VARCHAR(128)                 DEFAULT '' COMMENT '标签',
    content     TEXT                NOT NULL COMMENT '模板内容',
    metadata    TEXT                NOT NULL COMMENT '模板元数据',
    create_time DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_namespace_name(namespace, name),
    KEY idx_tag(tag)
)
    ENGINE = INNODB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_general_ci COMMENT ='模板表';

