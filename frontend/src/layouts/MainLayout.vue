<template>
  <div class="main-layout">
    <!-- 顶部固定导航栏 -->
    <TopNavBar />
    
    <!-- 主要内容区域 -->
    <div class="main-content">
      <router-view />
    </div>
    
    <!-- 底部固定栏 -->
    <BottomBar />
  </div>
</template>

<script setup lang="ts">
import TopNavBar from '@/components/TopNavBar.vue'
import BottomBar from '@/components/BottomBar.vue'
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  padding-top: 50px; /* 为顶部导航栏留空间 */
  padding-bottom: 50px; /* 为底部栏留空间 */
  background: #f5f5f5;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .main-content {
    padding-top: 55px;
    padding-bottom: 60px;
  }
}
</style> 