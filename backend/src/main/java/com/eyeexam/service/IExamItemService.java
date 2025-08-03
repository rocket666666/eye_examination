package com.eyeexam.service;

import com.eyeexam.entity.ExamItem;

import java.util.List;

/**
 * 检查项目Service接口
 */
public interface IExamItemService {

    List<ExamItem> selectExamItemList(ExamItem examItem);
    
    ExamItem selectExamItemById(Long itemId);
    
    int insertExamItem(ExamItem examItem);
    
    int updateExamItem(ExamItem examItem);
    
    int deleteExamItemByIds(Long[] itemIds);
    
    int deleteExamItemById(Long itemId);
} 