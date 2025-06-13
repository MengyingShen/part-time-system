/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.8.2-MariaDB, for osx10.20 (arm64)
--
-- Host: localhost    Database: part_time_db
-- ------------------------------------------------------
-- Server version	11.8.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(20) DEFAULT 'PENDING',
  `cover_letter` text DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `applied_at` datetime(6) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `applied_date` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `feedback` text DEFAULT NULL,
  `interview_date` varchar(255) DEFAULT NULL,
  `job_title` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_applications_user` (`user_id`),
  KEY `idx_applications_job` (`job_id`),
  CONSTRAINT `applications_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE,
  CONSTRAINT `applications_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications`
--

LOCK TABLES `applications` WRITE;
/*!40000 ALTER TABLE `applications` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `applications` VALUES
(1,1,1,'PENDING','我对贵公司的Java开发实习生职位非常感兴趣，希望能有机会加入贵公司。','2025-06-10 05:25:02','2025-06-10 05:25:02','0000-00-00 00:00:00.000000',0,'','',NULL,NULL,'',''),
(2,1,1,'PENDING','我对贵公司的Java开发实习生职位非常感兴趣，希望能有机会加入贵公司。','2025-06-10 05:28:24','2025-06-10 05:28:24','0000-00-00 00:00:00.000000',0,'','',NULL,NULL,'','');
/*!40000 ALTER TABLE `applications` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `employers`
--

DROP TABLE IF EXISTS `employers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `employers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `company_name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `verification_status` varchar(20) DEFAULT 'PENDING',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `company_address` text DEFAULT NULL,
  `company_description` text DEFAULT NULL,
  `company_logo_url` varchar(255) DEFAULT NULL,
  `company_website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `employers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employers`
--

LOCK TABLES `employers` WRITE;
/*!40000 ALTER TABLE `employers` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `employers` VALUES
(1,2,'示例科技有限公司','一家专注于互联网技术的创新公司','北京市海淀区中关村','https://example.com','https://example.com/logo.png','VERIFIED','2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,NULL,NULL,NULL),
(2,5,'星巴克',NULL,NULL,NULL,NULL,'VERIFIED','2025-06-10 06:08:04','2025-06-10 06:08:04','上海市静安区南京西路123号','全球领先的专业咖啡烘焙商和零售商',NULL,'https://www.starbucks.com.cn');
/*!40000 ALTER TABLE `employers` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `flyway_schema_history` VALUES
(1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'dev_user','2025-06-10 05:28:24',0,1),
(2,'2','Initial data','SQL','V2__Initial_data.sql',-512283145,'dev_user','2025-06-10 05:28:24',46,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `requirements` text DEFAULT NULL,
  `responsibilities` text DEFAULT NULL,
  `job_type` varchar(20) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `salary_min` decimal(10,2) DEFAULT NULL,
  `salary_max` decimal(10,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'OPEN',
  `employer_id` bigint(20) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `application_deadline` date DEFAULT NULL,
  `category` varchar(50) NOT NULL,
  `is_remote` bit(1) DEFAULT NULL,
  `salary_type` enum('DAILY','HOURLY','MONTHLY','PROJECT','WEEKLY') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jobs_employer` (`employer_id`),
  CONSTRAINT `jobs_ibfk_1` FOREIGN KEY (`employer_id`) REFERENCES `employers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs`
--

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `jobs` VALUES
(1,'Java开发实习生','参与公司核心业务系统的开发与维护','计算机相关专业，熟悉Java基础，了解Spring框架','1. 参与需求分析和系统设计\n2. 编写高质量代码\n3. 参与代码审查','INTERNSHIP','北京市海淀区',3000.00,6000.00,'OPEN',1,'2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,'',NULL,'DAILY'),
(2,'前端开发工程师','负责公司产品的前端开发工作','1. 熟悉Vue/React等前端框架\n2. 良好的JavaScript基础\n3. 了解前端工程化','1. 负责前端页面开发\n2. 与后端工程师协作完成接口联调\n3. 优化前端性能','FULL_TIME','上海市浦东新区',15000.00,25000.00,'OPEN',1,'2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,'',NULL,'DAILY'),
(3,'产品经理助理','协助产品经理完成产品规划与设计','1. 对互联网产品有浓厚兴趣\n2. 良好的沟通能力\n3. 熟练使用Axure等原型工具','1. 参与产品需求分析\n2. 制作产品原型\n3. 编写产品文档','PART_TIME','广州市天河区',200.00,300.00,'OPEN',1,'2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,'',NULL,'DAILY');
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` text NOT NULL,
  `conversation_id` varchar(100) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `receiver_id` bigint(20) NOT NULL,
  `sender_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt05r0b6n0iis8u7dfna4xdh73` (`receiver_id`),
  KEY `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id`),
  CONSTRAINT `FK4ui4nnwntodh6wjvck53dbk9m` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt05r0b6n0iis8u7dfna4xdh73` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `is_read` bit(1) NOT NULL,
  `message` text NOT NULL,
  `reference_id` bigint(20) DEFAULT NULL,
  `reference_type` varchar(50) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` date DEFAULT NULL,
  `education` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `experience` text NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `graduation_date` date NOT NULL,
  `major` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `projects` text NOT NULL,
  `school` varchar(255) NOT NULL,
  `self_introduction` text NOT NULL,
  `skills` text NOT NULL,
  `updated_at` date DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpv0whudowxosfu792veo6s2c0` (`user_id`),
  CONSTRAINT `FKpv0whudowxosfu792veo6s2c0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resume`
--

LOCK TABLES `resume` WRITE;
/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `resume` VALUES
(1,'2025-06-10','本科','zhangsan@example.com','暂无工作经历','张三','2027-06-01','计算机科学','13812345678','暂无项目经验','北京大学','北京大学计算机科学专业在读学生，对编程充满热情，具备扎实的计算机基础知识。','Java, Python, JavaScript, React, 英语 (流利)','2025-06-10',6);
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `rating` tinyint(4) NOT NULL CHECK (`rating` between 0 and 4),
  `job_id` bigint(20) DEFAULT NULL,
  `reviewer_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnic4bk1c0ljhjkvyrr055m4a` (`job_id`),
  KEY `FKd1isgfajhtdl8mgg29up6mofi` (`reviewer_id`),
  KEY `FK7nxdogqtq60bypxrtk2rhvq56` (`target_id`),
  CONSTRAINT `FK7nxdogqtq60bypxrtk2rhvq56` FOREIGN KEY (`target_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKd1isgfajhtdl8mgg29up6mofi` FOREIGN KEY (`reviewer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKqnic4bk1c0ljhjkvyrr055m4a` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `roles` VALUES
(3,'ROLE_ADMIN'),
(2,'ROLE_EMPLOYER'),
(1,'ROLE_STUDENT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `saved_jobs`
--

DROP TABLE IF EXISTS `saved_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved_jobs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `job_id` bigint(20) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL,
  `saved_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_job` (`user_id`,`job_id`),
  UNIQUE KEY `UKe0hdgkw4tkup6grelv0q8g6qb` (`user_id`,`job_id`),
  KEY `job_id` (`job_id`),
  KEY `idx_saved_jobs_user` (`user_id`),
  CONSTRAINT `saved_jobs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `saved_jobs_ibfk_2` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved_jobs`
--

LOCK TABLES `saved_jobs` WRITE;
/*!40000 ALTER TABLE `saved_jobs` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `saved_jobs` VALUES
(1,1,1,'2025-06-10 05:25:02',NULL,'0000-00-00 00:00:00.000000'),
(2,1,2,'2025-06-10 05:25:02',NULL,'0000-00-00 00:00:00.000000'),
(5,4,1,'2025-06-10 09:55:06',NULL,'2025-06-10 17:55:06.000000'),
(13,6,2,'2025-06-10 13:41:49','2025-06-10 21:41:49.248315','2025-06-10 21:41:49.247853'),
(16,6,1,'2025-06-10 14:22:02','2025-06-10 22:22:02.452409','2025-06-10 22:22:02.451522');
/*!40000 ALTER TABLE `saved_jobs` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `student_profiles`
--

DROP TABLE IF EXISTS `student_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_profiles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `school` varchar(100) DEFAULT NULL,
  `major` varchar(100) DEFAULT NULL,
  `grade` varchar(20) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `resume_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `edu_begin` date DEFAULT NULL,
  `edu_end` date DEFAULT NULL,
  `expected_job_type` enum('FULL_TIME','INTERN','PART_TIME','TEMPORARY') DEFAULT NULL,
  `expected_salary_max` int(11) DEFAULT NULL,
  `expected_salary_min` int(11) DEFAULT NULL,
  `job_categories` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`job_categories`)),
  `work_days` varchar(50) DEFAULT NULL,
  `work_hours` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `student_profiles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_profiles`
--

LOCK TABLES `student_profiles` WRITE;
/*!40000 ALTER TABLE `student_profiles` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `student_profiles` VALUES
(1,1,'清华大学','计算机科学与技术','大四','MALE','2000-01-01','2020012345','https://example.com/resumes/1.pdf','2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `student_profiles` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `avatar_url` varchar(255) DEFAULT NULL,
  `bio` text DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `users` VALUES
(1,'student1','student1@example.com','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W','张三','13800138001','STUDENT','2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,NULL,NULL),
(2,'employer1','employer1@example.com','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W','招聘公司','13900139001','EMPLOYER','2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,NULL,NULL),
(3,'admin','admin@example.com','$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W','管理员','13700137001','ADMIN','2025-06-10 05:25:02','2025-06-10 05:25:02',NULL,NULL,NULL),
(4,'testuser','test@example.com','$2a$10$H3Gbgma2fTch7XiTa1M5bODclFflPJYzPXQvJj29/PiOGUi5tgSIW','Test User','1234567890','STUDENT','2025-06-10 05:34:02','2025-06-10 05:34:02',NULL,NULL,NULL),
(5,'starbucks','hr@starbucks.com','$2a$10$lb/qNEMh3fgxc7LcBjRmUuj3GgMXbU4pb8MS5cauVtOO25IypPvOu','Starbucks HR','13800138000','EMPLOYER','2025-06-10 06:08:04','2025-06-10 06:08:04',NULL,NULL,NULL),
(6,'stepbystepcode','abc1870819884@gmail.com','$2a$10$vnuOIveI5rWQQwWmzod5yel6CwDEWB9fxAIhIkSDSPlg84WIqVt/O','Mengying','+86 123 4567 8900','STUDENT','2025-06-10 09:03:32','2025-06-10 09:03:32',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `users_roles` VALUES
(1,1),
(2,2),
(3,3);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
commit;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-06-10 22:31:46
