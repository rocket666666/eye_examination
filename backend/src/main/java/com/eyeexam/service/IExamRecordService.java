package com.eyeexam.service;

import com.eyeexam.entity.ExamRecord;

import java.util.List;

/**
 * 检查记录Service接口
 */
public interface IExamRecordService {

    List<ExamRecord> selectExamRecordList(ExamRecord examRecord);
    
    ExamRecord selectExamRecordById(Long recordId);
    
    int insertExamRecord(ExamRecord examRecord);
    
    int updateExamRecord(ExamRecord examRecord);
    
    int deleteExamRecordByIds(Long[] recordIds);
    
    int deleteExamRecordById(Long recordId);
} 