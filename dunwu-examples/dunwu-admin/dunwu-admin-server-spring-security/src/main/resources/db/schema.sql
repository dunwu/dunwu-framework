-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`       INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `username` VARCHAR(30)         NOT NULL COMMENT '用户名',
    `password` VARCHAR(32)         NOT NULL COMMENT '密码',
    `name`     VARCHAR(30)                  DEFAULT '' COMMENT '姓名',
    `birthday` DATE                         DEFAULT NULL COMMENT '生日',
    `sex`      INT(1) UNSIGNED COMMENT '性别',
    `avatar`   VARCHAR(100)                 DEFAULT '' COMMENT '头像',
    `email`    VARCHAR(100)                 DEFAULT '' COMMENT '邮箱',
    `mobile`   VARCHAR(20)                  DEFAULT '' COMMENT '手机号',
    `address`  VARCHAR(100)                 DEFAULT '' COMMENT '地址',
    `deleted`  TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username`(`username`),
    UNIQUE KEY `uk_email`(`email`),
    UNIQUE KEY `uk_mobile`(`mobile`),
    KEY `idx_name`(`name`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='用户表';

-- 角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id`     INT(20)     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`   VARCHAR(32) NOT NULL COMMENT '角色名',
    `type`   VARCHAR(32) NOT NULL COMMENT '角色类型',
    `code`   VARCHAR(32) NOT NULL COMMENT '角色code',
    `status` INT(1) UNSIGNED COMMENT '状态',
    `notes`  TEXT COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name`(`name`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='角色表';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
    `id`      INT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` INT(20) NOT NULL COMMENT '用户ID',
    `role_id` INT(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role`(`user_id`, `role_id`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='用户角色表';

-- 权限表
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
    `id`         INT(20)     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `module`     VARCHAR(32) NOT NULL COMMENT '模块',
    `name`       VARCHAR(32) NOT NULL COMMENT '权限名',
    `type`       VARCHAR(32) NOT NULL COMMENT '权限类型',
    `expression` VARCHAR(32) NOT NULL COMMENT '表达式',
    `status`     INT(1) UNSIGNED COMMENT '状态',
    `notes`      TEXT COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name`(`name`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='权限表';

-- 菜单表
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
    `id`         INT(20)     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `parent_id`  INT(20)      DEFAULT NULL COMMENT '父菜单项ID',
    `key`        VARCHAR(32) NOT NULL COMMENT '菜单KEY',
    `group`      VARCHAR(32) NOT NULL COMMENT '菜单组',
    `title`      VARCHAR(32) NOT NULL COMMENT '菜单标题',
    `icon`       VARCHAR(128) DEFAULT NULL COMMENT '菜单图标',
    `url`        VARCHAR(256) DEFAULT NULL COMMENT '菜单URL',
    `type`       VARCHAR(32) NOT NULL COMMENT '菜单类型',
    `power`      INT(3)       DEFAULT 1 COMMENT '菜单权重',
    `expression` VARCHAR(32) NOT NULL COMMENT '表达式',
    `status`     INT(1) UNSIGNED COMMENT '状态',
    `notes`      TEXT COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key`(`key`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='菜单表';

DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
    `id`           INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `file_name`    VARCHAR(128)        NOT NULL COMMENT '实际文件名',
    `namespace`    VARCHAR(32)  DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    `tag`          VARCHAR(32)  DEFAULT '' COMMENT '标签。供业务系统使用',
    `origin_name`  VARCHAR(128)        NOT NULL COMMENT '源文件名',
    `size`         BIGINT(32) UNSIGNED NOT NULL COMMENT '文件大小',
    `extension`    VARCHAR(32)         NOT NULL COMMENT '文件扩展名',
    `content_type` VARCHAR(32)         NOT NULL COMMENT '文件实际类型',
    `store_type`   VARCHAR(128) DEFAULT '' COMMENT '文件存储服务类型',
    `store_url`    VARCHAR(128) DEFAULT '' COMMENT '文件存储路径',
    `access_url`   VARCHAR(160)        NOT NULL COMMENT '文件访问路径',
    `update_time`  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_name`(`file_name`),
    UNIQUE KEY `uk_access_url`(`access_url`),
    UNIQUE KEY `uk_keys`(`origin_name`, `tag`, `namespace`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='文件信息表';

DROP TABLE IF EXISTS `file_content`;
CREATE TABLE `file_content` (
    `id`        INT(20)      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `file_name` VARCHAR(128) NOT NULL COMMENT '实际文件名',
    `content`   BLOB         NOT NULL COMMENT '文件内容',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_file_name`(`file_name`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='文件内容表';

-- 调度信息表
DROP TABLE IF EXISTS `scheduler`;
CREATE TABLE `scheduler` (
    `id`              INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `version`         INT(32)                      DEFAULT 0 COMMENT '版本号',
    `scheduler_name`  VARCHAR(32)                  DEFAULT '' COMMENT '调度器名',
    `trigger_group`   VARCHAR(32)         NOT NULL COMMENT '触发器组',
    `trigger_name`    VARCHAR(32)         NOT NULL COMMENT '触发器名称',
    `job_group`       VARCHAR(32)         NOT NULL COMMENT '任务组',
    `job_name`        VARCHAR(32)         NOT NULL COMMENT '任务名',
    `bean_name`       VARCHAR(256)                 DEFAULT '' COMMENT 'Bean名称',
    `bean_type`       VARCHAR(256)                 DEFAULT '' COMMENT 'Bean类型',
    `method_name`     VARCHAR(128)                 DEFAULT '' COMMENT '方法名称',
    `method_params`   VARCHAR(256)                 DEFAULT '' COMMENT '方法参数（JSON 形式）',
    `trigger_type`    INT(2)                       DEFAULT 0 COMMENT '触发类型',
    `invoke_type`     INT(2)                       DEFAULT 0 COMMENT '调用类型',
    `begin_time`      DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '触发开始时间',
    `end_time`        DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '触发结束时间',
    `repeat_interval` INT(20)                      DEFAULT 0 COMMENT '重复调度间隔',
    `repeat_count`    INT(3)                       DEFAULT 0 COMMENT '重复调度次数',
    `cron_expression` VARCHAR(64)                  DEFAULT '' COMMENT 'CRON 表达式',
    `status`          INT(1) UNSIGNED     NOT NULL DEFAULT '0' COMMENT '状态',
    `note`            VARCHAR(100)                 DEFAULT '' COMMENT '备注',
    `create_time`     DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_trigger_group_name`(`trigger_group`, `trigger_name`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='调度信息表';

DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
    `id`          INT(20)             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(128)        NOT NULL COMMENT '模板名',
    `namespace`   VARCHAR(32)                  DEFAULT 'default' COMMENT '命名空间',
    `tag`         VARCHAR(128)                 DEFAULT '' COMMENT '标签',
    `content`     TEXT                NOT NULL COMMENT '模板内容',
    `metadata`    TEXT                NOT NULL COMMENT '模板元数据',
    `create_time` DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_namespace_name`(`namespace`, `name`),
    KEY `idx_tag`(`tag`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `UTF8MB4`
    COLLATE = `UTF8MB4_0900_AI_CI` COMMENT ='模板表';

