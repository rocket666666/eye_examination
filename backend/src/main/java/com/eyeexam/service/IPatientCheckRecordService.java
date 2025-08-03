package com.eyeexam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyeexam.entity.PatientCheckRecord;

import java.util.List;

/**
 * 患者检查记录Service接口
 */
public interface IPatientCheckRecordService {

    /**
     * 查询检查记录列表
     * 
     * @param checkRecord 检查记录
     * @return 检查记录集合
     */
    List<PatientCheckRecord> selectCheckRecordList(PatientCheckRecord checkRecord);
    
    /**
     * 根据ID查询检查记录
     * 
     * @param id 检查记录ID
     * @return 检查记录
     */
    PatientCheckRecord selectCheckRecordById(Long id);
    
    /**
     * 新增检查记录
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    int insertCheckRecord(PatientCheckRecord checkRecord);
    
    /**
     * 修改检查记录
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    int updateCheckRecord(PatientCheckRecord checkRecord);
    
    /**
     * 删除检查记录
     * 
     * @param id 检查记录ID
     * @return 结果
     */
    int deleteCheckRecordById(Long id);
    
    /**
     * 根据患者ID查询检查记录列表
     * 
     * @param patientId 患者ID
     * @return 检查记录集合
     */
    List<PatientCheckRecord> selectCheckRecordsByPatientId(Long patientId);
    
    /**
     * 分页查询患者检查记录
     * 
     * @param patientId 患者ID
     * @param page 页码
     * @param size 每页数量
     * @return 检查记录分页结果
     */
    Page<PatientCheckRecord> selectCheckRecordsByPatientIdWithPage(Long patientId, int page, int size);
    
    /**
     * 获取患者最新的检查记录
     * 
     * @param patientId 患者ID
     * @return 最新检查记录
     */
    PatientCheckRecord selectLatestCheckRecordByPatientId(Long patientId);
} 