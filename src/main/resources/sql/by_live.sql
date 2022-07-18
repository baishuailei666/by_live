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

 Date: 18/07/2022 14:37:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for anchor
-- ----------------------------
DROP TABLE IF EXISTS `anchor`;
CREATE TABLE `anchor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主播昵称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `author_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抖音号id',
  `gender` tinyint(2) NULL DEFAULT NULL COMMENT '性别：男1-女-2',
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '账号等级：1、2、3、4、5',
  `fans` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '粉丝数',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主页链接',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推广类目（类目:数量,类目:数量）',
  `goods` int(11) NULL DEFAULT NULL COMMENT '推广商品',
  `sales` int(11) NULL DEFAULT NULL COMMENT '橱窗销量',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `live` int(11) NULL DEFAULT NULL COMMENT '直播带货场次：28',
  `live_day` int(11) NULL DEFAULT NULL COMMENT '直播带货天数：18',
  `view` int(11) NULL DEFAULT NULL COMMENT '直播间观看人数：9517',
  `avg_dur` int(11) NULL DEFAULT NULL COMMENT '平均观看时长：124',
  `rate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '转化指数：85.4',
  `avg_gmv` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场均销售额：1k-5k',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主推价格区间：0-50',
  `score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '带货口碑：4.09',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oid` int(11) NULL DEFAULT NULL COMMENT '主对象id',
  `rid` int(11) NULL DEFAULT NULL COMMENT '接收对象id',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '跟进记录-1、备注-2、消息通知-3',
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NULL DEFAULT NULL COMMENT '商户店铺id',
  `ope_user` int(11) NULL DEFAULT NULL COMMENT '业务员',
  `buy_type` tinyint(2) NULL DEFAULT NULL COMMENT '权益类型：月卡-1、季卡-2、年卡-3',
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `ut` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子签合同内容',
  `sign_status` tinyint(2) NULL DEFAULT 0 COMMENT '签署状态：0-未签、1-已签',
  `sign_type` tinyint(2) NULL DEFAULT NULL COMMENT '签署类型：1-企业签署、2-个人签署',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺公司名称',
  `tax` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '统一社会信用代码/签署人姓名',
  `owner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法定代表人/签署人身份证号',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签署人手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for data_config
-- ----------------------------
DROP TABLE IF EXISTS `data_config`;
CREATE TABLE `data_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_user` int(11) NULL DEFAULT NULL COMMENT '管理员（代理商）',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡',
  `ct` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NULL DEFAULT NULL COMMENT '商家店铺id',
  `ope_user` int(11) NULL DEFAULT NULL COMMENT '业务员',
  `money` double(10, 2) NULL DEFAULT NULL COMMENT '发票金额',
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '未开-0、已开-1、驳回-2',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注（拒绝原因）',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票公司名称',
  `tax` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纳税识别号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '申请时间',
  `ut` timestamp(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户手机号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户端登录密码',
  `shop_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺id',
  `shop` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺名称，只能绑定一个',
  `goods` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品链接',
  `introduce` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商家介绍（<140字）',
  `ope_user` int(11) NULL DEFAULT NULL COMMENT '业务员',
  `shop_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '店铺状态：未认证、已认证、购买等级（月卡、季卡、年卡）',
  `days` int(11) NULL DEFAULT NULL COMMENT '会员剩余天数（<7天标红）',
  `login_count` int(11) NULL DEFAULT NULL COMMENT '登录次数',
  `lt` timestamp(0) NULL DEFAULT NULL COMMENT '登录时间',
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for merchant_anchor
-- ----------------------------
DROP TABLE IF EXISTS `merchant_anchor`;
CREATE TABLE `merchant_anchor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NULL DEFAULT NULL COMMENT '商户id',
  `anchor_id` int(11) NULL DEFAULT NULL COMMENT '主播id',
  `ts` timestamp(0) NULL DEFAULT NULL,
  `add` tinyint(2) NULL DEFAULT 0 COMMENT '已加微信-1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for merchant_audit
-- ----------------------------
DROP TABLE IF EXISTS `merchant_audit`;
CREATE TABLE `merchant_audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NULL DEFAULT NULL COMMENT '商家店铺id',
  `ope_user` int(11) NULL DEFAULT NULL COMMENT '业务员',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '状态：待审核-0、审核通过-1、已拒绝-2',
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '申请时间',
  `ut` timestamp(0) NULL DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注：驳回原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mobile_code
-- ----------------------------
DROP TABLE IF EXISTS `mobile_code`;
CREATE TABLE `mobile_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `trade_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付交易号',
  `merchant_id` int(11) NULL DEFAULT NULL COMMENT '商家店铺id',
  `buy_type` tinyint(2) NULL DEFAULT NULL COMMENT '支付种类：月卡-1、季卡-2、年卡-3',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付状态：未支付、已支付',
  `money` double(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `ope_user` int(11) NULL DEFAULT NULL COMMENT '业务员',
  `pay_type` tinyint(2) NULL DEFAULT NULL COMMENT '支付类型：支付宝-1、微信-2、对公-3',
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '支付订单时间',
  `ut` timestamp(0) NULL DEFAULT NULL COMMENT '支付成功时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_config`;
CREATE TABLE `pay_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_user` int(11) NULL DEFAULT NULL COMMENT '对应每个管理员（代理商）支付信息',
  `ali_app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝支付，appid',
  `ali_private_key` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝支付，私钥',
  `ali_public_key` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝支付，公钥',
  `wx_app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信支付，appid',
  `wx_mch_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信支付，商户号',
  `wx_mch_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信支付，商户密钥',
  `wx_key_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信支付，认证证书',
  `contrary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对公公司',
  `contrary_bank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对公银行',
  `contrary_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对公账户',
  `sign_secret_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子签id',
  `sign_secret_key` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子签key',
  `sign_template_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子签合同模板id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `ut` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for relation_user
-- ----------------------------
DROP TABLE IF EXISTS `relation_user`;
CREATE TABLE `relation_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `main_user_id` int(11) NULL DEFAULT NULL COMMENT '主user_id',
  `child_user_id` int(11) NULL DEFAULT NULL COMMENT '子user_id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resource_merchant
-- ----------------------------
DROP TABLE IF EXISTS `resource_merchant`;
CREATE TABLE `resource_merchant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户店铺名称',
  `agent_user` int(11) NULL DEFAULT NULL COMMENT '管理员',
  `intention` tinyint(2) NULL DEFAULT 0 COMMENT '未联系-0、跟进中-1、已处理-2、已拒绝-3',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `ope_user` int(11) NULL DEFAULT NULL COMMENT '业务员',
  `ct` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `ut` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码：默认123456',
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '超级管理员-1、管理员（代理商）-2、业务员-3',
  `wx` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '微信二维码,可支持16mb',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ct` timestamp(0) NULL DEFAULT NULL,
  `ut` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频标题',
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '全部-0、月卡-1、季卡-2、年卡-3',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频地址',
  `ct` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
