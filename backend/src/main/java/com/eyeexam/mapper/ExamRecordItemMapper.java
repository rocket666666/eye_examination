package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.ExamRecordItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检查记录项目表Mapper接口
 */
@Mapper
public interface ExamRecordItemMapper extends BaseMapper<ExamRecordItem> {

    /**
     * 根据检查记录ID查询项目列表
     * 
     * @param recordId 检查记录ID
     * @return 检查记录项目集合
     */
    List<ExamRecordItem> selectByRecordId(@Param("recordId") Long recordId);
    
    /**
     * 根据检查项目ID查询记录
     * 
     * @param itemId 检查项目ID
     * @return 检查记录项目集合
     */
    List<ExamRecordItem> selectByItemId(@Param("itemId") Long itemId);
    
    /**
     * 批量删除检查记录项目
     * 
     * @param recordIds 检查记录ID数组
     * @return 删除数量
     */
    int deleteByRecordIds(@Param("recordIds") Long[] recordIds);
} 