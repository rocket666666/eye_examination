package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 检查记录表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_record")
public class ExamRecord {
    
    /**
     * 检查记录ID
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;
    
    /**
     * 检查单号
     */
    private String recordNo;
    
    /**
     * 患者ID
     */
    private Long patientId;
    
    /**
     * 医生ID
     */
    private Long doctorId;
    
    /**
     * 检查日期
     */
    private LocalDate examDate;
    
    /**
     * 检查时间
     */
    private LocalTime examTime;
    
    /**
     * 状态：1待检查，2检查中，3已完成，4已取消
     */
    private Integer status;
    
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 删除标志：0存在，1删除
     */
    @TableLogic
    private Integer delFlag;
    
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
    
    /**
     * 患者信息 (关联查询用)
     */
    @TableField(exist = false)
    private PatientInfo patientInfo;
    
    /**
     * 医生信息 (关联查询用)
     */
    @TableField(exist = false)
    private SysUser doctor;
} 