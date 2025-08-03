package com.eyeexam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyeexam.entity.ExamReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 检查报告表Mapper接口
 */
@Mapper
public interface ExamReportMapper extends BaseMapper<ExamReport> {

    /**
     * 查询检查报告列表
     * 
     * @param examReport 检查报告
     * @return 检查报告集合
     */
    List<ExamReport> selectExamReportList(ExamReport examReport);
    
    /**
     * 根据报告ID查询检查报告
     * 
     * @param reportId 报告ID
     * @return 检查报告
     */
    ExamReport selectExamReportById(Long reportId);
    
    /**
     * 根据报告编号查询检查报告
     * 
     * @param reportNo 报告编号
     * @return 检查报告
     */
    ExamReport selectExamReportByNo(String reportNo);
    
    /**
     * 根据检查记录ID查询检查报告
     * 
     * @param recordId 检查记录ID
     * @return 检查报告
     */
    ExamReport selectExamReportByRecordId(Long recordId);
    
    /**
     * 根据患者ID查询检查报告列表
     * 
     * @param patientId 患者ID
     * @return 检查报告集合
     */
    List<ExamReport> selectExamReportListByPatientId(Long patientId);
    
    /**
     * 根据报告医生查询检查报告列表
     * 
     * @param reportDoctor 报告医生
     * @return 检查报告集合
     */
    List<ExamReport> selectExamReportListByDoctor(String reportDoctor);
    
    /**
     * 根据报告日期查询检查报告列表
     * 
     * @param reportDate 报告日期
     * @return 检查报告集合
     */
    List<ExamReport> selectExamReportListByDate(LocalDate reportDate);
    
    /**
     * 根据日期范围查询检查报告列表
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 检查报告集合
     */
    List<ExamReport> selectExamReportListByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 校验报告编号是否唯一
     * 
     * @param reportNo 报告编号
     * @return 结果
     */
    ExamReport checkReportNoUnique(String reportNo);
    
    /**
     * 批量删除检查报告
     * 
     * @param reportIds 需要删除的报告ID
     * @return 结果
     */
    int deleteExamReportByIds(Long[] reportIds);
    
    /**
     * 根据状态统计检查报告数量
     * 
     * @param status 状态
     * @return 数量
     */
    int countExamReportByStatus(Integer status);
    
    /**
     * 查询今日检查报告数量
     * 
     * @return 数量
     */
    int countTodayExamReport();
} 