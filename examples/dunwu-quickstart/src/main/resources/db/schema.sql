DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
    `id` VARCHAR(32) NOT NULL COMMENT 'ID',
    `name` VARCHAR(32) NOT NULL COMMENT '姓名',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `sex` INT(1) NOT NULL COMMENT '性别',
    `avatar` VARCHAR(128) DEFAULT '' COMMENT '头像',
    `email` VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    `mobile` VARCHAR(20) DEFAULT '' COMMENT '手机号',
    `profession` VARCHAR(32) DEFAULT '' COMMENT '职业',
    `province` VARCHAR(10) DEFAULT '' COMMENT '省',
    `city` VARCHAR(10) DEFAULT '' COMMENT '市',
    `district` VARCHAR(10) DEFAULT '' COMMENT '区',
    `nickname` VARCHAR(32) DEFAULT '' COMMENT '昵称',
    `deleted` INT(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    `create_user` VARCHAR(32) DEFAULT '' COMMENT '创建者',
    `update_user` VARCHAR(32) DEFAULT '' COMMENT '更新者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    KEY `k_name` (`name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='用户信息表';

DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info` (
    `id` VARCHAR(32) NOT NULL COMMENT '用户ID',
    `loginname` VARCHAR(32) NOT NULL COMMENT '登录名',
    `nickname` VARCHAR(32) NOT NULL COMMENT '昵称',
    `password` VARCHAR(32) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    `mobile` VARCHAR(20) DEFAULT '' COMMENT '手机号',
    `device_type` VARCHAR(32) DEFAULT 'web' COMMENT '设备类型',
    `last_login_ip` VARCHAR(32) NOT NULL COMMENT '上一次登录IP',
    `last_login_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上一次上线时间',
    `last_logout_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上一次离线时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_loginname` (`loginname`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_mobile` (`mobile`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='登录信息表';

DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
    `id` VARCHAR(32) NOT NULL COMMENT 'ID',
    `namespace` VARCHAR(32) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
    `tag` VARCHAR(32) DEFAULT '' COMMENT '标签。供业务系统使用',
    `name` VARCHAR(128) NOT NULL COMMENT '文件名',
    `extension` VARCHAR(32) NOT NULL COMMENT '文件扩展名',
    `store_url` VARCHAR(128) DEFAULT '' COMMENT '文件存储路径',
    `access_url` VARCHAR(160) NOT NULL COMMENT '文件访问路径',
    `size` INT(10) NOT NULL COMMENT '文件大小',
    `deleted` INT(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    `create_user` VARCHAR(32) DEFAULT '' COMMENT '创建者',
    `update_user` VARCHAR(32) DEFAULT '' COMMENT '更新者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_access_url` (`access_url`),
    UNIQUE KEY `uk_full_key` (`name` , `extension` , `namespace` , `tag`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='文件信息表';

DROP TABLE IF EXISTS `file_content`;
CREATE TABLE `file_content` (
    `id` VARCHAR(32) NOT NULL COMMENT 'ID',
    `name` VARCHAR(128) NOT NULL COMMENT '文件名',
    `content` BLOB NOT NULL COMMENT '文件内容',
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='文件内容表';
