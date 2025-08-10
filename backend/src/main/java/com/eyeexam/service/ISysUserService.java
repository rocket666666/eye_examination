package com.eyeexam.service;

import com.eyeexam.entity.SysUser;

import java.util.List;

/**
 * 系统用户Service接口
 */
public interface ISysUserService {

    /**
     * 根据用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUser selectUserById(Long userId);
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    SysUser selectUserByUsername(String username);
    
    /**
     * 查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户集合
     */
    List<SysUser> selectUserList(SysUser sysUser);
    
    /**
     * 新增用户
     * 
     * @param sysUser 用户信息
     * @return 结果
     */
    int insertUser(SysUser sysUser);
    
    /**
     * 修改用户
     * 
     * @param sysUser 用户信息
     * @return 结果
     */
    int updateUser(SysUser sysUser);
    
    /**
     * 删除用户
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);
    
    /**
     * 删除用户信息
     * 
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserById(Long userId);
} 