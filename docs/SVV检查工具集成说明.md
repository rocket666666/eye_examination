# SVV检查工具集成说明

## 功能概述

耳鼻咽喉科检查系统已成功集成SVV（主观垂直视觉）检查工具，提供从Web界面启动专业桌面检查应用的完整解决方案。

## 系统架构

```
前端 Vue3 + Element Plus
    ↓ (API调用)
后端 Spring Boot
    ↓ (线程启动)
Java Swing SVV工具
```

## 核心功能

### 1. Web界面导航
- **Dashboard首页** → 点击"眼部检查"
- **检查页面** → 启动SVV检查工具
- **帮助页面** → 查看使用说明

### 2. SVV检查工具特性
- ✅ 高精度角度测量(±0.1°)
- ✅ 3D圆柱管样式线条
- ✅ 多种控制方式(键盘+鼠标+滚轮)
- ✅ 自动旋转和微调功能
- ✅ 患者信息管理
- ✅ 检查结果导出到桌面

### 3. 快捷键操作
```
← : 顺时针自动旋转
→ : 逆时针自动旋转  
↑ : 保存当前结果
↓ : 停止旋转
滚轮: ±0.1°微调
双击: 固定线条
```

## 使用流程

### 步骤1：登录系统
1. 访问耳鼻咽喉科检查系统
2. 使用业务账号登录

### 步骤2：进入检查界面
1. 在主页Dashboard点击"眼部检查"
2. 进入检查工具页面

### 步骤3：启动SVV工具
1. 点击"开始SVV检查"按钮
2. 确认启动对话框
3. 系统自动启动桌面检查工具

### 步骤4：进行检查
1. 患者坐在检查工具前
2. 使用快捷键或鼠标调整线条角度
3. 多次测量并保存结果
4. 填写患者信息并导出报告

## 技术实现

### 前端文件结构
```
frontend/src/
├── views/
│   ├── Examination.vue      # 检查主页面
│   └── Help.vue            # 帮助页面
├── api/
│   └── svv.ts              # SVV API调用
└── router/
    └── index.ts            # 路由配置
```

### 后端文件结构
```
backend/src/main/java/com/eyeexam/
├── controller/
│   └── SVVController.java   # SVV控制器
└── utils/
    ├── SVVWidget.java      # 绘图组件
    ├── SVVMainFrame.java   # 主窗口
    ├── PatientInfoDialog.java # 患者信息对话框
    ├── SVVChecker.java     # 主入口
    └── SVVDemo.java        # 演示类
```

### API接口

#### 1. 启动SVV工具
```http
POST /api/svv/launch
Response: { "code": 200, "message": "SVV检查工具启动成功", "data": null }
```

#### 2. 检查运行状态
```http
GET /api/svv/status
Response: { "code": 200, "message": "SVV检查工具正在运行", "data": null }
```

#### 3. 获取工具信息
```http
GET /api/svv/info
Response: { 
  "code": 200, 
  "data": {
    "name": "SVV检查工具",
    "version": "1.0.0",
    "features": [...],
    "systemRequirements": [...]
  }
}
```

## 部署说明

### 1. 环境要求
- Java Runtime Environment 8+
- Node.js 16+ (开发环境)
- Spring Boot 2.7+
- Vue 3 + Element Plus

### 2. 编译运行
```bash
# 后端编译
cd backend
mvn clean package

# 前端编译
cd frontend
npm install
npm run build

# 启动服务
java -jar backend/target/eye-examination-backend.jar
```

### 3. 验证功能
1. 访问 http://localhost:8080
2. 登录系统
3. 导航到眼部检查页面
4. 点击启动SVV工具
5. 验证桌面应用正常启动

## 医学应用

### 适用场景
- **前庭系统功能评估**：检测平衡功能异常
- **脑干病变诊断**：识别中枢性病变
- **眩晕病因分析**：区分周围性vs中枢性眩晕
- **康复效果评估**：跟踪治疗进展

### 正常值参考
- **健康成人**：SVV偏差 ≤ ±2°
- **轻度异常**：±2° ~ ±5°
- **显著异常**：> ±5°

### 注意事项
1. 确保检查环境安静
2. 患者头部保持稳定
3. 多次测量提高准确性
4. 记录详细患者信息

## 故障排除

### 常见问题

#### 1. SVV工具无法启动
**原因**：Java环境未安装或版本过低
**解决**：安装JRE 8或更高版本

#### 2. 界面显示异常
**原因**：显示器分辨率过低或字体缺失
**解决**：调整分辨率或安装中文字体

#### 3. 检查结果无法保存
**原因**：桌面路径权限不足
**解决**：以管理员权限运行或更改保存路径

### 日志查看
```bash
# 后端日志
tail -f backend/logs/eye-examination.log

# SVV工具日志
查看控制台输出或系统事件日志
```

## 更新计划

### 下一版本功能
- [ ] Web版SVV检查工具
- [ ] 多语言支持
- [ ] 云端数据同步
- [ ] 移动端支持
- [ ] AI辅助分析

### 技术优化
- [ ] 微服务架构重构
- [ ] Docker容器化部署
- [ ] 实时数据推送
- [ ] 性能监控仪表板

---

**技术支持**: support@eyeexam.com  
**更新日期**: 2024年12月  
**版本**: v1.0.0