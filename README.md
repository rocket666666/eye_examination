# 眼科检查系统 (Eye Examination System)

一个基于Vue3 + Spring Boot的前后端分离眼科检查管理系统，支持用户管理、角色权限、患者管理、检查记录等功能。

## 🏗️ 技术架构

### 前端技术栈
- **Vue 3** - 渐进式JavaScript框架
- **Element Plus** - Vue 3组件库
- **Vue Router** - 官方路由管理
- **Pinia** - 状态管理
- **Axios** - HTTP客户端
- **Vite** - 构建工具

### 后端技术栈
- **Spring Boot 2.7** - 企业级Java框架
- **Spring Security** - 安全认证框架
- **JWT** - Token认证
- **MyBatis Plus** - ORM框架
- **MySQL 8.0** - 关系型数据库
- **Redis 7.0** - 缓存数据库
- **Swagger** - API文档

### 部署技术
- **Docker** - 容器化部署
- **Docker Compose** - 容器编排
- **Nginx** - 反向代理

## 📊 数据库配置

- **数据库名称**: eye_examination
- **主机地址**: localhost
- **端口**: 3306
- **用户名**: eye_examination
- **密码**: eye_examination

## 🗂️ 项目结构

```
eye_examination/
├── backend/                    # Spring Boot后端
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/eye/examination/
│   │   │   │       ├── controller/     # 控制器
│   │   │   │       ├── service/        # 服务层
│   │   │   │       ├── mapper/         # 数据访问层
│   │   │   │       ├── entity/         # 实体类
│   │   │   │       ├── dto/            # 数据传输对象
│   │   │   │       ├── config/         # 配置类
│   │   │   │       └── common/         # 通用类
│   │   │   └── resources/
│   │   │       ├── application.yml     # 应用配置
│   │   │       └── mapper/             # MyBatis映射文件
│   │   └── test/
│   ├── pom.xml                         # Maven依赖配置
│   └── Dockerfile                      # Docker镜像构建文件
├── frontend/                   # Vue3前端
│   ├── src/
│   │   ├── api/               # API接口
│   │   ├── components/        # 公共组件
│   │   ├── views/            # 页面组件
│   │   ├── router/           # 路由配置
│   │   ├── store/            # 状态管理
│   │   ├── utils/            # 工具函数
│   │   └── assets/           # 静态资源
│   ├── package.json          # 依赖配置
│   ├── vite.config.js        # Vite配置
│   └── Dockerfile            # Docker镜像构建文件
├── docker/                   # Docker配置
│   ├── mysql/
│   │   ├── init.sql          # 数据库初始化脚本
│   │   └── my.cnf            # MySQL配置
│   ├── redis/
│   │   └── redis.conf        # Redis配置
│   └── nginx/
│       └── nginx.conf        # Nginx配置
├── docker-compose.yml        # Docker编排文件
└── README.md                 # 项目说明文档
```

## 📤 项目上传到 GitHub

### 初始化 Git 仓库并上传

如果这是一个新项目，需要初始化 Git 仓库并上传到 GitHub：

```bash
# 1. 初始化 Git 仓库
git init

# 2. 添加所有文件到暂存区
git add .

# 3. 创建初始提交
git commit -m "Initial commit: 眼科检查系统"

# 4. 添加远程仓库地址（替换为您的 GitHub 仓库地址）
git remote add origin https://github.com/rocket666666/eye_examination.git

# 5. 推送到 GitHub 主分支
git push -u origin main
```

### 克隆已存在的仓库

如果仓库已经存在于 GitHub 上：

```bash
# 克隆仓库到本地
git clone https://github.com/your-username/eye-examination-system.git
cd eye-examination-system
```

### 常用 Git 命令

```bash
# 查看当前状态
git status

# 查看提交历史
git log --oneline

# 创建新分支
git checkout -b feature/new-feature

# 切换分支
git checkout main

# 合并分支
git merge feature/new-feature

# 推送分支到远程
git push origin feature/new-feature

# 拉取最新代码
git pull origin main

# 查看远程仓库
git remote -v
```

### .gitignore 配置

项目应包含以下 `.gitignore` 文件内容：

```gitignore
# Java
*.class
*.jar
*.war
*.ear
*.zip
*.tar.gz
*.rar
target/
*.log

# Node.js
node_modules/
npm-debug.log*
yarn-debug.log*
yarn-error.log*
dist/
.env
.env.local
.env.development.local
.env.test.local
.env.production.local

# IDE
.idea/
.vscode/
*.swp
*.swo
*~

# OS
.DS_Store
Thumbs.db

# Docker
.dockerignore

# Logs
logs/
*.log

# Database
*.db
*.sqlite

# Environment variables
.env
.env.local
.env.*.local
```

### 贡献工作流

1. **Fork 项目**
   - 在 GitHub 上点击 Fork 按钮

2. **克隆您的 Fork**
   ```bash
   git clone https://github.com/your-username/eye-examination-system.git
   cd eye-examination-system
   ```

3. **添加上游仓库**
   ```bash
   git remote add upstream https://github.com/original-owner/eye-examination-system.git
   ```

4. **创建功能分支**
   ```bash
   git checkout -b feature/your-feature-name
   ```

5. **提交更改**
   ```bash
   git add .
   git commit -m "feat: 添加新功能描述"
   ```

6. **推送到您的 Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **创建 Pull Request**
   - 在 GitHub 上创建 Pull Request

### 版本标签管理

```bash
# 创建版本标签
git tag -a v1.0.0 -m "Release version 1.0.0"

# 推送标签到远程
git push origin v1.0.0

# 推送所有标签
git push origin --tags

# 查看所有标签
git tag -l

# 删除本地标签
git tag -d v1.0.0

# 删除远程标签
git push origin --delete v1.0.0
```

## 🚀 快速开始

### 前提条件

确保系统已安装以下软件：
- Docker (版本 20.10+)
- Docker Compose (版本 2.0+)

### 部署步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd eye_examination
   ```

2. **启动所有服务**
   ```bash
   docker-compose up -d
   ```

3. **查看服务状态**
   ```bash
   docker-compose ps
   ```

4. **查看日志**
   ```bash
   # 查看所有服务日志
   docker-compose logs -f
   
   # 查看特定服务日志
   docker-compose logs -f mysql
   docker-compose logs -f backend
   docker-compose logs -f frontend
   ```

### 访问地址

- **前端应用**: http://localhost
- **后端API**: http://localhost:8080/api
- **API文档**: http://localhost:8080/api/swagger-ui.html
- **MySQL**: localhost:3306
- **Redis**: localhost:6379

## 👥 默认账户

系统预置了以下测试账户：

| 用户名 | 密码 | 角色 | 说明 |
|--------|------|------|------|
| admin | admin123 | 超级管理员 | 拥有所有权限 |
| doctor | admin123 | 医生 | 医生角色权限 |
| nurse | admin123 | 护士 | 护士角色权限 |
| technician | admin123 | 技师 | 检查技师权限 |

### 🔒 安全说明
- 密码在前端使用 **SHA256 + 盐值** 加密后传输，确保网络传输安全
- 数据库中存储的是加密后的密码，不是明文
- 建议在生产环境中配置 HTTPS 以进一步提升安全性
- 登录验证采用 JWT Token 认证机制

## 🗄️ 数据库结构

### 系统管理表
- `sys_user` - 用户表
- `sys_role` - 角色表
- `sys_menu` - 菜单权限表
- `sys_dept` - 部门表
- `sys_user_role` - 用户角色关联表
- `sys_role_menu` - 角色菜单关联表
- `sys_config` - 系统配置表
- `sys_dict_type` - 字典类型表
- `sys_dict_data` - 字典数据表
- `sys_oper_log` - 操作日志表
- `sys_logininfor` - 登录日志表

### 业务功能表
- `patient_info` - 患者信息表
- `exam_item` - 检查项目表
- `exam_record` - 检查记录表
- `exam_record_item` - 检查记录项目明细表
- `exam_report` - 检查报告表

## 🔧 开发环境配置

### 开发环境启动（推荐）

项目提供了便捷的启动脚本，可以一键启动整个开发环境：

#### 环境要求
- Java 8+ 
- Maven 3.6+
- Node.js 16+
- npm 8+
- MySQL 8.0（运行中）
- Redis 7.0（运行中）

#### 启动命令

1. **一键启动项目**
   ```bash
   # 启动前后端服务
   ./start.sh
   ```
   
   此命令会：
   - 检查必要的开发环境（Java、Maven、Node.js、MySQL、Redis）
   - 自动编译后端代码并启动Spring Boot服务
   - 安装前端依赖并启动Vite开发服务器
   - 在后台运行服务，日志输出到 `logs/` 目录

2. **检查服务状态**
   ```bash
   # 查看所有服务运行状态
   ./status.sh
   ```
   
   此命令会显示：
   - 前端服务状态（端口3000）
   - 后端服务状态（端口8080）
   - MySQL服务状态（端口3306）
   - Redis服务状态（端口6379）
   - 系统基本信息

3. **停止所有服务**
   ```bash
   # 停止前后端服务
   ./stop.sh
   ```

#### 访问地址（开发环境）
- **前端开发服务器**: http://localhost:3000
- **后端API服务**: http://localhost:8080
- **API接口测试**: http://localhost:8080/api/

#### 日志查看
```bash
# 查看后端日志
tail -f logs/backend.log

# 查看前端日志  
tail -f logs/frontend.log

# 查看启动日志
tail -f logs/start.log
```

### 手动启动方式

如果需要手动启动各个服务：

#### 后端开发环境

1. **Java开发环境**
   - JDK 8 或以上版本
   - Maven 3.6+
   - IDE: IntelliJ IDEA 或 Eclipse

2. **运行后端服务**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

3. **配置文件**
   - 开发环境：`application-dev.yml`
   - 生产环境：`application-prod.yml`
   - Docker环境：`application-docker.yml`

#### 前端开发环境

1. **Node.js环境**
   - Node.js 16+ 
   - npm 8+ 或 yarn

2. **运行前端服务**
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

3. **构建生产版本**
   ```bash
   npm run build
   ```

## 📋 核心功能

### 系统管理
- ✅ 用户管理：用户增删改查、状态管理
- ✅ 角色管理：角色权限分配
- ✅ 菜单管理：动态菜单配置
- ✅ 部门管理：组织架构管理
- ✅ 字典管理：系统字典维护
- ✅ 参数配置：系统参数设置
- ✅ 日志管理：操作日志、登录日志

### 权限控制
- ✅ JWT Token认证
- ✅ 基于角色的访问控制(RBAC)
- ✅ 菜单权限控制
- ✅ 按钮权限控制
- ✅ 数据权限控制

### 眼科检查管理
- ✅ 患者信息管理
- ✅ 检查项目配置
- ✅ 检查记录管理
- ✅ 检查报告生成

## 🛠️ 维护命令

### Docker容器管理

```bash
# 启动服务
docker-compose up -d

# 停止服务
docker-compose down

# 重启服务
docker-compose restart

# 重新构建并启动
docker-compose up -d --build

# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f [service_name]

# 进入容器
docker exec -it eye_examination_mysql bash
docker exec -it eye_examination_redis sh
docker exec -it eye_examination_backend bash
```

### 数据库管理

```bash
# 连接MySQL
docker exec -it eye_examination_mysql mysql -u eye_examination -p

# 备份数据库
docker exec eye_examination_mysql mysqldump -u eye_examination -p eye_examination > backup.sql

# 恢复数据库
docker exec -i eye_examination_mysql mysql -u eye_examination -p eye_examination < backup.sql
```

### 缓存管理

```bash
# 连接Redis
docker exec -it eye_examination_redis redis-cli

# 清除所有缓存
docker exec eye_examination_redis redis-cli FLUSHALL
```

## 🚨 故障排除

### 常见问题

1. **端口冲突**
   - 检查3306、6379、8080、80端口是否被占用
   - 修改docker-compose.yml中的端口映射

2. **数据库连接失败**
   - 检查MySQL容器是否正常启动
   - 确认数据库配置信息正确

3. **Redis连接失败**
   - 检查Redis容器状态
   - 确认Redis配置正确

4. **前端访问404**
   - 检查Nginx配置
   - 确认前端构建成功

### 日志查看

```bash
# 查看所有服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f mysql
docker-compose logs -f redis
docker-compose logs -f backend
docker-compose logs -f frontend
```

## 📄 许可证

本项目采用 MIT 许可证。

## 🤝 贡献指南

我们欢迎任何形式的贡献！请参考上面的"贡献工作流"部分了解详细步骤。

### 简化版贡献流程

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'feat: Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

### 提交信息规范

请使用以下格式编写提交信息：

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

**类型 (type):**
- `feat`: 新功能
- `fix`: 修复 bug
- `docs`: 文档更新
- `style`: 代码格式修改
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建过程或辅助工具的变动

**示例:**
```
feat(auth): 添加JWT认证功能
fix(database): 修复用户查询时的SQL错误
docs(readme): 更新安装说明
```

## 📞 联系方式

如有问题或建议，请联系：
- 邮箱: admin@eye.com
- 项目地址: [GitHub Repository](https://github.com/your-username/eye-examination-system)
- 问题反馈: [Issues](https://github.com/your-username/eye-examination-system/issues)
- 功能建议: [Discussions](https://github.com/your-username/eye-examination-system/discussions)

---

**注意**: 这是一个示例项目，生产环境使用前请根据实际需求进行安全配置和优化。 