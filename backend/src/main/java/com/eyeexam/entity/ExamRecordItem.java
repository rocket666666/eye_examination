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
    
    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    
    public String getItemResult() { return itemResult; }
    public void setItemResult(String itemResult) { this.itemResult = itemResult; }
    
    public String getItemValue() { return itemValue; }
    public void setItemValue(String itemValue) { this.itemValue = itemValue; }
    
    public Integer getIsNormal() { return isNormal; }
    public void setIsNormal(Integer isNormal) { this.isNormal = isNormal; }
    
    public String getDoctorAdvice() { return doctorAdvice; }
    public void setDoctorAdvice(String doctorAdvice) { this.doctorAdvice = doctorAdvice; }
    
    public String getExamDoctor() { return examDoctor; }
    public void setExamDoctor(String examDoctor) { this.examDoctor = examDoctor; }
    
    public LocalDateTime getExamTime() { return examTime; }
    public void setExamTime(LocalDateTime examTime) { this.examTime = examTime; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    
    public ExamItem getExamItem() { return examItem; }
    public void setExamItem(ExamItem examItem) { this.examItem = examItem; }
} 