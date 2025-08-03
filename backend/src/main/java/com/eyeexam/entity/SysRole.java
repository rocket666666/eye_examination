package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色信息表
 */
@TableName("sys_role")
public class SysRole {
    
    /** 角色ID */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;
    
    /** 角色名称 */
    private String roleName;
    
    /** 角色权限字符串 */
    private String roleKey;
    
    /** 显示顺序 */
    private Integer roleSort;
    
    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限） */
    private String dataScope;
    
    /** 菜单树选择项是否关联显示 */
    private Boolean menuCheckStrictly;
    
    /** 部门树选择项是否关联显示 */
    private Boolean deptCheckStrictly;
    
    /** 角色状态（0正常 1停用） */
    private String status;
    
    /** 删除标志（0代表存在 1代表删除） */
    @TableLogic
    private String deleted;
    
    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    
    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    
    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /** 备注 */
    private String remark;
    
    /** 菜单权限列表 */
    @TableField(exist = false)
    private List<String> permissions;
    
    // 构造函数
    public SysRole() {}
    
    public SysRole(String roleName, String roleKey) {
        this.roleName = roleName;
        this.roleKey = roleKey;
    }
    
    // Getter和Setter方法
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    
    public String getRoleKey() { return roleKey; }
    public void setRoleKey(String roleKey) { this.roleKey = roleKey; }
    
    public Integer getRoleSort() { return roleSort; }
    public void setRoleSort(Integer roleSort) { this.roleSort = roleSort; }
    
    public String getDataScope() { return dataScope; }
    public void setDataScope(String dataScope) { this.dataScope = dataScope; }
    
    public Boolean getMenuCheckStrictly() { return menuCheckStrictly; }
    public void setMenuCheckStrictly(Boolean menuCheckStrictly) { this.menuCheckStrictly = menuCheckStrictly; }
    
    public Boolean getDeptCheckStrictly() { return deptCheckStrictly; }
    public void setDeptCheckStrictly(Boolean deptCheckStrictly) { this.deptCheckStrictly = deptCheckStrictly; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDeleted() { return deleted; }
    public void setDeleted(String deleted) { this.deleted = deleted; }
    
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    
    @Override
    public String toString() {
        return "SysRole{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleKey='" + roleKey + '\'' +
                ", roleSort=" + roleSort +
                ", dataScope='" + dataScope + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
} 