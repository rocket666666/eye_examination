/**
 * API响应通用类型
 */
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
  timestamp: number
}

/**
 * 分页参数
 */
export interface PageParams {
  page?: number
  size?: number
  sort?: string
  order?: 'asc' | 'desc'
}

/**
 * 分页响应
 */
export interface PageResponse<T = any> {
  records: T[]
  total: number
  size: number
  current: number
  pages: number
}

/**
 * 错误响应
 */
export interface ErrorResponse {
  code: number
  message: string
  details?: string
} 