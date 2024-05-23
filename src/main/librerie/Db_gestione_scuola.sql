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
INSERT INTO `allievo` VALUES ('AAAAAA11A11A111A','Mario','Rossi','m','Italia','Roma','Roma','2009-01-01','mariorossi@gmail.com',0),('AADDDD55D55D555D','Marta','Lamponi','f','Italia','Roma','Roma','2011-04-18','martalamp@gmail.com',1),('ADDDDD31D33D313A','Sabrina','Caffetti','f','Italia','Roma','Roma','2011-05-06','sabricaffe@gmail.com',1),('ADDDDD44D44D444A','Alessandra','Spagghetti','f','Italia','Roma','Roma','2008-12-31','alessandraspag@gmail.com',1),('ADDDDD55D33D333A','Barbara','Burbone','f','Italia','Roma','Roma','2005-10-18','barbarbara@gmail.com',1),('CCCCC111C11C111C','Giulia','Bianchi','f','Italia','Milano','Milano','2008-03-15','giuliabianchi@gmail.com',1),('DADDDD55D55D555D','Sofia','Santi','f','Italia','Roma','Roma','2004-05-30','sofiasanti@gmail.com',1),('DDDDD111D11D111D','Luca','Russo','m','Italia','Napoli','Napoli','2009-06-20','lucarusso@gmail.com',1),('DDDDDD55D55D555S','Roberto','Zampetti','m','Italia','Roma','Roma','2004-10-27','roberzampet@gmail.com',0),('EEEEE111E11E111E','Chiara','Ferrari','f','Italia','Torino','Torino','2010-07-25','chiaraferrari@gmail.com',0),('FFFFFF111F11F111','Marco','Galli','m','Italia','Firenze','Firenze','2009-09-10','marcogalli@gmail.com',1),('GGGGG111G11G111G','Sara','Martini','f','Italia','Bologna','Bologna','2010-11-05','saramartini@gmail.com',1),('NDDDDD55D55D555D','Mia','Zucchetti','f','Italia','Roma','Roma','2010-05-25','miazu@gmail.com',1),('SDDDDD55D55D555D','Paolo','Salvi','f','Italia','Roma','Roma','2006-02-03','paolosalvi@gmail.com',1),('SSSSSS11S11S111S','Anna','Verdi','f','Italia','Roma','Roma','2010-01-01','annaverdi@gmail.com',1),('ZDDDDD55D55D555D','Mauro','Cuoco','f','Italia','Roma','Roma','2006-12-12','maurocuoco@gmail.com',0),('ZZZZZZ11Z11Z111Q','mario','Bottone','m','Italia','Roma','Roma','2007-06-06','mariobottone@gmail.com',1);
/*!40000 ALTER TABLE `allievo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allievo_in_classe`
--

DROP TABLE IF EXISTS `allievo_in_classe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allievo_in_classe` (
  `anno_scolastico` char(9) NOT NULL,
  `cf_allievo` char(16) NOT NULL,
  `livello_classe` int(1) NOT NULL,
  `sezione_classe` varchar(2) NOT NULL,
  PRIMARY KEY (`anno_scolastico`,`cf_allievo`),
  KEY `livello_classe` (`livello_classe`,`sezione_classe`),
  KEY `cf_allievo` (`cf_allievo`),
  CONSTRAINT `allievo_in_classe_ibfk_1` FOREIGN KEY (`livello_classe`, `sezione_classe`) REFERENCES `classe` (`livello`, `sezione`),
  CONSTRAINT `allievo_in_classe_ibfk_2` FOREIGN KEY (`cf_allievo`) REFERENCES `allievo` (`cf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allievo_in_classe`
--

LOCK TABLES `allievo_in_classe` WRITE;
/*!40000 ALTER TABLE `allievo_in_classe` DISABLE KEYS */;
INSERT INTO `allievo_in_classe` VALUES ('2023/2024','AADDDD55D55D555D',1,'A'),('2023/2024','ADDDDD31D33D313A',1,'A'),('2023/2024','ADDDDD44D44D444A',1,'A'),('2023/2024','ADDDDD55D33D333A',1,'A'),('2023/2024','EEEEE111E11E111E',1,'A'),('2023/2024','DDDDD111D11D111D',2,'B'),('2023/2024','FFFFFF111F11F111',2,'B'),('2022/2023','SSSSSS11S11S111S',4,'D'),('2023/2024','AAAAAA11A11A111A',4,'D'),('2023/2024','GGGGG111G11G111G',4,'D');
/*!40000 ALTER TABLE `allievo_in_classe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `amministrativo`
--

DROP TABLE IF EXISTS `amministrativo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amministrativo` (
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
-- Dumping data for table `amministrativo`
--

LOCK TABLES `amministrativo` WRITE;
/*!40000 ALTER TABLE `amministrativo` DISABLE KEYS */;
INSERT INTO `amministrativo` VALUES ('ADDDDD33D33D333A','Anna','Zafferano','f','Italia','Roma','Roma','1986-04-10','annazaff@gmail.com',1),('QQQQQQ11Q11Q111Q','Alberto','Marconi','m','Italia','Roma','Roma','1985-03-03','albertomarconi@gmail.com',1);
/*!40000 ALTER TABLE `amministrativo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classe`
--

DROP TABLE IF EXISTS `classe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classe` (
  `livello` int(1) NOT NULL,
  `sezione` varchar(2) NOT NULL,
  `abilitato` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`livello`,`sezione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classe`
--

LOCK TABLES `classe` WRITE;
/*!40000 ALTER TABLE `classe` DISABLE KEYS */;
INSERT INTO `classe` VALUES (1,'A',1),(2,'B',1),(3,'C',0),(4,'D',1),(5,'E',0);
/*!40000 ALTER TABLE `classe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente` (
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
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES ('DDDDDD05D55D555D','Iryna','Mel','f','Italia','Roma','Roma','1978-10-03','irinaddda@gmail.com',1),('DDDDDD22D22D222D','Paolo','Faggioli','m','Italia','Roma','Roma','1982-02-02','paolo.faggioli@gmail.com',1),('DDDDDD33D33D333D','Daniele','Conti','m','Italia','Roma','Roma','1984-06-13','danieleconti@gmail.com',1),('DDDDDD44D44D444D','Francesca','Ferrari','f','Italia','Roma','Roma','1981-02-04','francescaferrir@gmail.com',1),('DDDDDD55D55D550D','Guliana','Sardini','f','Italia','Roma','Roma','1978-04-26','gulianasardini@gmail.com',1),('DDDDDD55D55D555D','Samuele','Innocenti','f','Italia','Roma','Roma','1978-06-29','samueleinnocenti@gmail.com',1),('DDDDDD66D66D666D','Federica','Baccardi','f','Italia','Roma','Roma','2005-11-30','federicabaccardi@gmail.com',1),('FDDDDD33D93D333A','Carla','Bruni','f','Italia','Roma','Roma','1992-06-24','carlabruni@gmail.com',1),('HHHHH111H11H111H','Alessandro','Moretti','m','Italia','Roma','Roma','1980-05-12','alessandrom@gmail.com',0),('IIIII111I11I111I','Laura','Conti','f','Italia','Milano','Milano','1985-08-25','lauraconti@gmail.com',1),('JJJJJ111J11J111J','Roberto','Ferrari','m','Italia','Napoli','Napoli','1978-12-10','robertoferrari@gmail.com',1),('KKKKK111K11K111K','Francesca','Ricci','f','Italia','Firenze','Firenze','1983-09-08','francescaricci@gmail.com',1),('ZZZZZZ11Z11Z111Z','Silvio','Bianchi','m','Italia','Roma','Roma','1982-02-02','silviob@gmail.com',1);
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente_classe`
--

DROP TABLE IF EXISTS `docente_classe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente_classe` (
  `anno_scolastico` char(9) NOT NULL,
  `cf_docente` char(16) NOT NULL,
  `livello_classe` int(1) NOT NULL,
  `sezione_classe` varchar(2) NOT NULL,
  `nome_materia` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`anno_scolastico`,`cf_docente`,`livello_classe`,`sezione_classe`),
  UNIQUE KEY `anno_scolastico` (`anno_scolastico`,`livello_classe`,`sezione_classe`,`nome_materia`),
  KEY `livello_classe` (`livello_classe`,`sezione_classe`),
  KEY `cf_docente` (`cf_docente`),
  KEY `nome_materia` (`nome_materia`),
  CONSTRAINT `docente_classe_ibfk_1` FOREIGN KEY (`livello_classe`, `sezione_classe`) REFERENCES `classe` (`livello`, `sezione`),
  CONSTRAINT `docente_classe_ibfk_2` FOREIGN KEY (`cf_docente`) REFERENCES `docente` (`cf`),
  CONSTRAINT `docente_classe_ibfk_3` FOREIGN KEY (`nome_materia`) REFERENCES `materia` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente_classe`
--

LOCK TABLES `docente_classe` WRITE;
/*!40000 ALTER TABLE `docente_classe` DISABLE KEYS */;
INSERT INTO `docente_classe` VALUES ('2023/2024','HHHHH111H11H111H',1,'A','Biologia'),('2023/2024','DDDDDD22D22D222D',4,'D','Fisica'),('2023/2024','IIIII111I11I111I',2,'B','Fisica'),('2023/2024','KKKKK111K11K111K',1,'A','Geografia'),('2023/2024','JJJJJ111J11J111J',4,'D','Italiano'),('2023/2024','ZZZZZZ11Z11Z111Z',2,'B','Matematica');
/*!40000 ALTER TABLE `docente_classe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente_materia`
--

DROP TABLE IF EXISTS `docente_materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente_materia` (
  `cf_docente` char(16) NOT NULL,
  `nome_materia` varchar(255) NOT NULL,
  PRIMARY KEY (`cf_docente`,`nome_materia`),
  KEY `materia_fkey` (`nome_materia`),
  CONSTRAINT `docente_fkey` FOREIGN KEY (`cf_docente`) REFERENCES `docente` (`cf`),
  CONSTRAINT `materia_fkey` FOREIGN KEY (`nome_materia`) REFERENCES `materia` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente_materia`
--

LOCK TABLES `docente_materia` WRITE;
/*!40000 ALTER TABLE `docente_materia` DISABLE KEYS */;
/*!40000 ALTER TABLE `docente_materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `codice` varchar(10) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `abilitato` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`codice`),
  UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia`
--

LOCK TABLES `materia` WRITE;
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
INSERT INTO `materia` VALUES ('BIO001','Biologia',1),('FIS001','Fisica',1),('GEO001','Geografia',1),('ITA001','Italiano',1),('LAT001','Latino',0),('MAT001','Matematica',1),('REL001','Religione',1),('SCI001','Scienze',1),('STO001','Storia',1),('TED001','Tedesco',0);
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prova`
--

DROP TABLE IF EXISTS `prova`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prova` (
  `data_ora` datetime NOT NULL,
  `cf_allievo` char(16) NOT NULL,
  `cf_docente` char(16) NOT NULL,
  `nome_materia` varchar(255) NOT NULL,
  `voto` int(2) DEFAULT NULL,
  `abilitato` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`data_ora`),
  KEY `cf_allievo` (`cf_allievo`),
  KEY `cf_docente` (`cf_docente`),
  KEY `nome_materia` (`nome_materia`),
  CONSTRAINT `prova_ibfk_1` FOREIGN KEY (`cf_allievo`) REFERENCES `allievo` (`cf`),
  CONSTRAINT `prova_ibfk_2` FOREIGN KEY (`cf_docente`) REFERENCES `docente` (`cf`),
  CONSTRAINT `prova_ibfk_3` FOREIGN KEY (`nome_materia`) REFERENCES `materia` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prova`
--

LOCK TABLES `prova` WRITE;
/*!40000 ALTER TABLE `prova` DISABLE KEYS */;
INSERT INTO `prova` VALUES ('2023-10-15 09:00:00','AAAAAA11A11A111A','HHHHH111H11H111H','Biologia',8,0),('2023-10-16 10:30:00','EEEEE111E11E111E','HHHHH111H11H111H','Biologia',9,1),('2023-10-17 11:45:00','DDDDD111D11D111D','IIIII111I11I111I','Fisica',7,1),('2023-11-15 09:00:00','AAAAAA11A11A111A','HHHHH111H11H111H','Biologia',6,1),('2024-05-09 08:00:00','AAAAAA11A11A111A','ZZZZZZ11Z11Z111Z','Matematica',8,1),('2024-05-13 18:42:42','AAAAAA11A11A111A','DDDDDD22D22D222D','Fisica',5,1),('2024-05-13 18:45:32','AAAAAA11A11A111A','DDDDDD22D22D222D','Fisica',6,1);
/*!40000 ALTER TABLE `prova` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `abilitato` int(1) NOT NULL DEFAULT '1',
  `profilo` int(1) NOT NULL DEFAULT '3',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('admin','admin123',1,1),('docente','docente123',1,2),('studente','studente123',1,3),('user1','pass1',1,1),('user2','pass2',1,2),('user3','pass3',1,3);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'gestione_scuola'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 20:04:55
