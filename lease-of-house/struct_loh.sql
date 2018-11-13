/*
Navicat MySQL Data Transfer

Source Server         : loh@localhost
Source Server Version : 50089
Source Host           : localhost:3306
Source Database       : loh

Target Server Type    : MYSQL
Target Server Version : 60099
File Encoding         : 65001

Date: 2018-11-13 12:26:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `login_info`
-- ----------------------------
DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info` (
`login_info_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`login_account`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号' ,
`login_password`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码' ,
`user_info_id`  int(11) NOT NULL COMMENT '用户ID' ,
`current_login_time`  datetime NULL DEFAULT NULL COMMENT '当前登录时间' ,
`last_login_time`  datetime NULL DEFAULT NULL COMMENT '上一次登录时间' ,
`login_ip`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP' ,
PRIMARY KEY (`login_info_id`),
FOREIGN KEY (`user_info_id`) REFERENCES `user_info` (`user_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_fk_login_info_user_info` (`user_info_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='登录信息表; InnoDB free: 21504 kB; (`user_info_id`) REFER `loh/user_info`(`user_info_'
AUTO_INCREMENT=113

;

-- ----------------------------
-- Table structure for `loh_file_info`
-- ----------------------------
DROP TABLE IF EXISTS `loh_file_info`;
CREATE TABLE `loh_file_info` (
`loh_file_info_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`loh_house_info_id`  int(11) NULL DEFAULT NULL COMMENT '房屋信息表ID（与房屋信息表的外键关联关系）' ,
`loh_file_type_id`  int(11) NULL DEFAULT NULL COMMENT '文件类型ID（与文件类型表的关联关系）' ,
`file_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房屋信息标题' ,
`file_link`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件连接' ,
PRIMARY KEY (`loh_file_info_id`),
FOREIGN KEY (`loh_file_type_id`) REFERENCES `loh_file_type` (`loh_file_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`loh_house_info_id`) REFERENCES `loh_house_info` (`loh_house_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_loh_house_info_loh_house_file_info` (`loh_house_info_id`) USING BTREE ,
INDEX `FK_loh_file_type_loh_house_file_info` (`loh_file_type_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='房屋租赁文件信息表; InnoDB free: 21504 kB; (`loh_file_type_id`) REFER `loh/loh_file_type`'
AUTO_INCREMENT=1443

;

-- ----------------------------
-- Table structure for `loh_file_type`
-- ----------------------------
DROP TABLE IF EXISTS `loh_file_type`;
CREATE TABLE `loh_file_type` (
`loh_file_type_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID 自动增长' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`file_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型' ,
PRIMARY KEY (`loh_file_type_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='文件类型表'
AUTO_INCREMENT=2

;

-- ----------------------------
-- Table structure for `loh_house_info`
-- ----------------------------
DROP TABLE IF EXISTS `loh_house_info`;
CREATE TABLE `loh_house_info` (
`loh_house_info_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID 自动增长' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`house_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房屋信息标题' ,
`user_info_id`  int(11) NULL DEFAULT NULL COMMENT '用户ID（与用户表的外键关联关系）' ,
`loh_house_type_id`  int(11) NULL DEFAULT NULL COMMENT '房屋类型ID（与房屋类型表的外键关联关系）' ,
`region_info_province_id`  int(11) NULL DEFAULT NULL COMMENT '省市级地区信息ID（与地区信息表的外键）' ,
`region_info_city_id`  int(11) NULL DEFAULT NULL COMMENT '市市辖区级地区信息ID（与地区信息表的外键）' ,
`region_info_county_id`  int(11) NULL DEFAULT NULL COMMENT '区县级地区信息ID（与地区信息表的外键）' ,
`house_address`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房屋地址' ,
`price`  decimal(10,0) NULL DEFAULT NULL COMMENT '房屋租赁价格' ,
`push_date`  date NULL DEFAULT NULL COMMENT '发布日期' ,
`contacts`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人' ,
`cell_phone`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话' ,
`qrcode_link`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码链接地址' ,
PRIMARY KEY (`loh_house_info_id`),
FOREIGN KEY (`region_info_city_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`loh_house_type_id`) REFERENCES `loh_house_type` (`loh_house_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`user_info_id`) REFERENCES `user_info` (`user_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`region_info_province_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`region_info_county_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_fk_loh_house_info_loh_house_type` (`loh_house_type_id`) USING BTREE ,
INDEX `FK_fk_loh_house_info_user_info` (`user_info_id`) USING BTREE ,
INDEX `FK_fk_region_info_loh_house_info` (`region_info_province_id`) USING BTREE ,
INDEX `FK_fk_region_info_loh_house_info_3` (`region_info_county_id`) USING BTREE ,
INDEX `FK_fk_region_info_loh_house_info_2` (`region_info_city_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='租赁房屋信息表; InnoDB free: 21504 kB; (`region_info_city_id`) REFER `loh/region_info`('
AUTO_INCREMENT=415

;

-- ----------------------------
-- Table structure for `loh_house_type`
-- ----------------------------
DROP TABLE IF EXISTS `loh_house_type`;
CREATE TABLE `loh_house_type` (
`loh_house_type_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID自动增长' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`house_type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '房屋类型' ,
PRIMARY KEY (`loh_house_type_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='房屋租赁类型表'
AUTO_INCREMENT=125

;

-- ----------------------------
-- Table structure for `loh_house_view_history`
-- ----------------------------
DROP TABLE IF EXISTS `loh_house_view_history`;
CREATE TABLE `loh_house_view_history` (
`loh_house_view_history_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID 自动增长' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`loh_house_id`  int(11) NULL DEFAULT NULL COMMENT '租赁房屋信息ID' ,
`user_info_id`  int(11) NULL DEFAULT NULL COMMENT '用户ID（与用户信息表的外键关联关系）' ,
PRIMARY KEY (`loh_house_view_history_id`),
FOREIGN KEY (`user_info_id`) REFERENCES `user_info` (`user_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_fk_loh_house_view_history_user_info` (`user_info_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='租赁房屋浏览记录表; InnoDB free: 21504 kB; (`user_info_id`) REFER `loh/user_info`(`user_i'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Table structure for `region_info`
-- ----------------------------
DROP TABLE IF EXISTS `region_info`;
CREATE TABLE `region_info` (
`region_info_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID 自动增长' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`region_code`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区编号' ,
`region_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区名称' ,
`region_level`  tinyint(4) NULL DEFAULT NULL COMMENT '地区级别' ,
`parent_region_id`  int(11) NULL DEFAULT NULL COMMENT '地区信息表ID（与地区信息表的外键关联关系）' ,
PRIMARY KEY (`region_info_id`),
FOREIGN KEY (`parent_region_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_fk_region_info_region_info` (`parent_region_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='地区信息表; InnoDB free: 21504 kB; (`parent_region_id`) REFER `loh/region_info`(`regi'
AUTO_INCREMENT=539137

;

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
`user_info_id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`gmt_create`  datetime NOT NULL COMMENT '记录创建时间' ,
`gmt_modified`  datetime NOT NULL COMMENT '记录修改时间' ,
`user_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名' ,
`born_date`  date NULL DEFAULT NULL COMMENT '出生日期' ,
`region_info_province_id`  int(11) NULL DEFAULT NULL COMMENT '省市级地区信息ID（与地区信息表的外键）' ,
`region_info_city_id`  int(11) NULL DEFAULT NULL COMMENT '市市辖区级地区信息ID（与地区信息表的外键）' ,
`region_info_county_id`  int(11) NULL DEFAULT NULL COMMENT '区县级地区信息ID（与地区信息表的外键）' ,
`cell_phone`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话' ,
`detailed_information`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细信息' ,
PRIMARY KEY (`user_info_id`),
FOREIGN KEY (`region_info_province_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`region_info_city_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`region_info_county_id`) REFERENCES `region_info` (`region_info_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `FK_fk_user_info_region_info_3` (`region_info_county_id`) USING BTREE ,
INDEX `FK_fk_user_info_region_info_1` (`region_info_province_id`) USING BTREE ,
INDEX `FK_fk_user_info_region_info_2` (`region_info_city_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='用户信息表; InnoDB free: 21504 kB; (`region_info_province_id`) REFER `loh/region_info'
AUTO_INCREMENT=115

;

-- ----------------------------
-- Auto increment value for `login_info`
-- ----------------------------
ALTER TABLE `login_info` AUTO_INCREMENT=113;

-- ----------------------------
-- Auto increment value for `loh_file_info`
-- ----------------------------
ALTER TABLE `loh_file_info` AUTO_INCREMENT=1443;

-- ----------------------------
-- Auto increment value for `loh_file_type`
-- ----------------------------
ALTER TABLE `loh_file_type` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for `loh_house_info`
-- ----------------------------
ALTER TABLE `loh_house_info` AUTO_INCREMENT=415;

-- ----------------------------
-- Auto increment value for `loh_house_type`
-- ----------------------------
ALTER TABLE `loh_house_type` AUTO_INCREMENT=125;

-- ----------------------------
-- Auto increment value for `loh_house_view_history`
-- ----------------------------
ALTER TABLE `loh_house_view_history` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `region_info`
-- ----------------------------
ALTER TABLE `region_info` AUTO_INCREMENT=539137;

-- ----------------------------
-- Auto increment value for `user_info`
-- ----------------------------
ALTER TABLE `user_info` AUTO_INCREMENT=115;
