package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
     * 检查类型
     */
    private String recordType;

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
    private Date createTime;
    
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    
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

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    // Getter and Setter methods
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    
    public String getRecordNo() { return recordNo; }
    public void setRecordNo(String recordNo) { this.recordNo = recordNo; }
    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    
    public LocalTime getExamTime() { return examTime; }
    public void setExamTime(LocalTime examTime) { this.examTime = examTime; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public Integer getDelFlag() { return delFlag; }
    public void setDelFlag(Integer delFlag) { this.delFlag = delFlag; }
    
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
    
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    
    public PatientInfo getPatientInfo() { return patientInfo; }
    public void setPatientInfo(PatientInfo patientInfo) { this.patientInfo = patientInfo; }
    
    public SysUser getDoctor() { return doctor; }
    public void setDoctor(SysUser doctor) { this.doctor = doctor; }
} 