package com.eyeexam.service;

import com.eyeexam.utils.CaptchaUtils;

/**
 * 验证码服务接口
 */
public interface ICaptchaService {
    
    /**
     * 生成验证码
     * @param uuid 唯一标识
     * @return 验证码结果
     */
    CaptchaUtils.CaptchaResult generateCaptcha(String uuid);
    
    /**
     * 验证验证码
     * @param uuid 唯一标识
     * @param captcha 用户输入的验证码
     * @return 是否验证成功
     */
    boolean verifyCaptcha(String uuid, String captcha);
    
    /**
     * 删除验证码
     * @param uuid 唯一标识
     */
    void removeCaptcha(String uuid);
} 