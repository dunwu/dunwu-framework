-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @old_character_set_client = @@character_set_client */;
/*!40101 SET @old_character_set_results = @@character_set_results */;
/*!40101 SET @old_collation_connection = @@collation_connection */;
SET NAMES utf8;
/*!40103 SET @old_time_zone = @@time_zone */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @old_unique_checks = @@unique_checks, UNIQUE_CHECKS = 0 */;
/*!40014 SET @old_foreign_key_checks = @@foreign_key_checks, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @old_sql_mode = @@sql_mode, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @old_sql_notes = @@sql_notes, SQL_NOTES = 0 */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS file;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE file (
    id           VARCHAR(32)         NOT NULL COMMENT 'ID',
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
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci COMMENT ='文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES file WRITE;
/*!40000 ALTER TABLE file
    DISABLE KEYS */;
INSERT INTO file
VALUES ('1172113226503041025', 'b2a006884fb74e6b8c35b2884610525d.png', 'image', 'image',
        'taiji.png', 19716, 'png',
        'image/png', '0', '1172113225836146689', 'image/image/taiji.png', '2019-09-12 19:42:25');
/*!40000 ALTER TABLE file
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_content`
--

DROP TABLE IF EXISTS file_content;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE file_content (
    id        VARCHAR(32)  NOT NULL COMMENT 'ID',
    file_name VARCHAR(128) NOT NULL COMMENT '实际文件名',
    content   BLOB         NOT NULL COMMENT '文件内容',
    PRIMARY KEY (id),
    UNIQUE KEY uk_file_name(file_name)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci COMMENT ='文件内容表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_content`
--

LOCK TABLES file_content WRITE;
/*!40000 ALTER TABLE file_content
    DISABLE KEYS */;
INSERT INTO file_content
VALUES ('1172113225836146689', 'b2a006884fb74e6b8c35b2884610525d.png',
        _binary 'ʐNG\r\n\Z\n\0\0\0\rIHDR\0\0\0򜰜0\0򈆜0\0\0ɜ횽\0\0\0sRGB\0ϜΜ\ꜰ\0\0gAMA\0\0я��0\0\0	pHYs\0\0\Ĝ0\0\Á\ȯɤ\0\0LډDATx^\흉ٽ��ץТ\"2՜nՆC\띚۲T\n׌āդ<ԡ\⫀iRѩÍ͑Ȩŋ\$\"\"ưӽ��W\缜刺��g࠿>\蝬}\Ϻࠜ輼\࠷󾻺Οܜ؜۫СŦױӿ񟿙򆧷\޶[󗿼Ź񦛛?��-����vԶ￷巾\ҜȜŜܠ����≜ڜ࠾��\ʫЬߴů5\ຜлڷ࠹\΍̟��\ǝwlל۪̦\ԍ7m\ךkΜ篷ܛ󷿷w\Μn+͜сq\䖖Zʙs\͵ܜr7ܰy��ޜ챜Î\΋^򒦟}��𜠜睐CmN?��ݳ\ϫ_[_񽜉\靛2ͷ񷾜ҜȜɜԔn��¹\ۗߖݰ\\́\Ѭ؜޶\̓ퟂ�ӿ��ߵʇ?˳ͱࠓݜʪ˴[nڥԳ\λ7o{\ܛڏ}\�嘟\߼\ؿ��@\坞\ǺFӦԚ򜈣Ь^𜂗6��\Z\;\坨;��W_}��Ĝr6h��и\ģZ<\�\؜᫯࠾sлܣ񇿸ȩ࠳&񻜟񜎜͖[l\Үeݴ\Ӊ\Υ؝\ַ񘘜ࠜ裶ϜάQSc\泟i��࠶S\܍\⧶\ٵj\ʇ?ퟅ���\ࠜ쾷\ߜ��ߜ欟񊳃��\ܾ.\״Ԝໜ믫.߸\㝦̳\Ϩ?��UкU󜘜Ǿֹ\̝/М❅��ȷ࠻ݛg>󙜍QG\ּ󜛜࠮_+Ĝߜ睎򆟃sc{򊯼𜂜瀃l7w\٠ni^˺ز\ʫثǽ��򨣛s\Ͻ׹\曫ۜ獜睍��Ͼ۹𜇿޼��؜n\㧰O͟UࠏP\䝯՜Ԏ\Ωǝ\׼\﷿𬲵9ӜМ؜ܜ쮯��'Ǧꭷ\ߚε_񊗴ḵF7\ڜņ܁Կ\ܭح:蠶ھ��ϕ\؜׺M࠮lϜٴK��س퟿�cܜ老^\ќݵϷ\�?}7݁𑏺T󯿲/\헉_׷\ҙ\ࠜ裴5��\ټ\ຜЫ̺--ք��5\⿻\|ퟍ�~��Ϗ\͕crf֜ݬ-{\ެ\́{\〺яֿ\'\ﰇ;tߓ\ͮh8񃟺\Ѷǈߜ靦\㳬U\뜽\ǜɯ\\t\҅\͞{\푬ל۪\̌\\󜚫М޾ߜלۺƛnjߘM\䱋_��­74=ࠌ\ԟƚݜ׾\rϹo\ě\ࠜ靓\'ء\ԯK��κ\땜'оϟq\đ\N\і\녜噜޲\˭\ͻ\߱Ϝ矷ۗ7&\\˫\縙��u{\󜝜ྷ}^߆֬��ܜ˜靭��\'Ӷ˜鯰;\߱΃\ғ��Ls��7࠾��zڜ'Ľ\Ʈ��\r��ֻМǶر\餻L\팺=σw|dࠟ\ľoڵ;\ԜࠎǞzjԜڦۥԔku¶ӟ��ŉה&b\״����ن6��ܝٜ羜ݙ��ß����ܥѾѧ,
        ۘ��ߵ\ﳟ󬳙ԓM;
ݾ򕯶ս\Μݜξ󜬶0]64Ǘίصdmֿ\浯iࠄ򆯛ӯtOӾ6ҦƎ*󄲞d5޾ܜ렔oҋ��\⌇𾜧9\ϩ6p\ηlM\̅_ܻ\￻��Ǳԭ_Ŀ࠹Ɂ\࠰Ʒԇ˨򈞐򜛜烟ퟁ�\ѽDڹyܼf򽜭pܸѣࠜƞ^\۷̴τ\̅ݔί����1\ȴЩ\ø=\኏h759Q\ݜ캁ğӏoq꫶̮ۜ��݈M\φ/5۠\併𑾼e}ï1\゗ϵՏ\"񜄜҄\Ҝ߳;\ߜܜڗ\ȟ����пll65}\"ꊵ\Ԏg\0׎˛*ל蜜݋\ի󅗜\\ќ脒M*O񛯾y󹜏~\ʍą2/з\ܜ࠾��\rۙZξAzP{ࠌϵԶĖ\ŜZ]\֜юԗޜ�r.\镎\Üڜܼڗ̍Mȣ\ധ;u\˦6؜㛓3ל歄\��վ_wқӘÛ:ѥ\֕W\햾XP\ќҫ<\0Ĝݜܜۛϣ\ι\赜猰yѣO͵��޿򜛔ܳK\Ŗ]óA\m]tK\ސڽަ\ڜ܂ۖǠࠗ>\ߜ̌ޛלΜ̜ٜ۴̟i?��ܗ͛̩Ϳh]I\ࠜغzJ��zӜ補rڢ̜쥾\\ok^fФ=\覦;5õf\̓Ҿ\"r\ȡ\��-\ۆ\׷5/Π^q\ŕ\ܜǜסͮsË\ゞD\䳜Ѳ\ҜϑФ\杈feܵtǹ\̞ѡxԍ6i~��Ͻ\ٜ炢􍮜ࠜܺpRܩƜ뗭օ;ʜƯ\\ҽ]񶫜\/c򜔌jֻ҆ͯq\ǝ7𺋜ŦבӅ락񉏬ڿڜְǝvX؜睮m򜌜̎\؜٣cډݼѣO\ΠΙW򜑜\ӱߍr󏤣{\ƶ\Ͽ��\߷߷\؜ᛨoݱۣ\ϵϜ̍͜\r࠵\כ\�5k��\팵ܺȜ❫_��Ԯކ\ׂ\ַ߲t\࠲^Pۖܜ㝚\ަ\ܜݬ:-և2\Ʀ5GߚXyҦРk\ڹ\̦Ӝ蝑\ĵڗ؆]]ܜ뭲��O|­amoԫ\\~򵷛׹؜}jǢ7ء]wι񜆛Ȝ߁d\ۜطږ̗4Yܜ䐼\ÇَͰŖ{[ܦ/𷾱̓\Μ˹@ލ9򜔌\ƺn��ùNۍ\ŜZޫf񟺔ȚæݰźܓN͐Μ❇��̖͜Һ̷7d\坨S3\ܜ'\캜rm-\빹\ͫ݇I҉itͮ[ą\႗ΙcϽ׫lOŜޖࠝr\ɥ\흐CjԚ5򜔌\ǶY\狏Ƚ\ߦ\ɦb\ԝƦ\߭؜݆Ŝ/\杄F\q\Zׯ΍\ﰽ\͉Ώ̣O\Πl߬\ؒͪ&\ʆϑԆ\֕D࠶ԧ\r𺜫Ϝ۞\0\ԜܜҶ\ޜ޿ƩNx\�5򜔌\Ƕɺf~BL\杚\ޜr.󜍯~\ӞʪµÜՑn>!؜ߴӇ\Ӛݾ9򜔌\Ƕ͞˺Ʉ��5ζ׫tA{\ѵ0\rͿĩͮޮӾg[oڃB\ܱ\ȜǤlΜ\\ӜƜї\ݜ巵\뱿rl\؜阜\qEw`ĆSx񋟜ݜ౜뭵��拍ʜٜZٺs̭ڜڤΕ\ܨÍ۫Ͻv\띌ߍMûq\ƺм\͵\לd\ޘ߷\޲Ӝ\Ҝn?񄷤c\º9򜔌\Ƕ\ʺн\\oڜΜۚǗ򞝞Xozӛ\ǲ𺛸\㽜Хɼ7\琼YԆߚϡ\ɜ؝{\Ǳ-Ҙë\ʥ؞z\ꀚ\\Эδ0󆜯Φ^Q\驉6#9򜔌\ǶɆc7ڿ\\䯿��/񋩷666kt��\ڧ@]񜃹ߜڻ\ˬ\΋ޏε̡yeP\̪سלꦿ֜r7\ݰ;\Z˵ڮ\˜ʜۏ\ۜ\ﭷaM��u\睔|^˲cLξ5ñ}2ʜ을+\ڂϤŜҽ-,\鏜Z\ۣ\ɅԜϾ󴛳޵6͜Ƛꛇ\\Ӗ\༹򜔌\ǶŞǆn󬓥5\חrN\лĳ܍��䀾\䁠\ꊬ2\׵\ל۰��ī՘̌٠̭ѳ=򎽽;񺏺��\\󜞜鿀j\姽\욜ހi@߱ͧ��ל۴ȴC\ͭϯ\ɑȦ0׏6ɻFѿhį|\嫗��Ι��\oޭל٢bΛ\﮼\��ď\Πlࠬ0\ϲ\ִ׵sy¬\ߜZ޼߷ݯ\0󭷜ߺ\{\쮹\γ\휜࠺Vئ͚\МrH=\ǜלȥ=c󞧂ϾĕޞȜࠜ콘ܶ۰\ί~s\ᝢ~:\ִ\ʫϜ\׏\ޜܜ⸝ݜĿ\؎׈��퓍Rߍ.\蟁\ဿ󜭻\\tΜuݜ~͵\תAP\ĻJ۲ߜڻƹߪܜ쳋сď\Πlࠬ\ժ\֜ݲ\Ɯל؞{\.~\߶\ǜ긷ß��8񤓧ҽ-йU\찌񗿼\混\듞򔶌@\ݯ\\\Ǭ\靟])��O\증ࠨ\ꏚ񽿿��Ǳ\͑Ȧ0׏6Ӛꌭ\꿻Z^^	\˜萜ۜ潜ҽ\砳ۜشۯ®\Ԝܲܜېޡu࠱xAǬ\҉ڲ\җ§?󜙮y񹦜ڴ-7V\坨S3\ܜ'Ym\̱v\퀿��\ХԜ̜禜ԝǐ򃜫 þ󜨌\ι\߹\蝲��1\؜Ţݜ꫸ΜꀷޜﶜϷʜ毨m\Üȫ��\铳\ܜ'Y-\↗\κ\뭶ݺ\❧\˜潑Ϲ\曦֖֜\뾰6\Ȝ큿I6O5󜫞��fքҿ]\ꦔԬחޜ醜򧳯؟ܚƋĜ︫/\꣜䝮w࠻󓟼dNܜ\6\'Μ\ׯ}8O*\ޜឹކ׸n˵šӞ��\ŉĸN4kٮtM4\ގxӍwZ񜧜⨳ݽ��Öz\\I:ǜŜ㽗}1ǍλںƺMҘ~.Ź̼gYͻҹ\Ӱ9\ΦYI^nO򜓜ܥ6ʜ̊+~>!̜䙘\̴ґ\̜ȚkǞܮH!󸏺݇\ߜ麃®\個ŭ؜ӻ\ײ\ę��\Ǳ󑆞0qep\rپࠜȐϔ\٤./]\̭&ߙѦ0׏6ӜڬG4ւz墋.\ZXϜӜ楑Ֆэ\流��y\涪~󢣷ٛv۱Ӹ򤓭N2kQ\賘\Ӝξ󜬉\\^j߶\Z?˹򜔌\Ƕ\ʆV\˜󢯻\ƺ\ܜԷxsÏ\˜祜ԝWƜሜэ\툽Ɨ˱mο\ĸʜ컎Ԝ˚ƋͬF{е؝ę\ǜɫ޸ڜ͑Ȧ0׏6Ӛ\橇��Мｏ򋵤ΜΦ\׍ٜʾԧ>u\尺ɛ\ǲ\ǿ��ܨγ\ʔ\Ӝņkb��xG��<uVd��Ј\Πlࠬd֜ͅ=̑ˑw\Z��Ļ«s]\rϽ\캎]G]M\ۜĘg󘲮$1׆.\ꢃ5<\Đ\أ\付\Ի\ݳ\Ͻ>ܽ\Ȝלȥ]lІZ̭ҋۘG0򜞅òҮڟp\\휵۾��2ݍ\ӴŪÎJ%򜔌\ǶɆ\Z\ݿT󜛟ߺ׷\Ѥ\ف\ݽ߸񊣐\ӜņkٜМ띩S3\̜ױôѴږ𵜗_\ߝĉ|zۛӿwݜ㝗ٴ1׆.\ꢃ51>��ࠪԜӜ麛ןžck\䒮6X\Ĝٚ\坨S3k񜪟+\٨\ǹd;󮻷oPM~ǻݩ=ڇࠜ޻޽\◿ؔ\坨S3\ܜ'Y͎4\Բޔ&؜ꄝ𙘳sR\̴ҁ\Z\ǜԣGߚ\ஜ'Ҝ\枒/Ϙ/wĜ읲|��xįy\̛ۜ\o÷\ޜ坂\䎸Ӱ\"ُ}jc󏽜�ߜƤ͜ѵyۿȽ\ȼ͜Բ]lІ1aݜ}jǾ6}\䣟\ꝆyA]��=ϻ\ԜۡÓV&\霿\ӌܴܘ��شҁ\ZƄ��ռ\'e̙��ڜɑȦ0׏6Ӛ樗XЗٷÜ߷߷mo\rݼלξ\ꐿۻĺӺSͷٵ\˯࠼\趺��ش񜣟��1Ư#ݪIı��\٠\r\㉜蝈ѧf(\ײ��gy֍΁\ԋ\읆rz3Î͜靗\崫\Z\ࠛo޵`b\ٷlΜ\\Ӝņk/}\ꋧ\ߜʜ坘>퟇�󳑣O\Πlࠬd֜͑gߔk_Gל띪L\̣Ԯt��Ӊ\'uoNܜZ?򑏚ܿy򦟛\ԸjӎӁ}jc󤣜֞u\ՑS\ࢲLs𽜯}\ﲟ\Ϝ}jc󤣫eϺ֏ѵ΄uڜ饱[\ލ̜áy\9\߷񜙜ǖ7۠İ\ݘ\Z٤̜r\ְ6՜䶜ӌG��ξ5ñ}ґ\ղG=\洍ѷֵ\"\֏לښ͜؜靇{l��iƜ枴\鮜槆��!\ɑȦ0׏6ɫʪԨ>��&\ǼݜԲ]lІq\蝉ѧf8~����aVƳjt��\֜\��񜚗޶}\Ҿ->��ߜ嗛ەVYƛ}!N8qɥِۜݜ࠾󵯽iǜ로Ծ_��\Ƕ\ʆV\˜��\嫘kuދ\㞛ǜࠦ֜꿼񜇷Ԋ;\ntT\ܰ/\ۜܲھīݷ\߻\ग़[Ф0ɜո򜔴\Z:J\댚iʹŜَS��:kٮ6Xøטw\۩ǩO6ݜr\ޜ靚	w࠻ݯ{qGl\뜽~\̫ͣݜߜYԧվQk\Ɖ4͕/3\ϜМǜٜZ٤̜r\ְρu\坨SԌ\ƚW\\qE؜ĻƶtĿ\郟\랐N\靗��򆟿\ӵіؑßࠜyH{\읟I4\Μʻ\ޜ잣��ߚؾ\ڜɪٓ��߹\足\r\Ԝ흓6zl\⍷޸{½��򅶭a񜈜őLf󺮹:Qɿ\'Ѵ/#󜜢\铳\ܜ'Y-s\Ջy\癃_\̅;࠿󜛜ߎ֌f݆ד\볂ߌ򍦍Ǿ\ї\ߴ]ԍ6ڨkNNϒġf(\糬ۜꫜη߳݉^֗_|q��Gߚؾ\ڜɪ٣^\ʳ.\杅ZF:Zn\䎜\ݡǝ\ڗҿ𻦟󘜫\ݍ\Ͷ\ܬډAξ5ñ.~gd~Ρ΃񯠿֘󳓜̴ҁ\Z\Ƶќ־˜֌ז\ְ?Ȧ݆࠺\꬜ۗ\ըڔQo\䤪Bmǜ믜ޜ尗Ӂ\槟ڞÿp��)&ѱ��/&Ѯ睏\釡lΜ\\Ӝņk\ؠڌď\Π̝rǜrJ󹧢3򍮸ࠈM]N6ɓaњ\'؜윜࠸F{ށ\ݜᝤѯ\ݓGCq-\Ϝ眴\Ի\ؾ��獜}jc󤣫eϺ)՘[3?򑏜엜Դݸõڎќҵ\࠼ڙ߬o��%Y\Ĩмלܮ\ܜלZ[M-_ǰ?󜙜Юϋٜ뻜ȍۜ鳈:ߜğrґYlΜ\\Ӝņk\؜຾ƾc_I\̲\࠾\f՜ͦ\՜蠽\釻յȭ̡R,󲈜M_\ٜۛ˝wݕӜ뽥ݘלꝇ>��߆ξ5ñ}ґ\ղGݔgܱ}7ٵ��˭xg\՜轾𑇞\ڪ̩B��󜒳s؝vIۈѵrI͡\\Ĝ쪜}jc��\ͷןؖԜӶ:ЏǍ>͑d��\䭺}ߜ曫vs؜rck\䒮6XøבĜ︫Ϻ\ל࠾:۶\я;\챶I��\词~Μߛؚ\睦ŗ^8\А\坨S3\ܜ'Y-s\Ջ񬎜}Cࠓ؜࠾󩏞ש}\Ĝr7\�\賑־\һG󖦎𱳜ĆQ\ʚƋ\r\ְρu3\❨S3˷ui\靟��ĭڳXWͱ\޳߷\츜ػ{\Լ\֜Ɖ\ؘćƠGߚؾ\ڜɪ٣^\˜Ȝٚ\坨jti۹J͊ͮ~\Û\ߜн\ڞ{\\Ǩͷq1_r؆Ҧ0׏6Ӛ樗򱎚}\ְ\'\ֵ\᫖nڻµ\ꦵڰ����\ܘܜ睨6\ܬ3\И��R\Ӝņk\؜່Ɯ\��\ۜΤ-Y񷺜Ϙ򋨜যލÜ鏁Z1y\n7ǵY>򜔌\Ƕ\ʆV\˜��>s\iī׸\\늴:ʬ_ķ6Φ񩋚iκ\ڜ݌%ѩ\�\͙\̴ҁ\Z\Ƶخr-}^S.��򖛬\юװ\߹\趍\ߜܘۜ榜蝦ͷ\ߜٜݽ\︯\'ࠜ蝮Lۄہ\ؾ\ڜɪ٣^\˜Ȝٚɣ/\髃ںԬ\ۆ؜陡=ɆJ \Ձ޵6Ϝ熗ξ򨁆\Ѽ֏Ơ󜤒.6Xø\؅f޹\ǜ׈Ǿǟ{\헹͜Z\޷לр\錦[|poݍ̜Ꝝl\ۜ\`3УO\Πlࠬd֜͑/\椬-\睬;ϵb]ʷƍϜܜ﹜ܰ՜Ԏkվ\ʻG󼜔Yzqғď\Πlࠬd֜͑/\椬m:ξ5A؈\ˬ��Ĝrԩ\ᤜܻF\ଡ଼M󒳔W\\qԸ򜔌\Ƕ\ʆV\˜��ǜ׈٦ξ^\օb<6ץ\Z��/\힤Զ],ر3\Ή\ĆP\ƚƋ\r\ְρuљ7ӜٜZ٤̜r\Ɯڒ?񤓻ѱ-\Ԝ靯~��\邻\촜Ѐq\ࠨݍܽ��\࿦\ǉoP+1Ҝքߚؾ\ڜɪ٣^\˜Șۘ��:\ќ銾\酯jW��%쭷q4\Ε\ԣ50\ꝭ;fP\̴ҁ\Z\Ƶخ2󥚘#ę͜퓅]9\ɣdk݂��̘\ߺ\'\㊕ڈʦ𜜬љglΜ\\Ӝņk\؜ໜɜ͓ӯԙι暩Q݆ۜ\Zݗژ��؜ߜǜΜ՜횗ěxŔ\';ckGߚؾ\ڜɪ٣^\˜ȜٜZ٤˽ڌmϜνͭk;߷\ߜǜҾ򗿶ܬӉ;W\rЕʥȚ\Ü}jc󤣫eϺ)ckd\댽��\ZΛλͮǗRƜZգιf*\ԛoo^!im��ȱ\ç`D\࠱̳לꢃ5ͫp]d\泭r􅯜ҿ\頾ɝ/\܀ǻӟ��]tQ̵\ל܂ۜ榡;��ǚ𜖌\Ƕ\ʆV\˜��ǜלȥݜ}jc񺨤\Ȍ֭\ҵMʻݫP\ࠜnڴpoݍ̩~\껰ۜ砜槮T\ʉNξ5ñ}ґ\ղGݔϱ5rǇ\࠱̳\ǘ��\�Ç\ࠜ軷ןL��ͷq1\Α\݂l��\靈7Q=g9\ʜʑȦ0׏6Ӛ樗򱶆.\隇\ࠐĻF+Ӯξů|m\ќ0>\纏ڒ{\쮡̛޺7HW;҄v\û1̧O6Ӛ樗򱶆.\Ꝣ\輜Ϝ괝}̳񬜝\Zޜ'\雱\非zۯ󼳩\Ѻe۷O\䥦lΜ\\Ӝņk\؜ໜɜ͜״󬡜ϚƋ\r\ְ\՜谹\Ϋ\ڱյkt\݉|A𾜙{ܯ󜓜읖Q񯴁Ӛ}jc󤣫eϺ)ck\䓞\䝨S3k纭k��\ኜ�˴\㷜ߺ[sĿ\淿i򐇎L\\r��퓍̖9\ꦼ̭ҋ:9򎳺ҮV/?\Oh\Ȓ\׽ޜǜܜ؜ݰ\Ĝrmҷ\ߜ烸؝~񜥜κ\뮗ݜőȦ0׏6Ӛ樗򱶆.\鴜❨S3\ܗpӶ\ҵݩ̸\⋝ҷ\ߜ睚\ٜ坂7࠹\ζf!Z#	q򲜎&79򜔌\Ƕ\ʆV\˜��ǜלȥ=\䝨;֦=\ݚ2ҵΜﴜѕ\ᤜݻf\୾\͍ϽC򟜜s1\䝨S3\ܜ'Y-s\Ջ񘛣شa8򜔌\ǲ��\ŗأ\ܶ򹜧ݜӕ\闉Ҿ\һۯ\Ӟ��ݳVkq\ȶcߜ\\Ӝņk\؜ໜɜͧZɣO\Πlࠬݵ\י\혷ξ\ʩȴܜܨͷٶ5𕗝\֬߹\筃sÜn߈ܼ��֑Ȧ0׏6Ӛ樗򱶆.\饶\Ӊ\'֣\ݶ򜛜߶ז:\蠶\қoöo%ˁ\杫£ږ٦\歜؜睖blΜ\\Ӝņk\؜ໜɜͧZ��Ȧ0׏t\ܳY\ܜ黣\݉5}oލƜ颈\ܭ؜ݲ\ԗ6Rs\睨S3\ܜ'Y-s\Ջ񘛣شۧΘ֜Բ]ࠜʜۆ\ጟ\ئēO<ҍ��{֩MϜò}򔶱=\ꓳ\ܜ'Y-s\Ջ񘛣شOW}jc󤡷\޷oǾmt\큳\㝌3\Ь޵6\Ԝn{򜮜䝬ԜЮ6\٠ĪĤO\Πlࠬd֜͑/\椬Μ\\Ӌ\\򜉑Ȧ0׏6󾬭ÿ��\կ~u`A��Ц\緜Ĝ퀱ͷ޺޸sփ;9򜔌\Ƕ\ʆV\˜��ǜלȥބڏڧ>��2i��Wx\ɥ״ʜߺۭʁu?��֛pѣO\Πlࠬd֜͑/\椬Μ\\Ӌ\\򳎾5ñ}Ӂ��|܌\ۆ��͗Ьͷڜۜ࠾��\ß��fN߬\"Z#شҁ\Z\Ƶخ2󙖜杭\Ǽ٤̜r\ְρuљМڣ񈶙ԍϜĞ]\ԟ\蝞[Ըcֱ7\ѯܜ쳦��]��}jc󤣫eϺ)ck\䓞\⚸\坨S3\ܜ'YͰ񜆛؋˭t󿎸p{[:\ǆ׹ࠜr��ҹ\∣ۜͶ۬ܜ\ߠѣO\Πlࠬd֜͑/\椬Μ\\Ӌ\\󙓣O\ΠlࠬdՂϖՂ\Zܜƽķ&󼜐��Oȓ\ꘋM\u\ۼҊݜٜ'Gߚؾ\ڜɪ٣^\˜ȜٜZ٤׸\燍}jc󤜃wW]\❏��ί򥧚\溻\ꗾ��Îjv\ܭئ\Ĝr7\\f\"ڜޜݲɳΏі\ꢃ5ͫp]d\泭\殅ٵ\Բ]lІq\r΋\ͼǜҷ9\ꝣZhݍߕހŋ7\߸c󜃟��\ݳۏ}\鄜κPEܜ۪޷࠽\[\࠺\ל摃i[\증\ۃΆߜ잜ﻨۗ:9򜔌\Ƕ\ʆV\˜��ǜלȥ=ӾQӭp\˭شɾ\ҜȜԔݺݬٜʜ束|󜍜훗\ػ{񜋟\ޞcδռڜЙs\"Pc\ͦϜ}jc󤣫eϺ)ck\䓞\⛏͏clΜ\\Ӝņk\؜ໜɂۜ믜܎ו��tɾ\ҜȜԜݜ东޶[s\߹\絯{\ޜ욿ѴY\̜Μ̎ߣ/W~ck\䒮6Xø\؅f>\Ԋy󜤜铳\ܜ'Y-s\Ջ񘛣ش\ؼjƎFԭ+\̴;~ǿƿ񜉏6/xK\ϴ򲋜}jc󤣫eϺ)ck\䓞\⛏->Μ}jc󤣫eϺ)ck\䓞ba\ԍ7m\蒻ڧ&{ܟ󦸍ۮ𿜇{t3\ŵ6ƗߵQ\˞}jc󤣫eϺ)ck\䓞\⛏-ˡGߚؾ\ڜɪ٣^\ʻ~\䣟\ڎʶtN\"��\ؚܼZЦڜǾ#񜈀ҥ󜑜୧lΜ\\Ӝņk\؜ໜɜͧ\ی򜤜铳\ܜ'Y-s\Ջ񘛣ش\Կ\ݜ㟜؎ԶΜΣ\㵇ַ񵜝\莧k\ꂬڜΑȦ0fޜڜɪ٣^\˜ȜٜZ٤׸\泟kۏyrI͡\\Ĝ윢35��k\بܧՏu\϶){Ω-��ǹ\ྜݛ-ظڰ\퓳\ܜ'Y-s\Ջ񘛣ش\ؼjцZ̭ҋۜ٠\r\䝚\\ڹL̜宇ξ5\ıλ\��\軲˓����\Ü߆k\߄ؿ\ů~ѽ\ڜ䝮c߱5rI͡\\Ĝ윢3ּࠩ\՜�9kٮ6Xø\؅f>\Ԫy\륎>5ñ}ґ\ղG\ޜ���?ۋ\ꀜڜ߆gln΁񜋟6\кֳ\۱ޯ\δ򜤜铳\ܜ'Y-s\Ջ񘛣ش\ؼjцɜZ\읈%]lІq\r΋\ͼƉ}\rȶ\ҹ��ƓԷٛ\ȐMϜࠜ⺋L\r΅Aξ5ñ}ґ\ղGݔϱ5rI/qͧk\"ÜϚrI͡\\Ĝ윢3ࠩռ��﹜י̺G\ێ߶\ҵTՓ\໗ܜۄ\ࠜ䣱ͣn󻤜'=ɝ_ϜƜ䝚=.$s��퓍̖9\ꦼ̭ҋzʫ>֘ӡ\癏.\ꢃ5ͫp]d\泭ַ>G��ϫ\袜ܜ臞x`׼𜠃ۄͯ��Μſ��˭ךԋ\ű̳לꢃ5ͫp]d\泭ַN\δq��퓍̖9\ꦼ̭ҋzʫ>֘a=r��ݱF;\܆\؉^ܜ莙ޜΜ߼\婿\ŷ\޷\࠶н֞��\㆕ˣ͵L͡\\Ĝ윢3ࠩռuӽ\ꍙK-\Ԝņk\؜ໜɜͧZ-oݜi\坨S3\ܜ'\믌Ҷ\ѿ򑏶\Ż\챃ܜ鮶ƛ��֖\̬ګ.-s��퓍̖9\ꦼ̭ҋzʫ>֘\죜ϜZ٤̜r\ְρuљϴZ\޺ڜ䔜񖜨mğx\ᆜ݃}úoӳ\࠲\؍\εy��퓍̖9\ꦼ̭ҋzʫ>֘aޜőȦ0׏6Ӛ樗򱶆.\饮񜔴箽��^֍ϛ˺@wӔ��د󜛜޵\Ӽؑď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚묉}jc󤣫eϺ)ck\䓞\⛏-\ל靬F\Ӝޞ+\䡰࠻\ޜڭo��c$\鵜ѻ\࠹\Ϝ\ꜜիt´`JLDͱ|>ֱkʥۜ٠\r\䝚\\ڹL̥͓3ߵ͙K-\Ԝņk\؜ໜɜͧZ-oݜ񆢏ݵ\؜\sj׎ΜѵQ7\ۨĜ\\pAܜ�����J+ʹ0ȄɜȜؚɉȱׯd1\몥ۜ٠\r\䝚\\ڹL̥͓3?֐ď\Πlࠬd֜͑/\椬Μ\\Ӌ\\򵗚[\ܜ貟ͥݰ\Sjoә��罜hr��퓍̖9\ꦼ̭ҋzʫ>֘a\ݵ͜ʑȦ0׏6Ӛ樗򱶆.\饮񜔢ΰ\�j֢\ҵȜ��ߩշӜ鸜Ϸ߱ζsޫqգk\䒮6Xø\؅f>\Ԫy\읤\͜в\坨S3\ܜ'Y-s\Ջ񘛣ش\ؼjѦºkX9򜆾󜬳5[񛽃��\圧?ڜֺ\ࠜ鋚wn\颋ˮ۪;fq\ຜ鴩ͭӍ̖9\ꦼ̭ҋzʫ>֘a\ݵD\͜Ȝǐ~\쩓ֹͬ\ꦼ̭ҋzʫ>֘c\颠׶ͮߙڸJj\�ߜҳ󹜏ެԜκ��z͹jٮ6Xø\؅f>\Ԫy\읤̏ϋСνjc󤣫eϺ)ck\䓞⚯ނ࠸\Ŝ'Ǧ,6ݵǜ㝅ϻLo4]cO��r\ʑȦ0׏6Ӛ樗򱶆.\饮񜔢Mŵ\؄Ě𸂏a=}ґ\ղGݔϱ5rI/qͧkĻ\ݜᏜ�׮ϻ\�X[̵է?��\읭v\Ә	/}\ꌜܱ񠆎>5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&\"\ֱ׆.\ꢃ5ͫp]d\泭ַN\ϼټϵ��թڻܜr4򫞵ˮs􍯺S˭u\ԜМZۮԐܜ쌑ď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉ȵ\坨S3\ܜ'Y-s\Ջ񘛳󶉶��ϵ\֜ܿ\݃\О��ڣO\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.֋ď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩜅\Z݁Ȝל\鹭?HH\靭vӵ\շ\ڤԮ rΜμ9򜔌\Ƕ\ʆV\˜��ǜלȥޜĵࠚ̉ќ\睢cӖZƋ\r\ְρuљǦ\֨\ޜθπҔϟ\ǯ>󯸜Ûʃ\́׏\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.֓ď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩜅\Z\\ɬץ\Z}󜭷of?\晣ǔƥjp��߷޷\ᬰ֐ď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.󜉑Ȧ0׏6Ӛ樗򱜖Uŵǹ\Ӛǜя?��ۜݜн\ﳧԥe ��)̓û9򜔌\Ƕ\ʆV\˜��ǜלȥޜĵࠚ̉ќ\棜Мǳf-֌͡\\Ĝ윢3/\최Ԏ\-\Ԝ頿򜗜ۇir\췀K\ҜݨǜrJ;ӾcjΩ\ȜٜZ٤̜r\ְρuљϴZ\޺9󳜇99򜔌\Ƕ\ʆV\˜��ǜלȥޜĵࠚ̉ќ\ݦܫs\ٲͮBx\˜띅_\ݜ뱁̜ڴYo۩Ɓs˅8\Ȟ#򜔌\Ƕ\ʆV\˜��ǜלȥޜĵࠚ̉ќ\沿\䝨S3\ܜ'Y-s\Ջ񘛣ش\ؼj\\๜Ȝ窫Ϟۜʿo\˴ۜ슏xB��\宜_Jͮ;\䑃ہ\䀺׆.\ꢃ5ͫp]d\泭ַN\ϼ\�|<\읭S3\ܜ'Y-s\Ջ񘛣ش\ؼjѦºk\"ٜ̼q��׳7��ͮŮ͜ܧ\ٲ\˭ǲKgͮoĳ\Ҽ1׆.\ꢃ5ͫp]d\泭ַN\ϼ\�|>\ƾ5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹\̷\읢ϙߦktݲW􃿿󜟯٦ع򜗜ڀr\\\퓳\ܜ'Y-s\Ջ񘛣ش\ؼjѦºk\"ٜ̼ڦξ5ñ}ґ\ղGݔϱ5rI/qͧ&\鳜㝪\֘ЩĜ믥��sKƜҿퟟ�+ϼr׶��>5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹̏\ZuƬŖ\ꢃ5ͫp]d\泭ַN\ϼƞȜ⼜ݜ寜ͭ؜ܲ󵺬؝v\韘ج\ⲟ̜ͼ0⣎:j`𸀑ď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.󫜚9򜔌\Ƕ\ʆV\˜��ǜלȥޜŜҜ׼߹\ͧvs9��ύ\0Ox]*I{W̹p��۫Мޜrd\ל鶝ۏyrI͡\\Ĝ윢3ࠩռur\秏ˈ\МĜșȦ0׏6Ӛ樗򱶆.\饮񜔢Mŵ\؄0ǹʭoYגNw\ǜʴ|؎u󓞾򜩝nљ?\ا?󜙥ٱ5rI͡\\Ĝ윢3ࠩռur\秏̠޽>ιkٮ6Xø\؅f>\Ԫy\읤\͜Ϟ\Ờ؜ǜٜZ٤̜r\ֈ:��\Ӭҗ\ӵڋ^��k��ǜՖĹ󜪢zՁǏ\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.󋚴\Ǻkٮ6Xø\؅f>\Ԫy\읤\͜Ϟg\靧uͪξ񏿼{5ܯ��톛nHМ睪\پ̓Kۜ٠\r\䝚\\ڹL̥͓3?{\\\벎>5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹󚛟v\۩\ֽhŝqjv%\蝍w=\ٻ\࠹Ω\흢1ʜ긍@r��퓍̖9\ꦼ̭ҋzʫ>֘a\ݵ\ͥ~M\˸򜔌\Ƕ\ʆV\˜��ǜלȥޜĵ_GӾ\ꐿ\띦rf\յ۬؜޶\런\ԍ7ޒࠩ\ԜǿSvɉȱׯІ񜈑Ȧ0׏6Ӛ樗򱶆.\饮񜔢Mŵ\؄0ع\Ī\㝨S3\ܜ'Y-s\՟퟉�\Μⲕ\ب֋.ڨ{BҜݎX󶘴ܹ\朜ۭ\"\־̓Kۜ٠\r\䝚\\ڹL̥͓3?{\\D̳Oξ5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹󟼜☜ܹ<��ƇϿ��k\r\ݟ˥\ٿ��ks!\"\㹜ɜԲξ5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹̟N\̴q��퓍̖9\ꦼ̭ѩ\ࠜ࿷k󜐨ٴͮ\ӹ\뮄]߇d˥\ҷ\޷\࠶\ᝆm\ԝ٬ֹkٮ\ל貥ݜ欜솜흇𳻮Љߖ#ęͭӍ̖9\ꦼ̭ҋzʫ>֘a\ݵ\ͥ����Y<µ򯿻ہkޟ��\ʵ9��\Ɂ$Gߚؾ\ڜɪ٣^\˜ȜٜZY\ѡѺϞvĞ}��η߳ޜ漜࠽n󅯼¹\ǻ\ߜҾź\릿]J}jc󤣫eϺ)ck\䓞\⛏-\ׄXwMsڟ\ԔӾv󴽸Ćt\֜n݀𠇽ɜΜֶ\菊韅\Ҥȏξ5ñ}ґ\ղG=\̫m쵳\؜͜ÚkΜ՜{\천Ԯ͕i9��'?iύ؜ǜZkįiξ5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹̟N\ܱ\ǝǖ\ᴶčw\ٷ࠹\ζ\ɽپ󙜏Le&\ܴĄ7JLq��퓍̖9\ꦼ͜ڜݜޤ\ࠫϽ\ڝ;Җ՚޻[O\ȗ߼ӣO\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`ξ׋ֳΆ5SܱīB𖄏}\듧2ԭ\ǝw\\;pL󎭑Kۜ٠\r\䝚\\ڹLsԜ띺O~򓜭\賜䝚5˵Z\ק޵Vz{߾}\Ȝלȥ]lІq\r΋\ͼǜֲ\׉ٟ=.\"̳Lξ5ñ}-o\ޕiܑƻ񃟸@ˮǜ́g;\㽹[#شҁ\Z\Ƶخ2󜔴\ߵjͶNdYcgڬꌿ��+ʹ\ҲЩࠜkٮ6Xø\؅f>\Ԫy\읤\͜ϞQʳFξ5ñ}񻜟��43ܱ\Z]Ƴܜ?󷯶��*s܍\㰶mڜ贰\ǁ֖\ꢃ5ͫp]d\洍ܳڹ6Ƌ\r\궸Eh\ŷaΜń\Ԙ\˷XS\˜ȜٜZ٤̜r\ְρuљϴZ\޺پzз\r\֜賷࠻\ޝܜԜÜ؜ޝԖ(ͱ\ǜҼ~Ň?ퟅ�z3ҧf0׏6Ӛ樗򱶶؜صӗ��՜blΜ\\Ӝņk\؜ໜɜͧZ-oݜ񜙜䜢ju\לʑȦ(Vϩȝ\׃7򟾲؜睞��\ⰿ񋻗݋ɶ\Ƿ\ʍ\ӒGߚؾ\ڜɪ٣^\ʳ~\郟:\蜒ۜX|_񽘧f0׏6Ӛ樗򱶆.\饮񜔢Mŵ\؄0عkιfۃs\ߜ賞<R@\ΜȦΜҵ\דќ}jc󤣫eϺ)ck_񜊗Ǟ̛\ִ^\и\Ƴ׹��شҁ\Z\Ƶخ2󙖜̛\'g~��ꝪܣGߚC\ئcԪt5ϩ��=М֜'\᷺ܜ\��hفͱ5rI͡\\Ĝ윢3OM��i̾񉏌\t\؜ŜٜZ٤̜r\ְρuљϴZ޺\ȝ=\礒Ȝ�5ږ����ɿ\䀾c*;\ߜ熗޻\߁}jc󤣫eϺ)\М؋^\硏ȩ̴٨ۃ𽰽\ŜٜZ٤̜r\ְρuљϴZ\޺9󳜇E̤\Ϲӽ~ڜ뜰(\П\Ϝ㚘͜Z��nk/5Ń\뾆߾��\Ȝ߼Hgݱƴmߚؾ\ڜɪ٣^\˜Ș۫Ә\Ꝛ��ٜ輣\ϚƋ\r\ְρuљϴZ\޺9󳜇ESپ��@󗿾ujiܾ<ǩ��ی\ྜ໇ެ~/NӱÃ\\Z٤̜r\ְρuљ\؜ÜࠬԍǾVߍ񵜄{\첇󺼯̭ҋۜ٠\r\䝚\\ڹL̥͓3?{\\\Ŭ\띬󱲵ݏ=��1͜Ϊή\Ӌnс\휒ܑũ����\읈#Ϝ񠓋ۜ٠\r\䝚\\ڹk;\ｳ󹜦\Լ۪޷r��퓍̖9\ꦼ̭ҋzʫ>֘a\ݵ\ͭӜɦԪn\۲5ۜ흋_��¯ߜ澣󡯽\웗x\Ɯpjٮ6Xø\؅fߜہ<��\М߾��^\�1g-\Ԝņk\؜ໜɜͧZ-oݜ񜙜䜢Ʃ+ҺJ=&[ޞZ\\鯗ݡA��䌩Ŷmq\逓\r&ױ6𥝬Іq\r΋\̼5޷񶃏=t\ᾜٜ輢\ϚƋ\r\ְρuљϴZ\޺9󳜇ES}Ӷ}͢זܜҵ&ι暁[-\ﾷ\ߣ\ޜ鿯ǝvX:ٜ睨S3\ܜ'Y-s\Ջy\ǯzӛ\ۜϰ\؜ǜ婜؜廰\ݘ\Z٤̜r\ְρuљϴZ\޺9󳜇ES؜Ƣ��^\٭Ĝ橩Ѭ\ҥ:יo򒋮i��\ρ\Ӝ혜㝠ڣO\Πlࠬd֜͑/\椼\◿x\딌Щ׽\�[#شҁ\Z\Ƶخ2󙖜̛\'g~��\뢬ݱίݜZ��\毴7ӾgսڵFՉ��˭ʈq2\ၜӷlΜ\\Ӝņk\؜ໜɜ͛[o޵ƾ\Ɯڗи\㍷x~/1׆.\ꢃ5ͫp]d\泭ַN\ϼ\�\ĔʽX\ة\ՅQFe#\ڴة\"\靯5މީҿ\Ḝ͸5۞̯Pۜ}jc󤣫eϺ)\٣˟L󡜚+\ޥ\׋װ󜢻ӟ15\ǲ\r\פy\לƜٜZ٤̜r\ְρuљ\ϴ򜚣]\"\ꝳΙ\Z��و6\ޜڀ޼\ӛ\׵\˾򓟎e\ǜд��}jc󤣫eϺ)cͯ��/\䝲.��Gߚؾ\ڜɪ٣^\˜ȜٜZ٤׸\瓋5\םC\軜�Ϝ췺MGk񸸜ǿ��۞��\ꌨ\ݜؒszߊ\ƜZ\蹉ȹ0��\ƜƷ\杘\Z٤̜r\ְρuљϴZ\޺9󳜇E̤\ϳ\М̕gȝ��؏rَݑՉЛ.󍋧s��\܂ȱjpݰ\ɑȦ0׏6Ӛ樗򎜙\靚\Ŝܧ࠽\ۜϲ؜伜߹\箳��铳\ܜ'Y-s\Ջ񘛣ش\ؼjѦºk-KA9󜌳\襙μ\ҥzúr\˟L\\z\馓مոɷޜཇ=.q��퓍̖9ꥼࠀ=A\ο��x\Ȝ٨Mksn\̜؜ΞԀΜń\Ԙ\˷XԜ晗q��퓍̖9\ꦼ̭=��m\蜧1*yĻ\Μ޴\ԍ\Μ۫М޽˵\蹜ڜכo=\عԞԼѶ\ⵥ߹Ѓ<p\ᶹ[#شҁ\Z\Ƶخ2󙖜̛\'g~��\띬\넱\+۳5ۜ삋.h\'±\࠾󵺸ٮ\駱ď\Π\ͺӑ\ղGݔϱ5mڜ\v\ڥ\k\Μ殺ӵêƧ࠾z��O\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉\Ц󗿺\ֶ\흧\˜浜Ҝź&\۟X\rŻƸ\"̓Ž\ʂ\\8~��ͭӍ̖9\ꦼ̵ךԜ쯙ϯǸ4\沆̿F񺾭[#شҁ\Z\Ƶخ2󙖜̛\'g~��\꽲8\n\ݜ蝊\歑ޖչ\쏷\۪˵?󜙏ںŶ]0\Ã\΅5\ȑ˜}jc󤣫eϺ��ࠌֈjc\흸W࠺U{ȑ͉\䒞\\\Ӝņk\؜ໜɜͧZ-oݜ񜙜㴮\Ӝźь?i\裿\nm\ϾĜԾ󛜟\\Pr󜍷ܜࠜ캿۱Ŝ'5Ͻ\Ȝלȥ]lІq\r΋̼I\ļ}\ﳟ����6\壩\'\鿬:\ࠜ\\̴s񹹼\\\䷔\鲯ߚؾ\ڜɪ٣^\˜ȜٜZ٤׸\挜ZWa��߷֞ܬ\ҩ��ڳ\쏭񜊗޲\̅ߏ\ԫ򜚜(\坨S3\ܜ'Y-s\Ջ񘛣j\\ݣ\뙣ϩOw􁏾\Ѯ\Ɯ關ѺNgG\ꀛ7w|q��퓍̖9\ꦼ̭ҋzʫ>֘a\ݵ\Ϝ극~\ǎ؜ιēξ򜨁|\⊜'ֺۭ!ۜ\ǜ؜ֹ\מ{qa1׆.\ꢃ5ͫp]d\泭շ��\ؚ}\ְg-\Ԝņk\؜ໜɜͧZ-oݜ񜙜䜢Ʃ\̢Ԝ睚A4г~^\ל魤޵\姜ҁۑ󋡜࠻\߷��׽ǎξ5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹򚧏{\ۜԜ罾\МܜZ\ݟLg\嬱\Ŗ\퇖��ˮjsezo\ݼ\杂!Gߚؾ\ڜɪ٣^\˜ȜٜZ٤׸\瓋5\ם\\珧9fα5rI͡\\Ĝ윢3oM7\ݜб&s޳-ڼn̜ۜՔ\؞{mw75\؃򐮊ǜʛ󥺭^𜐋%\ǜלȥ]lІq\r΋\ͼǜֲ\׉ٟ=.bغ,\督\�u\ֲ]lІq\r΋\ͼǜֲ\׉þq򜍎\睛\澜ҥj,݉u\֕W\퇁k񿿹\Г؜ܼ5ܞȗߜ䃢lΜ\\Ӝņk\؜ໜɜͧZ-oݜ񜙜䜢Ʃ\̜㝚Gߚؾ\ڜɪ٣^\˜ȜٜZ٤ܵM|\ࠜМ\󍘏ֺ¬\ҭ:YB\ᝍ𿾜逜ﲵ߄FmtpÅ\ĸ[%]lІq\r΋\ͼǜֲ\׉ٟ=.bغ,ƞ5q��퓍̖9\ꦼ̭ҋ۶Ǿ𜃟\\ۼ\Ϋڂ6ۜ탟򜐀۴��࠷°\읩]/ .,\ǜ\\Բ]lІq\r΋\ͼǜֲ\׉ٟ=.bغ,ǜϜ粟󜤒.6Xø\؅f>\Ԋy\Μ�bڻ\㮺\ࠜ靑\դ\Z\rȇ굯}mۛЁ\ѻ\ѫ񞬜ߜÜἲ³\ݘ\Z٤̜r\ְρuљϴZ\޺9󳜇ES؜ƔĆξ5ñ}ґ\ղGݔϱ5͟=��j֭ے܏[ퟙ�\0tqI\ṙH\؜\��\蝚��\ݜ䝚\渜↨߱5rI͡\\Ĝ윢3ࠩռur\秏˘Ʈ˩S˾\䙧-\Ԝņk\؜ໜɜ͇\γgǝv\ZٜËic\Ԝ賝DCǵޜ͍>å\赳\뵾��K\�ξ5ñ}ґ\ղGݔϱ5rI/qͧk\"̻&¹̟N\̴r��퓍̖9\껜؁ܷ󱲪\ژ4یİ˗ܢm27ڎӜˆǱ\ڵ[J7ԋ\΋Ӿck\䒮6Xø\؅f>\Ԫy\읤\͜Ϟ1L]SК\ǑȦ0׏6Ӛ樗򎟲ԧՇ̜ņࠜ캜\ӱitڮܜx:\ֵ>\깜㐜ݗ\蕟Z̉ќ\泿:-\띑w\뭥ۜ٠\rc\썍��MޜڜصچH\٫ϽWɜзųsٙŜܜԜ띬/��{ќݘ\Z٤̜r\ְρuљϴZ\޺9󳜇ES؜Ɣæξ㬾ċ\r\ְρuҜrΜɵw<GƱѱ\\Ĝܼw׷\栲җ􍱗ͮēnJV}jc󤣫eϺ)ck\䓞\⛏-\ׄXwMsٿݖu#̣͵L͡ll࠽��ڛܯ��Ɔ\צ;׬\ܮܭ{W׌\ۼܺ_~޵\֙Xٞ1׆.\ꢃ5ͫp]d\泭ַN\ϼ\�\Ĝե1��,\ΞS\Ӝņk\؜ຝ,ċ:򺜮ݜĜ䣣ކםp\ͮW\颖Ցϝ݀t\ꝿǞ?.dr��퓍̖9\ꦼ̭ҋzʫ>֘a\ݵ\ͥ��գM)GH3\ܜؼ\ѯs^Waݭ\윝èk\Ɯ읎/pݔË(\ٜ՜褪v.ٜ'>񉜝\Ü͸򜔌\Ƕ\ʆV\˜��ǜלȥޜĵࠚ̉ќ\泿:-\궺\睭S3\̜ۗҹкqo��ˁ5З]tQsлܣl\r��Мݞƣիsٗۘף��aۣO\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.󧓢\죮\睉%]k򓏾y`ߜ؟WۈFȜ꿜ѺΕͧW��׸y,~q\鶵~άǜ!\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.󧓢\몜ƜٜZٺθԙβ\ٜ靣\ݜ씜؜賝𰉏z\Ӏ\ڡǝڛoݱˢ՟ӚܜZo\̯\Z#N\nk%&Üǘ߁ڬࠜչ&Gߚؾ\ڜɪ٣^\˜ȜٜZ٤׸\瓋5ҵݜ؝w\޶|\ٸ7uf\؜韜ܪ\r��ߺ]\0ެ~Ѓ\՞J6\ʅ络䓋ڻ\ޜꏜޜ첵㤈ѵrI͡\\Ĝ윢3ࠩռur\秏˘Ʈ˩GMLDδؒ.\צږ󖛭\֜\w\޵\��񰉜\Ĝ̜ݜɜဿ󛻜ߵϝ\㩙\࠹\ϷμхÎ:ʻ\nʯ\'߈\ȚɉȱׯЦ˗ꬑď\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩜅\Z-_��֨Ԝל씜ࠜ賜؜ත 7ߎ`𜝜ྷU1\Z\ԫn؜޶Ó#\ǜלȥ]lІq\r΋\ͼǜֲ\׉ٟ=.bغ,ƞ5qD\Ջϭ\꣜ͣ]\࿌3ΘZ򷛿��ˍlĜӴ\ຳɮ_\ٮ\Z񜩏zş޷\ݰC󛭴AÏ\Πlࠬd֜͑/\椬Μ\\Ӌ\\󩅚뮉`.󧓢\놜ϗ\ϙҴM6\ڤl\<\n[ͮ󷫾��\Zhۄ՜ZT\ro\ɦ󍬜ȼퟟ�ࠜݘ\Z٤̜r\ְρuљϴZ\޺9󳜇ES؜Ɣæ&j5����Uϴ��ͱǳʯt-BǛ\럖ZZɺݥۍ~򓟜�����Ɠʚؾ\ڜɪ٣^\˜ȜٜZ٤׸\瓋5\ם\\珧E=\ֻ\וZu=Üƨ̦ήs#kϼϚ\㞧ZѪL��\꬜ܜZ\׎kn��eڜʢ߱5rI͡\\Ĝ윢3ࠩռur\秏˘Ʈ˩GMLLWG_+\��ݮW\ڬW\䪋ϑĽ\윜ࠪ6\ۨĮ\�Ծ𜃏\τ٭\Ĝˎ=��9򜔌\Ƕ\ʆV\˜��ǜלȥޜĵࠚ̉ќ\泿:ͺ\惧ÎϜԗל灲̃q؅\ࠜ貵��\Ӿ��ϝjڜ׷חռc&^5:rΓ\ǓʾcjϫZ̭ҋۜ٠\r\䝚\\ڹL̥͓3?{\\\İuY̭=m\޽��ࠚҋۖDĻ3L;Ѷ\ݲˮ)ݘ:\⼜࠹NW\'s\䐧Ϝˎԥ\͉F-\Ԝņk\؜ໜɜͧZ-oݜ񜙜䜢Ʃ\̢\둓ϣ\ΚkΜֽ��겞T[ͮc࠷ޜஜﵯ{\rL\05޾ߜԟ*̝ʩӨ8ʜɑȦ0׏6Ӛ樗򱶆.\饮񜔢Mŵ\؄0ع٦eݷ\߻ؿdM\ݷ򜢲/L/υ\9)^��?��\ۺ.��ݐ\"��dѵrI͡\\Ĝ윢3ࠩռur\秏˘Ʈ˩\ܜײ\לꆺ/��\؜買͜לܺˎ{\\\֜욘bAԃ׮\ӟuʷdѣ\ꗓḵF7\⽜ųՒQc,\ࠠM֟׮\ᝥ\覸>ѹ\ؿ��巔mI6ڷ͹ɮ\甕k49<aĊuɠΜrT\ωSڸ\ӵަܮ:𜜜ö\Ѭ\Znهܻ͜!f󸁏\֜Žu5\ࠜܮܭ`٭U[\ӫtڵ\�绛\ܬԍ󅯼]<Ӝ۳\ɮ޵\צ؜޶\뾼f˜嚜坂򾞘\Ƴĺa&ϥӼ\kn߹橥p󲉖\֒ҥ\ڜ賱м\䤍ƛn\ص<\؊ڙ����޴O&1ז��򜪫wԑϥ\栍۾ԍ#ͺ񄹸N´ݜZG\=\챃ڛn۩\ɷuf}Ç̍˜࠷\ڄ\蛜ڜr˜睕%ʿ��/ԙos��˹\ۓ޶̜旜櫹Ĺ|naΞ_\̄Ƕ}��ޜZ\ݜ㝭˥l}Ç\䄑ڜ궹'?��óa\杫ʷڇלݒI\ǜ蝒Ǧ\講^ٜ읆4\睫uš^K\̜я|d;\߱:��{ܜ޺F/\'\n\'\ӕW\\\Ҝ߅FȎz\⺩\Ꝛ[��}ν\"O\Мí\٭؜ݜҜt\с͝\ཛྷ蝮q\ԡ>܎ٜݗǻ\͕W\\ѽC\꺧߿\̖T\טٶTί��Ͽ\שқlҜƀZߜџl\ڙw\יg֜Μ�_��򤐧�,D#\n󹺜ZCΜ͆l\Мs\̱\͟��\ݺ\ߺF_NӚ\漜࠿~{��߳ߜ\ԤUCќ교͎֜ձ��sNܜԈͯ\ȟu\֕\γ߷ݶ,ʸ\ڋXɆ7F��ϝX;\ش\틯߸jŻ[\뜽9,n:˱��\λ\ܬӜʪԗ΁Xا։:BO7\Сٜ˜̈́\ޜ邨ރ\Ǆcŭ \މ\؜؜띧ؒ{\ߺF߅զu]\"Zܜ흚4\؜ۉԙPs\Ŝ縤МڰȜƗǹ򜌫ͷ^󺗽Ϲ򗿾5ryc��\ַ򼙶Au!\�״3x4Ԁ��ϢϭߤփM��\ڜо65:ލֵ;浑7GuÉΜa��>Ͷ񅅺\ɞϵAࠋ\轜ﳜˮ\uڰΟCϯ��ǉͦן\'َ��􇞹d󻝜苻񳆚	\洹��\؞۽/@Gɽ��ඒ󳚏c\ќܜݚ\ࠜ鴬qҋl򨜖��*y\Ӊ\'5p@{_x݉F؁򜚿ڭ\ٶ{��ހ\Րڅ֎x\л\ߜԜ\x\ᅃ؃ӹԜ͜崷6؜ַ򄙚\廜밯ψĻث׮ϛw\G׻󵴜ߣ󘶞q:\П��5\ۜ㮜άԹΜ罜∣\ڣ\ЮٜÜ睚kЭڵ؉Ҿїɜ錻��\豝¯ڥbM󿜉\źŮк\0\0\0\0IENDς`§);
/*!40000 ALTER TABLE file_content
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS login;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE login (
    id          VARCHAR(32)         NOT NULL COMMENT '用户ID',
    version     INT(32) UNSIGNED             DEFAULT '0' COMMENT '版本号',
    username    VARCHAR(32)         NOT NULL COMMENT '昵称',
    password    VARCHAR(32)         NOT NULL COMMENT '密码',
    email       VARCHAR(100)                 DEFAULT '' COMMENT '邮箱',
    mobile      VARCHAR(20)                  DEFAULT '' COMMENT '手机号',
    create_time DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username(username),
    UNIQUE KEY uk_email(email),
    UNIQUE KEY uk_mobile(mobile)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci COMMENT ='登录信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES login WRITE;
/*!40000 ALTER TABLE login
    DISABLE KEYS */;
INSERT INTO login
VALUES ('1161473704962564098', 0, 'admin', '123456', 'forbreak@163.com', '15111111111',
        '2019-08-14 11:04:45',
        '2019-08-26 14:33:51', 0),
('1161479401813155842', 0, 'user', '123456', 'forbreak2@163.com', '15111111112',
 '2019-08-14 11:27:23',
 '2019-08-26 14:34:07', 0);
/*!40000 ALTER TABLE login
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler`
--

DROP TABLE IF EXISTS scheduler;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE scheduler (
    id              VARCHAR(32)         NOT NULL COMMENT 'ID',
    version         INT(32)                      DEFAULT '0' COMMENT '版本号',
    scheduler_name  VARCHAR(32)                  DEFAULT '' COMMENT '调度器名',
    trigger_group   VARCHAR(32)         NOT NULL COMMENT '触发器组',
    trigger_name    VARCHAR(32)         NOT NULL COMMENT '触发器名称',
    job_group       VARCHAR(32)         NOT NULL COMMENT '任务组',
    job_name        VARCHAR(32)         NOT NULL COMMENT '任务名',
    bean_name       VARCHAR(256)                 DEFAULT '' COMMENT 'Bean名称',
    bean_type       VARCHAR(256)                 DEFAULT '' COMMENT 'Bean类型',
    method_name     VARCHAR(128)                 DEFAULT '' COMMENT '方法名称',
    method_params   VARCHAR(256)                 DEFAULT '' COMMENT '方法参数（JSON 形式）',
    trigger_type    INT(2)                       DEFAULT '0' COMMENT '触发类型',
    invoke_type     INT(2)                       DEFAULT '0' COMMENT '调用类型',
    begin_time      DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '触发开始时间',
    end_time        DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '触发结束时间',
    repeat_interval INT(20)                      DEFAULT '0' COMMENT '重复调度间隔',
    repeat_count    INT(3)                       DEFAULT '0' COMMENT '重复调度次数',
    cron_expression VARCHAR(64)                  DEFAULT '' COMMENT 'CRON 表达式',
    status          INT(1) UNSIGNED     NOT NULL DEFAULT '0' COMMENT '状态',
    note            VARCHAR(100)                 DEFAULT '' COMMENT '备注',
    create_time     DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_trigger_group_name(trigger_group, trigger_name)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci COMMENT ='调度信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler`
--

LOCK TABLES scheduler WRITE;
/*!40000 ALTER TABLE scheduler
    DISABLE KEYS */;
INSERT INTO scheduler
VALUES ('1172113692678959105', 0, 'quartzScheduler', 'trigger_group_test', 'trigger_testjob',
        'test', 'testjob',
        'sampleJob1', 'io.github.dunwu.quickstart.scheduler.job.SampleJob1', '', '天王盖地虎，宝塔镇河妖。', 1,
        0,
        '2019-09-12 19:44:15', '2019-09-12 19:44:15', 0, 0, '0/30 * * * * ? ', 0, '',
        '2019-09-12 19:44:15',
        '2019-09-12 19:44:15', 0);
/*!40000 ALTER TABLE scheduler
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `template`
--

DROP TABLE IF EXISTS template;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE template (
    id          VARCHAR(32)         NOT NULL COMMENT 'ID',
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
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci COMMENT ='模板配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `template`
--

LOCK TABLES template WRITE;
/*!40000 ALTER TABLE template
    DISABLE KEYS */;
INSERT INTO template
VALUES ('1172115878335062018', '校验码邮件模板', 'mail', 'checkcode',
        '<html>\n<head>\n  <base target=\"_blank\" />\n  <style type=\"text/css\">\n    ::-webkit-scrollbar {\n      display: none;\n    }\n  </style>\n  <style id=\"cloudAttachStyle\" type=\"text/css\">\n    #divNeteaseBigAttach,\n    #divNeteaseBigAttach_bak {\n      display: none;\n    }\n  </style>\n</head>\n<body tabindex=\"0\" role=\"listitem\">\n<div id=\"content\">\n  <style></style>\n\n  <div\n    style=\"background-color:#d0d0d0;background-image:url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg.png);padding:40px;\"\n  >\n    <div\n      class=\"mmsgLetter\"\n      style=\"margin:0 auto;padding:10px;color:#333;background-color:#fff;border:0px solid #aaa;border-radius:5px;-webkit-box-shadow:3px 3px 10px #999;-moz-box-shadow:3px 3px 10px #999;box-shadow:3px 3px 10px #999;font-family:Verdana, sans-serif; \"\n    >\n      <div\n        class=\"mmsgLetterHeader\"\n        style=\"height:23px;background:url(http://weixin.qq.com/zh_CN/htmledition/images/weixin/letter/mmsgletter_2_bg_topline.png) repeat-x 0 0;\"\n      ></div>\n\n      <div class=\"letterLog\"><p>Dunwu Quickstart</p></div>\n\n      <div style=\"text-align:left; margin-top: 20px; padding: 30px 30px 0 30px;\">\n        <div>\n          <p>${to}，您好!</p>\n          <p>\n            感谢您注册 <strong>Dunwu QuickStart</strong>!<br />\n            您的登录邮箱为：<a data-auto-link=\"1\" href=\"mailto:${to}\">${to}</a>。请回填如下6位验证码：\n          </p>\n          <p style=\"text-align: center;\"><code class=\"mmsgLetterDigital\">${checkCode}</code></p>\n          <p>验证码在30分钟内有效，30分钟后需要重新激活邮箱</p>\n        </div>\n\n        <div class=\"mmsgLetterInscribe\" style=\"padding:40px 0 0;\">\n          <img\n            class=\"mmsgAvatar\"\n            src=\"http://dunwu.test.upcdn.net/cs/others/zp.png\"\n            style=\"float:left; width:60px; height:60px; margin-right: 10px\"\n          />\n          <div class=\"mmsgSender\" style=\"margin:0 0 0 54px;\">\n            <p class=\"mmsgName\" style=\"margin:0 0 10px;\">Dunwu</p>\n            <p class=\"mmsgInfo\" style=\"font-size:12px;margin:0;line-height:1.2;\">\n              <a href=\"https://github.com/dunwu/\" style=\"color:#777777;\">Github：https://github.com/dunwu/</a>\n              <br />\n              <a href=\"mailto:forbreak@163.com\" style=\"color:#777777;\">邮箱：forbreak@163.com</a>\n            </p>\n          </div>\n        </div>\n      </div>\n\n      <div\n        class=\"mmsgLetterFooter\"\n        style=\"margin:16px;text-align:center;font-size:12px;color:#888;text-shadow:1px 0px 0px #eee;\"\n      ></div>\n    </div>\n  </div>\n</div>\n\n<script>\n  var _c = document.getElementById(\"content\");\n  _c.innerHTML = (_c.innerHTML || \"\").replace(/(href|formAction|onclick|javascript)/gi, \"__$1\");\n</script>\n<style type=\"text/css\">\n  body {\n    font-size: 14px;\n    font-family: arial, verdana, sans-serif;\n    line-height: 1.666;\n    padding: 0;\n    margin: 0;\n    overflow: auto;\n    white-space: normal;\n    word-wrap: break-word;\n    min-height: 100px;\n  }\n\n  td,\n  input,\n  button,\n  select,\n  body {\n    font-family: Helvetica, \"Microsoft Yahei\", verdana;\n  }\n\n  pre {\n    white-space: pre-wrap;\n    white-space: -moz-pre-wrap;\n    white-space: -pre-wrap;\n    white-space: -o-pre-wrap;\n    word-wrap: break-word;\n    width: 95%;\n  }\n\n  th,\n  td {\n    font-family: arial, verdana, sans-serif;\n    line-height: 1.666;\n  }\n\n  img {\n    border: 0;\n  }\n\n  header,\n  footer,\n  section,\n  aside,\n  article,\n  nav,\n  hgroup,\n  figure,\n  figcaption {\n    display: block;\n  }\n\n  .letterLog {\n    font-size: 24px;\n    font-family: Impact, Elephant, serif, Arial;\n    font-weight: bold;\n    text-align: center;\n    background-image: -webkit-linear-gradient(left, #ffdcb4, #b96972 25%, #e88a57 50%, #804170 75%, #a596cd);\n    -webkit-text-fill-color: transparent;\n    -webkit-background-clip: text;\n    -webkit-background-size: 200% 100%;\n    -webkit-animation: masked-animation 1.5s infinite linear;\n    float: right;\n    padding-right: 30px;\n  }\n\n  blockquote {\n    margin-right: 0px;\n  }\n\n  @-webkit-keyframes masked-animation {\n    0% {\n      background-position: 0 0;\n    }\n    100% {\n      background-position: -100% 0;\n    }\n  }\n\n  .mmsgLetterDigital {\n    color: #24b0cf;\n    background: #fafafa;\n    border: 1px solid #24b0cf;\n    box-shadow: 1px 1px 1px whitesmoke;\n    font-size: 16px;\n    margin: 0 2px;\n    padding: 5px 5px;\n    white-space: pre-wrap;\n  }\n</style>\n\n<style id=\"netease_mail_footer_style\" type=\"text/css\">\n  #netease_mail_footer {\n    display: none;\n  }\n</style>\n</body>\n</html>\n',
        '', '2019-09-12 19:52:56', '2019-09-12 19:52:56', 0);
/*!40000 ALTER TABLE template
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS user;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE user (
    id          VARCHAR(32)         NOT NULL COMMENT 'ID',
    version     INT(32) UNSIGNED             DEFAULT '0' COMMENT '版本号',
    username    VARCHAR(32)         NOT NULL COMMENT '昵称',
    name        VARCHAR(32)                  DEFAULT '' COMMENT '姓名',
    birthday    DATE                         DEFAULT NULL COMMENT '生日',
    sex         INT(1) UNSIGNED              DEFAULT NULL COMMENT '性别',
    avatar      VARCHAR(128)                 DEFAULT '' COMMENT '头像',
    email       VARCHAR(100)                 DEFAULT '' COMMENT '邮箱',
    mobile      VARCHAR(20)                  DEFAULT '' COMMENT '手机号',
    profession  VARCHAR(32)                  DEFAULT '' COMMENT '职业',
    province    VARCHAR(10)                  DEFAULT '' COMMENT '省',
    city        VARCHAR(10)                  DEFAULT '' COMMENT '市',
    county      VARCHAR(10)                  DEFAULT '' COMMENT '区',
    create_time DATETIME                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '逻辑删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username(username),
    UNIQUE KEY uk_email(email),
    UNIQUE KEY uk_mobile(mobile),
    KEY idx_name(name)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES user WRITE;
/*!40000 ALTER TABLE user
    DISABLE KEYS */;
INSERT INTO user
VALUES ('1161473705096781826', 0, 'user', '用户', NULL, NULL,
        'http://dunwu.test.upcdn.net/images/others/zp.png',
        'forbreak2@163.com', '15111111112', '', '', '', '', '2019-08-14 11:04:45',
        '2019-08-26 14:35:25', 0),
('1165798967011774465', 0, 'admin', '管理员', NULL, NULL,
 'http://dunwu.test.upcdn.net/images/others/zp.png',
 'forbreak@163.com', '15111111111', '', '', '', '', '2019-08-14 03:02:53', '2019-08-26 14:35:34',
 0);
/*!40000 ALTER TABLE user
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @old_time_zone */;

/*!40101 SET SQL_MODE = @old_sql_mode */;
/*!40014 SET FOREIGN_KEY_CHECKS = @old_foreign_key_checks */;
/*!40014 SET UNIQUE_CHECKS = @old_unique_checks */;
/*!40101 SET CHARACTER_SET_CLIENT = @old_character_set_client */;
/*!40101 SET CHARACTER_SET_RESULTS = @old_character_set_results */;
/*!40101 SET COLLATION_CONNECTION = @old_collation_connection */;
/*!40111 SET SQL_NOTES = @old_sql_notes */;

-- Dump completed on 2019-09-12 20:15:03
