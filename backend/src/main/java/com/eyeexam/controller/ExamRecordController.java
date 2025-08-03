package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.entity.ExamRecord;
import com.eyeexam.service.IExamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查记录Controller
 */
@RestController
@RequestMapping("/api/exam/record")
public class ExamRecordController {
    
    @Autowired
    private IExamRecordService examRecordService;

    /**
     * 查询检查记录列表
     */
    @GetMapping("/list")
    public Result list(ExamRecord examRecord) {
        List<ExamRecord> list = examRecordService.selectExamRecordList(examRecord);
        return Result.success(list);
    }

    /**
     * 获取检查记录详细信息
     */
    @GetMapping(value = "/{recordId}")
    public Result getInfo(@PathVariable("recordId") Long recordId) {
        return Result.success(examRecordService.selectExamRecordById(recordId));
    }

    /**
     * 新增检查记录
     */
    @PostMapping
    public Result add(@RequestBody ExamRecord examRecord) {
        return Result.success(examRecordService.insertExamRecord(examRecord));
    }

    /**
     * 修改检查记录
     */
    @PutMapping
    public Result edit(@RequestBody ExamRecord examRecord) {
        return Result.success(examRecordService.updateExamRecord(examRecord));
    }

    /**
     * 删除检查记录
     */
    @DeleteMapping("/{recordIds}")
    public Result remove(@PathVariable Long[] recordIds) {
        return Result.success(examRecordService.deleteExamRecordByIds(recordIds));
    }
} 