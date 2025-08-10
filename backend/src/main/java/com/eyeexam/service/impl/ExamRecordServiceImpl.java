package com.eyeexam.service.impl;

import com.eyeexam.entity.ExamRecord;
import com.eyeexam.entity.ExamRecordItem;
import com.eyeexam.mapper.ExamRecordMapper;
import com.eyeexam.mapper.ExamRecordItemMapper;
import com.eyeexam.service.IExamRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 检查记录Service业务层处理
 */
@Service
public class ExamRecordServiceImpl implements IExamRecordService {

    @Autowired
    private ExamRecordMapper examRecordMapper;

    @Autowired
    private ExamRecordItemMapper examRecordItemMapper;

    @Override
    public List<ExamRecord> selectExamRecordList(ExamRecord examRecord) {
        return examRecordMapper.selectExamRecordList(examRecord);
    }

    @Override
    public ExamRecord selectExamRecordById(Long recordId) {
        return examRecordMapper.selectById(recordId);
    }

    @Override
    public int insertExamRecord(ExamRecord examRecord) {
        return examRecordMapper.insert(examRecord);
    }

    @Override
    public int updateExamRecord(ExamRecord examRecord) {
        return examRecordMapper.updateById(examRecord);
    }

    @Override
    public int deleteExamRecordById(Long recordId) {
        return examRecordMapper.deleteById(recordId);
    }

    @Override
    public int deleteExamRecordByIds(Long[] recordIds) {
        int result = 0;
        for (Long id : recordIds) {
            result += examRecordMapper.deleteById(id);
        }
        return result;
    }

    @Override
    @Transactional
    public void saveExamRecordWithItems(ExamRecord examRecord, List<ExamRecordItem> examRecordItems) {
        // 保存检查记录
        examRecordMapper.insert(examRecord);
        
        // 保存检查记录项目
        for (ExamRecordItem item : examRecordItems) {
            item.setRecordId(examRecord.getRecordId());
            examRecordItemMapper.insert(item);
        }
    }

    @Override
    public List<ExamRecord> getExamRecordList(Integer page, Integer size, Long patientId) {
        int offset = (page - 1) * size;
        return examRecordMapper.selectExamRecordListWithPage(offset, size, patientId);
    }

    @Override
    public List<ExamRecordItem> getExamRecordItems(Long recordId) {
        return examRecordItemMapper.selectByRecordId(recordId);
    }
    
    @Override
    public List<ExamRecord> selectReportList(ExamRecord examRecord) {
        return examRecordMapper.selectReportList(examRecord);
    }
    
    @Override
    public ExamRecord selectReportById(Long recordId) {
        return examRecordMapper.selectReportById(recordId);
    }
    
    @Override
    public int insertReport(ExamRecord examRecord) {
        // 生成报告编号
        if (examRecord.getRecordNo() == null || examRecord.getRecordNo().isEmpty()) {
            examRecord.setRecordNo(generateReportNo());
        }
        // 设置报告日期
        if (examRecord.getExamDate() == null) {
            examRecord.setExamDate(LocalDate.now());
        }
        // 设置报告状态为草稿
        if (examRecord.getStatus() == null) {
            examRecord.setStatus(1);
        }
        return examRecordMapper.insert(examRecord);
    }
    
    @Override
    public int updateReport(ExamRecord examRecord) {
        return examRecordMapper.updateById(examRecord);
    }
    
    @Override
    public int deleteReportByIds(Long[] recordIds) {
        int result = 0;
        for (Long id : recordIds) {
            result += examRecordMapper.deleteById(id);
        }
        return result;
    }
    
    @Override
    public int publishReport(Long recordId) {
        ExamRecord examRecord = examRecordMapper.selectById(recordId);
        if (examRecord != null) {
            examRecord.setStatus(2); // 设置为已发布
            return examRecordMapper.updateById(examRecord);
        }
        return 0;
    }
    
    @Override
    public String generateReportNo() {
        // 生成报告编号：RP + 年月日 + 6位序号
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        return "RP" + dateStr + timestamp;
    }
} 