/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : master

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-05-02 17:18:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appuser
-- ----------------------------
DROP TABLE IF EXISTS `appuser`;
CREATE TABLE `appuser` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of appuser
-- ----------------------------
INSERT INTO `appuser` VALUES ('1', '张三');
INSERT INTO `appuser` VALUES ('2', '李四');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickName` varchar(100) DEFAULT NULL COMMENT '昵称',
  `trueName` varchar(100) DEFAULT NULL COMMENT '真名',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `lastOper` varchar(50) DEFAULT NULL COMMENT '最后操作人',
  `pwShow` varchar(50) DEFAULT NULL COMMENT '显示密码',
  `applyDate` datetime DEFAULT NULL COMMENT '申请时间',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `updateDate` datetime DEFAULT NULL COMMENT '更新时间',
  `expireDate` datetime DEFAULT NULL COMMENT '失效时间',
  `status` int(2) DEFAULT NULL COMMENT '是否有效，0：无效；1：有效',
  `colorSchemaId` int(2) DEFAULT NULL COMMENT '颜色方案，外键颜色方案表',
  `attentionCount` int(8) DEFAULT '0' COMMENT '关注量',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhangjun', 'yonghu1', '昵称1', '真名1', '18712345678', '西安市', '1@1.com', 'zhangjun', null, null, '2018-04-03 18:37:22', '2018-04-03 18:37:22', null, '1', null, '0', '备注1');
INSERT INTO `user` VALUES ('2', 'sunsongsong', 'yonghu1', '昵称1', '真名1', '18712345678', '西安市', '1@1.com', 'zhangjun', null, null, '2018-04-03 18:37:22', '2018-04-03 18:37:22', null, '1', null, '0', '备注1');
