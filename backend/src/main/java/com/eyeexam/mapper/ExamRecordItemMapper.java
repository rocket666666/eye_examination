package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.ExamRecordItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检查记录项目明细表Mapper接口
 */
@Mapper
public interface ExamRecordItemMapper extends BaseMapper<ExamRecordItem> {

    /**
     * 查询检查记录项目明细列表
     * 
     * @param examRecordItem 检查记录项目明细
     * @return 检查记录项目明细集合
     */
    List<ExamRecordItem> selectExamRecordItemList(ExamRecordItem examRecordItem);
    
    /**
     * 根据记录项目明细ID查询信息
     * 
     * @param id 记录项目明细ID
     * @return 检查记录项目明细
     */
    ExamRecordItem selectExamRecordItemById(Long id);
    
    /**
     * 根据检查记录ID查询项目明细列表
     * 
     * @param recordId 检查记录ID
     * @return 检查记录项目明细集合
     */
    List<ExamRecordItem> selectExamRecordItemListByRecordId(Long recordId);
    
    /**
     * 根据检查项目ID查询明细列表
     * 
     * @param itemId 检查项目ID
     * @return 检查记录项目明细集合
     */
    List<ExamRecordItem> selectExamRecordItemListByItemId(Long itemId);
    
    /**
     * 根据检查记录ID和项目ID查询明细
     * 
     * @param recordId 检查记录ID
     * @param itemId 检查项目ID
     * @return 检查记录项目明细
     */
    ExamRecordItem selectExamRecordItemByRecordIdAndItemId(Long recordId, Long itemId);
    
    /**
     * 根据检查记录ID删除项目明细
     * 
     * @param recordId 检查记录ID
     * @return 结果
     */
    int deleteExamRecordItemByRecordId(Long recordId);
    
    /**
     * 批量删除检查记录项目明细
     * 
     * @param ids 需要删除的明细ID
     * @return 结果
     */
    int deleteExamRecordItemByIds(Long[] ids);
    
    /**
     * 根据检查记录ID统计项目数量
     * 
     * @param recordId 检查记录ID
     * @return 数量
     */
    int countExamRecordItemByRecordId(Long recordId);
    
    /**
     * 根据检查记录ID统计已完成项目数量
     * 
     * @param recordId 检查记录ID
     * @return 数量
     */
    int countCompletedExamRecordItemByRecordId(Long recordId);
    
    /**
     * 根据检查记录ID统计异常项目数量
     * 
     * @param recordId 检查记录ID
     * @return 数量
     */
    int countAbnormalExamRecordItemByRecordId(Long recordId);
} 