package com.eyeexam.service;

import com.eyeexam.entity.ExamRecord;
import com.eyeexam.entity.ExamRecordItem;

import java.util.List;

/**
 * 检查记录Service接口
 */
public interface IExamRecordService {

    List<ExamRecord> selectExamRecordList(ExamRecord examRecord);
    
    ExamRecord selectExamRecordById(Long recordId);

    ExamRecord selectReportByCode(String recordCode);

    int insertExamRecord(ExamRecord examRecord);
    
    int updateExamRecord(ExamRecord examRecord);
    
    int deleteExamRecordByIds(Long[] recordIds);
    
    int deleteExamRecordById(Long recordId);
    
    /**
     * 保存检查记录和项目
     */
    void saveExamRecordWithItems(ExamRecord examRecord, List<ExamRecordItem> examRecordItems);
    
    /**
     * 获取检查记录列表（分页）
     */
    List<ExamRecord> getExamRecordList(Integer page, Integer size, Long patientId);
    
    /**
     * 获取检查记录项目
     */
    List<ExamRecordItem> getExamRecordItems(Long recordId);
    
    /**
     * 获取报告列表
     */
    List<ExamRecord> selectReportList(ExamRecord examRecord);
    
    /**
     * 根据报告ID获取报告详情
     */
    ExamRecord selectReportById(Long recordId);
    
    /**
     * 新增报告
     */
    int insertReport(ExamRecord examRecord);
    
    /**
     * 更新报告
     */
    int updateReport(ExamRecord examRecord);
    
    /**
     * 删除报告
     */
    int deleteReportByIds(Long[] recordIds);
    
    /**
     * 发布报告
     */
    int publishReport(Long recordId);
    
    /**
     * 生成报告编号
     */
    String generateReportNo();
} 