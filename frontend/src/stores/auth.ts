import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, logout, getCurrentUser } from '@/api/auth'
import type { LoginRequest, LoginResponse, User } from '@/types/auth'
import { setToken, getToken, removeToken } from '@/utils/auth'
import { encryptPassword } from '@/utils/crypto'

export const useAuthStore = defineStore('auth', () => {
  // 状态
  const token = ref<string>(getToken() || '')
  const user = ref<User | null>(null)
  const loading = ref(false)
  
  // 计算属性
  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.roles?.some(role => role.roleKey === 'admin') || false)
  
  // 登录
  const loginAction = async (loginRequest: LoginRequest): Promise<LoginResponse> => {
    loading.value = true
    try {
      // 加密密码后再发送请求
      const encryptedRequest = {
        ...loginRequest,
        password: encryptPassword(loginRequest.password)
      }
      
      const response = await login(encryptedRequest)
      
      if (response.code === 200) {
        const { token: accessToken, user: userData } = response.data
        
        // 保存token和用户信息
        token.value = accessToken
        user.value = userData
        setToken(accessToken)
        
        return response.data
      } else {
        throw new Error(response.message || '登录失败')
      }
    } catch (error) {
      throw error
    } finally {
      loading.value = false
    }
  }
  
  // 退出登录
  const logoutAction = async () => {
    try {
      await logout()
    } catch (error) {
      console.error('退出登录失败:', error)
    } finally {
      // 清除本地状态
      token.value = ''
      user.value = null
      removeToken()
    }
  }
  
  // 获取当前用户信息
  const fetchCurrentUser = async () => {
    if (!token.value) return
    
    try {
      const response = await getCurrentUser()
      if (response.code === 200) {
        user.value = response.data
      }
    } catch (error) {
      console.error('获取用户信息失败:', error)
      // 如果token无效，清除登录状态
      logoutAction()
    }
  }
  
  // 初始化
  const init = async () => {
    if (token.value) {
      await fetchCurrentUser()
    }
  }
  
  return {
    token,
    user,
    loading,
    isAuthenticated,
    isAdmin,
    loginAction,
    logoutAction,
    fetchCurrentUser,
    init
  }
}) 