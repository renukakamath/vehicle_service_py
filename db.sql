/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - vehicle
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vehicle` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `vehicle`;

/*Table structure for table `buy` */

DROP TABLE IF EXISTS `buy`;

CREATE TABLE `buy` (
  `buy_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_id` int(11) DEFAULT NULL,
  `amount` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`buy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `buy` */

insert  into `buy`(`buy_id`,`vehicle_id`,`amount`,`date`,`status`) values 
(1,1,'23355567','2023-04-28','sell'),
(2,1,'23355567','2023-04-28','sell'),
(3,1,'23355567','2023-04-28','sell'),
(4,1,'233555','2023-04-28','sell'),
(5,1,'233555','2023-04-28','sell'),
(6,1,'233555','2023-04-28','sell'),
(7,1,'233555','2023-04-28','sell'),
(8,1,'233555','2023-04-28','sell'),
(9,1,'233555','2023-04-28','sell'),
(10,1,'233555','2023-04-28','sell'),
(11,1,'233555','2023-04-28','sell'),
(12,1,'233555','2023-04-28','sell'),
(13,1,'23355567','2023-04-29','buy');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `usertype` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(3,'achu','achu','staff'),
(8,'mans','mans','service'),
(9,'pass','56787','staff'),
(10,'123','567','service'),
(11,'martin','martin','service'),
(12,'joshy','joshy','staff'),
(13,'ann','ann','user'),
(14,'Ann','Mary','user'),
(15,'ussr','bsjsj','user'),
(16,'livinmariya','vgh','user'),
(17,'livin','hi','user'),
(18,'user','hai','user'),
(19,'123','123','service'),
(20,'parvana','123','staff'),
(21,'1234\n','1234','user'),
(22,'1233','1233','user'),
(23,'Ann\n\n','123','user'),
(24,'madha','1234','user'),
(25,'susan','paul','user');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`vehicle_id`,`date`,`status`,`time`) values 
(1,1,'21-02-2130','approved','21:00'),
(2,4,'2023-04-28','pending','2023-04-28 10:09:19'),
(3,1,'2023-04-29','pending','2023-04-29 15:34:28');

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `service` */

insert  into `service`(`service_id`,`login_id`,`name`,`place`,`phone`,`email`) values 
(4,8,'man','wayanad','7788996655','mans@gmail.com'),
(5,10,'parvana','Thiruvananthapuram','7994656072','parvanaprakash3@gmail.com'),
(6,11,'maruthi','thodupuzha','9995987260','martinthomas@gmail.com');

/*Table structure for table `service_detail` */

DROP TABLE IF EXISTS `service_detail`;

CREATE TABLE `service_detail` (
  `service_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_id` int(11) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`service_detail_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `service_detail` */

insert  into `service_detail`(`service_detail_id`,`request_id`,`detail`,`date`) values 
(1,1,'abcdefghij','2023-04-04'),
(2,1,'fgg','2023-04-28');

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `staff` */

insert  into `staff`(`staff_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,3,'achu','mon','palakkad','9996541230','achu12@gmail.com'),
(2,9,'Evelin','Martin','Thiruvananthapuram','7025222538','evelinmartin2001@gmail.com'),
(3,12,'joshy','dani','kothamagalam','9846380973','joshykdani@gmail.com');

/*Table structure for table `upload` */

DROP TABLE IF EXISTS `upload`;

CREATE TABLE `upload` (
  `upload_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_id` int(11) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `file` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`upload_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `upload` */

insert  into `upload`(`upload_id`,`vehicle_id`,`type`,`file`) values 
(1,1,'insurance','static/upload/9bb9ca8f-cf6d-461c-9eb7-0aea499f9e8ealexander-andrews-fsH1KjbdjE8-unsplash.jpg'),
(2,1,'tax','static/upload/ac18a911-99d2-4090-9fb0-0cd5b6d35ca7ss1.jpg'),
(3,2,'pollution certificate','static/upload/0e6d3db3-64c0-4226-8bf9-e4c83ec8b277avinash proof.webp'),
(4,3,'pollution certificate','static/upload/bbe735df-02a3-4473-9024-c6eae6663263Anuradha proof.jfif'),
(5,3,'pollution certificate','static/upload/9dbcdd5e-d1ab-4a08-ad25-563e82bf944aashraf license.jfif');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`,`image`) values 
(1,5,'sanku','mon','alpy','7410236589','sanku@gmail.com',NULL),
(2,13,'Ann Mary','Joshy','pala','7994656872','annmarybusy@gmail.com',NULL),
(3,14,'Ann Mary','Joshy','ekm','7306349513','ann.maryy00@gmail.com',NULL),
(4,1,'%s','%s','%s','%s','%s','%s'),
(5,17,'Livin','Mariya','Koratty','9856369852','livinmariyababu12@gmail.com','static/upload4c538865-7944-4948-a269-05e1f9a5f0d0abc.jpg'),
(6,18,'Renuka','Kamath','place','12345678912','renukakamath2@gmail.com','175440'),
(7,21,'Parvana','Prakash','pala','7994656072','pachunish10@gmail.com','847748'),
(8,22,'evelin ','marty','thdpa','7994656072','parvanaprakash3@gmail.com','303884'),
(9,23,'Ann ','Joshy','Koratty','9447126072','parvanaprakash3@gmail.com','1414172'),
(10,24,'Madha','Parafool','Selam','9497679072','madhap@gmail.com','1076844'),
(11,25,'susan','paul','kochi','9495747750','susanpaul@gmail.com','792124');

/*Table structure for table `vehicle` */

DROP TABLE IF EXISTS `vehicle`;

CREATE TABLE `vehicle` (
  `vehicle_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `vehicle` varchar(100) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `vehicle` */

insert  into `vehicle`(`vehicle_id`,`user_id`,`vehicle`,`detail`,`image`) values 
(1,10,'bmw','single use','static/upload17036d4c-eb34-49f1-bd6c-55eee6756ebfabc.jpg'),
(2,10,'wagonar','KL56 L2345','static/upload0c5bf2fa-ce8d-4c28-ba95-2347f8724e16abc.jpg'),
(3,11,'BMW','High class vehicle used for 1 year, no complaints with any parts','static/uploadab4a342f-3809-41dd-a7b9-8208deda1718abc.jpg'),
(4,11,'brezza','red brezza, high maintenance ','static/upload5e62bdbe-ddb8-4c81-97f9-5fe75627b0d0abc.jpg'),
(5,11,'brezza','red brezza, high maintenance ','static/upload94efa8c1-50a8-4ced-b966-a60d7d6e3bc3abc.jpg'),
(6,11,'brezza','red brezza, high maintenance ','static/uploadf121c337-c11e-44ff-9796-cbde93d89918abc.jpg'),
(7,11,'brezza','red brezza, high maintenance ','static/upload9a313b0c-48e3-46b0-89ca-7caf5f2e8055abc.jpg'),
(8,11,'brezza','red brezza, high maintenance ','static/upload6a446d24-d766-475a-81d7-157cd7e64019abc.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
