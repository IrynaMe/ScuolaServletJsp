-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: gestione_scuola
-- ------------------------------------------------------
-- Server version	5.7.24

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
-- Table structure for table `allievo`
--

DROP TABLE IF EXISTS `allievo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allievo` (
  `cf` char(16) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `sesso` varchar(1) NOT NULL,
  `stato_nascita` varchar(255) NOT NULL,
  `provincia_nascita` varchar(255) NOT NULL,
  `comune_nascita` varchar(255) NOT NULL,
  `data_nascita` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `abilitato` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cf`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allievo`
--

LOCK TABLES `allievo` WRITE;
/*!40000 ALTER TABLE `allievo` DISABLE KEYS */;
INSERT INTO `allievo` VALUES ('AAAAAA11A11A111A','Mario','Rossi','m','Italia','Roma','Roma','2009-01-01','mariorossi@gmail.com',0),('AADDDD55D55D555D','Marta','Lamponi','f','Italia','Roma','Roma','2011-04-18','martalamp@gmail.com',1),('ADDDDD31D33D313A','Sabrina','Caffetti','f','Italia','Roma','Roma','2011-05-06','sabricaffe@gmail.com',1),('ADDDDD44D44D444A','Alessandra','Spagghetti','f','Italia','Roma','Roma','2008-12-31','alessandraspag@gmail.com',1),('ADDDDD55D33D333A','Barbara','Burbone','f','Italia','Roma','Roma','2005-10-18','barbarbara@gmail.com',1),('ADDDVO33D33D333A','Barbara','Zucchini','f','Italia','Bologna','Bologna','2010-09-27','barzucch@gmail.com',1),('ALDDDD00D33D333A','Katia','Broccoli','f','Italia','Roma','Roma','2008-10-29','katbr@gmail.com',1),('ALDDDD01D33D333A','Fabrizio','Pizzatti','m','Italia','Roma','Roma','2012-02-12','fabpiz@gmail.com',1),('ALDDDD02D44D444D','Massimiliano','Martini','m','Italia','Roma','Roma','2007-02-07','massmart@gmail.com',1),('ALDDDD55D55D555D','Sonia','Orsi','f','Italia','Roma','Frascati','2005-10-26','soors@gmail.com',1),('ALIDDD99D33D333A','Federica','Gamberini','f','Estero','Estero','Estero','2011-08-14','fedgam@gmail.com',1),('ALLDDD04D33D333A','Viktor','Gialli','m','Italia','Milano','Milano','2005-12-11','vikgial@gmail.com',1),('ALZZZZ11Z11Z111Q','Valentina','Verdi','f','Italia','Bari','Capurso','2012-08-24','valver@gmail.com',1),('DADDDD55D55D555D','Sofia','Santi','f','Italia','Roma','Roma','2004-05-30','sofiasanti@gmail.com',1),('DDDDDD55D55D555S','Roberto','Zampetti','m','Italia','Roma','Roma','2004-10-27','roberzampet@gmail.com',0),('NDDDDD55D55D555D','Mia','Zucchetti','f','Italia','Roma','Roma','2010-05-25','miazu@gmail.com',1),('SDDDDD55D55D555D','Paolo','Salvi','f','Italia','Roma','Roma','2006-02-03','paolosalvi@gmail.com',1),('ZDDDDD55D55D555D','Mauro','Cuoco','f','Italia','Roma','Roma','2006-12-12','maurocuoco@gmail.com',0),('ZZZZZZ11Z11Z111Q','Sergio','Bottone','m','Italia','Roma','Roma','2007-06-06','srgiobottone@gmail.com',1);
/*!40000 ALTER TABLE `allievo` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cf_allievo_format` BEFORE INSERT ON `allievo` FOR EACH ROW BEGIN
    DECLARE msg VARCHAR(255);
    IF NOT NEW.cf REGEXP '^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$' THEN
        SET msg = CONCAT('Invalid codice_fiscale format: ', NEW.cf);
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-29 22:02:43
