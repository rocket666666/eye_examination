import Cookies from 'js-cookie'

const TokenKey = 'eye-exam-token'

/**
 * 获取token
 */
export function getToken(): string | undefined {
  return Cookies.get(TokenKey)
}

/**
 * 设置token
 */
export function setToken(token: string): void {
  Cookies.set(TokenKey, token, { expires: 7 })
}

/**
 * 删除token
 */
export function removeToken(): void {
  Cookies.remove(TokenKey)
}

/**
 * 检查是否已登录
 */
export function isAuthenticated(): boolean {
  return !!getToken()
}

/**
 * 获取Authorization头
 */
export function getAuthHeader(): { Authorization: string } | {} {
  const token = getToken()
  if (token) {
    return { Authorization: `Bearer ${token}` }
  }
  return {}
} 