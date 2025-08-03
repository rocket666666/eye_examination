package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.entity.ExamReport;
import com.eyeexam.service.IExamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查报告Controller
 */
@RestController
@RequestMapping("/api/exam/report")
public class ExamReportController {
    
    @Autowired
    private IExamReportService examReportService;

    /**
     * 查询检查报告列表
     */
    @GetMapping("/list")
    public Result list(ExamReport examReport) {
        List<ExamReport> list = examReportService.selectExamReportList(examReport);
        return Result.success(list);
    }

    /**
     * 获取检查报告详细信息
     */
    @GetMapping(value = "/{reportId}")
    public Result getInfo(@PathVariable("reportId") Long reportId) {
        return Result.success(examReportService.selectExamReportById(reportId));
    }

    /**
     * 新增检查报告
     */
    @PostMapping
    public Result add(@RequestBody ExamReport examReport) {
        return Result.success(examReportService.insertExamReport(examReport));
    }

    /**
     * 修改检查报告
     */
    @PutMapping
    public Result edit(@RequestBody ExamReport examReport) {
        return Result.success(examReportService.updateExamReport(examReport));
    }

    /**
     * 删除检查报告
     */
    @DeleteMapping("/{reportIds}")
    public Result remove(@PathVariable Long[] reportIds) {
        return Result.success(examReportService.deleteExamReportByIds(reportIds));
    }
} 