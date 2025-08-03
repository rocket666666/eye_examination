package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.ExamItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检查项目表Mapper接口
 */
@Mapper
public interface ExamItemMapper extends BaseMapper<ExamItem> {

    /**
     * 查询检查项目列表
     * 
     * @param examItem 检查项目
     * @return 检查项目集合
     */
    List<ExamItem> selectExamItemList(ExamItem examItem);
    
    /**
     * 根据检查项目ID查询项目信息
     * 
     * @param itemId 检查项目ID
     * @return 检查项目
     */
    ExamItem selectExamItemById(Long itemId);
    
    /**
     * 根据项目编码查询项目信息
     * 
     * @param itemCode 项目编码
     * @return 检查项目
     */
    ExamItem selectExamItemByCode(String itemCode);
    
    /**
     * 查询启用状态的检查项目列表
     * 
     * @return 检查项目集合
     */
    List<ExamItem> selectActiveExamItemList();
    
    /**
     * 根据项目类型查询检查项目列表
     * 
     * @param itemType 项目类型
     * @return 检查项目集合
     */
    List<ExamItem> selectExamItemListByType(String itemType);
    
    /**
     * 校验项目编码是否唯一
     * 
     * @param itemCode 项目编码
     * @return 结果
     */
    ExamItem checkItemCodeUnique(String itemCode);
    
    /**
     * 校验项目名称是否唯一
     * 
     * @param itemName 项目名称
     * @param itemId 项目ID
     * @return 结果
     */
    ExamItem checkItemNameUnique(@Param("itemName") String itemName, @Param("itemId") Long itemId);
    
    /**
     * 批量删除检查项目
     * 
     * @param itemIds 需要删除的项目ID
     * @return 结果
     */
    int deleteExamItemByIds(Long[] itemIds);
    
    /**
     * 查询项目是否被检查记录使用
     * 
     * @param itemId 项目ID
     * @return 结果
     */
    int checkExamItemUsed(Long itemId);
} 