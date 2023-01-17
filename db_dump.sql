CREATE DATABASE  IF NOT EXISTS `sql7582896` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sql7582896`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: sql7.freemysqlhosting.net    Database: sql7582896
-- ------------------------------------------------------
-- Server version	5.5.62-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Cars`
--

DROP TABLE IF EXISTS `Cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `year` varchar(45) NOT NULL,
  `color` varchar(45) NOT NULL,
  `hp` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cars`
--

LOCK TABLES `Cars` WRITE;
/*!40000 ALTER TABLE `Cars` DISABLE KEYS */;
INSERT INTO `Cars` VALUES (1,'4231','1234','abc',95,'Jak haos'),(2,'fihsajif','1234','abc',95,'Jak haos'),(8,'gdsgs','2021','Sucmurasta',95,'saso mange');
/*!40000 ALTER TABLE `Cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservations`
--

DROP TABLE IF EXISTS `Reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_date` varchar(45) NOT NULL,
  `arrival_date` varchar(45) NOT NULL,
  `user_fk` int(11) NOT NULL,
  `car_fk` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `car_fk_UNIQUE` (`car_fk`),
  KEY `usr_res_fk_idx` (`user_fk`),
  KEY `car_res_fk_idx` (`car_fk`),
  CONSTRAINT `car_res_fk` FOREIGN KEY (`car_fk`) REFERENCES `Cars` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usr_res_fk` FOREIGN KEY (`user_fk`) REFERENCES `Users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservations`
--

LOCK TABLES `Reservations` WRITE;
/*!40000 ALTER TABLE `Reservations` DISABLE KEYS */;
INSERT INTO `Reservations` VALUES (20,'2005-11-11','2011-12-11',1,1),(31,'2023-01-12','2023-02-12',7,2);
/*!40000 ALTER TABLE `Reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `admin` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (1,'admin','admin',1),(2,'abc','abc',0),(4,'1','1',1),(5,'','',0),(7,'123','123',0),(9,'1234','123',0),(10,'gdsgds','gdsgsd',0),(15,'12','532',0),(17,'5','352',0),(19,'hijgdas','1234',0),(20,'fjsaofjpo','fgdgd',0),(21,'12345','12415',0);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-17 12:41:11
