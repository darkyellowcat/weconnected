-- 添加个人简介字段到用户表

use weconnected;

-- 为用户表添加个人简介字段
ALTER TABLE user ADD COLUMN profile varchar(512) NULL COMMENT '个人简介';