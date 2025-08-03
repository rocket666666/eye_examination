package com.eyeexam.service.impl;

import com.eyeexam.entity.ExamItem;
import com.eyeexam.mapper.ExamItemMapper;
import com.eyeexam.service.IExamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 检查项目Service业务层处理
 */
@Service
public class ExamItemServiceImpl implements IExamItemService {

    @Autowired
    private ExamItemMapper examItemMapper;

    @Override
    public List<ExamItem> selectExamItemList(ExamItem examItem) {
        return examItemMapper.selectExamItemList(examItem);
    }

    @Override
    public ExamItem selectExamItemById(Long itemId) {
        return examItemMapper.selectById(itemId);
    }

    @Override
    public int insertExamItem(ExamItem examItem) {
        return examItemMapper.insert(examItem);
    }

    @Override
    public int updateExamItem(ExamItem examItem) {
        return examItemMapper.updateById(examItem);
    }

    @Override
    public int deleteExamItemById(Long itemId) {
        return examItemMapper.deleteById(itemId);
    }

    @Override
    public int deleteExamItemByIds(Long[] itemIds) {
        int result = 0;
        for (Long id : itemIds) {
            result += examItemMapper.deleteById(id);
        }
        return result;
    }
} 