package com.eyeexam.service.impl;

import com.eyeexam.entity.SysUser;
import com.eyeexam.mapper.SysUserMapper;
import com.eyeexam.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户Service业务层处理
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
    public SysUser selectUserByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public List<SysUser> selectUserList(SysUser sysUser) {
        return sysUserMapper.selectUserList(sysUser);
    }

    @Override
    public int insertUser(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        return sysUserMapper.updateById(sysUser);
    }

    @Override
    public int deleteUserById(Long userId) {
        return sysUserMapper.deleteById(userId);
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        int result = 0;
        for (Long id : userIds) {
            result += sysUserMapper.deleteById(id);
        }
        return result;
    }
} 