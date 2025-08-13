# 耳鼻咽喉科检查系统UI设计规范

## 文档信息
- **版本**: 1.0
- **更新时间**: 2025年7月12日
- **适用范围**: 前端界面设计
- **设计原则**: 简洁、易用、专业、安全

---

## 1. 总体设计原则

### 1.1 设计理念
- **医疗专业性**: 界面设计体现医疗行业的专业性和严谨性
- **用户友好**: 界面简洁直观，降低学习成本
- **信息清晰**: 重要信息突出显示，避免干扰
- **操作高效**: 减少操作步骤，提高工作效率

### 1.2 设计目标
- 提供清晰的信息架构和导航系统
- 确保在不同设备上的良好体验
- 支持快速的数据录入和查询
- 保证系统的可访问性和易用性

---

## 2. 视觉设计规范

### 2.1 色彩系统

#### 主色调
```css
/* 主色调 - 医疗蓝 */
--primary-color: #1890ff;          /* 主要操作按钮 */
--primary-light: #40a9ff;          /* 悬停状态 */
--primary-dark: #096dd9;           /* 按压状态 */
--primary-bg: #e6f7ff;             /* 背景色 */

/* 辅助色 - 成功绿 */
--success-color: #52c41a;          /* 成功状态 */
--success-light: #73d13d;          /* 悬停状态 */
--success-bg: #f6ffed;             /* 背景色 */

/* 警告色 - 橙黄 */
--warning-color: #faad14;          /* 警告状态 */
--warning-light: #ffc53d;          /* 悬停状态 */
--warning-bg: #fffbe6;             /* 背景色 */

/* 错误色 - 红色 */
--error-color: #ff4d4f;            /* 错误状态 */
--error-light: #ff7875;            /* 悬停状态 */
--error-bg: #fff2f0;               /* 背景色 */
```

#### 中性色
```css
/* 文本颜色 */
--text-primary: #262626;           /* 主要文本 */
--text-secondary: #595959;         /* 次要文本 */
--text-disabled: #bfbfbf;          /* 禁用文本 */
--text-placeholder: #d9d9d9;       /* 占位符 */

/* 边框颜色 */
--border-color: #d9d9d9;           /* 默认边框 */
--border-light: #f0f0f0;           /* 浅色边框 */
--border-dark: #bfbfbf;            /* 深色边框 */

/* 背景颜色 */
--bg-white: #ffffff;               /* 白色背景 */
--bg-light: #fafafa;               /* 浅色背景 */
--bg-gray: #f5f5f5;                /* 灰色背景 */
--bg-dark: #001529;                /* 深色背景 */
```

### 2.2 字体系统

#### 字体家族
```css
/* 中文字体 */
--font-family-zh: "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", "Arial", sans-serif;

/* 英文字体 */
--font-family-en: "Segoe UI", "Helvetica Neue", "Arial", sans-serif;

/* 等宽字体 */
--font-family-mono: "SFMono-Regular", "Consolas", "Monaco", "Courier New", monospace;
```

#### 字体大小
```css
/* 标题字体 */
--font-size-h1: 24px;              /* 页面主标题 */
--font-size-h2: 20px;              /* 区块标题 */
--font-size-h3: 16px;              /* 子标题 */
--font-size-h4: 14px;              /* 小标题 */

/* 正文字体 */
--font-size-base: 14px;            /* 基础字号 */
--font-size-lg: 16px;              /* 大字号 */
--font-size-sm: 12px;              /* 小字号 */
--font-size-xs: 10px;              /* 超小字号 */
```

#### 字体粗细
```css
--font-weight-light: 300;          /* 细体 */
--font-weight-normal: 400;         /* 正常 */
--font-weight-medium: 500;         /* 中等 */
--font-weight-bold: 600;           /* 粗体 */
```

### 2.3 间距系统

#### 基础间距
```css
--spacing-xs: 4px;                 /* 超小间距 */
--spacing-sm: 8px;                 /* 小间距 */
--spacing-base: 16px;              /* 基础间距 */
--spacing-lg: 24px;                /* 大间距 */
--spacing-xl: 32px;                /* 超大间距 */
--spacing-xxl: 48px;               /* 特大间距 */
```

#### 组件间距
```css
--margin-xs: 4px;
--margin-sm: 8px;
--margin-base: 16px;
--margin-lg: 24px;
--margin-xl: 32px;

--padding-xs: 4px;
--padding-sm: 8px;
--padding-base: 16px;
--padding-lg: 24px;
--padding-xl: 32px;
```

### 2.4 圆角系统
```css
--border-radius-sm: 2px;           /* 小圆角 */
--border-radius-base: 4px;         /* 基础圆角 */
--border-radius-lg: 6px;           /* 大圆角 */
--border-radius-xl: 8px;           /* 超大圆角 */
```

### 2.5 阴影系统
```css
--shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);        /* 小阴影 */
--shadow-base: 0 1px 6px rgba(0, 0, 0, 0.1);       /* 基础阴影 */
--shadow-lg: 0 4px 12px rgba(0, 0, 0, 0.15);       /* 大阴影 */
--shadow-xl: 0 8px 24px rgba(0, 0, 0, 0.2);        /* 超大阴影 */
```

---

## 3. 登录界面设计

### 3.1 前端业务登录页面

#### 布局结构
```css
.login-container {
    display: flex;
    min-height: 100vh;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-content {
    margin: auto;
    padding: 40px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    min-width: 400px;
}
```

#### 设计要点
- **整体布局**: 居中布局，背景使用渐变色
- **表单容器**: 白色背景，圆角边框，投影效果
- **品牌标识**: 顶部显示系统logo和名称
- **输入框**: 大尺寸，圆角边框，图标标识
- **按钮**: 主色调，圆角，悬停效果

#### 响应式设计
```css
/* 移动端适配 */
@media (max-width: 768px) {
    .login-content {
        margin: 20px;
        padding: 30px 20px;
        min-width: auto;
    }
}
```

### 3.2 后端管理登录页面

#### 布局结构
```css
.admin-login-container {
    display: flex;
    min-height: 100vh;
    background: #f0f2f5;
}

.admin-login-content {
    margin: auto;
    padding: 48px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    min-width: 450px;
}
```

#### 设计要点
- **整体风格**: 更商务化，色调偏向中性
- **验证码**: 图形验证码，刷新功能
- **安全提示**: 显示安全相关信息
- **链接**: 提供到业务系统的链接

---

## 4. 九宫格首页设计

### 4.1 布局结构
```css
.dashboard-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 24px;
    padding: 24px;
    max-width: 1200px;
    margin: 0 auto;
}

.grid-item {
    aspect-ratio: 1;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.3s ease;
}

.grid-item:hover {
    transform: translateY(-4px);
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}
```

### 4.2 九宫格项目设计

#### 激活项目（患者管理、眼部检查、帮助说明）
```css
.grid-item.active {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-decoration: none;
    color: inherit;
}

.grid-item.active .icon {
    font-size: 48px;
    color: var(--primary-color);
    margin-bottom: 16px;
}

.grid-item.active .title {
    font-size: 18px;
    font-weight: 600;
    color: var(--text-primary);
    margin-bottom: 8px;
}

.grid-item.active .subtitle {
    font-size: 14px;
    color: var(--text-secondary);
}
```

#### 预留项目
```css
.grid-item.placeholder {
    background: #f8f9fa;
    border: 2px dashed #dee2e6;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 16px;
    color: #6c757d;
}
```

### 4.3 响应式设计
```css
/* 平板端 */
@media (max-width: 768px) {
    .dashboard-grid {
        grid-template-columns: repeat(2, 1fr);
        gap: 16px;
        padding: 16px;
    }
}

/* 手机端 */
@media (max-width: 480px) {
    .dashboard-grid {
        grid-template-columns: 1fr;
        gap: 16px;
        padding: 16px;
    }
}
```

---

## 5. 管理系统界面设计

### 5.1 整体布局
```css
.admin-layout {
    display: flex;
    min-height: 100vh;
}

.admin-sidebar {
    width: 256px;
    background: #001529;
    color: white;
    overflow-y: auto;
}

.admin-main {
    flex: 1;
    display: flex;
    flex-direction: column;
}

.admin-header {
    height: 64px;
    background: white;
    border-bottom: 1px solid #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 24px;
}

.admin-content {
    flex: 1;
    padding: 24px;
    background: #f0f2f5;
}
```

### 5.2 侧边栏设计
```css
.sidebar-menu {
    padding: 16px 0;
}

.menu-item {
    display: flex;
    align-items: center;
    padding: 12px 24px;
    color: rgba(255, 255, 255, 0.7);
    cursor: pointer;
    transition: all 0.3s ease;
}

.menu-item:hover {
    background: rgba(255, 255, 255, 0.1);
    color: white;
}

.menu-item.active {
    background: var(--primary-color);
    color: white;
}

.menu-item .icon {
    font-size: 16px;
    margin-right: 12px;
}
```

### 5.3 内容区域设计
```css
.content-card {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 24px;
    margin-bottom: 24px;
}

.content-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
}

.content-title {
    font-size: 20px;
    font-weight: 600;
    color: var(--text-primary);
}
```

---

## 6. 组件设计规范

### 6.1 按钮设计
```css
/* 主要按钮 */
.btn-primary {
    background: var(--primary-color);
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.3s ease;
}

.btn-primary:hover {
    background: var(--primary-light);
}

/* 次要按钮 */
.btn-secondary {
    background: white;
    color: var(--primary-color);
    border: 1px solid var(--primary-color);
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.3s ease;
}

.btn-secondary:hover {
    background: var(--primary-bg);
}
```

### 6.2 表单设计
```css
.form-group {
    margin-bottom: 16px;
}

.form-label {
    display: block;
    margin-bottom: 8px;
    font-weight: 500;
    color: var(--text-primary);
}

.form-input {
    width: 100%;
    padding: 8px 12px;
    border: 1px solid var(--border-color);
    border-radius: 4px;
    font-size: 14px;
    transition: border-color 0.3s ease;
}

.form-input:focus {
    outline: none;
    border-color: var(--primary-color);
    box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
}
```

### 6.3 表格设计
```css
.table {
    width: 100%;
    border-collapse: collapse;
    background: white;
    border-radius: 8px;
    overflow: hidden;
}

.table th {
    background: #fafafa;
    color: var(--text-primary);
    font-weight: 600;
    padding: 12px 16px;
    text-align: left;
    border-bottom: 1px solid var(--border-light);
}

.table td {
    padding: 12px 16px;
    border-bottom: 1px solid var(--border-light);
    color: var(--text-primary);
}

.table tr:hover {
    background: var(--bg-light);
}
```

---

## 7. 图标系统

### 7.1 图标规范
- **图标库**: 使用 Element Plus Icons 或 Ant Design Icons
- **尺寸**: 16px、20px、24px、32px、48px
- **颜色**: 遵循色彩系统，默认使用文本色
- **风格**: 线性图标为主，保持一致性

### 7.2 功能图标
```css
/* 常用图标 */
.icon-user { /* 用户相关 */ }
.icon-eye { /* 眼部检查 */ }
.icon-help { /* 帮助说明 */ }
.icon-setting { /* 设置 */ }
.icon-logout { /* 退出 */ }
.icon-menu { /* 菜单 */ }
.icon-search { /* 搜索 */ }
.icon-add { /* 新增 */ }
.icon-edit { /* 编辑 */ }
.icon-delete { /* 删除 */ }
```

---

## 8. 交互设计

### 8.1 动画效果
```css
/* 过渡动画 */
.transition-all {
    transition: all 0.3s ease;
}

.transition-transform {
    transition: transform 0.3s ease;
}

/* 淡入淡出 */
.fade-enter-active, .fade-leave-active {
    transition: opacity 0.3s ease;
}

.fade-enter, .fade-leave-to {
    opacity: 0;
}

/* 滑动效果 */
.slide-enter-active, .slide-leave-active {
    transition: transform 0.3s ease;
}

.slide-enter, .slide-leave-to {
    transform: translateX(100%);
}
```

### 8.2 状态反馈
```css
/* 加载状态 */
.loading {
    position: relative;
    pointer-events: none;
}

.loading::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.8);
    display: flex;
    align-items: center;
    justify-content: center;
}

/* 禁用状态 */
.disabled {
    opacity: 0.5;
    cursor: not-allowed;
    pointer-events: none;
}
```

---

## 9. 响应式设计

### 9.1 断点设置
```css
/* 断点定义 */
--breakpoint-xs: 480px;     /* 超小屏幕 */
--breakpoint-sm: 576px;     /* 小屏幕 */
--breakpoint-md: 768px;     /* 中等屏幕 */
--breakpoint-lg: 992px;     /* 大屏幕 */
--breakpoint-xl: 1200px;    /* 超大屏幕 */
--breakpoint-xxl: 1600px;   /* 特大屏幕 */
```

### 9.2 适配策略
- **移动端优先**: 从小屏幕开始设计
- **弹性布局**: 使用 Flex 和 Grid 布局
- **相对单位**: 使用 rem、em、% 等相对单位
- **图片适配**: 使用 max-width: 100% 确保图片响应式

---

## 10. 无障碍设计

### 10.1 色彩对比
- 确保文本和背景的对比度至少为 4.5:1
- 重要信息不仅依赖颜色区分

### 10.2 键盘导航
```css
/* 焦点样式 */
.focusable:focus {
    outline: 2px solid var(--primary-color);
    outline-offset: 2px;
}

/* 跳过链接 */
.skip-link {
    position: absolute;
    top: -40px;
    left: 6px;
    background: var(--primary-color);
    color: white;
    padding: 8px;
    text-decoration: none;
    border-radius: 4px;
    z-index: 1000;
}

.skip-link:focus {
    top: 6px;
}
```

### 10.3 语义化标签
- 使用语义化的 HTML 标签
- 为图片添加 alt 属性
- 使用 aria-label 等 ARIA 属性

---

## 11. 设计检查清单

### 11.1 登录页面
- [ ] 品牌标识清晰显示
- [ ] 表单验证信息友好
- [ ] 支持键盘导航
- [ ] 移动端适配良好
- [ ] 加载状态显示

### 11.2 九宫格首页
- [ ] 功能图标清晰易懂
- [ ] 悬停效果自然
- [ ] 响应式布局正确
- [ ] 预留功能明确标识
- [ ] 用户信息显示完整

### 11.3 管理系统
- [ ] 菜单结构清晰
- [ ] 内容区域布局合理
- [ ] 操作按钮位置合适
- [ ] 数据表格易读
- [ ] 权限控制明确

---

**文档版本**: 1.0  
**最后更新**: 2024年12月7日  
**审核状态**: 待审核 