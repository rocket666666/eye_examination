package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.ExamRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 检查记录表Mapper接口
 */
@Mapper
public interface ExamRecordMapper extends BaseMapper<ExamRecord> {

    /**
     * 查询检查记录列表
     * 
     * @param examRecord 检查记录
     * @return 检查记录集合
     */
    List<ExamRecord> selectExamRecordList(ExamRecord examRecord);
    
    /**
     * 根据检查记录ID查询信息
     * 
     * @param recordId 检查记录ID
     * @return 检查记录
     */
    ExamRecord selectExamRecordById(Long recordId);
    
    /**
     * 根据检查单号查询记录
     * 
     * @param recordNo 检查单号
     * @return 检查记录
     */
    ExamRecord selectExamRecordByNo(String recordNo);
    
    /**
     * 根据患者ID查询检查记录列表
     * 
     * @param patientId 患者ID
     * @return 检查记录集合
     */
    List<ExamRecord> selectExamRecordListByPatientId(Long patientId);
    
    /**
     * 根据医生ID查询检查记录列表
     * 
     * @param doctorId 医生ID
     * @return 检查记录集合
     */
    List<ExamRecord> selectExamRecordListByDoctorId(Long doctorId);
    
    /**
     * 根据检查日期查询检查记录列表
     * 
     * @param examDate 检查日期
     * @return 检查记录集合
     */
    List<ExamRecord> selectExamRecordListByDate(LocalDate examDate);
    
    /**
     * 根据日期范围查询检查记录列表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 检查记录集合
     */
    List<ExamRecord> selectExamRecordListByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 校验检查单号是否唯一
     * 
     * @param recordNo 检查单号
     * @return 结果
     */
    ExamRecord checkRecordNoUnique(String recordNo);
    
    /**
     * 批量删除检查记录
     * 
     * @param recordIds 需要删除的记录ID
     * @return 结果
     */
    int deleteExamRecordByIds(Long[] recordIds);
    
    /**
     * 根据状态统计检查记录数量
     * 
     * @param status 状态
     * @return 数量
     */
    int countExamRecordByStatus(Integer status);
    
    /**
     * 查询今日检查记录数量
     * 
     * @return 数量
     */
    int countTodayExamRecord();
} 