package com.eyeexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyeexam.entity.PatientCheckRecord;
import com.eyeexam.mapper.PatientCheckRecordMapper;
import com.eyeexam.service.IPatientCheckRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 患者检查记录Service业务层处理
 */
@Service
public class PatientCheckRecordServiceImpl implements IPatientCheckRecordService {

    @Autowired
    private PatientCheckRecordMapper patientCheckRecordMapper;

    @Override
    public List<PatientCheckRecord> selectCheckRecordList(PatientCheckRecord checkRecord) {
        QueryWrapper<PatientCheckRecord> queryWrapper = new QueryWrapper<>();
        
        if (checkRecord.getPatientId() != null) {
            queryWrapper.eq("patient_id", checkRecord.getPatientId());
        }
        if (checkRecord.getCheckDate() != null) {
            queryWrapper.eq("check_date", checkRecord.getCheckDate());
        }
        if (StringUtils.hasText(checkRecord.getDoctorName())) {
            queryWrapper.like("doctor_name", checkRecord.getDoctorName());
        }
        if (StringUtils.hasText(checkRecord.getReportCode())) {
            queryWrapper.like("report_code", checkRecord.getReportCode());
        }
        
        queryWrapper.orderByDesc("check_date");
        return patientCheckRecordMapper.selectList(queryWrapper);
    }

    @Override
    public PatientCheckRecord selectCheckRecordById(Long id) {
        return patientCheckRecordMapper.selectById(id);
    }

    @Override
    public int insertCheckRecord(PatientCheckRecord checkRecord) {
        return patientCheckRecordMapper.insert(checkRecord);
    }

    @Override
    public int updateCheckRecord(PatientCheckRecord checkRecord) {
        return patientCheckRecordMapper.updateById(checkRecord);
    }

    @Override
    public int deleteCheckRecordById(Long id) {
        return patientCheckRecordMapper.deleteById(id);
    }

    @Override
    public List<PatientCheckRecord> selectCheckRecordsByPatientId(Long patientId) {
        return patientCheckRecordMapper.selectCheckRecordsByPatientId(patientId);
    }

    @Override
    public Page<PatientCheckRecord> selectCheckRecordsByPatientIdWithPage(Long patientId, int page, int size) {
        Page<PatientCheckRecord> pageObj = new Page<>(page, size);
        QueryWrapper<PatientCheckRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id", patientId).orderByDesc("check_date");
        return patientCheckRecordMapper.selectPage(pageObj, queryWrapper);
    }

    @Override
    public PatientCheckRecord selectLatestCheckRecordByPatientId(Long patientId) {
        return patientCheckRecordMapper.selectLatestCheckRecordByPatientId(patientId);
    }
} 