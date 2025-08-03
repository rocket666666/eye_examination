package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.PatientCheckRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检查记录表Mapper接口
 */
@Mapper
public interface PatientCheckRecordMapper extends BaseMapper<PatientCheckRecord> {

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
     * @return 检查记录集合
     */
    List<PatientCheckRecord> selectCheckRecordsByPatientIdWithPage(@Param("patientId") Long patientId, @Param("page") int page, @Param("size") int size);
    
    /**
     * 获取患者最新的检查记录
     * 
     * @param patientId 患者ID
     * @return 最新检查记录
     */
    PatientCheckRecord selectLatestCheckRecordByPatientId(Long patientId);
} 