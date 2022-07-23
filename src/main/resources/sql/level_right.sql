/*
 Navicat Premium Data Transfer

 Source Server         : 10.101.116.2（3308
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 10.101.116.2:3308
 Source Schema         : by_live

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 18/07/2022 14:37:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for level_right
-- ----------------------------
DROP TABLE IF EXISTS `level_right`;
CREATE TABLE `level_right`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '等级：超级管理员-1、管理员（代理）-2、业务员-3\r\n0-商户端商户用户',
  `api` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务接口',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 135 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of level_right
-- ----------------------------
INSERT INTO `level_right` VALUES (101, 0, '/merchant/**', '商户接口');
INSERT INTO `level_right` VALUES (102, 0, '/anchor/**', '主播接口');
INSERT INTO `level_right` VALUES (103, 0, '/common/**', '通用接口');
INSERT INTO `level_right` VALUES (105, 1, '/user/merchant/list', '商户列表');
INSERT INTO `level_right` VALUES (106, 1, '/user/merchant/audit/**', '店铺审核列表');
INSERT INTO `level_right` VALUES (107, 1, '/user/merchant/order', '店铺开通权益记录');
INSERT INTO `level_right` VALUES (108, 1, '/resource/**', '商户资源');
INSERT INTO `level_right` VALUES (109, 1, '/order/**', '订单列表');
INSERT INTO `level_right` VALUES (110, 1, '/invoice/**', '发票列表');
INSERT INTO `level_right` VALUES (111, 1, '/contract/**', '合同列表');
INSERT INTO `level_right` VALUES (112, 1, '/config/**', '配置列表');
INSERT INTO `level_right` VALUES (113, 1, '/user/**', '用户管理');
INSERT INTO `level_right` VALUES (114, 1, '/common/**', '素材管理');
INSERT INTO `level_right` VALUES (120, 2, '/merchant/list', '商户列表');
INSERT INTO `level_right` VALUES (121, 2, '/user/merchant/list', '商户列表');
INSERT INTO `level_right` VALUES (122, 2, '/user/merchant/audit/**', '店铺审核列表');
INSERT INTO `level_right` VALUES (123, 2, '/user/merchant/order', '店铺开通权益记录');
INSERT INTO `level_right` VALUES (124, 2, '/resource/**', '商户资源');
INSERT INTO `level_right` VALUES (125, 2, '/order/**', '订单列表');
INSERT INTO `level_right` VALUES (126, 2, '/invoice/**', '发票列表');
INSERT INTO `level_right` VALUES (127, 2, '/contract/**', '合同列表');
INSERT INTO `level_right` VALUES (128, 3, '/user/merchant/list', '商户列表');
INSERT INTO `level_right` VALUES (129, 3, '/user/merchant/audit/**', '店铺审核列表');
INSERT INTO `level_right` VALUES (130, 3, '/user/merchant/order', '店铺开通权益记录');
INSERT INTO `level_right` VALUES (131, 3, '/resource/**', '商户资源');
INSERT INTO `level_right` VALUES (132, 3, '/order/**', '订单列表');
INSERT INTO `level_right` VALUES (133, 3, '/invoice/**', '发票列表');
INSERT INTO `level_right` VALUES (134, 3, '/contract/**', '合同列表');

SET FOREIGN_KEY_CHECKS = 1;
