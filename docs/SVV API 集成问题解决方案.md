# SVV API 集成问题解决方案

## 问题诊断

### 原始错误
```json
{
  "timestamp": "2025-08-02 17:04:45",
  "status": 404,
  "error": "Not Found", 
  "path": "/api/svv/launch"
}
```

### 问题根源
1. **后端服务重启**：SVVController需要重新编译和部署
2. **Spring Security配置**：SVV API需要正确的认证配置
3. **前端认证状态**：需要用户先登录获取JWT token

## 解决方案

### 1. 后端修复 ✅

#### 重新编译部署
```bash
# 编译项目
mvn clean package -DskipTests

# 重启服务
pkill -f "eye-examination-backend"
java -jar target/eye-examination-backend-1.0.0.jar &
```

#### 更新Spring Security配置
在`SecurityConfig.java`中添加SVV API授权：
```java
// SVV检查工具接口需要认证
.antMatchers("/api/svv/**").authenticated()
```

### 2. API状态验证 ✅

#### 测试端点可访问性
```bash
# 测试API端点（应返回401未授权，说明端点存在但需要认证）
curl -X POST http://localhost:8080/api/svv/launch \
     -H "Content-Type: application/json"
```

**预期响应**：
```json
{"code":401,"message":"未授权访问","data":null,"timestamp":1754125679093}
```

### 3. 前端使用指南

#### 完整操作流程
1. **用户登录**：
   ```
   访问系统 → 输入用户名密码 → 登录成功获得JWT token
   ```

2. **导航到SVV检查**：
   ```
   Dashboard → 点击"眼部检查" → 检查工具页面
   ```

3. **启动SVV工具**：
   ```
   点击"开始SVV检查" → 确认对话框 → API调用 → 桌面工具启动
   ```

#### API调用示例
```typescript
// 前端API调用（已集成认证）
import { launchSVVChecker } from '@/api/svv'

const startSVVChecker = async () => {
  try {
    const response = await launchSVVChecker()
    if (response.code === 200) {
      ElMessage.success('SVV检查工具启动成功！')
    }
  } catch (error) {
    ElMessage.error('启动失败：' + error.message)
  }
}
```

### 4. 故障排除

#### 常见问题及解决方案

**问题1：404 Not Found**
- **原因**：后端服务未正确加载SVVController
- **解决**：重新编译部署后端服务

**问题2：401 Unauthorized** 
- **原因**：用户未登录或token已过期
- **解决**：确保用户已登录系统

**问题3：500 Internal Server Error**
- **原因**：Java环境或SVV工具启动失败  
- **解决**：检查Java环境和系统权限

#### 调试命令
```bash
# 检查后端服务状态
ps aux | grep java | grep eye-examination

# 查看后端日志
tail -f backend/logs/eye-examination.log

# 测试API连通性
curl -X GET http://localhost:8080/api/svv/info
```

### 5. 系统架构

```
前端 (localhost:3000)
├── Vue 3 + Element Plus
├── JWT认证管理  
├── API代理配置 → /api/* → localhost:8080
└── SVV组件界面

后端 (localhost:8080)  
├── Spring Boot + Spring Security
├── JWT过滤器认证
├── SVVController API端点
└── Java Swing SVV工具启动器

桌面应用
├── Java Swing GUI
├── SVV检查工具界面
├── 患者信息管理
└── 结果导出功能
```

### 6. API接口文档

#### 启动SVV工具
- **URL**: `POST /api/svv/launch`
- **认证**: 需要JWT token
- **响应**: 
  ```json
  {
    "code": 200,
    "message": "SVV检查工具启动成功", 
    "data": null
  }
  ```

#### 获取工具信息
- **URL**: `GET /api/svv/info`
- **认证**: 需要JWT token
- **响应**:
  ```json
  {
    "code": 200,
    "data": {
      "name": "SVV检查工具",
      "version": "1.0.0",
      "features": [...],
      "systemRequirements": [...]
    }
  }
  ```

#### 检查运行状态
- **URL**: `GET /api/svv/status`  
- **认证**: 需要JWT token
- **响应**:
  ```json
  {
    "code": 200,
    "message": "SVV检查工具正在运行",
    "data": null
  }
  ```

## 验证清单

- [x] 后端服务正常运行 (port 8080)
- [x] SVVController已加载到Spring容器
- [x] Spring Security正确配置SVV API权限
- [x] 前端代理配置正确 (port 3000 → 8080)
- [x] JWT认证机制工作正常
- [x] SVV Java工具编译无错误

## 使用建议

1. **开发环境**：确保前后端都在运行
2. **用户培训**：强调需要先登录再使用SVV工具
3. **错误提示**：在前端添加更友好的错误提示
4. **日志监控**：定期检查后端日志确保系统稳定

---

**解决状态**: ✅ 已修复  
**更新时间**: 2025-08-02 17:10  
**版本**: v1.0.1