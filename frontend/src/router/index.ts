import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

// 路由配置
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '业务登录' }
  },
  {
    path: '/admin-login',
    name: 'AdminLogin',
    component: () => import('@/views/AdminLogin.vue'),
    meta: { title: '管理员登录' }
  },
  {
    path: '/main',
    name: 'MainLayout',
    component: () => import('@/layouts/MainLayout.vue'),
    redirect: '/dashboard',
    meta: { requireAuth: true },
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '主页', requireAuth: true }
      },
      {
        path: '/patient',
        name: 'PatientList',
        component: () => import('@/views/PatientList.vue'),
        meta: { title: '患者管理', requireAuth: true }
      },
      {
        path: '/patient/add',
        name: 'PatientAdd',
        component: () => import('@/views/PatientAdd.vue'),
        meta: { title: '新增患者', requireAuth: true }
      },
      {
        path: '/examination',
        name: 'Examination',
        component: () => import('@/views/Examination.vue'),
        meta: { title: '眼部检查', requireAuth: true }
      },
      {
        path: '/svv-check',
        name: 'SVVCheck',
        component: () => import('@/views/SVVCheck.vue'),
        meta: { title: 'SVV检查工具', requireAuth: true }
      },
      {
        path: '/svh-check',
        name: 'SVHCheck',
        component: () => import('@/views/SVHCheck.vue'),
        meta: { title: 'SVH检查工具', requireAuth: true }
      },
      {
        path: '/help',
        name: 'Help',
        component: () => import('@/views/Help.vue'),
        meta: { title: '帮助说明', requireAuth: true }
      },
      {
        path: '/exam-report',
        name: 'ExamReport',
        component: () => import('@/views/ExamReport.vue'),
        meta: { title: '检查报告', requireAuth: true }
      }
    ]
  },
  {
    path: '/admin',
    name: 'AdminLayout',
    component: () => import('@/layouts/AdminLayout.vue'),
    redirect: '/admin/dashboard',
    meta: { requireAuth: true, isAdmin: true },
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '管理首页', requireAuth: true, isAdmin: true }
      },
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('@/views/admin/UserManagement.vue'),
        meta: { title: '用户管理', requireAuth: true, isAdmin: true }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue'),
    meta: { title: '页面不存在' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  // 设置页面标题
  document.title = `${to.meta.title} - 耳鼻咽喉科检查系统`
  
  // 检查是否需要认证
  if (to.meta.requireAuth && !authStore.isAuthenticated) {
    next('/login')
    return
  }
  
  // 检查是否需要管理员权限
  if (to.meta.isAdmin && !authStore.isAdmin) {
    next('/login')
    return
  }
  
  // 如果已登录，访问登录页面则重定向到主页
  if (authStore.isAuthenticated && (to.path === '/login' || to.path === '/admin-login')) {
    if (authStore.isAdmin) {
      next('/admin/dashboard')
    } else {
      next('/dashboard')
    }
    return
  }
  
  next()
})

export default router 