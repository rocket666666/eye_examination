# 样式文件说明

## 通用样式文件

### `list-common.css`
这个文件包含了所有列表页面的通用样式，确保所有列表页面保持一致的视觉风格。

#### 使用方法
在需要使用的Vue组件中引入：
```typescript
import '@/styles/list-common.css'
```

#### 包含的样式类

##### 页面容器
- `.list-page-container` - 页面主容器，设置背景色和最小高度
- `.list-page-content` - 页面内容区域，设置内边距和布局

##### 面包屑导航
- `.breadcrumb-container` - 面包屑导航容器
- `.breadcrumb-left` - 左侧面包屑内容
- `.breadcrumb-right` - 右侧返回按钮
- `.back-btn` - 返回按钮样式

##### 主内容区域
- `.main-content` - 主要内容区域，包含搜索、操作按钮和表格

##### 搜索栏
- `.search-bar` - 搜索栏容器
- `.search-bar .el-form` - 搜索表单样式
- `.search-bar .el-form-item` - 搜索表单项样式

##### 操作按钮栏
- `.action-bar` - 操作按钮栏容器
- `.action-left` - 左侧操作按钮组
- `.action-right` - 右侧操作按钮组
- `.action-btn` - 操作按钮样式

##### 表格容器
- `.table-container` - 表格容器样式

##### 分页容器
- `.pagination-container` - 分页器容器样式

#### 样式特点
- 统一的白色背景和阴影效果
- 一致的圆角设计
- 响应式布局支持
- 统一的间距和字体大小

#### 注意事项
1. 引入此文件后，组件中不需要重复定义这些通用样式
2. 只保留页面特有的样式定义
3. 确保HTML结构使用对应的CSS类名
4. 如需修改通用样式，请在此文件中统一修改

#### 示例
```vue
<template>
  <div class="list-page-container">
    <div class="list-page-content">
      <!-- 面包屑导航 -->
      <div class="breadcrumb-container">
        <!-- 面包屑内容 -->
      </div>
      
      <!-- 主内容区域 -->
      <div class="main-content">
        <!-- 搜索栏 -->
        <div class="search-bar">
          <!-- 搜索表单 -->
        </div>
        
        <!-- 操作按钮栏 -->
        <div class="action-bar">
          <!-- 操作按钮 -->
        </div>
        
        <!-- 表格容器 -->
        <div class="table-container">
          <!-- 表格内容 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import '@/styles/list-common.css'
</script>

<style scoped>
/* 只保留页面特有的样式 */
</style> 