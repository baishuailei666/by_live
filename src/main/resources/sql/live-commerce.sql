
-- ----------------------------
-- Table structure for anchor
-- ----------------------------
    
CREATE TABLE `anchor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL COMMENT '主播昵称',
  `img` varchar(255) DEFAULT NULL COMMENT '头像',
  `author_id` varchar(255) DEFAULT NULL COMMENT '抖音号id',
  `gender` tinyint(2) DEFAULT NULL COMMENT '性别：男1-女-2',
  `level` tinyint(2) DEFAULT NULL COMMENT '账号等级：1、2、3、4、5',
  `fans` varchar(255) DEFAULT NULL COMMENT '粉丝数',
  `url` varchar(1024) DEFAULT NULL COMMENT '主页链接',
  `category` varchar(255) DEFAULT NULL COMMENT '推广类目（类目:数量,类目:数量）',
  `goods` int(11) DEFAULT NULL COMMENT '推广商品',
  `sales` int(11) DEFAULT NULL COMMENT '橱窗销量',
  `introduce` varchar(255) DEFAULT NULL COMMENT '简介',
  `live` int(11) DEFAULT NULL COMMENT '直播带货场次：28',
  `live_day` int(11) DEFAULT NULL COMMENT '直播带货天数：18',
  `view` int(11) DEFAULT NULL COMMENT '直播间观看人数：9517',
  `avg_dur` int(11) DEFAULT NULL COMMENT '平均观看时长：124',
  `rate` varchar(255) DEFAULT NULL COMMENT '转化指数：85.4',
  `avg_gmv` varchar(255) DEFAULT NULL COMMENT '场均销售额：1k-5k',
  `price` varchar(255) DEFAULT NULL COMMENT '主推价格区间：0-50',
  `score` varchar(255) DEFAULT NULL COMMENT '带货口碑：4.09',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`category`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`rate`,`avg_gmv`,`price`,`score`) VALUES (1,'李二哥家','','智能家居=96;家用电器=120;男装女装=23',182,1864,'居家好物 分享达人 商务+（weige199077）',34,21,2686,33,'84.6','500-1k','100-500','4.1');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`category`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`rate`,`avg_gmv`,`price`,`score`) VALUES (2,'安家严选','','智能家居=96;家用电器=120;男装女装=23',97,3736,'喜欢简单的你，喜欢单纯的你感谢关注我哦客服：tui9276',64,54,3410,31,'83.8','100-500','0-50','4.23');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`category`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`rate`,`avg_gmv`,`price`,`score`) VALUES (3,'陈三废','','chensanfeigg31',1,7,'3306w','https://www.douyin.com/user/MS4wLjABAAAAPrwjPeBPCOAiC9Qg9lLGN9ny69RZKye_K54L6zu95NI','食品饮料=226;个护家清=109;智能家居=119',2875,2068000,'8月7号早10点超大场🎁小月亮周岁礼🎁 开播直接10w单1元福利，不要错过♥️ 广西一家人👨‍👩‍👧‍👧 陈三废弟弟，陈婷姐姐，陈橙弟媳妇，每天给大家分享，记录姐弟一家人的生活！谢谢大家的支持❤️ 售后☎：19128687726 实力童装工厂联系：CTMM0404（备注来意） 工作联系：CSF90900 （品牌方 商务）',55,301,35000,130,'1.96','100-500w','10-200','4.92');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (4,'我叫张小五','','xiaowu0913',2,4,'161.5w','https://www.douyin.com/user/MS4wLjABAAAAWC13HV0-4DLVqmFXcnRUApmOCgaa5L1uF3Hz3aO98X8',73,100500,20,359,1262,60,'7.5w-10w','50-100','4.98');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (5,'兔兔家严选','','D414978581',2,4,'68.8w','https://www.douyin.com/user/MS4wLjABAAAAP8WDvqP6nLGou0R63KG9x13iOCxZjQXlkGql1DUhHoCn49nN-wrevHVlrLC2L81h',228,40000,'感谢抖音爸爸提供绿色正能量平台从事电商8年，西安人在义乌！兔兔家专注做厨房好物🥃及玻璃制品、24小时全天在线直播.喜欢什么来直播间下单！或者商品橱窗购买',45,90,2217,120,'10-25w','10-60','4.98');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (6,'大号甄选','','dahaowenhua',1,4,'11.9w','https://www.douyin.com/user/MS4wLjABAAAAspJtADTIay4ttpCFDDI306t2WtHy8u4KY0OGxutKVQsj3eoc48SvJvv_trwPH0tI',420,119000,'品牌方合作认准我本人才行。 一个来自东北的农村小子，不忘初心，方得始终。 每天替粉丝多说一句话。做好每一个用户的眼睛。 是你们在我最需要鼓励的时候，无条件的给予了我信任，往后余生，我只想服务好每一个相信我的粉丝，谢谢你们，我不会让每一个曾经的他(她)失望，只要你在我身上赌，我就不会让你输！',15,10,5670,110,'20-50w','0-40','4.68');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (7,'大美家居','','meimojie88',2,4,'25.1w','https://www.douyin.com/user/MS4wLjABAAAAFWiFaNXWrTrMyA8fp50UBFf55zuMpPm1xbB75v09g8ngayh2lEug86jHGTSEh210',871,340000,'认真做事，诚信做人 ✨合作抖音duomeihan888',65,180,6000,130,'20-60w','10-200','4.69');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (8,'杨小仙儿的钢琴沙龙','','Yang19831983',2,4,'67.1w','https://www.douyin.com/user/MS4wLjABAAAAhDMYtCG3TJDllRQeLggE8LyOLmmUyJBh06ojHpC48X0',180,23000,'✅因为热爱，无需坚持。个人主播，无助理！无团队！✅非商务合作请不要加v，加v请注明来意:yang830zs✅商品橱窗都是杨老师甄选，喜欢的朋友可以放心选购。✅钢琴直播时间：每晚8:30-11:00（周六不弹琴，播生活）✅生活号ID：杨小仙儿生活号',86,60,2500,98,'25-50w','20-150','4.9');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (9,'静姐','','Jj5205209',2,4,'24w','https://www.douyin.com/user/MS4wLjABAAAAn9G62wZM8VK63i1JHLL81OlV2QVmrF9dRFmmG1nmBzc',3533,43900,'❤️主播：老板娘（静姐）❤️身高：166❤️体重：110❤️每月9级粉丝送衣服，记得主动联系客服或者私信我哦！❤️12年从事服装行业，从研发～设计～生产～发货～品控，亲力亲为，严格把关，以最好的品质服务家人。',31,33,3910,109,'10-15w','10-50','4.94');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (10,'优优精选','','erjiejiajubaihuo',2,4,'20.8w','https://www.douyin.com/user/MS4wLjABAAAAzB1Nznw_3wmEueYbmdGau91x912OAm7XzmZwFcOsLTY',616,93000,'🥰每天分享实用好物，性价比超高哦😋视频里的产品，本人亲自试用才推荐的😀不自用不推荐🤝不辜负每一份信任❤️',150,246,1200,52,'1-10w','0-30','4.94');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`,`score`) VALUES (11,'薺鏽膠','','20611071',1,4,'46w','https://www.douyin.com/user/MS4wLjABAAAA4ov8UUeiZkzHBjlf473jWyI5uTAG0Vt7PbYAKTiq13k',318,46000,'商务对接 k w 72777',318,230,2105,160,'10-25w','50-200','4.58');
INSERT INTO `anchor` (`id`,`nickname`,`img`,`author_id`,`gender`,`level`,`fans`,`url`,`goods`,`sales`,`introduce`,`live`,`live_day`,`view`,`avg_dur`,`avg_gmv`,`price`) VALUES (12,'椰岛朵妈','','duoma0898',2,4,'74.6w','https://www.douyin.com/user/MS4wLjABAAAAhSaUk8JHfRN5oZcsQLLWP9u8ccM_S_TeBdP0ViphBAQ',416,250000,'二胎宝妈，家有二老，照顾孩子的同时抽空回家陪伴年迈的父母，用镜头记录真实的双城生活。大女儿叫朵朵，所以大家都叫我朵妈，感谢每一位愿意关注朵妈的你们食品商务：duoma0898非食品商务：DY-yedaoduoma护肤品商务：duoma0898',102,165,1223,98,'5-10w','10-50');
-- ----------------------------
-- Table structure for content
-- ----------------------------
    
CREATE TABLE `content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oid` int(11) DEFAULT NULL COMMENT '主对象id',
  `rid` int(11) DEFAULT NULL COMMENT '接收对象id',
  `note` varchar(255) DEFAULT NULL COMMENT '内容',
  `type` tinyint(2) DEFAULT NULL COMMENT '跟进记录-1、备注-2、消息通知-3',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `oid_rid_type` (`oid`,`rid`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for contract
-- ----------------------------
    
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL COMMENT '商户店铺id',
  `ope_user` int(11) DEFAULT NULL COMMENT '业务员',
  `buy_type` tinyint(2) DEFAULT NULL COMMENT '权益类型：月卡-1、季卡-2、年卡-3',
  `ct` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `ut` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `sign_status` tinyint(2) DEFAULT '0' COMMENT '签署状态：0-未签、1-已签',
  `sign_type` tinyint(2) DEFAULT NULL COMMENT '签署类型：0-企业签署、1-个人签署',
  `document_id` varchar(255) DEFAULT NULL COMMENT '签署成功合同id，可用于下载查看等',
  `document_name` varchar(255) DEFAULT NULL COMMENT '合同名称',
  `flow_id` varchar(255) DEFAULT '' COMMENT '合同签署流程id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for data_config
-- ----------------------------
    
CREATE TABLE `data_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agent_user` int(11) DEFAULT NULL COMMENT '管理员（代理商）',
  `content` varchar(255) NOT NULL COMMENT '逗号分隔：发件邮箱,收件邮箱;客服电话1,客服电话2;月卡,季卡,年卡',
  `ct` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      
INSERT INTO `data_config` (`id`,`agent_user`,`content`,`ct`) VALUES (1,11,'taibowenhua@163.com,baishuailei@zhejianglab.com;19357669138,-;2580,5999,18888','2022-08-16 15:10:08');
-- ----------------------------
-- Table structure for invoice
-- ----------------------------
    
CREATE TABLE `invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家店铺id',
  `ope_user` int(11) DEFAULT NULL COMMENT '业务员',
  `money` double(10,2) DEFAULT NULL COMMENT '发票金额',
  `status` tinyint(2) DEFAULT '0' COMMENT '未开-0、已开-1、驳回-2',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注（拒绝原因）',
  `company` varchar(255) DEFAULT NULL COMMENT '发票公司名称',
  `tax` varchar(255) DEFAULT NULL COMMENT '纳税识别号',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `ct` timestamp NULL DEFAULT NULL COMMENT '申请时间',
  `ut` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for level_right
-- ----------------------------
    
CREATE TABLE `level_right` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` tinyint(2) DEFAULT NULL COMMENT '等级：超级管理员-1、管理员（代理）-2、业务员-3\r\n0-商户端商户用户',
  `api` varchar(255) DEFAULT NULL COMMENT '服务接口',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (101,0,'/merchant/**','商户接口');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (102,0,'/anchor/**','主播接口');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (103,0,'/common/**','通用接口');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (105,1,'/user/merchant/list','商户列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (106,1,'/user/merchant/audit/**','店铺审核列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (107,1,'/user/merchant/order','店铺开通权益记录');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (108,1,'/resource/**','商户资源');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (109,1,'/order/**','订单列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (110,1,'/invoice/**','发票列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (111,1,'/contract/**','合同列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (112,1,'/config/**','配置列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (113,1,'/user/**','用户管理');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (114,1,'/common/**','素材管理');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (120,2,'/merchant/list','商户列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (121,2,'/user/merchant/list','商户列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (122,2,'/user/merchant/audit/**','店铺审核列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (123,2,'/user/merchant/order','店铺开通权益记录');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (124,2,'/resource/**','商户资源');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (125,2,'/order/**','订单列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (126,2,'/invoice/**','发票列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (127,2,'/contract/**','合同列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (128,3,'/user/merchant/list','商户列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (129,3,'/user/merchant/audit/**','店铺审核列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (130,3,'/user/merchant/order','店铺开通权益记录');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (131,3,'/resource/**','商户资源');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (132,3,'/order/**','订单列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (133,3,'/invoice/**','发票列表');
INSERT INTO `level_right` (`id`,`level`,`api`,`note`) VALUES (134,3,'/contract/**','合同列表');
-- ----------------------------
-- Table structure for merchant
-- ----------------------------
    
CREATE TABLE `merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL COMMENT '商户手机号',
  `pwd` varchar(255) DEFAULT NULL COMMENT '商户端登录密码',
  `shop_id` varchar(255) DEFAULT NULL COMMENT '店铺id',
  `shop` varchar(255) DEFAULT NULL COMMENT '店铺名称，只能绑定一个',
  `goods` varchar(255) DEFAULT NULL COMMENT '商品链接',
  `introduce` varchar(1024) DEFAULT NULL COMMENT '商家介绍（<140字）',
  `ope_user` int(11) DEFAULT NULL COMMENT '业务员',
  `shop_status` varchar(255) DEFAULT NULL COMMENT '店铺状态：未认证、已认证、购买等级（月卡、季卡、年卡）',
  `days` int(11) DEFAULT NULL COMMENT '会员剩余天数（<7天标红）',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `lt` timestamp NULL DEFAULT NULL COMMENT '登录时间',
  `ct` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `shop` (`shop`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for merchant_anchor
-- ----------------------------
    
CREATE TABLE `merchant_anchor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL COMMENT '商户id',
  `anchor_id` int(11) DEFAULT NULL COMMENT '主播id',
  `ts` timestamp NULL DEFAULT NULL,
  `add` tinyint(2) DEFAULT '0' COMMENT '已加微信-1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for merchant_audit
-- ----------------------------
    
CREATE TABLE `merchant_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家店铺id',
  `ope_user` int(11) DEFAULT NULL COMMENT '业务员',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态：待审核-0、审核通过-1、已拒绝-2',
  `ct` timestamp NULL DEFAULT NULL COMMENT '申请时间',
  `ut` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注：驳回原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for merchant_sign
-- ----------------------------
    
CREATE TABLE `merchant_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NOT NULL,
  `subject` varchar(255) DEFAULT NULL COMMENT '小店主体名称',
  `person` varchar(255) DEFAULT NULL COMMENT '签署人',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `tax` varchar(255) DEFAULT NULL COMMENT '统一社会信用代码/身份证号码',
  PRIMARY KEY (`id`,`merchant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for mobile_code
-- ----------------------------
    
CREATE TABLE `mobile_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for order
-- ----------------------------
    
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `trade_no` varchar(255) DEFAULT NULL COMMENT '支付交易号',
  `merchant_id` int(11) DEFAULT NULL COMMENT '商家店铺id',
  `buy_type` tinyint(2) DEFAULT NULL COMMENT '支付种类：月卡-1、季卡-2、年卡-3',
  `status` varchar(255) DEFAULT NULL COMMENT '支付状态：未支付、已支付',
  `money` double(10,2) DEFAULT NULL COMMENT '金额',
  `ope_user` int(11) DEFAULT NULL COMMENT '业务员',
  `pay_type` tinyint(2) DEFAULT NULL COMMENT '支付类型：支付宝-1、微信-2、对公-3',
  `ct` timestamp NULL DEFAULT NULL COMMENT '支付订单时间',
  `ut` timestamp NULL DEFAULT NULL COMMENT '支付成功时间',
  `flow_id` varchar(255) DEFAULT '' COMMENT '合同签署id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `merchant_id` (`merchant_id`) USING BTREE,
  KEY `ope_user` (`ope_user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for relation_user
-- ----------------------------
    
CREATE TABLE `relation_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `main_user_id` int(11) DEFAULT NULL COMMENT '主user_id',
  `child_user_id` int(11) DEFAULT NULL COMMENT '子user_id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      

-- ----------------------------
-- Table structure for resource_merchant
-- ----------------------------
    
CREATE TABLE `resource_merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop` varchar(255) DEFAULT NULL COMMENT '商户店铺名称',
  `agent_user` int(11) DEFAULT NULL COMMENT '管理员',
  `intention` tinyint(2) DEFAULT '0' COMMENT '未联系-0、跟进中-1、已处理-2、已拒绝-3',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `ope_user` int(11) DEFAULT NULL COMMENT '业务员',
  `ct` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `ut` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (1,'测试小店1',11,0,'13107736070',42,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (2,'测试小店2',11,0,'13107736071',31,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (3,'测试小店3',11,0,'13107736072',35,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (4,'测试小店4',11,0,'13107736073',33,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (5,'测试小店5',11,0,'13107736074',34,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (6,'测试小店6',11,0,'13107736075',40,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (7,'测试小店7',11,0,'13107736076',30,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (8,'测试小店8',11,0,'13107736077',42,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (9,'测试小店9',11,0,'13107736078',31,'2022-08-04 11:08:36','2022-08-15 23:29:59');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (112,'测试小店112',11,0,'13107736181',33,'2022-08-04 11:08:36','2022-08-15 23:30:00');
INSERT INTO `resource_merchant` (`id`,`shop`,`agent_user`,`intention`,`mobile`,`ope_user`,`ct`,`ut`) VALUES (113,'测试小店113',11,0,'13107736182',38,'2022-08-04 11:08:36','2022-08-15 23:30:00');
-- ----------------------------
-- Table structure for user
-- ----------------------------
    
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(255) DEFAULT NULL COMMENT '密码：默认123456',
  `level` tinyint(2) DEFAULT NULL COMMENT '超级管理员-1、管理员（代理商）-2、业务员-3',
  `wx` mediumtext COMMENT '微信二维码,可支持16mb',
  `remark` varchar(255) DEFAULT NULL,
  `ct` timestamp NULL DEFAULT NULL,
  `ut` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
      
INSERT INTO `user` (`id`,`mobile`,`pwd`,`level`,`wx`,`remark`,`ct`,`ut`) VALUES (11,'19100000000','42429fecda72a361536f58ba59d8a531',1,'','管理员01','2022-08-01 21:56:30');