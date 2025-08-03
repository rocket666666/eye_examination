package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.PatientInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 患者信息表Mapper接口
 */
@Mapper
public interface PatientInfoMapper extends BaseMapper<PatientInfo> {

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
     * 根据手机号查询患者信息
     * 
     * @param phone 手机号
     * @return 患者信息
     */
    PatientInfo selectPatientInfoByPhone(String phone);
    
    /**
     * 根据身份证号查询患者信息
     * 
     * @param idCard 身份证号
     * @return 患者信息
     */
    PatientInfo selectPatientInfoByIdCard(String idCard);
    
    /**
     * 校验患者编号是否唯一
     * 
     * @param patientCode 患者编号
     * @return 结果
     */
    PatientInfo checkPatientCodeUnique(String patientCode);
    
    /**
     * 校验手机号是否唯一
     * 
     * @param phone 手机号
     * @param id 患者ID
     * @return 结果
     */
    PatientInfo checkPhoneUnique(@Param("phone") String phone, @Param("id") Long id);
    
    /**
     * 校验身份证号是否唯一
     * 
     * @param idCard 身份证号
     * @param id 患者ID
     * @return 结果
     */
    PatientInfo checkIdCardUnique(@Param("idCard") String idCard, @Param("id") Long id);
} 