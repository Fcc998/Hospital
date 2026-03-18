USE `healerworld`;

-- 评价表
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `cid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `did` bigint(20) NOT NULL COMMENT '医生ID',
  `score` int(11) DEFAULT '5' COMMENT '评分1-5',
  `content` varchar(500) DEFAULT NULL COMMENT '评价内容',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`cid`),
  KEY `idx_uid` (`uid`),
  KEY `idx_did` (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 号源表
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `sid` bigint(20) NOT NULL,
  `did` bigint(20) NOT NULL COMMENT '医生ID',
  `schedule_date` date NOT NULL COMMENT '排班日期',
  `time_period` varchar(20) NOT NULL COMMENT '时段：上午/下午/晚上',
  `total_num` int(11) DEFAULT '20' COMMENT '总号源数',
  `remain_num` int(11) DEFAULT '20' COMMENT '剩余号源数',
  `price` decimal(10,2) DEFAULT '50.00' COMMENT '挂号费用',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `uk_schedule` (`did`,`schedule_date`,`time_period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 支付订单表
DROP TABLE IF EXISTS `payment_order`;
CREATE TABLE `payment_order` (
  `order_id` bigint(20) NOT NULL,
  `aid` bigint(20) NOT NULL COMMENT '预约ID',
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `pay_type` int(11) DEFAULT NULL COMMENT '支付方式：1支付宝 2微信',
  `pay_status` int(11) DEFAULT '0' COMMENT '支付状态：0待支付 1已支付 2已取消',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 验证码表
DROP TABLE IF EXISTS `verify_code`;
CREATE TABLE `verify_code` (
  `vid` bigint(20) NOT NULL,
  `target` varchar(64) NOT NULL COMMENT '邮箱或手机号',
  `code` varchar(10) NOT NULL COMMENT '验证码',
  `type` int(11) DEFAULT '1' COMMENT '类型：1邮箱 2手机',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `used` int(11) DEFAULT '0' COMMENT '是否已使用：0否 1是',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`vid`),
  KEY `idx_target` (`target`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 修改预约表增加支付相关字段
ALTER TABLE `appointment` ADD COLUMN `status` int(11) DEFAULT '0' COMMENT '状态：0待支付 1已预约 2已就诊 3已取消';
ALTER TABLE `appointment` ADD COLUMN `order_no` varchar(64) DEFAULT NULL COMMENT '订单号';
ALTER TABLE `appointment` ADD COLUMN `amount` decimal(10,2) DEFAULT '50.00' COMMENT '挂号费用';
ALTER TABLE `appointment` ADD COLUMN `pay_status` int(11) DEFAULT '0' COMMENT '支付状态：0待支付 1已支付';
ALTER TABLE `appointment` ADD COLUMN `pay_time` datetime DEFAULT NULL COMMENT '支付时间';
ALTER TABLE `appointment` ADD COLUMN `schedule_id` bigint(20) DEFAULT NULL COMMENT '号源ID';

-- 插入测试号源数据
INSERT INTO `schedule` (`sid`, `did`, `schedule_date`, `time_period`, `total_num`, `remain_num`, `price`, `createtime`, `updatetime`) VALUES
(1, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '上午', 20, 18, 50.00, NOW(), NOW()),
(2, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '下午', 20, 15, 50.00, NOW(), NOW()),
(3, 1, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '上午', 20, 20, 50.00, NOW(), NOW()),
(4, 2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '上午', 15, 12, 60.00, NOW(), NOW()),
(5, 2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '下午', 15, 10, 60.00, NOW(), NOW()),
(6, 3, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '上午', 25, 22, 45.00, NOW(), NOW()),
(7, 4, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '下午', 18, 16, 55.00, NOW(), NOW()),
(8, 5, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '上午', 20, 19, 50.00, NOW(), NOW()),
(9, 6, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '下午', 15, 14, 48.00, NOW(), NOW());

-- 插入测试评价数据
INSERT INTO `comment` (`cid`, `uid`, `did`, `score`, `content`, `createtime`, `updatetime`) VALUES
(1, 151093275581282, 1, 5, '曹医生非常专业，态度很好，治疗效果显著，强烈推荐！', NOW(), NOW()),
(2, 151093275581282, 2, 4, '张医生医术精湛，就是排队时间有点长。', NOW(), NOW()),
(3, 151093312149388, 1, 5, '非常感谢曹医生，我的病情得到了很好的控制。', NOW(), NOW()),
(4, 151093312149388, 3, 4, '李医生很细心，解答问题很耐心。', NOW(), NOW()),
(5, 151100071769897, 4, 5, '李五医生对儿科很专业，孩子恢复得很快。', NOW(), NOW()),
(6, 151100071769897, 6, 4, '李七医生服务态度很好，治疗效果不错。', NOW(), NOW());
