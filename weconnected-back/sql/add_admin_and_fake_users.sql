USE yupao;

-- 添加管理员用户
INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('admin', 'admin', 'https://example.com/admin-avatar.png', 0, 'e10adc3949ba59abbe56e057f20f883e', '13800138000', 'admin@example.com', 0, NOW(), NOW(), 0, 1, '000001', '["管理员"]', '我是系统管理员');

-- 添加假用户
INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('张三', 'zhangsan', 'https://example.com/avatar1.png', 0, 'e10adc3949ba59abbe56e057f20f883e', '13800138001', 'zhangsan@example.com', 0, NOW(), NOW(), 0, 0, '000002', '["Java", "Python"]', '热爱编程的开发者');

INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('李四', 'lisi', 'https://example.com/avatar2.png', 1, 'e10adc3949ba59abbe56e057f20f883e', '13800138002', 'lisi@example.com', 0, NOW(), NOW(), 0, 0, '000003', '["JavaScript", "React"]', '前端开发工程师');

INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('王五', 'wangwu', 'https://example.com/avatar3.png', 0, 'e10adc3949ba59abbe56e057f20f883e', '13800138003', 'wangwu@example.com', 0, NOW(), NOW(), 0, 0, '000004', '["Node.js", "MongoDB"]', '全栈开发工程师');

INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('赵六', 'zhaoliu', 'https://example.com/avatar4.png', 1, 'e10adc3949ba59abbe56e057f20f883e', '13800138004', 'zhaoliu@example.com', 0, NOW(), NOW(), 0, 0, '000005', '["Vue", "CSS"]', 'UI设计师兼前端');

INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('钱七', 'qianqi', 'https://example.com/avatar5.png', 0, 'e10adc3949ba59abbe56e057f20f883e', '13800138005', 'qianqi@example.com', 0, NOW(), NOW(), 0, 0, '000006', '["Python", "数据分析"]', '数据分析师');

INSERT INTO user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags, profile) 
VALUES 
('孙八', 'sunba', 'https://example.com/avatar6.png', 1, 'e10adc3949ba59abbe56e057f20f883e', '13800138006', 'sunba@example.com', 0, NOW(), NOW(), 0, 0, '000007', '["Java", "Spring"]', '后端架构师');