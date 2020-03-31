-- 初始化 user
LOCK TABLES user WRITE;

INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('dunwu', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'forbreak@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user1', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user1@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user2', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user2@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user3', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user3@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user4', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user4@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user5', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user5@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user6', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user6@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user7', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user7@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user8', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user8@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user9', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user9@163.com', '15122223333', 0);
INSERT INTO user (username, password, sex, avatar, email, mobile, status)
VALUES ('user10', '$2a$10$QDhiLyZvGS/dwzAIOOGJAeseEZKMEwcI76pyFQYuHJR1avr5K3qgS', 0,
        'http://dunwu.test.upcdn.net/common/logo/zp.png', 'user10@163.com', '15122223333', 0);

UNLOCK TABLES;

-- 初始化 role
LOCK TABLES role WRITE;

INSERT INTO role(code, name, status, notes)
VALUES ('admin', '超级管理员', 0, '拥有系统所有权限');
INSERT INTO role(code, name, status, notes)
VALUES ('user', '注册用户', 0, '拥有系统部分权限');
INSERT INTO role(code, name, status, notes)
VALUES ('guest', '游客', 0, '没有任何权限');

UNLOCK TABLES;

-- 初始化 user_role
LOCK TABLES user_role WRITE;

INSERT INTO user_role(user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role(user_id, role_id)
VALUES (2, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (3, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (4, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (5, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (6, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (7, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (8, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (9, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (10, 2);

UNLOCK TABLES;

-- 初始化 menu
LOCK TABLES menu WRITE;
INSERT INTO menu (id, parent_id, url, name, perms, icon, type, power, status, notes)
VALUES (1, 0, '/system', '系统管理', '', '', '', 0, 0, '');
INSERT INTO menu (id, parent_id, url, name, perms, icon, type, power, status, notes)
VALUES (2, 1, '/system/user', '用户列表', '', '', '', 0, 0, '');
INSERT INTO menu (id, parent_id, url, name, perms, icon, type, power, status, notes)
VALUES (3, 1, '/system/role', '角色列表', '', '', '', 0, 0, '');
INSERT INTO menu (id, parent_id, url, name, perms, icon, type, power, status, notes)
VALUES (4, 1, '/system/menu', '菜单列表', '', '', '', 0, 0, '');
INSERT INTO menu (id, parent_id, url, name, perms, icon, type, power, status, notes)
VALUES (5, 0, '/showcase', '功能示例', '', '', '', 0, 0, '');
INSERT INTO menu (id, parent_id, url, name, perms, icon, type, power, status, notes)
VALUES (6, 5, '/showcase/file', '文件列表', '', '', '', 0, 0, '');
UNLOCK TABLES;

-- 初始化 menu
INSERT INTO permission (id, name, expression, type, status, notes)
VALUES (1, '菜单管理查看', 'menu:select', '', 0, '');
INSERT INTO permission (id, name, expression, type, status, notes)
VALUES (2, '菜单管理查看', 'menu:insert', '', 0, '');
INSERT INTO permission (id, name, expression, type, status, notes)
VALUES (3, '菜单管理查看', 'menu:update', '', 0, '');
INSERT INTO permission (id, name, expression, type, status, notes)
VALUES (4, '菜单管理查看', 'menu:delete', '', 0, '');

UNLOCK TABLES;
