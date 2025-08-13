package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.entity.ExamRecord;
import com.eyeexam.entity.ExamRecordItem;
import com.eyeexam.entity.PatientInfo;
import com.eyeexam.entity.SysUser;
import com.eyeexam.service.IExamRecordService;
import com.eyeexam.service.IPatientInfoService;
import com.eyeexam.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 检查记录控制器
 */
@RestController
@RequestMapping("/api/exam-record")
public class ExamRecordController {

    @Autowired
    private IExamRecordService examRecordService;

    @Autowired
    private IPatientInfoService patientInfoService;

    @Autowired
    private ISysUserService userService;

    /**
     * 保存检查结果
     */
    @PostMapping("/save")
    public Result<Map<String, Object>> saveExamResult(@RequestBody Map<String, Object> requestData) {
        try {
            // 解析请求数据
            Map<String, Object> examRecordData = (Map<String, Object>) requestData.get("examRecord");
            List<Map<String, Object>> examRecordItemsData = (List<Map<String, Object>>) requestData.get("examRecordItems");
            Long patientId = Long.valueOf(requestData.get("patientId").toString());

            // 构建检查记录
            ExamRecord examRecord = new ExamRecord();
            examRecord.setPatientId(patientId);
            examRecord.setExamDate(LocalDate.parse(examRecordData.get("examDate").toString()));
            examRecord.setExamTime(LocalTime.parse(examRecordData.get("examTime").toString()));
            examRecord.setStatus((Integer) examRecordData.get("status"));
            examRecord.setRemark((String) examRecordData.get("remark"));
            // 设置医生ID为当前登录用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                String username = authentication.getName();
                SysUser current = userService.selectUserByUsername(username);
                if (current != null) {
                    examRecord.setDoctorId(current.getUserId());
                    examRecord.setCreateBy(username);
                    examRecord.setCreateTime(new Date());
                }
            }

            // 生成检查单号
            String recordNo = "ER" + System.currentTimeMillis();
            examRecord.setRecordNo(recordNo);

            // 构建检查记录项目
            List<ExamRecordItem> examRecordItems = examRecordItemsData.stream()
                .map(itemData -> {
                    ExamRecordItem item = new ExamRecordItem();
                    item.setItemId(Long.valueOf(itemData.get("itemId").toString()));
                    item.setItemResult((String) itemData.get("itemResult"));
                    item.setItemValue((String) itemData.get("itemValue"));
                    item.setIsNormal((Integer) itemData.get("isNormal"));
                    item.setStatus((Integer) itemData.get("status"));
                    return item;
                })
                .collect(Collectors.toList());

            // 保存检查记录和项目
            examRecordService.saveExamRecordWithItems(examRecord, examRecordItems);

            // 更新患者信息
            PatientInfo patientInfo = patientInfoService.selectPatientInfoById(patientId);
            if (patientInfo != null) {
                patientInfo.setLastCheckDate(LocalDate.now());
                patientInfo.setLastCheckId(examRecord.getRecordId());
                patientInfo.setLastRecordCode(examRecord.getRecordNo());
                patientInfoService.updatePatientInfo(patientInfo);
            }

            return Result.success(Map.of(
                "message", "检查结果保存成功",
                "recordId", examRecord.getRecordId(),
                "patientInfo", patientInfo
            ));

        } catch (Exception e) {
            return Result.error("保存检查结果失败: " + e.getMessage());
        }
    }

    /**
     * 获取检查记录列表
     */
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getExamRecordList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long patientId) {
        try {
            List<ExamRecord> records = examRecordService.getExamRecordList(page, size, patientId);
            List<Map<String, Object>> result = new ArrayList<>();

            for (ExamRecord record : records) {
                Map<String, Object> row = new HashMap<>();
                // 基础字段
                row.put("recordId", record.getRecordId());
                row.put("recordNo", record.getRecordNo());
                row.put("patientId", record.getPatientId());
                row.put("doctorId", record.getDoctorId());
                row.put("status", record.getStatus());
                row.put("reportStatus", record.getStatus());
                row.put("examDate", record.getExamDate());
                row.put("examTime", record.getExamTime());
                row.put("remark", record.getRemark());
                // 患者姓名
                String patientName = "-";
                if (record.getPatientId() != null) {
                    PatientInfo p = patientInfoService.selectPatientInfoById(record.getPatientId());
                    if (p != null) {
                        patientName = p.getFullName();
                    }
                }
                row.put("patientName", patientName);

                // 医生真实姓名（优先 realName 其次 nickName 再次 username）
                String doctorName = "-";
                if (record.getDoctorId() != null) {
                    SysUser d = userService.selectUserById(record.getDoctorId());
                    if (d != null) {
                        doctorName = d.getRealName() != null && !d.getRealName().isEmpty()
                                ? d.getRealName()
                                : (d.getNickName() != null && !d.getNickName().isEmpty() ? d.getNickName() : d.getUsername());
                    }
                }
                row.put("doctorName", doctorName);

                // 检查日期格式化为 yyyy/MM/dd
                String examDateStr = null;
                if (record.getExamDate() != null) {
                    examDateStr = record.getExamDate().toString().replace('-', '/');
                }
                row.put("examDate", examDateStr);

                // 检查时间格式化为 HH:mm:ss
                String examTimeStr = null;
                if (record.getExamTime() != null) {
                    examTimeStr = record.getExamTime().toString();
                }
                row.put("examTime", examTimeStr);

                result.add(row);
            }
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取检查记录失败: " + e.getMessage());
        }
    }

    /**
     * 获取检查记录详情
     */
    @GetMapping("/{recordId}")
    public Result<Map<String, Object>> getExamRecordDetail(@PathVariable Long recordId) {
        try {
            ExamRecord record = examRecordService.selectExamRecordById(recordId);
            List<ExamRecordItem> items = examRecordService.getExamRecordItems(recordId);
            
            return Result.success(Map.of(
                "record", record,
                "items", items
            ));
        } catch (Exception e) {
            return Result.error("获取检查记录详情失败: " + e.getMessage());
        }
    }


    /**
     * 获取报告详细信息
     */
    @GetMapping(value = "/report/{recordCode}")
    public Result getReportInfo(@PathVariable("recordCode") String recordCode) {
        return Result.success(examRecordService.selectReportByCode(recordCode));
    }

    /**
     * 新增报告
     */
    @PostMapping("/report")
    public Result addReport(@RequestBody ExamRecord examRecord) {
        // 设置医生ID为当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            SysUser current = userService.selectUserByUsername(username);
            if (current != null) {
                examRecord.setDoctorId(current.getUserId());
            }
        }
        return Result.success(examRecordService.insertReport(examRecord));
    }

    /**
     * 修改报告
     */
    @PutMapping("/report")
    public Result editReport(@RequestBody ExamRecord examRecord) {
        return Result.success(examRecordService.updateReport(examRecord));
    }

    /**
     * 删除报告
     */
    @DeleteMapping("/report/{recordIds}")
    public Result removeReport(@PathVariable Long[] recordIds) {
        return Result.success(examRecordService.deleteReportByIds(recordIds));
    }
    
    /**
     * 发布报告
     */
    @PutMapping("/report/publish/{recordId}")
    public Result publishReport(@PathVariable Long recordId) {
        return Result.success(examRecordService.publishReport(recordId));
    }
} 