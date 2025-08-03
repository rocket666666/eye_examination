package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.dto.LoginRequest;
import com.eyeexam.dto.LoginResponse;
import com.eyeexam.entity.SysUser;
import com.eyeexam.mapper.SysUserMapper;
import com.eyeexam.service.ICaptchaService;
import com.eyeexam.utils.JwtUtils;
import com.eyeexam.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private ICaptchaService captchaService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // 如果是管理员登录（有验证码），先验证验证码
            if (loginRequest.getCaptcha() != null && !loginRequest.getCaptcha().isEmpty()) {
                if (loginRequest.getUuid() == null || loginRequest.getUuid().isEmpty()) {
                    return Result.error("验证码UUID不能为空");
                }
                
                boolean captchaValid = captchaService.verifyCaptcha(loginRequest.getUuid(), loginRequest.getCaptcha());
                if (!captchaValid) {
                    return Result.error("验证码错误或已过期");
                }
                
                // 验证成功后删除验证码
                captchaService.removeCaptcha(loginRequest.getUuid());
            }
            
            // 获取用户信息进行验证
            SysUser user = sysUserMapper.selectByUsername(loginRequest.getUsername());
            if (user == null) {
                return Result.error("用户不存在");
            }
            
            // 验证密码（前端已加密）
            // 前端传来的密码已经是加密后的，直接与数据库中的加密密码比较
            if (!loginRequest.getPassword().equals(user.getPassword())) {
                return Result.error("用户名或密码错误");
            }
            
            // 检查用户状态（1=正常，0=停用）
            if (!"1".equals(user.getStatus().toString())) {
                return Result.error("用户已被停用");
            }
            
            // 创建认证对象（绕过AuthenticationManager）
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                null, // 密码不需要了
                java.util.Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER"))
            );
            
            // 设置到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // 生成JWT token
            String jwt = jwtUtils.generateToken(loginRequest.getUsername());
            
            // 隐藏密码
            user.setPassword(null);
            
            // 构建响应
            LoginResponse loginResponse = new LoginResponse(jwt, user);
            
            return Result.success("登录成功", loginResponse);
            
        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    public Result<SysUser> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            SysUser user = sysUserMapper.selectByUsername(username);
            if (user != null) {
                user.setPassword(null);
                return Result.success(user);
            }
            
            return Result.error("用户不存在");
            
        } catch (Exception e) {
            return Result.error("获取用户信息失败");
        }
    }
    
    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        try {
            SecurityContextHolder.clearContext();
            return Result.success("退出登录成功");
        } catch (Exception e) {
            return Result.error("退出登录失败");
        }
    }
} 