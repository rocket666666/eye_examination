import request from '@/utils/request'
import type { LoginRequest, LoginResponse, User } from '@/types/auth'
import type { ApiResponse } from '@/types/api'

/**
 * 用户登录
 */
export const login = (data: LoginRequest): Promise<ApiResponse<LoginResponse>> => {
  return request.post('/auth/login', data)
}

/**
 * 用户退出登录
 */
export const logout = (): Promise<ApiResponse<string>> => {
  return request.post('/auth/logout')
}

/**
 * 获取当前用户信息
 */
export const getCurrentUser = (): Promise<ApiResponse<User>> => {
  return request.get('/auth/me')
} 