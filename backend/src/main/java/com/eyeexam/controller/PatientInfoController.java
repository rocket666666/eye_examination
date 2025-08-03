package com.eyeexam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyeexam.common.Result;
import com.eyeexam.entity.PatientInfo;
import com.eyeexam.entity.PatientCheckRecord;
import com.eyeexam.service.IPatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 患者信息Controller
 */
@RestController
@RequestMapping("/api/patients")
public class PatientInfoController {
    
    @Autowired
    private IPatientInfoService patientInfoService;

    /**
     * 分页查询患者信息列表
     */
    @GetMapping
    public Result list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        Page<PatientInfo> pageResult = patientInfoService.selectPatientInfoPage(page, size, keyword);
        return Result.success(pageResult);
    }

    /**
     * 获取患者信息详细信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        PatientInfo patientInfo = patientInfoService.selectPatientInfoById(id);
        if (patientInfo == null) {
            return Result.error("患者信息不存在");
        }
        return Result.success(patientInfo);
    }

    /**
     * 新增患者信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody PatientInfo patientInfo) {
        // 自动生成患者编号
        String patientCode = patientInfoService.generatePatientCode();
        patientInfo.setPatientCode(patientCode);
        
        int result = patientInfoService.insertPatientInfo(patientInfo);
        if (result > 0) {
            return Result.success("新增患者信息成功");
        } else {
            return Result.error("新增患者信息失败");
        }
    }

    /**
     * 修改患者信息
     */
    @PostMapping("/edit/{id}")
    public Result edit(@PathVariable("id") Long id, @RequestBody PatientInfo patientInfo) {
        // 设置患者ID
        patientInfo.setId(id);
        
        // 校验患者是否存在
        PatientInfo existPatient = patientInfoService.selectPatientInfoById(id);
        if (existPatient == null) {
            return Result.error("患者信息不存在");
        }
        
        // 如果修改了患者编号，校验唯一性
        if (patientInfo.getPatientCode() != null && 
            !patientInfo.getPatientCode().equals(existPatient.getPatientCode()) &&
            !patientInfoService.checkPatientCodeUnique(patientInfo.getPatientCode())) {
            return Result.error("患者编号已存在");
        }
        
        int result = patientInfoService.updatePatientInfo(patientInfo);
        if (result > 0) {
            return Result.success("修改患者信息成功");
        } else {
            return Result.error("修改患者信息失败");
        }
    }

    /**
     * 逻辑删除患者信息
     */
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        // 校验患者是否存在
        PatientInfo existPatient = patientInfoService.selectPatientInfoById(id);
        if (existPatient == null) {
            return Result.error("患者信息不存在");
        }
        
        int result = patientInfoService.deletePatientInfoById(id);
        if (result > 0) {
            return Result.success("删除患者信息成功");
        } else {
            return Result.error("删除患者信息失败");
        }
    }

    /**
     * 校验患者编号是否唯一
     */
    @GetMapping("/check-code")
    public Result checkPatientCode(@RequestParam String patientCode) {
        boolean isUnique = patientInfoService.checkPatientCodeUnique(patientCode);
        return Result.success(isUnique);
    }

    /**
     * 生成患者编号
     */
    @GetMapping("/generate-code")
    public Result generatePatientCode() {
        String patientCode = patientInfoService.generatePatientCode();
        return Result.success(patientCode);
    }

    /**
     * 添加检查记录
     */
    @PostMapping("/{id}/check-records")
    public Result addCheckRecord(@PathVariable("id") Long patientId, 
                                @RequestBody PatientCheckRecord checkRecord) {
        // 校验患者是否存在
        PatientInfo existPatient = patientInfoService.selectPatientInfoById(patientId);
        if (existPatient == null) {
            return Result.error("患者信息不存在");
        }
        
        // 设置患者ID
        checkRecord.setPatientId(patientId);
        
        int result = patientInfoService.addCheckRecordAndUpdatePatient(checkRecord);
        if (result > 0) {
            return Result.success("添加检查记录成功");
        } else {
            return Result.error("添加检查记录失败");
        }
    }

    /**
     * 获取患者检查记录列表
     */
    @GetMapping("/{id}/check-records")
    public Result getCheckRecords(
            @PathVariable("id") Long patientId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        // 校验患者是否存在
        PatientInfo existPatient = patientInfoService.selectPatientInfoById(patientId);
        if (existPatient == null) {
            return Result.error("患者信息不存在");
        }
        
        Page<PatientCheckRecord> pageResult = patientInfoService.selectCheckRecordsByPatientIdWithPage(patientId, page, size);
        return Result.success(pageResult);
    }
} 