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
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` bit(1) NOT NULL,
  `firstname` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `lastname` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `verification_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `company_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `send_updates` bit(1) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1j9d9a06i600gd43uu3km82jw` (`email`),
  KEY `FKdc8dahbfy46q8r9le5nnxkgr3` (`location_id`),
  CONSTRAINT `FKdc8dahbfy46q8r9le5nnxkgr3` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'info@roester.ch',_binary '','Yves','Peissard','$2a$10$XVfWzeaJm/w/XIJ16wz4R.QD6ALvdQLUSI20feRaMP24O5KM1bz8G','3wT0tLg1aL0iHHAPeKqupJmnlfND2MhUzXeQdWBSub7bQyTtGYg75WVhslKXYdG9',6,'röster.ch',_binary '\0',NULL,NULL),(129,'ypeissard@gmail.com',_binary '','Yves','Peissard','$2a$10$PdJNxc42RbheClSQZeZbAuyxBFGRUeRco187WS7fwdF.cVAQpjW36','btaAw0t70FPzXJyJx9d1QAMFR5ExpS9OEl3ape2eRQOZs6DSNsd8GLKHnIs2uGgJ',6,NULL,_binary '\0',NULL,NULL),(159,'mail@example.com',_binary '',NULL,NULL,'$2a$10$g8RQuZsSkF4VMWbCxV6I/OneQ6z2JyjKLcWmOPfL/4nhgtrx1P382','AT0m4xWMtIyz8nvwhhAf45ImCrOVAtvreuImeYCF889ghElKPRAPez1ZkH4HcGL6',NULL,NULL,_binary '\0',NULL,NULL),(160,'dummy@mail.com',_binary '',NULL,NULL,'$2a$10$7OmnoIognPrn5liTJrY5ieLsBoFO1FEwjJjgQVrXMKW9uvVwhhikq','fgAa3wpHNpnYOrS1Mqe1NlRryvUIsu7XZLNT1TktCDZqmTLevuPjwCfx9kjc0Ab0',NULL,NULL,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrier`
--

DROP TABLE IF EXISTS `carrier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contact_number` varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `website` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrier`
--

LOCK TABLES `carrier` WRITE;
/*!40000 ALTER TABLE `carrier` DISABLE KEYS */;
INSERT INTO `carrier` VALUES (1,'',NULL,'Die Schweizerische Post AG',NULL,NULL,NULL),(2,'9876',NULL,'UPS',NULL,'2024-10-23 22:00:58.449700','2024-10-23 22:02:28.185546');
/*!40000 ALTER TABLE `carrier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_user_id` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_surd71kgr0t6ityyixak3lq0a` (`app_user_id`),
  CONSTRAINT `FK13iilo76wx2fenw2v0pn910i2` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,NULL,NULL),(14,129,NULL,NULL),(34,159,NULL,NULL),(35,160,NULL,NULL);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `cart_id` int NOT NULL,
  `event_product_amount_id` int DEFAULT NULL,
  `variant_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlmddnw6pd7gder2x4r07f1ves` (`cart_id`),
  KEY `FKb49qa1u2dddtgfu5l5ghopm9b` (`event_product_amount_id`),
  KEY `FKtffu8neg3nei9d2ho9qxyolso` (`variant_id`),
  CONSTRAINT `FKb49qa1u2dddtgfu5l5ghopm9b` FOREIGN KEY (`event_product_amount_id`) REFERENCES `event_product_amount` (`id`),
  CONSTRAINT `FKlmddnw6pd7gder2x4r07f1ves` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKtffu8neg3nei9d2ho9qxyolso` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`id`),
  CONSTRAINT `cart_item_chk_1` CHECK ((`amount` >= 1))
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (112,2,14,1,12),(183,2,1,1,12),(184,1,34,1,12),(185,1,35,1,12),(186,1,1,1,20);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` datetime(6) NOT NULL,
  `days_before_subscription_closes` int NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `location_id` int NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrsjviye5pxh9d1rroqg6x108n` (`location_id`),
  CONSTRAINT `FKrsjviye5pxh9d1rroqg6x108n` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'2024-10-31 20:11:54.811000',2,NULL,'Herbströstungg',1,NULL,NULL);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_app_user`
--

DROP TABLE IF EXISTS `event_app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_app_user` (
  `event_id` int NOT NULL,
  `app_user_id` int NOT NULL,
  PRIMARY KEY (`event_id`,`app_user_id`),
  KEY `FKixpex8cysr6c8jhsffv3b487` (`app_user_id`),
  CONSTRAINT `FK86n4juaxtb2pl5vxruqdxk8ta` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `FKixpex8cysr6c8jhsffv3b487` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_app_user`
--

LOCK TABLES `event_app_user` WRITE;
/*!40000 ALTER TABLE `event_app_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_product_amount`
--

DROP TABLE IF EXISTS `event_product_amount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_product_amount` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount_left` double DEFAULT NULL,
  `amount_total` double DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6yjethbe5v2kjj0kc7dkwmrk8` (`event_id`),
  KEY `FK8sjcie5ndsaxxch6s4343v393` (`product_id`),
  CONSTRAINT `FK6yjethbe5v2kjj0kc7dkwmrk8` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `FK8sjcie5ndsaxxch6s4343v393` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_product_amount`
--

LOCK TABLES `event_product_amount` WRITE;
/*!40000 ALTER TABLE `event_product_amount` DISABLE KEYS */;
INSERT INTO `event_product_amount` VALUES (1,10,10,1,1,NULL,NULL),(2,5,5,1,2,NULL,NULL),(3,5,5,1,3,NULL,NULL);
/*!40000 ALTER TABLE `event_product_amount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `postal_code` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `street` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `street_number` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Schwarzenburg',NULL,NULL,'3150','Thangässli','14',NULL,NULL),(6,'Bern',NULL,NULL,'3027','Kasparstrasse','17',NULL,NULL),(7,'string',0,0,'0','string','',NULL,NULL),(8,'string',0,0,'0','string','',NULL,NULL),(9,'string',0,0,'0','string','',NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `total_cost` decimal(10,2) DEFAULT NULL,
  `app_user_id` int DEFAULT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm8h8wu73sjwpc5kn8i0xo74j5` (`app_user_id`),
  KEY `FK15irmd4an64n9rwswd8a4fcb9` (`status_id`),
  CONSTRAINT `FK15irmd4an64n9rwswd8a4fcb9` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FKm8h8wu73sjwpc5kn8i0xo74j5` FOREIGN KEY (`app_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `event_id` int DEFAULT NULL,
  `order_id` int NOT NULL,
  `variant_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qtwcjeylhnfo99c2tabwhtxvl` (`variant_id`),
  KEY `FKm6m0jtn73kimxwuqwaognua8q` (`event_id`),
  KEY `FK8e2do4k6gjtngqr7c4rf4jryd` (`order_id`),
  CONSTRAINT `FK8e2do4k6gjtngqr7c4rf4jryd` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKisooitr3kygqngrgbmnb6mnhj` FOREIGN KEY (`variant_id`) REFERENCES `variant` (`id`),
  CONSTRAINT `FKm6m0jtn73kimxwuqwaognua8q` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `position_chk_1` CHECK ((`amount` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount_in_stock` double DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` longtext COLLATE utf8mb4_general_ci,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price_per_unit` decimal(38,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `sold_unit_id` int DEFAULT NULL,
  `stock_id` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj3fnxg3y22s0pgdnthuoxch5q` (`sold_unit_id`),
  KEY `FKqe1osfu3g49d81f1favbue3ow` (`stock_id`),
  CONSTRAINT `FKj3fnxg3y22s0pgdnthuoxch5q` FOREIGN KEY (`sold_unit_id`) REFERENCES `unit` (`id`),
  CONSTRAINT `FKqe1osfu3g49d81f1favbue3ow` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,15,NULL,'Dieser Kaffee besticht durch seine sanften Aromen von Schokolade, Nuss und Honig. Sein intensiver Körper sorgt für einen vollmundigen Genuss, während der würzige Nachgeschmack das Geschmackserlebnis abrundet.\r\n\r\nObwohl Robusta-Kaffees geschmacklich weniger komplex sind als Arabicas, erfreuen sie sich großer Beliebtheit in verschiedenen Mischungen. Sie verleihen dem Kaffee nicht nur eine vollere Textur, sondern sorgen auch für eine herrliche Crema. Darüber hinaus enthalten sie deutlich mehr Koffein als Arabicas, was sie zu einer ausgezeichneten Wahl für Kaffeeliebhaber macht, die einen kräftigen Kick suchen.','Rohkaffee Bio & Fair: Tansania Robusta',40.00,NULL,2,1,NULL,NULL),(2,15,NULL,'Tauche ein in die Welt unseres milden, runden und ausgewogenen Kaffees. Mit einem sanften, vollmundigen Körper und einer angenehmen mittleren Säure bietet dieser Kaffee ein unvergessliches Geschmackserlebnis.\r\n\r\nDie elegante Süsse wird von verlockenden Noten von Haselnuss und Kakao begleitet, die jeden Schluck zu einem wahren Genuss machen.\r\n\r\nLass dich verführen und entdecke, wie dieser Kaffee dein tägliches Ritual bereichern kann. Gönn dir diesen aussergewöhnlichen Genuss – perfekt für jeden Kaffeeliebhaber!','Rohkaffee Bio & Fair: Peru Cajamarca',40.00,NULL,2,1,NULL,NULL),(3,15,NULL,'Tauche ein in die Welt unseres milden, runden und ausgewogenen Kaffees. Mit einem sanften, vollmundigen Körper und einer angenehmen mittleren Säure bietet dieser Kaffee ein unvergessliches Geschmackserlebnis.\r\n\r\nDie elegante Süsse wird von verlockenden Noten von Haselnuss und Kakao begleitet, die jeden Schluck zu einem wahren Genuss machen.\r\n\r\nLass dich verführen und entdecke, wie dieser Kaffee dein tägliches Ritual bereichern kann. Gönn dir diesen aussergewöhnlichen Genuss – perfekt für jeden Kaffeeliebhaber!','Rohkaffee Bio & Direct: Brasilien Red Iapar',40.00,NULL,2,1,NULL,NULL),(4,20,NULL,'Geniesse die perfekte Balance mit unserem Brasilien Natural Santos – ein Kaffee, der mit seiner milden, runden und ausgewogenen Natur begeistert. Mit einem weichen, vollmundigen Körper und einer angenehmen, geringen Säure ist dieser Kaffee der Inbegriff von Genuss.\r\n\r\nDie ausgeprägte Süsse wird von köstlichen Noten von Milchschokolade, Haselnuss und feinen Gewürzen begleitet, die jeden Schluck zu einem unvergleichlichen Erlebnis machen.\r\n\r\nOb du ihn als sortenreinen Kaffee (Single Origin) oder als Grundlage für kreative Mischungen (Blends) geniesst, der Brasilien Natural Santos bietet dir die Flexibilität, die du suchst.\r\n\r\nSeine ausgewogene Beschaffenheit und die harmonische Süsse machen ihn zur idealen Wahl für den Siebträger – perfekt für Espresso, Café Crème oder Cappuccino. Auch in der French Press oder im Bialetti Espressokocher entfaltet er seine Aromen und überzeugt mit nussigen und schokoladigen Noten.\r\n\r\nGönn dir den Brasilien Natural Santos und tauche ein in die Welt des vollendeten Kaffeegenusses – ein Muss für jeden Kaffeeliebhaber!','Rohkaffee Bio & Fair: Brasilien natural Santos',40.00,NULL,2,1,NULL,NULL),(5,0,NULL,'Entdecke einen Espresso, der das Beste aus zwei faszinierenden Kaffeesorten vereint. Diese Mischung besteht aus sanftem Tansania Robusta und feinstem Brasilien natural Santos Arabica – beide natürlich bio und fair gehandelt.\r\n\r\nDer Tansania Robusta bringt Noten von dunkler Schokolade, Nüssen und einem Hauch Honig mit. Sein intensiver Körper und der würzige Nachgeschmack verleihen Deinem Espresso eine kraftvolle Tiefe. Mit seinem höheren Koffeingehalt sorgt der Robusta außerdem für einen starken Energieschub und zaubert eine dichte, goldene Crema.\r\n\r\nDer Brasilien Santos Arabica sorgt für die perfekte Balance: mild, weich und süß, mit Aromen von Milchschokolade, Haselnuss und Gewürzen. Seine geringe Säure und der runde, vollmundige Geschmack machen jeden Schluck zu einem echten Genuss.\r\n\r\nFreu Dich auf einen Espresso, der die Kraft und Fülle des Robustas mit der feinen Süße des Arabicas kombiniert – für einen cremigen, intensiven und perfekt ausgewogenen Kaffee, der Dich begeistert.','Espresso Mischung',40.00,NULL,2,1,NULL,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_product`
--

DROP TABLE IF EXISTS `product_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `part_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `unit_id` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnkre6c0xintu9psbkqey33m1v` (`part_id`),
  KEY `FKbw3c1r9ndj45gq30ny8rous19` (`product_id`),
  KEY `FK8q9kqes7d0m1p10jnryvfqe23` (`unit_id`),
  CONSTRAINT `FK8q9kqes7d0m1p10jnryvfqe23` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`id`),
  CONSTRAINT `FKbw3c1r9ndj45gq30ny8rous19` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKnkre6c0xintu9psbkqey33m1v` FOREIGN KEY (`part_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_product`
--

LOCK TABLES `product_product` WRITE;
/*!40000 ALTER TABLE `product_product` DISABLE KEYS */;
INSERT INTO `product_product` VALUES (1,20,1,5,3,NULL,NULL),(2,80,4,5,3,NULL,NULL);
/*!40000 ALTER TABLE `product_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_property`
--

DROP TABLE IF EXISTS `product_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_property` (
  `property_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`property_id`,`product_id`),
  KEY `FKluvcwopraiag8xdm5qxf4exs1` (`product_id`),
  CONSTRAINT `FKaj1b1oapeuwx3d7trj3rlxki` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`),
  CONSTRAINT `FKluvcwopraiag8xdm5qxf4exs1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_property`
--

LOCK TABLES `product_property` WRITE;
/*!40000 ALTER TABLE `product_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_tag`
--

DROP TABLE IF EXISTS `product_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_tag` (
  `tag_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`tag_id`,`product_id`),
  KEY `FK55wqghhg52wsdk6wmxftvya4y` (`product_id`),
  CONSTRAINT `FK55wqghhg52wsdk6wmxftvya4y` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKhygs5cw0xgh6s7ldsx3djlv73` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_tag`
--

LOCK TABLES `product_tag` WRITE;
/*!40000 ALTER TABLE `product_tag` DISABLE KEYS */;
INSERT INTO `product_tag` VALUES (3,1),(2,2),(2,3),(2,4),(3,4);
/*!40000 ALTER TABLE `product_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `shipment_cost` decimal(10,2) DEFAULT NULL,
  `shipment_date` date DEFAULT NULL,
  `tracking_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `carrier_id` int NOT NULL,
  `order_id` int NOT NULL,
  `shipping_method_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrxouko0hu5lhbxn90t6r4h9pi` (`carrier_id`),
  KEY `FKdn1s1alngkt2l7rjvl7uc4dl5` (`order_id`),
  KEY `FK1nne0sqhwixe2u7lv4dtnowrp` (`shipping_method_id`),
  CONSTRAINT `FK1nne0sqhwixe2u7lv4dtnowrp` FOREIGN KEY (`shipping_method_id`) REFERENCES `shipping_method` (`id`),
  CONSTRAINT `FKdn1s1alngkt2l7rjvl7uc4dl5` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKrxouko0hu5lhbxn90t6r4h9pi` FOREIGN KEY (`carrier_id`) REFERENCES `carrier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_method`
--

DROP TABLE IF EXISTS `shipping_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `estimated_delivery_time` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `weight_in_grams_limit` decimal(38,2) NOT NULL,
  `inner_depth_in_cm` decimal(38,2) NOT NULL,
  `inner_height_in_cm` decimal(38,2) NOT NULL,
  `inner_width_in_cm` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_method`
--

LOCK TABLES `shipping_method` WRITE;
/*!40000 ALTER TABLE `shipping_method` DISABLE KEYS */;
INSERT INTO `shipping_method` VALUES (1,'2024-10-27 14:34:54.994034','2024-10-27 14:34:54.994034','Standard shipping method with delivery in 3-5 business days','3-5 Werktage','Briefpost',1.40,320.00,1.50,17.10,24.50),(2,'2024-10-27 14:35:41.722757','2024-10-27 14:35:41.722757','Standard shipping method with delivery in 3-5 business days','3-5 Werktage','Paket',8.00,2000.00,60.00,60.00,100.00);
/*!40000 ALTER TABLE `shipping_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_reccgx9nr0a8dwv201t44l6pd` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (12,'Cancelled'),(13,'Delivered'),(10,'Paid'),(14,'Returned'),(11,'Shipped'),(9,'Waiting for payment');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `location_id` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgturmptgssd7mshfjtnd20wfr` (`location_id`),
  CONSTRAINT `FKgturmptgssd7mshfjtnd20wfr` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,1,NULL,NULL);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1wdpsed5kna2y38hnbgrnhi5b` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (2,'arabica',NULL,NULL),(3,'robusta',NULL,NULL);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (1,'g',NULL,NULL),(2,'kg',NULL,NULL),(3,'%',NULL,NULL);
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount_in_stock` double DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `stock_multiplier` decimal(38,2) NOT NULL,
  `display_unit_id` int DEFAULT NULL,
  `product_id` int NOT NULL,
  `stock_id` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `weight_in_grams` decimal(38,2) NOT NULL,
  `active` bit(1) NOT NULL,
  `is_separate_shipment` bit(1) NOT NULL,
  `separate_shipment_comment` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `depth_in_cm` decimal(38,2) NOT NULL,
  `height_in_cm` decimal(38,2) NOT NULL,
  `width_in_cm` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj3oiipf7v4epj6tet3ol8vkd0` (`display_unit_id`),
  KEY `FKs6rahw3dxw77v1a4ovlkowf5f` (`product_id`),
  KEY `FKiiw8o4iyu0ei5bxrp30y9c61w` (`stock_id`),
  CONSTRAINT `FKiiw8o4iyu0ei5bxrp30y9c61w` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `FKj3oiipf7v4epj6tet3ol8vkd0` FOREIGN KEY (`display_unit_id`) REFERENCES `unit` (`id`),
  CONSTRAINT `FKs6rahw3dxw77v1a4ovlkowf5f` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (10,NULL,NULL,'560',0.56,1,5,NULL,NULL,NULL,560.00,_binary '\0',_binary '\0',NULL,6.00,20.00,27.00),(11,NULL,'<null>','320',0.32,1,5,NULL,NULL,NULL,320.00,_binary '\0',_binary '','Briefpost',1.40,17.00,24.00),(12,NULL,'<null>','320',0.32,1,1,NULL,NULL,NULL,320.00,_binary '\0',_binary '','Briefpost',1.40,17.00,24.00),(14,NULL,NULL,'320',0.32,1,2,NULL,NULL,NULL,320.00,_binary '\0',_binary '\0',NULL,4.00,17.00,24.00),(15,NULL,NULL,'560',0.56,1,2,NULL,NULL,NULL,560.00,_binary '\0',_binary '\0',NULL,6.00,20.00,27.00),(16,NULL,NULL,'320',0.32,1,3,NULL,NULL,NULL,320.00,_binary '\0',_binary '\0',NULL,4.00,17.00,24.00),(17,NULL,NULL,'560',0.56,1,3,NULL,NULL,NULL,560.00,_binary '\0',_binary '\0',NULL,6.00,20.00,27.00),(18,NULL,NULL,'320',0.32,1,4,NULL,NULL,NULL,320.00,_binary '\0',_binary '\0',NULL,4.00,17.00,24.00),(19,NULL,NULL,'560',0.56,1,4,NULL,NULL,NULL,560.00,_binary '\0',_binary '\0',NULL,6.00,20.00,27.00),(20,NULL,NULL,'560',0.56,1,1,NULL,NULL,NULL,560.00,_binary '\0',_binary '\0',NULL,6.00,20.00,27.00);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-03 15:05:34
