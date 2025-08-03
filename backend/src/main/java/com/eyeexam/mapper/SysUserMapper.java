package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息Mapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND del_flag = '0'")
    SysUser selectByUsername(String username);
    
    /**
     * 根据用户名和密码查询用户
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username} AND password = #{password} AND del_flag = '0'")
    SysUser selectByUsernameAndPassword(String username, String password);
} 