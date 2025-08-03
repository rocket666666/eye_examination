package com.eyeexam.service;

import com.eyeexam.entity.SysUser;
import com.eyeexam.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户详情服务实现
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser sysUser = sysUserMapper.selectByUsername(username);
        
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        
        // 检查用户状态
        if ("0".equals(sysUser.getStatus())) {
            throw new UsernameNotFoundException("用户已被停用: " + username);
        }
        
        // 获取用户权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        // 这里可以根据需要添加用户权限
        // 暂时添加一个基本权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
    }
} 