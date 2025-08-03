package com.eyeexam.service;

import com.eyeexam.entity.PatientInfo;
import com.eyeexam.entity.PatientCheckRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 患者信息Service接口
 */
public interface IPatientInfoService {

    /**
     * 分页查询患者信息列表
     * 
     * @param page 页码
     * @param size 每页数量
     * @param keyword 关键词（可选）
     * @return 患者信息分页结果
     */
    Page<PatientInfo> selectPatientInfoPage(int page, int size, String keyword);
    
    /**
     * 查询患者信息列表
     * 
     * @param patientInfo 患者信息
     * @return 患者信息集合
     */
    List<PatientInfo> selectPatientInfoList(PatientInfo patientInfo);
    
    /**
     * 根据患者ID查询患者信息
     * 
     * @param id 患者ID
     * @return 患者信息
     */
    PatientInfo selectPatientInfoById(Long id);
    
    /**
     * 根据患者编号查询患者信息
     * 
     * @param patientCode 患者编号
     * @return 患者信息
     */
    PatientInfo selectPatientInfoByCode(String patientCode);
    
    /**
     * 新增患者信息
     * 
     * @param patientInfo 患者信息
     * @return 结果
     */
    int insertPatientInfo(PatientInfo patientInfo);
    
    /**
     * 修改患者信息
     * 
     * @param patientInfo 患者信息
     * @return 结果
     */
    int updatePatientInfo(PatientInfo patientInfo);
    
    /**
     * 逻辑删除患者信息
     * 
     * @param id 患者ID
     * @return 结果
     */
    int deletePatientInfoById(Long id);
    
    /**
     * 校验患者编号是否唯一
     * 
     * @param patientCode 患者编号
     * @return 是否唯一
     */
    boolean checkPatientCodeUnique(String patientCode);
    
    /**
     * 生成患者编号
     * 
     * @return 患者编号
     */
    String generatePatientCode();
    
    /**
     * 添加检查记录并更新患者信息
     * 
     * @param checkRecord 检查记录
     * @return 结果
     */
    int addCheckRecordAndUpdatePatient(PatientCheckRecord checkRecord);
    
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
} 