DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `version` INT(32) UNSIGNED DEFAULT 0 COMMENT '版本号',
  `nickname` VARCHAR(32) NOT NULL COMMENT '昵称',
  `name` VARCHAR(32) DEFAULT '' COMMENT '姓名',
  `birthday` DATE DEFAULT NULL COMMENT '生日',
  `sex` INT(1) UNSIGNED COMMENT '性别',
  `avatar` VARCHAR(128) DEFAULT '' COMMENT '头像',
  `email` VARCHAR(100) DEFAULT '' COMMENT '邮箱',
  `mobile` VARCHAR(20) DEFAULT '' COMMENT '手机号',
  `profession` VARCHAR(32) DEFAULT '' COMMENT '职业',
  `province` VARCHAR(10) DEFAULT '' COMMENT '省',
  `city` VARCHAR(10) DEFAULT '' COMMENT '市',
  `county` VARCHAR(10) DEFAULT '' COMMENT '区',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_nickname` (`nickname`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_mobile` (`mobile`),
  KEY `k_name` (`name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='用户信息表';


DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info` (
  `id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `version` INT(32) UNSIGNED DEFAULT 0 COMMENT '版本号',
  `nickname` VARCHAR(32) NOT NULL COMMENT '昵称',
  `password` VARCHAR(32) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) DEFAULT '' COMMENT '邮箱',
  `mobile` VARCHAR(20) DEFAULT '' COMMENT '手机号',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_nickname` (`nickname`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_mobile` (`mobile`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='登录信息表';


DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `file_name` VARCHAR(128) NOT NULL COMMENT '实际文件名',
  `namespace` VARCHAR(32) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
  `tag` VARCHAR(32) DEFAULT '' COMMENT '标签。供业务系统使用',
  `origin_name` VARCHAR(128) NOT NULL COMMENT '源文件名',
  `size` BIGINT(32) UNSIGNED NOT NULL COMMENT '文件大小',
  `extension` VARCHAR(32) NOT NULL COMMENT '文件扩展名',
  `content_type` VARCHAR(32) NOT NULL COMMENT '文件实际类型',
  `store_type` VARCHAR(128) DEFAULT '' COMMENT '文件存储服务类型',
  `store_url` VARCHAR(128) DEFAULT '' COMMENT '文件存储路径',
  `access_url` VARCHAR(160) NOT NULL COMMENT '文件访问路径',
  `time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_file_name` (`file_name`),
  UNIQUE KEY `uk_access_url` (`access_url`),
  UNIQUE KEY `uk_keys` (`origin_name`, `tag`, `namespace`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='文件信息表';

DROP TABLE IF EXISTS `file_content`;
CREATE TABLE `file_content` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `file_name` VARCHAR(128) NOT NULL COMMENT '实际文件名',
  `namespace` VARCHAR(32) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
  `tag` VARCHAR(32) DEFAULT '' COMMENT '标签。供业务系统使用',
  `origin_name` VARCHAR(128) NOT NULL COMMENT '源文件名',
  `store_type` VARCHAR(128) DEFAULT '' COMMENT '文件存储服务类型',
  `store_url` VARCHAR(128) DEFAULT '' COMMENT '文件存储路径',
  `content` BLOB NOT NULL COMMENT '文件内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_file_name` (`file_name`),
  UNIQUE KEY `uk_store_url` (`store_url`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='文件内容表';

-- 调度信息表
DROP TABLE IF EXISTS `scheduler_info`;
CREATE TABLE `scheduler_info` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `version` INT(32) DEFAULT 0 COMMENT '版本号',
  `scheduler_name` VARCHAR(32) DEFAULT '' COMMENT '调度器名',
  `trigger_group` VARCHAR(32) NOT NULL COMMENT '触发器组',
  `trigger_name` VARCHAR(32) NOT NULL COMMENT '触发器名称',
  `job_group` VARCHAR(32) NOT NULL COMMENT '任务组',
  `job_name` VARCHAR(32) NOT NULL COMMENT '任务名',
  `bean_name` VARCHAR(256) DEFAULT '' COMMENT 'Bean名称',
  `bean_type` VARCHAR(256) DEFAULT '' COMMENT 'Bean类型',
  `method_name` VARCHAR(128) DEFAULT '' COMMENT '方法名称',
  `method_params` VARCHAR(256) DEFAULT '' COMMENT '方法参数（JSON 形式）',
  `trigger_type` INT(2) DEFAULT 0 COMMENT '触发类型',
  `invoke_type` INT(2) DEFAULT 0 COMMENT '调用类型',
  `begin_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '触发开始时间',
  `end_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '触发结束时间',
  `repeat_interval` INT(20) DEFAULT 0 COMMENT '重复调度间隔',
  `repeat_count` INT(3) DEFAULT 0 COMMENT '重复调度次数',
  `cron_expression` VARCHAR(64) DEFAULT '' COMMENT 'CRON 表达式',
  `status` INT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态',
  `note` VARCHAR(100) DEFAULT '' COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trigger_group_name` (`trigger_group`, `trigger_name`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='调度信息表';

DROP TABLE IF EXISTS `template_config`;
CREATE TABLE `template_config` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `template_name` VARCHAR(128) NOT NULL COMMENT '模板名',
  `namespace` VARCHAR(32) DEFAULT 'default' COMMENT '命名空间。一般对应业务系统',
  `tag` VARCHAR(32) DEFAULT '' COMMENT '标签。供业务系统使用',
  `content` TEXT NOT NULL COMMENT '模板内容',
  `metadata` TEXT NOT NULL COMMENT '模板元数据',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_namespace_template` (`namespace`, `template_name`),
  KEY `idx_tag` (`tag`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI COMMENT='模板配置表';
