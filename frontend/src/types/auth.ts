/**
 * 角色类型
 */
export interface Role {
  roleId: number
  roleName: string
  roleKey: string
  roleSort: number
  dataScope: string
  menuCheckStrictly: boolean
  deptCheckStrictly: boolean
  status: string
  createTime: string
  updateTime: string
  remark: string
  permissions?: string[]
}

/**
 * 用户类型
 */
export interface User {
  userId: number
  username: string
  nickName: string
  email: string
  phone: string
  sex: string
  avatar: string
  status: string
  createTime: string
  updateTime: string
  remark: string
  roles?: Role[]
  permissions?: string[]
}

/**
 * 登录请求类型
 */
export interface LoginRequest {
  username: string
  password: string
  captcha?: string
  uuid?: string
}

/**
 * 登录响应类型
 */
export interface LoginResponse {
  token: string
  tokenType: string
  user: User
}

/**
 * 用户注册请求类型
 */
export interface RegisterRequest {
  username: string
  password: string
  confirmPassword: string
  email: string
  phone: string
  captcha?: string
  uuid?: string
}

/**
 * 密码修改请求类型
 */
export interface ChangePasswordRequest {
  oldPassword: string
  newPassword: string
  confirmPassword: string
}

/**
 * 用户状态枚举
 */
export enum UserStatus {
  NORMAL = '0',
  DISABLED = '1'
}

/**
 * 性别枚举
 */
export enum Gender {
  MALE = '0',
  FEMALE = '1',
  UNKNOWN = '2'
} 