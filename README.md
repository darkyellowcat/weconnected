# 🌐 WeConnected

> 一个基于 **Spring Boot + Vue** 的校园社交平台，帮助同学们互相认识、分享与交流，找到志同道合之人。

![Java](https://img.shields.io/badge/Java-8-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-2.6.4-green)
![Vue](https://img.shields.io/badge/Vue-3.2.33-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 🏗️ 项目简介

**WeConnected** 是一个专为校园环境设计的社交与信息共享平台，用户可以注册、登录、组队等。  
后端使用 **Spring Boot + MyBatis + MySQL** 构建，前端采用 **Vue 3 + Axios** 实现响应式界面。  
项目同时集成了 **全局异常处理**、**Swagger 文档** 等现代化功能。

---

## 🧩 技术栈

| 分类 | 技术 |
|------|------|
| 前端 | Vue 3、Axios、Vue Router、Vite |
| 后端 | Spring Boot 2、MyBatis、MySQL、Redis |
| 安全 | 盐值、MD5加密 |
| 工具 | Knife4j、Lombok、Maven |
| 部署 | 前端：Vercel(免费) 后端：云托管平台(微信云托管) |

---

## 🚀 功能模块

- 👤 用户模块：注册、登录、资料修改、根据名称生成头像 
- 🗨️ 动态模块：组队、获得联系方式  
- 📖 接口文档：集成 Swagger + Knife4j，可视化查看 API  

---

## 功能展示
后续会更新到博客中 https://darkyellowcat.github.io/2025/10/20/weconnected%E9%A1%B9%E7%9B%AE/

<img width="500" height="879" alt="image" src="https://github.com/user-attachments/assets/e920cc8c-f164-4b4e-9547-79700d70303c" />

<img width="501" height="881" alt="image" src="https://github.com/user-attachments/assets/40c3de2b-2320-4c32-9983-b8ec9950d880" />


## ⚙️ 本地运行

### 1️⃣ 克隆仓库
```bash
git clone https://github.com/darkyellowcat/weconnected.git
```
### 2️⃣ 后端启动
1.Maven下载依赖
2.连接数据库，用给出的建表语句建表
3.启动Reids服务
4.运行
5.API 文档(http://localhost:8080/doc.html)
### 3️⃣ 前端启动
```bash
npm install
npm run dev
```
链接 'http://localhost:3000/'
## 🧠 未来计划

 WebSocket 实时聊天

 AI 兴趣推荐系统

 Docker 一键部署

 用户成就系统 & 积分机制

 引入 WebSocket 实现实时聊天
 
 用 Spring Security 重构认证模块

## 💬 联系作者

👨‍💻 Author: darkyellowcat
📧 Email: k4729.23098@gmail.com
📘 Blog: https://darkyellowcat.github.io/


