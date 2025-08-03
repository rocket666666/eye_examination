package com.eyeexam.controller;

import com.eyeexam.service.ICaptchaService;
import com.eyeexam.utils.CaptchaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 验证码控制器
 */
@RestController
@RequestMapping("/api/captcha")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CaptchaController {
    
    @Autowired
    private ICaptchaService captchaService;
    
    /**
     * 生成验证码图片
     */
    @GetMapping("/image")
    public ResponseEntity<byte[]> generateCaptcha(@RequestParam String uuid) {
        try {
            // 生成验证码
            CaptchaUtils.CaptchaResult result = captchaService.generateCaptcha(uuid);
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setCacheControl("no-cache, no-store, must-revalidate");
            headers.setPragma("no-cache");
            headers.setExpires(0);
            
            return new ResponseEntity<>(result.getImageBytes(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 