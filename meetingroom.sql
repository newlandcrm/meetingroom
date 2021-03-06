/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.26 : Database - meetingroom
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`meetingroom` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `meetingroom`;

/*Table structure for table `building` */

DROP TABLE IF EXISTS `building`;

CREATE TABLE `building` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '楼名',
  `sort` varchar(20) DEFAULT NULL COMMENT '权重',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `building` */

insert  into `building`(`id`,`name`,`sort`) values ('12312','test1','4'),('123123','test3','2'),('12344','test4','6'),('123563463','B楼','4'),('1251','test2','2'),('2123123','弘卓楼','5');

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` varchar(20) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '部门名字',
  `another` varchar(30) DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `department` */

insert  into `department`(`id`,`name`,`another`) values ('123','CRM产品',NULL),('124','大数据项建',NULL);

/*Table structure for table `floor` */

DROP TABLE IF EXISTS `floor`;

CREATE TABLE `floor` (
  `id` varchar(20) NOT NULL,
  `describe` varchar(20) DEFAULT NULL COMMENT '描述',
  `floor` int(20) NOT NULL COMMENT '楼层',
  `buildingid` varchar(20) NOT NULL COMMENT '所属楼',
  PRIMARY KEY (`id`),
  KEY `buildingid` (`buildingid`),
  CONSTRAINT `floor_ibfk_1` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `floor` */

insert  into `floor`(`id`,`describe`,`floor`,`buildingid`) values ('123123','一楼',1,'2123123'),('6234','一楼',1,'123563463');

/*Table structure for table `room` */

DROP TABLE IF EXISTS `room`;

CREATE TABLE `room` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL COMMENT '会议室名字',
  `floorid` varchar(20) NOT NULL COMMENT '所属楼层',
  `capacity` int(20) DEFAULT NULL COMMENT '会议室容量',
  `addr` varchar(30) DEFAULT NULL COMMENT '会议室地址',
  `openstate` int(1) DEFAULT NULL COMMENT '开放状态0:开放 1：不开放',
  PRIMARY KEY (`id`),
  KEY `room_ibfk_1` (`floorid`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`floorid`) REFERENCES `floor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `room` */

insert  into `room`(`id`,`name`,`floorid`,`capacity`,`addr`,`openstate`) values ('111','B楼一楼大厅','123123',100,'B楼咖啡厅旁',1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `username` varchar(20) NOT NULL COMMENT '账号',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT 'E-Mail',
  `regdate` datetime DEFAULT NULL COMMENT '注册日期',
  `lastdate` datetime DEFAULT NULL COMMENT '最后登陆日期',
  `roleid` varchar(20) DEFAULT NULL COMMENT '角色：初期暂定0 普通1 管理员 后期再加哥角色表',
  `departmentid` varchar(20) DEFAULT NULL COMMENT '所属部门id',
  PRIMARY KEY (`id`),
  KEY `departmentid` (`departmentid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`departmentid`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`mobile`,`password`,`nickname`,`avatar`,`email`,`regdate`,`lastdate`,`roleid`,`departmentid`) values ('123','abc','110','123456','测试人员1',NULL,NULL,NULL,NULL,NULL,'123'),('124','bbb','120','123456','测试人员2',NULL,NULL,NULL,NULL,NULL,'124');

/*Table structure for table `user_room_reserve` */

DROP TABLE IF EXISTS `user_room_reserve`;

CREATE TABLE `user_room_reserve` (
  `id` varchar(20) NOT NULL,
  `userid` varchar(20) NOT NULL COMMENT '申请人id',
  `roomid` varchar(20) NOT NULL COMMENT '会议室id',
  `startdate` datetime DEFAULT NULL COMMENT '开始时间',
  `enddate` datetime DEFAULT NULL COMMENT '结束时间',
  `content` varchar(50) DEFAULT NULL COMMENT '内容',
  `state` int(1) DEFAULT NULL COMMENT '0:未审核 1：通过审核',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  KEY `roomid` (`roomid`),
  CONSTRAINT `user_room_reserve_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
  CONSTRAINT `user_room_reserve_ibfk_2` FOREIGN KEY (`roomid`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_room_reserve` */

insert  into `user_room_reserve`(`id`,`userid`,`roomid`,`startdate`,`enddate`,`content`,`state`) values ('2131','123','111','2019-07-03 16:15:20','2019-07-03 17:15:27','BMT业务组迭代回顾会',0),('2222','124','111','2019-07-05 16:16:00','2019-07-05 18:16:06','大合唱练习',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
