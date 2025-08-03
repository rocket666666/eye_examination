<template>
  <div class="top-navbar">
    <!-- 系统信息栏 -->
    <div class="system-bar">
      <div class="system-content">
        <div class="system-left">
          <span class="system-title">眼科检查系统</span>
        </div>
        <div class="system-right">
          <span class="user-welcome">欢迎，{{ authStore.user?.nickName || authStore.user?.username }}</span>
          <el-button type="danger" size="small" @click="handleLogout" class="logout-btn">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { SwitchButton } from '@element-plus/icons-vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

// 退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await authStore.logoutAction()
    ElMessage.success('退出登录成功')
    router.push('/login')
  } catch (error) {
    // 用户取消操作
  }
}
</script>

<style scoped>
.top-navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 系统信息栏 */
.system-bar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 12px 0;
}

.system-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.system-title {
  font-size: 16px;
  font-weight: 600;
}

.system-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-welcome {
  font-size: 14px;
  opacity: 0.9;
}

.logout-btn {
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: white;
}

.logout-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  border-color: rgba(255, 255, 255, 0.5);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .system-content {
    padding: 0 15px;
  }
  
  .user-welcome {
    display: none;
  }
  
  .system-title {
    font-size: 14px;
  }
}
</style> 