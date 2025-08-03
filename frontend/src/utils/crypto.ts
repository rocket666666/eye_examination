import CryptoJS from 'crypto-js'

/**
 * 密码加密工具类
 */
export class CryptoUtils {
  // 密码加密盐值
  private static readonly SALT = 'eyeexam2024salt'
  
  /**
   * 使用SHA256加密密码
   * @param password 原始密码
   * @returns 加密后的密码
   */
  static encryptPassword(password: string): string {
    // 使用SHA256 + 盐值加密
    const encrypted = CryptoJS.SHA256(password + CryptoUtils.SALT).toString()
    return encrypted
  }
  
  /**
   * MD5加密（备用方案）
   * @param text 要加密的文本
   * @returns MD5加密结果
   */
  static md5(text: string): string {
    return CryptoJS.MD5(text).toString()
  }
  
  /**
   * Base64编码
   * @param text 要编码的文本
   * @returns Base64编码结果
   */
  static base64Encode(text: string): string {
    return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(text))
  }
  
  /**
   * Base64解码
   * @param encodedText Base64编码的文本
   * @returns 解码后的文本
   */
  static base64Decode(encodedText: string): string {
    return CryptoJS.enc.Base64.parse(encodedText).toString(CryptoJS.enc.Utf8)
  }
}

// 默认导出便于使用，使用箭头函数包装以保持正确的上下文
export const encryptPassword = (password: string): string => CryptoUtils.encryptPassword(password)
export const md5 = (text: string): string => CryptoUtils.md5(text) 