import request from '@/utils/request'
import type { ApiResponse } from '@/types/api'

// SVV工具信息接口
export interface SVVToolInfo {
  name: string
  version: string
  description: string
  features: string[]
  systemRequirements: string[]
}

/**
 * 启动SVV检查工具
 */
export const launchSVVChecker = (): Promise<ApiResponse<string>> => {
  return request({
    url: '/svv/launch',
    method: 'post'
  })
}

/**
 * 检查SVV工具运行状态
 */
export const checkSVVStatus = (): Promise<ApiResponse<string>> => {
  return request({
    url: '/svv/status',
    method: 'get'
  })
}

/**
 * 获取SVV工具信息
 */
export const getSVVInfo = (): Promise<ApiResponse<SVVToolInfo>> => {
  return request({
    url: '/svv/info',
    method: 'get'
  })
}