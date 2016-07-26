/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50631
 Source Host           : localhost
 Source Database       : thor

 Target Server Type    : MySQL
 Target Server Version : 50631
 File Encoding         : utf-8

 Date: 07/26/2016 12:01:11 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `work_task`
-- ----------------------------
DROP TABLE IF EXISTS `work_task`;
CREATE TABLE `work_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ref_id` varchar(64) DEFAULT NULL,
  `task_type` int(11) DEFAULT NULL,
  `key_a` varchar(64) DEFAULT NULL,
  `key_b` varchar(64) DEFAULT NULL,
  `key_c` varchar(256) DEFAULT NULL,
  `task_data` varchar(1024) DEFAULT NULL,
  `task_exe_count` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '1：未执行 3：执行成功，删除 4：执行失败',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
