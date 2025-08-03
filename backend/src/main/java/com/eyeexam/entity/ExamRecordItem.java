package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 检查记录项目明细表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_record_item")
public class ExamRecordItem {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 检查记录ID
     */
    private Long recordId;
    
    /**
     * 检查项目ID
     */
    private Long itemId;
    
    /**
     * 检查结果
     */
    private String itemResult;
    
    /**
     * 检查数值
     */
    private String itemValue;
    
    /**
     * 是否正常：1正常，0异常
     */
    private Integer isNormal;
    
    /**
     * 医生建议
     */
    private String doctorAdvice;
    
    /**
     * 检查医生
     */
    private String examDoctor;
    
    /**
     * 检查时间
     */
    private LocalDateTime examTime;
    
    /**
     * 状态：1待检查，2已完成
     */
    private Integer status;
    
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
     * 检查项目信息 (关联查询用)
     */
    @TableField(exist = false)
    private ExamItem examItem;
} 