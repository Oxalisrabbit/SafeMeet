/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1 (XAMPP)
 Source Server Type    : MySQL
 Source Server Version : 100419
 Source Host           : localhost:3306
 Source Schema         : safemeet

 Target Server Type    : MySQL
 Target Server Version : 100419
 File Encoding         : 65001

 Date: 13/06/2021 19:26:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activities
-- ----------------------------
DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL,
  `type` int NOT NULL,
  `size` int NOT NULL,
  `state` int NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `members_id_group` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activities
-- ----------------------------
INSERT INTO `activities` VALUES (1, 1, 1, 2, -1, '2021-05-26 20:49:16', '2021-05-26 23:49:20', 'test location', 'for test', '2;');
INSERT INTO `activities` VALUES (2, 2, 1, 2, -1, '2021-05-26 19:30:00', '2021-05-26 19:30:00', 'test1', 'test1', NULL);
INSERT INTO `activities` VALUES (3, 1, 1, 2, -1, '2021-05-26 12:17:49', '2021-05-26 13:17:59', 'test2', 'test2', NULL);
INSERT INTO `activities` VALUES (4, 1, 1, 2, 0, '2021-06-11 10:00:00', '2021-06-30 15:00:00', 'test3', 'test3', '2;');
INSERT INTO `activities` VALUES (5, 2, 4, 2, -1, '2021-06-12 10:00:00', '2021-06-12 12:00:00', 'test4', 'test4', NULL);
INSERT INTO `activities` VALUES (6, 1, 1, 2, 1, '2021-06-11 10:00:00', '2021-06-30 15:00:00', 'test5', 'test5', NULL);
INSERT INTO `activities` VALUES (7, 3, 1, 2, 1, '2021-06-11 10:00:00', '2021-06-30 15:00:00', 'test6', 'test6', NULL);
INSERT INTO `activities` VALUES (8, 2, 1, 2, 1, '2021-06-12 10:00:00', '2021-12-31 20:00:00', 'test location', 'Here are some test information in details area.', NULL);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `act_id` bigint NOT NULL,
  `creator_id` bigint NOT NULL,
  `uid` bigint NOT NULL,
  `rating` float NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (1, 1, 1, 2, 3, 'test');
INSERT INTO `comments` VALUES (2, 4, 1, 2, 4.5, 'pretty  interesting');

-- ----------------------------
-- Table structure for emergency
-- ----------------------------
DROP TABLE IF EXISTS `emergency`;
CREATE TABLE `emergency`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL,
  `act_id` bigint NOT NULL,
  `time` datetime NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of emergency
-- ----------------------------
INSERT INTO `emergency` VALUES (1, 1, 1, '2021-05-25 17:14:43', 'Emergency request at2021-05-25 17:14:43.852;(144.95588666666666,-37.81241);');
INSERT INTO `emergency` VALUES (2, 1, 4, '2021-06-12 13:34:12', '(-122.084,37.421998333333335);Emergency request at2021-06-12 13:34:13.052;Emergency request at2021-06-12 13:34:13.139;');

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `auth_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `emergency_call` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rating` float NOT NULL DEFAULT 5,
  PRIMARY KEY (`id`, `emergency_call`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'oxalis', '123456', 'YES', 25, 'M', '1 555-521-5554', '1 555-521-5558', 'Coding;Badminton;Gaming', 4.16667);
INSERT INTO `users` VALUES (2, 'test2', '123456', 'NO', 25, 'F', '1 555-521-5554', '1 555-521-5558', 'Coding;Badminton;Gaming', 5);
INSERT INTO `users` VALUES (3, 'test3', '123456', 'NO', 25, 'M', '1 555-521-5554', '1 555-521-5556', 'Coding;Badminton;Gaming', 5);

SET FOREIGN_KEY_CHECKS = 1;
