-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: 127.0.0.1    Database: roester
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `verification_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_user_email_uindex` (`email`),
  KEY `app_user_location_id_fk` (`location_id`),
  CONSTRAINT `app_user_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'roester@roester.ch','$2a$10$WRIeaRJm8NfEYcFGsoWDS.iZqdV4W5FgPgBbMmSS8j7laLwwVRxqe',_binary '',NULL,1);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coffee`
--

DROP TABLE IF EXISTS `coffee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coffee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `coffee_type_id_fk` (`type_id`),
  CONSTRAINT `coffee_type_id_fk` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coffee`
--

LOCK TABLES `coffee` WRITE;
/*!40000 ALTER TABLE `coffee` DISABLE KEYS */;
INSERT INTO `coffee` VALUES (1,'Rohkaffee Bio & Fair: Tansania Robusta',2),(2,'Rohkaffee Bio & Direct: Brasilien Red Iapar',1);
/*!40000 ALTER TABLE `coffee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `street` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `street_nr` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `city` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `postal_code` int NOT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Thangässli','14','Schwarzenburg',3150,7.339771352558502,46.81702437755243);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_user_id` int NOT NULL,
  `kg` int NOT NULL,
  `roast_id` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_app_user_id_fk` (`app_user_id`),
  KEY `order_roast_id_fk` (`roast_id`),
  CONSTRAINT `order_app_user_id_fk` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `order_roast_id_fk` FOREIGN KEY (`roast_id`) REFERENCES `roast` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roast`
--

DROP TABLE IF EXISTS `roast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roast` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roast_event_id` int NOT NULL,
  `coffee_id` int NOT NULL,
  `kg` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `roast_coffee_id_fk` (`coffee_id`),
  KEY `roast_roast_event_id_fk` (`roast_event_id`),
  CONSTRAINT `roast_coffee_id_fk` FOREIGN KEY (`coffee_id`) REFERENCES `coffee` (`id`),
  CONSTRAINT `roast_roast_event_id_fk` FOREIGN KEY (`roast_event_id`) REFERENCES `roast_event` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roast`
--

LOCK TABLES `roast` WRITE;
/*!40000 ALTER TABLE `roast` DISABLE KEYS */;
INSERT INTO `roast` VALUES (2,1,1,10),(3,1,2,5),(4,2,2,20);
/*!40000 ALTER TABLE `roast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roast_event`
--

DROP TABLE IF EXISTS `roast_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roast_event` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `description` tinytext COLLATE utf8mb4_general_ci,
  `location_id` int NOT NULL,
  `days_before_orders_close` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `roast_event_location_id_fk` (`location_id`),
  CONSTRAINT `roast_event_location_id_fk` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roast_event`
--

LOCK TABLES `roast_event` WRITE;
/*!40000 ALTER TABLE `roast_event` DISABLE KEYS */;
INSERT INTO `roast_event` VALUES (1,'2024-04-27',NULL,1,1),(2,'2024-05-18',NULL,1,1);
/*!40000 ALTER TABLE `roast_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'Arabica'),(2,'Robusta');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-14  2:14:51
