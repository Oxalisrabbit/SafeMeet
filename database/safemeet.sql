/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1(XAMPP)
 Source Server Type    : MySQL
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : safemeet

 Target Server Type    : MySQL
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 21/05/2021 21:08:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activities
-- ----------------------------
DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities`  (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `type` int(255) NOT NULL,
  `state` int(255) NOT NULL,
  `start` datetime(0) NOT NULL,
  `end` datetime(0) NOT NULL,
  `location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `members_id_group` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activities
-- ----------------------------
INSERT INTO `activities` VALUES (1, 1, 1, 1, '2021-05-19 17:49:16', '2021-05-30 23:49:20', 'test location', 'for test', NULL);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint(20) NOT NULL,
  `act_id` int(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `rating` int(255) NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for emergency
-- ----------------------------
DROP TABLE IF EXISTS `emergency`;
CREATE TABLE `emergency`  (
  `id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of types
-- ----------------------------
INSERT INTO `types` VALUES (1, 'MEAL');
INSERT INTO `types` VALUES (2, 'MOVIE');
INSERT INTO `types` VALUES (3, 'SHOPPING');
INSERT INTO `types` VALUES (4, 'OTHER');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `auth_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rating` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'oxalis', '123456', NULL, 25, 'M', 'Coding;Badminton', '10');

SET FOREIGN_KEY_CHECKS = 1;
