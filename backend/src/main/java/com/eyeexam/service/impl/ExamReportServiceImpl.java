package com.eyeexam.service.impl;

import com.eyeexam.entity.ExamReport;
import com.eyeexam.mapper.ExamReportMapper;
import com.eyeexam.service.IExamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 检查报告Service业务层处理
 */
@Service
public class ExamReportServiceImpl implements IExamReportService {

    @Autowired
    private ExamReportMapper examReportMapper;

    @Override
    public List<ExamReport> selectExamReportList(ExamReport examReport) {
        return examReportMapper.selectExamReportList(examReport);
    }

    @Override
    public ExamReport selectExamReportById(Long reportId) {
        return examReportMapper.selectById(reportId);
    }

    @Override
    public int insertExamReport(ExamReport examReport) {
        return examReportMapper.insert(examReport);
    }

    @Override
    public int updateExamReport(ExamReport examReport) {
        return examReportMapper.updateById(examReport);
    }

    @Override
    public int deleteExamReportById(Long reportId) {
        return examReportMapper.deleteById(reportId);
    }

    @Override
    public int deleteExamReportByIds(Long[] reportIds) {
        int result = 0;
        for (Long id : reportIds) {
            result += examReportMapper.deleteById(id);
        }
        return result;
    }
} 