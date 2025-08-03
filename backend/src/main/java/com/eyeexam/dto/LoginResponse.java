package com.eyeexam.dto;

import com.eyeexam.entity.SysUser;

/**
 * 登录响应DTO
 */
public class LoginResponse {
    
    private String token;
    
    private String tokenType = "Bearer";
    
    private SysUser user;
    
    public LoginResponse() {}
    
    public LoginResponse(String token, SysUser user) {
        this.token = token;
        this.user = user;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public SysUser getUser() {
        return user;
    }
    
    public void setUser(SysUser user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", user=" + user +
                '}';
    }
} 