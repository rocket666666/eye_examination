package com.eyeexam.service;

import com.eyeexam.entity.ExamReport;

import java.util.List;

/**
 * 检查报告Service接口
 */
public interface IExamReportService {

    List<ExamReport> selectExamReportList(ExamReport examReport);
    
    ExamReport selectExamReportById(Long reportId);
    
    int insertExamReport(ExamReport examReport);
    
    int updateExamReport(ExamReport examReport);
    
    int deleteExamReportByIds(Long[] reportIds);
    
    int deleteExamReportById(Long reportId);
} 