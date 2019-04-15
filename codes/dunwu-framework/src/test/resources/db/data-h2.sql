DELETE FROM role;
INSERT INTO role (id, role_name, role_describe)
VALUES (1, '管理员', '为所欲为'),
       (2, '用户', '只能动自己的数据'),
       (3, '访客', '只能看不能动');

INSERT INTO user VALUES
(1,'许丽',39,2,50,'cukpcp@cfguex.qo',3),
(2,'曹鹏',30,2,88,'wnrdpoioab@jkcx.wy',1),
(3,'曾洁',29,2,8,'fyuka@jihwxyonj.zmg',2),
(4,'许勇',59,1,60,'yif@mgsxjdvwz.jfa',1),
(5,'孙杰',58,1,41,'fta@anz.bs',1),
(6,'邱语怡',13,1,82,'za@odqshxhvo.af',3),
(7,'杨静',52,1,26,'hrfsfmw@qfauaught.hog',1),
(8,'陶浩宇',53,2,57,'ubfnamvrk@xib.luw',2),
(9,'邱安云',10,2,23,'cjiw@ffwid.grn',1),
(10,'汤玟',41,2,59,'yezj@maxwietdi.zig',3);
