# WeConnected

> 基于 Spring Boot + Vue 3 的校园社交平台，帮助同学们发现志同道合的伙伴、组建团队、实时交流。

![Java](https://img.shields.io/badge/Java-8-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-2.6.4-green)
![Vue](https://img.shields.io/badge/Vue-3.2-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Redis](https://img.shields.io/badge/Redis-latest-red)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

---

## 项目简介

**WeConnected** 是一个面向校园场景的社交平台。用户可以通过标签匹配找到兴趣相投的同学，创建或加入团队，并在团队内进行实时聊天。

后端基于 **Spring Boot + MyBatis-Plus + Redis** 构建，提供 RESTful API 和 WebSocket 实时通信；前端采用 **Vue 3 + Vant** 打造移动端优先的响应式界面。

---

## 技术栈

| 分类 | 技术 |
|------|------|
| 前端 | Vue 3、TypeScript、Vite、Vant 3、Vue Router 4、Axios |
| 后端 | Spring Boot 2.6.4、MyBatis-Plus、Spring WebSocket (STOMP) |
| 数据存储 | MySQL、Redis（会话 + 缓存 + 消息持久化） |
| 安全 | Spring Session (Redis)、BCrypt 密码加密 |
| 实时通信 | WebSocket + STOMP、Redis Pub/Sub（多实例消息广播） |
| 分布式 | Redisson 分布式锁（定时任务） |
| 文档 | Knife4j / Swagger |
| 部署 | 前端：Vercel｜后端：微信云托管 |

---

## 功能模块

### 用户系统
- 注册 / 登录 / 登出（会话存储于 Redis，24h 超时）
- 个人资料编辑（昵称、头像、性别、手机、邮箱、个人简介、标签）
- 自动生成头像
- 管理员角色（可搜索/管理用户）

### 用户发现与匹配
- 首页用户推荐（Redis 缓存 + 定时预热）
- 按标签搜索用户
- "心动模式" — 基于标签相似度的智能匹配算法

### 团队系统
- 创建 / 编辑 / 解散团队
- 团队可见性：公开、私密、加密（密码保护）
- 加入 / 退出团队
- 我创建的团队 / 我加入的团队
- 团队过期时间设置

### 实时聊天（WebSocket）
- 基于 STOMP 协议的团队聊天室
- 消息类型：加入、聊天、离开
- Redis Pub/Sub 实现多实例消息广播
- 聊天记录持久化（Redis，每个团队保留最近 100 条，7 天 TTL）
- 消息气泡、头像、时间戳、自动滚动

### API 文档
- Knife4j 集成，可视化查看所有接口：`http://localhost:8080/api/doc.html`

---

## 项目结构

```
WeConnected/
├── weconnected-back/                  # Spring Boot 后端
│   ├── pom.xml
│   └── src/main/java/com/darkyellowcat/weconnected/
│       ├── MyApplication.java         # 启动类
│       ├── config/                    # WebSocket、Redis、安全、跨域、Swagger 配置
│       ├── controller/                # UserController、TeamController、ChatController
│       ├── service/                   # 业务逻辑层
│       ├── mapper/                    # MyBatis 数据访问层
│       ├── model/                     # 实体、DTO、VO、枚举、请求对象
│       ├── job/                       # 定时任务（推荐缓存预热）
│       ├── common/                    # 统一响应、错误码
│       └── exception/                 # 全局异常处理
│
└── weconnected-front/                 # Vue 3 前端
    ├── package.json
    └── src/
        ├── pages/                     # 页面组件（首页、团队、聊天、用户等）
        ├── components/                # 通用组件（用户卡片、团队卡片）
        ├── config/route.ts            # 路由配置
        ├── layouts/BasicLayout.vue    # 底部导航布局
        ├── plugins/myAxios.ts         # Axios 实例配置
        └── services/                  # 用户状态管理
```

---

## 功能展示

博客详细介绍：https://darkyellowcat.github.io/2025/10/20/weconnected%E9%A1%B9%E7%9B%AE/

<img width="500" height="879" alt="首页推荐" src="https://github.com/user-attachments/assets/e920cc8c-f164-4b4e-9547-79700d70303c" />

<img width="501" height="881" alt="团队页面" src="https://github.com/user-attachments/assets/40c3de2b-2320-4c32-9983-b8ec9950d880" />

---

## 本地运行

### 环境要求
- JDK 8+
- Maven 3.6+
- MySQL 8.0
- Redis
- Node.js 16+

### 后端启动

```bash
cd weconnected-back
mvn clean install
mvn spring-boot:run
```

1. 创建 MySQL 数据库 `weconnected`，执行建表 SQL
2. 确保 Redis 服务已启动（默认 localhost:6379）
3. 启动后端服务（默认端口 8080）
4. API 文档：http://localhost:8080/api/doc.html

### 前端启动

```bash
cd weconnected-front
npm install
npm run dev
```

访问：http://localhost:3000/

---

## 未来计划

- AI 兴趣推荐系统
- Docker 一键部署
- 用户成就系统 & 积分机制
- Elasticsearch 全文搜索
- 推荐算法优化
- 消息已读/未读状态

---

## 联系作者

Author: darkyellowcat
Email: k4729.23098@gmail.com
Blog: https://darkyellowcat.github.io/


