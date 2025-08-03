<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <div class="not-found-icon">
        <el-icon size="120"><WarningFilled /></el-icon>
      </div>
      <h1>404</h1>
      <h2>页面不存在</h2>
      <p>抱歉，您访问的页面不存在或已被删除。</p>
      <div class="not-found-actions">
        <el-button type="primary" @click="$router.push('/dashboard')">
          返回首页
        </el-button>
        <el-button @click="$router.back()">
          返回上页
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { WarningFilled } from '@element-plus/icons-vue'
</script>

<style scoped>
.not-found-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.not-found-content {
  text-align: center;
  padding: 40px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
}

.not-found-icon {
  margin-bottom: 30px;
  color: #f56c6c;
}

.not-found-content h1 {
  font-size: 72px;
  color: #f56c6c;
  margin: 0 0 20px 0;
  font-weight: 700;
}

.not-found-content h2 {
  font-size: 24px;
  color: #333;
  margin: 0 0 15px 0;
  font-weight: 600;
}

.not-found-content p {
  font-size: 16px;
  color: #666;
  margin: 0 0 30px 0;
  line-height: 1.6;
}

.not-found-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .not-found-content {
    padding: 30px 20px;
  }
  
  .not-found-content h1 {
    font-size: 48px;
  }
  
  .not-found-content h2 {
    font-size: 20px;
  }
  
  .not-found-actions {
    flex-direction: column;
    align-items: center;
  }
}
</style> 