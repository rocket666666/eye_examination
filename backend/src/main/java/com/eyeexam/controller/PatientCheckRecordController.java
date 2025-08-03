package com.eyeexam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyeexam.common.Result;
import com.eyeexam.entity.PatientCheckRecord;
import com.eyeexam.service.IPatientCheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 患者检查记录Controller
 */
@RestController
@RequestMapping("/api/check-records")
public class PatientCheckRecordController {
    
    @Autowired
    private IPatientCheckRecordService patientCheckRecordService;

    /**
     * 查询检查记录列表
     */
    @GetMapping
    public Result list(PatientCheckRecord checkRecord) {
        List<PatientCheckRecord> list = patientCheckRecordService.selectCheckRecordList(checkRecord);
        return Result.success(list);
    }

    /**
     * 获取检查记录详细信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        PatientCheckRecord checkRecord = patientCheckRecordService.selectCheckRecordById(id);
        if (checkRecord == null) {
            return Result.error("检查记录不存在");
        }
        return Result.success(checkRecord);
    }

    /**
     * 新增检查记录
     */
    @PostMapping
    public Result add(@RequestBody PatientCheckRecord checkRecord) {
        int result = patientCheckRecordService.insertCheckRecord(checkRecord);
        if (result > 0) {
            return Result.success("新增检查记录成功");
        } else {
            return Result.error("新增检查记录失败");
        }
    }

    /**
     * 修改检查记录
     */
    @PutMapping
    public Result edit(@RequestBody PatientCheckRecord checkRecord) {
        int result = patientCheckRecordService.updateCheckRecord(checkRecord);
        if (result > 0) {
            return Result.success("修改检查记录成功");
        } else {
            return Result.error("修改检查记录失败");
        }
    }

    /**
     * 删除检查记录
     */
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable("id") Long id) {
        // 校验检查记录是否存在
        PatientCheckRecord existRecord = patientCheckRecordService.selectCheckRecordById(id);
        if (existRecord == null) {
            return Result.error("检查记录不存在");
        }
        
        int result = patientCheckRecordService.deleteCheckRecordById(id);
        if (result > 0) {
            return Result.success("删除检查记录成功");
        } else {
            return Result.error("删除检查记录失败");
        }
    }

    /**
     * 根据患者ID获取最新检查记录
     */
    @GetMapping("/latest/{patientId}")
    public Result getLatestByPatientId(@PathVariable("patientId") Long patientId) {
        PatientCheckRecord latestRecord = patientCheckRecordService.selectLatestCheckRecordByPatientId(patientId);
        return Result.success(latestRecord);
    }
} 