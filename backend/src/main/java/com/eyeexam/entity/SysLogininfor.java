package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统访问记录表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_logininfor")
public class SysLogininfor {
    
    /**
     * 访问ID
     */
    @TableId(value = "info_id", type = IdType.AUTO)
    private Long infoId;
    
    /**
     * 用户账号
     */
    private String userName;
    
    /**
     * 登录IP地址
     */
    private String ipaddr;
    
    /**
     * 登录地点
     */
    private String loginLocation;
    
    /**
     * 浏览器类型
     */
    private String browser;
    
    /**
     * 操作系统
     */
    private String os;
    
    /**
     * 登录状态：0成功，1失败
     */
    private String status;
    
    /**
     * 提示消息
     */
    private String msg;
    
    /**
     * 访问时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime loginTime;
} 