<template>
  <div class="admin-layout">
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <div class="admin-sidebar">
          <div class="sidebar-header">
            <h3>管理系统</h3>
          </div>
          <el-menu
            :default-active="$route.path"
            router
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409eff"
          >
            <el-menu-item index="/admin/dashboard">
              <el-icon><House /></el-icon>
              <span>控制台</span>
            </el-menu-item>
            <el-menu-item index="/admin/users">
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/roles">
              <el-icon><Key /></el-icon>
              <span>角色管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/menus">
              <el-icon><Menu /></el-icon>
              <span>菜单管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/system">
              <el-icon><Setting /></el-icon>
              <span>系统设置</span>
            </el-menu-item>
          </el-menu>
        </div>
      </el-aside>
      
      <!-- 主要内容区域 -->
      <el-container>
        <!-- 顶部导航 -->
        <el-header>
          <div class="admin-header">
            <div class="header-left">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">
                  首页
                </el-breadcrumb-item>
                <el-breadcrumb-item>{{ pageTitle }}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <div class="header-right">
              <el-dropdown @command="handleCommand">
                <span class="user-dropdown">
                  {{ authStore.user?.nickName || authStore.user?.username }}
                  <el-icon class="el-icon--right"><arrow-down /></el-icon>
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item command="profile">
                      <el-icon><User /></el-icon>
                      个人资料
                    </el-dropdown-item>
                    <el-dropdown-item command="password">
                      <el-icon><Lock /></el-icon>
                      修改密码
                    </el-dropdown-item>
                    <el-dropdown-item divided command="logout">
                      <el-icon><SwitchButton /></el-icon>
                      退出登录
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </el-header>
        
        <!-- 主要内容 -->
        <el-main>
          <div class="admin-content">
            <router-view />
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  House,
  User,
  Key,
  Menu,
  Setting,
  ArrowDown,
  Lock,
  SwitchButton
} from '@element-plus/icons-vue'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

// 页面标题
const pageTitle = computed(() => {
  const routeMap: { [key: string]: string } = {
    '/admin/dashboard': '控制台',
    '/admin/users': '用户管理',
    '/admin/roles': '角色管理',
    '/admin/menus': '菜单管理',
    '/admin/system': '系统设置'
  }
  return routeMap[route.path] || '未知页面'
})

// 处理下拉菜单命令
const handleCommand = async (command: string) => {
  switch (command) {
    case 'profile':
      // 跳转到个人资料页面
      router.push('/admin/profile')
      break
    case 'password':
      // 跳转到修改密码页面
      router.push('/admin/password')
      break
    case 'logout':
      try {
        await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await authStore.logoutAction()
        ElMessage.success('退出登录成功')
        router.push('/admin-login')
      } catch (error) {
        // 用户取消操作
      }
      break
  }
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}

.admin-sidebar {
  height: 100vh;
  background-color: #304156;
}

.sidebar-header {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #263445;
  color: white;
  border-bottom: 1px solid #3d4d63;
}

.sidebar-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}

.admin-header {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: white;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.header-left {
  flex: 1;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-dropdown {
  display: flex;
  align-items: center;
  padding: 0 12px;
  cursor: pointer;
  color: #606266;
  font-size: 14px;
}

.user-dropdown:hover {
  color: #409eff;
}

.admin-content {
  background: #f0f2f5;
  min-height: calc(100vh - 120px);
  padding: 20px;
}

/* 深色模式下的菜单样式 */
:deep(.el-menu) {
  border-right: none;
}

:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
}

:deep(.el-menu-item.is-active) {
  background-color: #409eff !important;
  color: white !important;
}

:deep(.el-menu-item:hover) {
  background-color: #263445 !important;
  color: #409eff !important;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .admin-layout {
    :deep(.el-aside) {
      width: 64px !important;
    }
    
    .sidebar-header h3 {
      display: none;
    }
    
    :deep(.el-menu-item span) {
      display: none;
    }
  }
}
</style> 