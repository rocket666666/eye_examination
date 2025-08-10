package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;

/**
 * 患者信息表实体类
 */
@EqualsAndHashCode(callSuper = false)
@TableName("patient_info")
public class PatientInfo {
    
    /**
     * 患者ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 姓（可选）
     */
    private String firstName;
    
    /**
     * 名（可选）
     */
    private String lastName;
    
    /**
     * 姓名
     */
    private String fullName;
    
    /**
     * 患者编号，唯一
     */
    private String patientCode;
    
    /**
     * 性别：M男，F女，U未知
     */
    private String gender;
    
    /**
     * 出生日期
     */
    private LocalDate dateOfBirth;
    
    /**
     * 年龄
     */
    private Long age;
    
    /**
     * 上次检查时间
     */
    private LocalDate lastCheckDate;
    
    /**
     * 上次检查ID
     */
    private Long lastCheckId;
    
    /**
     * 上次报告编码
     */
    private String lastRecordCode;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 状态：1正常，0禁用
     */
    private Integer status;
    
    /**
     * 是否删除（逻辑删除标记）：true删除，false存在
     */
    private String isDeleted;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
    
    /**
     * 备注
     */
    private String remark;
    
    // 手动添加getter/setter以解决编译问题
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPatientCode() { return patientCode; }
    public void setPatientCode(String patientCode) { this.patientCode = patientCode; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getLastCheckDate() { return lastCheckDate; }
    public void setLastCheckDate(LocalDate lastCheckDate) { this.lastCheckDate = lastCheckDate; }
    
    public Long getLastCheckId() { return lastCheckId; }
    public void setLastCheckId(Long lastCheckId) { this.lastCheckId = lastCheckId; }
    
    public String getLastRecordCode() { return lastRecordCode; }
    public void setLastRecordCode(String lastRecordCode) { this.lastRecordCode = lastRecordCode; }
    
    // 添加缺失的getter/setter方法
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public Long getAge() { return age; }
    public void setAge(Long age) { this.age = age; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
} 