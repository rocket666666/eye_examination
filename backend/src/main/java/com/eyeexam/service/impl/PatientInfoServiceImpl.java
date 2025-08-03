package com.eyeexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyeexam.common.CommonUtil;
import com.eyeexam.entity.PatientInfo;
import com.eyeexam.entity.PatientCheckRecord;
import com.eyeexam.mapper.PatientInfoMapper;
import com.eyeexam.mapper.PatientCheckRecordMapper;
import com.eyeexam.service.IPatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 患者信息Service业务层处理
 */
@Service
public class PatientInfoServiceImpl implements IPatientInfoService {

    @Autowired
    private PatientInfoMapper patientInfoMapper;
    
    @Autowired
    private PatientCheckRecordMapper patientCheckRecordMapper;

    @Override
    public Page<PatientInfo> selectPatientInfoPage(int page, int size, String keyword) {
        Page<PatientInfo> pageObj = new Page<>(page, size);
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", false);
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like("full_name", keyword)
                .or().like("patient_code", keyword)
                .or().like("phone", keyword)
                .or().like("id_card", keyword)
            );
        }
        
        queryWrapper.orderByDesc("created_at");
        return patientInfoMapper.selectPage(pageObj, queryWrapper);
    }

    @Override
    public List<PatientInfo> selectPatientInfoList(PatientInfo patientInfo) {
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", false);
        
        if (StringUtils.hasText(patientInfo.getFullName())) {
            queryWrapper.like("full_name", patientInfo.getFullName());
        }
        if (StringUtils.hasText(patientInfo.getPatientCode())) {
            queryWrapper.like("patient_code", patientInfo.getPatientCode());
        }
        if (StringUtils.hasText(patientInfo.getPhone())) {
            queryWrapper.like("phone", patientInfo.getPhone());
        }
        
        queryWrapper.orderByDesc("created_at");
        return patientInfoMapper.selectList(queryWrapper);
    }

    @Override
    public PatientInfo selectPatientInfoById(Long id) {
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("is_deleted", false);
        return patientInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public PatientInfo selectPatientInfoByCode(String patientCode) {
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_code", patientCode).eq("is_deleted", false);
        return patientInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public int insertPatientInfo(PatientInfo patientInfo) {
        // 如果没有提供患者编号，自动生成
        if (!StringUtils.hasText(patientInfo.getPatientCode())) {
            patientInfo.setPatientCode(generatePatientCode());
        }
        
        // 设置默认状态
        if (patientInfo.getStatus() == null) {
            patientInfo.setStatus(1);
        }
        
        return patientInfoMapper.insert(patientInfo);
    }

    @Override
    public int updatePatientInfo(PatientInfo patientInfo) {
        return patientInfoMapper.updateById(patientInfo);
    }

    @Override
    public int deletePatientInfoById(Long id) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(id);
        patientInfo.setUpdatedAt(new Date());
        patientInfo.setIsDeleted(CommonUtil.YES);
        return patientInfoMapper.updateById(patientInfo);
    }

    @Override
    public boolean checkPatientCodeUnique(String patientCode) {
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_code", patientCode).eq("is_deleted", false);
        return patientInfoMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public String generatePatientCode() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String prefix = "P-" + dateStr;
        
        // 查询当天已有的最大编号
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("patient_code", prefix)
                   .eq("is_deleted", false)
                   .orderByDesc("patient_code")
                   .last("LIMIT 1");
        
        PatientInfo lastPatient = patientInfoMapper.selectOne(queryWrapper);
        
        int sequence = 1;
        if (lastPatient != null && lastPatient.getPatientCode() != null) {
            String lastCode = lastPatient.getPatientCode();
            if (lastCode.length() >= prefix.length() + 3) {
                try {
                    sequence = Integer.parseInt(lastCode.substring(prefix.length())) + 1;
                } catch (NumberFormatException e) {
                    sequence = 1;
                }
            }
        }
        
        return prefix + "-" + String.format("%03d", sequence);
    }

    @Override
    @Transactional
    public int addCheckRecordAndUpdatePatient(PatientCheckRecord checkRecord) {
        // 插入检查记录
        int result = patientCheckRecordMapper.insert(checkRecord);
        
        if (result > 0) {
            // 更新患者的上次检查信息
            PatientInfo patientInfo = new PatientInfo();
            patientInfo.setId(checkRecord.getPatientId());
            patientInfo.setLastCheckDate(checkRecord.getCheckDate());
            patientInfo.setLastCheckId(checkRecord.getId());
            patientInfo.setLastReportCode(checkRecord.getReportCode());
            
            patientInfoMapper.updateById(patientInfo);
        }
        
        return result;
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
} 