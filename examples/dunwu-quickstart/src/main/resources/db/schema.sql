DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id bigint(20) NOT NULL COMMENT '主键ID',
  name varchar(30) DEFAULT NULL COMMENT '姓名',
  age int(11) DEFAULT NULL COMMENT '年龄',
  gender int(2) DEFAULT NULL COMMENT '性别,0:MALE, 1:FEMALE',
  salary double DEFAULT NULL COMMENT '收入',
  email varchar(50) DEFAULT NULL COMMENT '邮箱',
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (id)
) COMMENT='用户表';
