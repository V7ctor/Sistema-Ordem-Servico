-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: osdatabase
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) NOT NULL,
  `Endereco` varchar(100) NOT NULL,
  `Telefone` varchar(20) NOT NULL,
  `Email` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'José de Assis','Rua dos Alpes','(14) 99543-8922','Assis@gmail.com'),(3,'Josefina Almeida de Albuquerque','Rua Florestal Centro - 563 - Cidade do Mar','(14) 99584-5453','josefina@outlook.com'),(4,'Carlos Monteiro de Souza Barreto','Rua Florindo Costa - 885 - Vila X','(14) 99583-4858','carlos@gmail.com'),(5,'Graziela Nunes Pereira','Rua Monte Cristo - 978 - Cidade do Mar','(14) 99867-5745','grazielle@outlook.com'),(6,'Cleide Duque Duarte Santana','Rua Assunção Torres - 885 - Cidade Montana','(14) 99857-4545','Duque@gmail.com'),(7,'Marcos Alves Pereira','Rua Assunção Filho - 887 - Cidade do Cabo','(14) 99349-3535','alves@hotmail.com'),(9,'Martillia Alves dos Santos Paiva','Rua Robertina Figueira - 274 - Ap. São Paulo','(14) 99584-5454','Martilia346@gmail.com');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordemservico`
--

DROP TABLE IF EXISTS `ordemservico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordemservico` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Data_OS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Equipamento` varchar(100) NOT NULL,
  `Defeito` varchar(120) NOT NULL,
  `Servico` varchar(100) NOT NULL,
  `Id_Tecnico` int(11) NOT NULL,
  `Valor` decimal(10,2) NOT NULL,
  `Id_Cliente` int(11) NOT NULL,
  `Tipo` varchar(30) NOT NULL,
  `Situacao` varchar(30) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_Tecnico` (`Id_Tecnico`),
  KEY `Id_Cliente` (`Id_Cliente`),
  CONSTRAINT `ordemservico_ibfk_1` FOREIGN KEY (`Id_Tecnico`) REFERENCES `tecnico` (`Id`),
  CONSTRAINT `ordemservico_ibfk_2` FOREIGN KEY (`Id_Cliente`) REFERENCES `clientes` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordemservico`
--

LOCK TABLES `ordemservico` WRITE;
/*!40000 ALTER TABLE `ordemservico` DISABLE KEYS */;
INSERT INTO `ordemservico` VALUES (2,'2020-01-24 17:00:21','Notebook Samsung i5','Monitor não Exibe Imagem','Trocar Cabo de Monitor',2,30.00,6,'Orçamento','Aguardando Aprovação'),(3,'2020-01-24 17:18:37','CPU Dell i5','Boot Demorado','Verificar a RAM e atualizar a BIOS',3,50.43,3,'Ordem de Serviço','Na Bancada'),(4,'2020-01-25 04:25:35','Monitor AOC E970SWNL LED 18.5 preto 220V','Não Liga','Trocar Cabo de Força',3,54.65,7,'Orçamento','Aguardando Aprovação'),(5,'2020-01-25 04:42:31','Computador Easypc Standard Plus, Sistema Operacional WindowsXP','Erro no Sistema Operacional','Reinstalar o Sistema Operacional sem que ocorra a perda de Dados',2,70.87,3,'Orçamento','Aguardando Peças'),(7,'2020-01-25 07:23:13','Celular MotoG3','Problema com Atualizações do Sistema Operacional','Restaurar padrões de Fábrica',1,60.00,6,'Ordem de Serviço','Aguardando Aprovação');
/*!40000 ALTER TABLE `ordemservico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tecnico`
--

DROP TABLE IF EXISTS `tecnico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tecnico` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `Telefone` varchar(20) NOT NULL,
  `Endereco` varchar(100) NOT NULL,
  `Email` varchar(60) DEFAULT NULL,
  `Cidade` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecnico`
--

LOCK TABLES `tecnico` WRITE;
/*!40000 ALTER TABLE `tecnico` DISABLE KEYS */;
INSERT INTO `tecnico` VALUES (1,'Jair Moura','(14) 99345-8295','Rua Figueiro Lopez Garcia - 556','Moura@gmail.com','Duartina'),(2,'Carlos Messias Santoro','(14) 99574-5465','Rua Figueiredo Alves Cruz','messiah.554.carlos@gmail.com','Cidade do Cabo'),(3,'Ulisses Guimarães','(14) 99765-4732','Rua Conceição Pinto','ulius@gmail.com','Metrópolis');
/*!40000 ALTER TABLE `tecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) NOT NULL,
  `Telefone` varchar(20) DEFAULT NULL,
  `Login` varchar(50) NOT NULL,
  `Senha` varchar(15) NOT NULL,
  `Perfil` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Login` (`Login`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Admin','(14) 99384-3843','root','1234','Administrador');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'osdatabase'
--

--
-- Dumping routines for database 'osdatabase'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-25  4:46:37
