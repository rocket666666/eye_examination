package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dept")
public class SysDept {
    
    /**
     * 部门ID
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;
    
    /**
     * 父部门ID
     */
    private Long parentId;
    
    /**
     * 祖级列表
     */
    private String ancestors;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 部门编码
     */
    private String deptCode;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 负责人
     */
    private String leader;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 状态：1启用，0禁用
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
     * 子部门
     */
    @TableField(exist = false)
    private List<SysDept> children;
    
    // 手动添加getter和setter方法
    public Long getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
    
    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    
    public Long getParentId() {
        return this.parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getAncestors() {
        return this.ancestors;
    }
    
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }
    
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public List<SysDept> getChildren() {
        return this.children;
    }
    
    public void setChildren(List<SysDept> children) {
        this.children = children;
    }
} 