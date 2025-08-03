package com.eyeexam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查项目表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_item")
public class ExamItem {
    
    /**
     * 检查项目ID
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;
    
    /**
     * 项目编码
     */
    private String itemCode;
    
    /**
     * 项目名称
     */
    private String itemName;
    
    /**
     * 项目类型
     */
    private String itemType;
    
    /**
     * 项目描述
     */
    private String description;
    
    /**
     * 价格
     */
    private BigDecimal price;
    
    /**
     * 单位
     */
    private String unit;
    
    /**
     * 正常范围
     */
    private String normalRange;
    
    /**
     * 排序
     */
    private Integer sortOrder;
    
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
     * 备注
     */
    private String remark;
} 