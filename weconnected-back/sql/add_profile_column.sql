-- 添加个人简介字段到用户表
-- @author <a href="https://github.com/liyupi">程序员鱼皮</a>
-- @from <a href="https://yupi.icu">编程导航知识星球</a>

use yupao;

-- 为用户表添加个人简介字段
ALTER TABLE user ADD COLUMN profile varchar(512) NULL COMMENT '个人简介';