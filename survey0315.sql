/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.51 : Database - survey0315
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`survey0315` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `survey0315`;

/*Table structure for table `emps` */

DROP TABLE IF EXISTS `emps`;

CREATE TABLE `emps` (
  `EMP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_NAME` varchar(255) DEFAULT NULL,
  `AGE` int(11) DEFAULT NULL,
  PRIMARY KEY (`EMP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

/*Data for the table `emps` */

insert  into `emps`(`EMP_ID`,`EMP_NAME`,`AGE`) values (1,'emp0',0),(2,'emp1',10),(3,'emp2',20),(4,'emp3',30),(5,'emp4',40),(6,'emp5',50),(7,'emp6',60),(8,'emp7',70),(9,'emp8',80),(10,'emp9',90),(11,'emp10',100),(12,'emp11',110),(13,'emp12',120),(14,'emp13',130),(15,'emp14',140),(16,'emp15',150),(17,'emp16',160),(18,'emp17',170),(19,'emp18',180),(20,'emp19',190),(21,'emp20',200),(22,'emp21',210),(23,'emp22',220),(24,'emp23',230),(25,'emp24',240),(26,'emp25',250),(27,'emp26',260),(28,'emp27',270),(29,'emp28',280),(30,'emp29',290),(31,'emp30',300),(32,'emp31',310),(33,'emp32',320),(34,'emp33',330),(35,'emp34',340),(36,'emp35',350),(37,'emp36',360),(38,'emp37',370),(39,'emp38',380),(40,'emp39',390),(41,'emp40',400),(42,'emp41',410),(43,'emp42',420),(44,'emp43',430),(45,'emp44',440),(46,'emp45',450),(47,'emp46',460),(48,'emp47',470),(49,'emp48',480),(50,'emp49',490),(51,'emp50',500),(52,'emp51',510),(53,'emp52',520),(54,'emp53',530),(55,'emp54',540),(56,'emp55',550),(57,'emp56',560),(58,'emp57',570),(59,'emp58',580),(60,'emp59',590),(61,'emp60',600),(62,'emp61',610),(63,'emp62',620),(64,'emp63',630),(65,'emp64',640),(66,'emp65',650),(67,'emp66',660),(68,'emp67',670),(69,'emp68',680),(70,'emp69',690),(71,'emp70',700),(72,'emp71',710),(73,'emp72',720),(74,'emp73',730),(75,'emp74',740),(76,'emp75',750),(77,'emp76',760),(78,'emp77',770),(79,'emp78',780),(80,'emp79',790),(81,'emp80',800),(82,'emp81',810),(83,'emp82',820),(84,'emp83',830),(85,'emp84',840),(86,'emp85',850),(87,'emp86',860),(88,'emp87',870),(89,'emp88',880),(90,'emp89',890),(91,'emp90',900),(92,'emp91',910),(93,'emp92',920),(94,'emp93',930),(95,'emp94',940),(96,'emp95',950),(97,'emp96',960),(98,'emp97',970),(99,'emp98',980),(100,'emp99',990);

/*Table structure for table `inner_admin_role` */

DROP TABLE IF EXISTS `inner_admin_role`;

CREATE TABLE `inner_admin_role` (
  `admin_id` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`,`ROLE_ID`),
  KEY `FK_dc6o9ct9a0047xrdaa176i9qs` (`ROLE_ID`),
  KEY `FK_6lpb2fshofifviwj254hy25hk` (`admin_id`),
  CONSTRAINT `FK_6lpb2fshofifviwj254hy25hk` FOREIGN KEY (`admin_id`) REFERENCES `survey_admin` (`ADMIN_ID`),
  CONSTRAINT `FK_dc6o9ct9a0047xrdaa176i9qs` FOREIGN KEY (`ROLE_ID`) REFERENCES `survey_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `inner_admin_role` */

/*Table structure for table `inner_auth_res` */

DROP TABLE IF EXISTS `inner_auth_res`;

CREATE TABLE `inner_auth_res` (
  `auth_id` int(11) NOT NULL,
  `RES_ID` int(11) NOT NULL,
  PRIMARY KEY (`auth_id`,`RES_ID`),
  KEY `FK_nar93xu7towjkkk7o0xff8iuv` (`RES_ID`),
  KEY `FK_ch34m1xvsco1ownab124iaeyn` (`auth_id`),
  CONSTRAINT `FK_ch34m1xvsco1ownab124iaeyn` FOREIGN KEY (`auth_id`) REFERENCES `survey_auth` (`AUTH_ID`),
  CONSTRAINT `FK_nar93xu7towjkkk7o0xff8iuv` FOREIGN KEY (`RES_ID`) REFERENCES `survey_res` (`RES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `inner_auth_res` */

/*Table structure for table `inner_role_auth` */

DROP TABLE IF EXISTS `inner_role_auth`;

CREATE TABLE `inner_role_auth` (
  `ROLE_ID` int(11) NOT NULL,
  `AUTH_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`AUTH_ID`),
  KEY `FK_kkuma7gekybju69blhqwgxd3w` (`AUTH_ID`),
  KEY `FK_alyg66yjhncvtjtg7ukoc53l4` (`ROLE_ID`),
  CONSTRAINT `FK_alyg66yjhncvtjtg7ukoc53l4` FOREIGN KEY (`ROLE_ID`) REFERENCES `survey_role` (`ROLE_ID`),
  CONSTRAINT `FK_kkuma7gekybju69blhqwgxd3w` FOREIGN KEY (`AUTH_ID`) REFERENCES `survey_auth` (`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `inner_role_auth` */

/*Table structure for table `inner_user_role` */

DROP TABLE IF EXISTS `inner_user_role`;

CREATE TABLE `inner_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_hop8x4ajh1kd7jjuimik01jgt` (`ROLE_ID`),
  KEY `FK_sbg9nt9a3ypgkjdlglmhc36rl` (`USER_ID`),
  CONSTRAINT `FK_hop8x4ajh1kd7jjuimik01jgt` FOREIGN KEY (`ROLE_ID`) REFERENCES `survey_role` (`ROLE_ID`),
  CONSTRAINT `FK_sbg9nt9a3ypgkjdlglmhc36rl` FOREIGN KEY (`USER_ID`) REFERENCES `survey_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `inner_user_role` */

/*Table structure for table `survey_admin` */

DROP TABLE IF EXISTS `survey_admin`;

CREATE TABLE `survey_admin` (
  `ADMIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADMIN_NAME` varchar(255) DEFAULT NULL,
  `ADMIN_PWD` varchar(255) DEFAULT NULL,
  `RES_CODE_ARR` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ADMIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_admin` */

/*Table structure for table `survey_answer` */

DROP TABLE IF EXISTS `survey_answer`;

CREATE TABLE `survey_answer` (
  `ANSWER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTENT` varchar(255) DEFAULT NULL,
  `QUESTION_ID` int(11) DEFAULT NULL,
  `SURVEY_ID` int(11) DEFAULT NULL,
  `UUID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ANSWER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_answer` */

/*Table structure for table `survey_auth` */

DROP TABLE IF EXISTS `survey_auth`;

CREATE TABLE `survey_auth` (
  `AUTH_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTH_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_auth` */

/*Table structure for table `survey_bag` */

DROP TABLE IF EXISTS `survey_bag`;

CREATE TABLE `survey_bag` (
  `BAG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BAG_NAME` varchar(255) DEFAULT NULL,
  `BAG_ORDER` int(11) DEFAULT NULL,
  `SURVEY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`BAG_ID`),
  KEY `FK_jfidvth63bvvieyv5lxoref1n` (`SURVEY_ID`),
  CONSTRAINT `FK_jfidvth63bvvieyv5lxoref1n` FOREIGN KEY (`SURVEY_ID`) REFERENCES `survey_survey` (`SURVEY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_bag` */

/*Table structure for table `survey_log` */

DROP TABLE IF EXISTS `survey_log`;

CREATE TABLE `survey_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OPERATOR` varchar(255) DEFAULT NULL,
  `OPERATE_TIME` varchar(255) DEFAULT NULL,
  `METHOD_NAME` varchar(255) DEFAULT NULL,
  `TYPE_NAME` varchar(255) DEFAULT NULL,
  `PARAMS` varchar(8000) DEFAULT NULL,
  `RETURN_VALUE` varchar(8000) DEFAULT NULL,
  `EXCEPTION_TYPE` varchar(255) DEFAULT NULL,
  `EXCEPTION_MESSAGE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_log` */

/*Table structure for table `survey_question` */

DROP TABLE IF EXISTS `survey_question`;

CREATE TABLE `survey_question` (
  `QUESTION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `QUESTION_NAME` varchar(255) DEFAULT NULL,
  `QUESTION_TYPE` int(11) DEFAULT NULL,
  `OPTIONS` varchar(255) DEFAULT NULL,
  `BAG_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`QUESTION_ID`),
  KEY `FK_h139vegki2g0kbyh46x9i23ls` (`BAG_ID`),
  CONSTRAINT `FK_h139vegki2g0kbyh46x9i23ls` FOREIGN KEY (`BAG_ID`) REFERENCES `survey_bag` (`BAG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_question` */

/*Table structure for table `survey_res` */

DROP TABLE IF EXISTS `survey_res`;

CREATE TABLE `survey_res` (
  `RES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RES_NAME` varchar(255) DEFAULT NULL,
  `RES_CODE` int(11) DEFAULT NULL,
  `RES_POS` int(11) DEFAULT NULL,
  `PUBLIC_RES` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`RES_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `survey_res` */

insert  into `survey_res`(`RES_ID`,`RES_NAME`,`RES_CODE`,`RES_POS`,`PUBLIC_RES`) values (1,'/manager/admin/toMainUI',1,0,0),(2,'/guest/user/toRegistUI',2,0,0),(3,'/guest/user/toLoginUI',4,0,1),(4,'/manager/admin/toLoginUI',8,0,0),(5,'/manager/admin/login',16,0,0),(6,'/manager/statistics/showAllSurvey',32,0,0),(7,'/manager/admin/logout',64,0,0),(8,'/manager/res/showList',128,0,0),(9,'/manager/auth/toAddUI',256,0,0),(10,'/manager/auth/showList',512,0,0),(11,'/manager/role/toAddUI',1024,0,0),(12,'/manager/role/showList',2048,0,0),(13,'/manager/admin/toAddUI',4096,0,0),(14,'/manager/admin/showList',8192,0,0),(15,'/manager/log/showLogPage',16384,0,0),(16,'/guest/user/regiest',32768,0,1),(17,'/manager/res/updateResStatus',65536,0,0),(18,'/guest/user/login',131072,0,0),(19,'/manager/showList',262144,0,0),(20,'/manager/testFileUpload',524288,0,0),(21,'/manager/testFileDownload',1048576,0,0),(22,'/manager/testParseExcelFile',2097152,0,0);

/*Table structure for table `survey_role` */

DROP TABLE IF EXISTS `survey_role`;

CREATE TABLE `survey_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_role` */

/*Table structure for table `survey_survey` */

DROP TABLE IF EXISTS `survey_survey`;

CREATE TABLE `survey_survey` (
  `SURVEY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SURVEY_NAME` varchar(255) DEFAULT NULL,
  `LOGO_PATH` varchar(255) DEFAULT NULL,
  `COMPLETED` tinyint(1) DEFAULT NULL,
  `COMPLETED_TIME` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`SURVEY_ID`),
  KEY `FK_83dmli2d4qm6vs31xqkuag261` (`user_id`),
  CONSTRAINT `FK_83dmli2d4qm6vs31xqkuag261` FOREIGN KEY (`user_id`) REFERENCES `survey_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_survey` */

/*Table structure for table `survey_user` */

DROP TABLE IF EXISTS `survey_user`;

CREATE TABLE `survey_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(255) DEFAULT NULL,
  `USER_PWD` varchar(255) DEFAULT NULL,
  `COMPANY` tinyint(1) DEFAULT NULL,
  `RES_CODE_ARR` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `survey_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
