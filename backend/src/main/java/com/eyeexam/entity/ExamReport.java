package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 检查报告表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_report")
public class ExamReport {
    
    /**
     * 报告ID
     */
    @TableId(value = "report_id", type = IdType.AUTO)
    private Long reportId;
    
    /**
     * 检查记录ID
     */
    private Long recordId;
    
    /**
     * 患者ID
     */
    private Long patientId;
    
    /**
     * 报告编号
     */
    private String reportNo;
    
    /**
     * 报告标题
     */
    private String reportTitle;
    
    /**
     * 报告内容
     */
    private String reportContent;
    
    /**
     * 检查结论
     */
    private String conclusion;
    
    /**
     * 建议
     */
    private String suggestion;
    
    /**
     * 报告医生
     */
    private String reportDoctor;
    
    /**
     * 报告日期
     */
    private LocalDate reportDate;
    
    /**
     * 状态：1草稿，2已发布
     */
    private Integer status;
    
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
     * 患者信息 (关联查询用)
     */
    @TableField(exist = false)
    private PatientInfo patientInfo;
    
    /**
     * 检查记录信息 (关联查询用)
     */
    @TableField(exist = false)
    private ExamRecord examRecord;
} 