<template>
  <div class="bottom-bar">
    <div class="bottom-content">
      <div class="copyright">
        <span>&copy; 2024 耳鼻咽喉科检查系统 - 版本 1.0.0</span>
      </div>
      <div class="system-info">
        <span>当前时间：{{ currentTime }}</span>
        <span class="divider">|</span>
        <span>在线用户：{{ authStore.user?.nickName || authStore.user?.username }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const currentTime = ref('')
let timer: ReturnType<typeof setInterval> | null = null

// 更新当前时间
const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
})

onUnmounted(() => {
  if (timer) {
    clearInterval(timer)
  }
})
</script>

<style scoped>
.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background: #f8f9fa;
  border-top: 1px solid #e9ecef;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.1);
}

.bottom-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  max-width: 1400px;
  margin: 0 auto;
  font-size: 12px;
  color: #666;
}

.copyright {
  display: flex;
  align-items: center;
}

.system-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.divider {
  color: #ccc;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .bottom-content {
    flex-direction: column;
    gap: 5px;
    padding: 8px 15px;
    text-align: center;
  }
  
  .system-info {
    font-size: 11px;
  }
}
</style> 