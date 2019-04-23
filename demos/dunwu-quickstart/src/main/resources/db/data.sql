DELETE FROM user;

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'袁俊义',52,1,8235.428933916193,'zffrq@dsqj.spr',1),(2,'岳慧',40,2,2946.2383223411734,'xrwlhj@ni.db',1),(3,'邢晨',26,2,470.7189897784547,'cscwfwffgg@glkqpfe.ifc',3),(4,'翟雨琴',54,2,5720.216452819107,'jpyxeqo@vegzj.ld',2),(5,'莫俊',27,1,4964.973147028023,'dkywtakkpj@sml.ihl',1),(6,'温静',60,1,9769.334926596353,'elv@xtstnn.sjf',3),(7,'龚杰',60,1,2549.2367816975125,'hispqw@cwdgpg.ga',1),(8,'岳小德',21,2,1610.7962501735417,'ub@txcxm.je',2),(9,'蒋玟',60,1,5554.094196906049,'gybcknnct@tgwib.yxa',3),(10,'陆浩刚',46,1,6696.693684157404,'tcdtpo@wlwagzfk.ax',1);
UNLOCK TABLES;
