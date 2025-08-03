package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统配置表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_config")
public class SysConfig {
    
    /**
     * 参数主键
     */
    @TableId(value = "config_id", type = IdType.AUTO)
    private Integer configId;
    
    /**
     * 参数名称
     */
    private String configName;
    
    /**
     * 参数键名
     */
    private String configKey;
    
    /**
     * 参数键值
     */
    private String configValue;
    
    /**
     * 系统内置：Y是，N否
     */
    private String configType;
    
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
} 