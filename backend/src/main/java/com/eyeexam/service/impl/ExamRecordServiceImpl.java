package com.eyeexam.service.impl;

import com.eyeexam.entity.ExamRecord;
import com.eyeexam.mapper.ExamRecordMapper;
import com.eyeexam.service.IExamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 检查记录Service业务层处理
 */
@Service
public class ExamRecordServiceImpl implements IExamRecordService {

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @Override
    public List<ExamRecord> selectExamRecordList(ExamRecord examRecord) {
        return examRecordMapper.selectExamRecordList(examRecord);
    }

    @Override
    public ExamRecord selectExamRecordById(Long recordId) {
        return examRecordMapper.selectById(recordId);
    }

    @Override
    public int insertExamRecord(ExamRecord examRecord) {
        return examRecordMapper.insert(examRecord);
    }

    @Override
    public int updateExamRecord(ExamRecord examRecord) {
        return examRecordMapper.updateById(examRecord);
    }

    @Override
    public int deleteExamRecordById(Long recordId) {
        return examRecordMapper.deleteById(recordId);
    }

    @Override
    public int deleteExamRecordByIds(Long[] recordIds) {
        int result = 0;
        for (Long id : recordIds) {
            result += examRecordMapper.deleteById(id);
        }
        return result;
    }
} 