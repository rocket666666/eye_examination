package com.eyeexam.service.impl;

import com.eyeexam.service.ICaptchaService;
import com.eyeexam.utils.CaptchaUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {
    
    // 验证码存储Map (uuid -> 验证码文本)
    private final ConcurrentHashMap<String, String> captchaMap = new ConcurrentHashMap<>();
    
    // 验证码过期时间Map (uuid -> 过期时间戳)
    private final ConcurrentHashMap<String, Long> expireTimeMap = new ConcurrentHashMap<>();
    
    // 验证码有效期（5分钟）
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    
    // 定时清理任务
    private final ScheduledExecutorService cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
    
    public CaptchaServiceImpl() {
        // 每分钟清理一次过期的验证码
        cleanupExecutor.scheduleAtFixedRate(this::cleanupExpiredCaptcha, 1, 1, TimeUnit.MINUTES);
    }
    
    @Override
    public CaptchaUtils.CaptchaResult generateCaptcha(String uuid) {
        // 生成验证码
        CaptchaUtils.CaptchaResult result = CaptchaUtils.generateCaptcha();
        
        // 存储验证码（转为小写以便验证时忽略大小写）
        captchaMap.put(uuid, result.getText().toLowerCase());
        expireTimeMap.put(uuid, System.currentTimeMillis() + EXPIRE_TIME);
        
        return result;
    }
    
    @Override
    public boolean verifyCaptcha(String uuid, String captcha) {
        if (uuid == null || captcha == null) {
            return false;
        }
        
        String storedCaptcha = captchaMap.get(uuid);
        Long expireTime = expireTimeMap.get(uuid);
        
        if (storedCaptcha == null || expireTime == null) {
            return false;
        }
        
        // 检查是否过期
        if (System.currentTimeMillis() > expireTime) {
            removeCaptcha(uuid);
            return false;
        }
        
        // 验证验证码（忽略大小写）
        return storedCaptcha.equals(captcha.toLowerCase());
    }
    
    @Override
    public void removeCaptcha(String uuid) {
        captchaMap.remove(uuid);
        expireTimeMap.remove(uuid);
    }
    
    /**
     * 清理过期的验证码
     */
    private void cleanupExpiredCaptcha() {
        long currentTime = System.currentTimeMillis();
        expireTimeMap.entrySet().removeIf(entry -> {
            if (entry.getValue() < currentTime) {
                captchaMap.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }
} 