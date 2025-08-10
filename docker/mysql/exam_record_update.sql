-- 为exam_record表添加报告相关字段
-- 执行时间：2024年

-- 添加报告相关字段
ALTER TABLE exam_record 
ADD COLUMN report_no VARCHAR(50) COMMENT '报告编号',
ADD COLUMN report_title VARCHAR(200) COMMENT '报告标题',
ADD COLUMN report_content TEXT COMMENT '报告内容',
ADD COLUMN conclusion TEXT COMMENT '检查结论',
ADD COLUMN suggestion TEXT COMMENT '建议',
ADD COLUMN report_doctor VARCHAR(100) COMMENT '报告医生',
ADD COLUMN report_date DATE COMMENT '报告日期',
ADD COLUMN report_status TINYINT DEFAULT 1 COMMENT '报告状态：1草稿，2已发布';

-- 创建索引
CREATE INDEX idx_exam_record_report_no ON exam_record(report_no);
CREATE INDEX idx_exam_record_report_date ON exam_record(report_date);
CREATE INDEX idx_exam_record_report_status ON exam_record(report_status);

-- 更新现有记录的report_status为已发布状态（如果status为已完成）
UPDATE exam_record SET report_status = 2 WHERE status = 3;

-- 为现有记录生成报告编号（如果没有的话）
UPDATE exam_record 
SET report_no = CONCAT('RP', DATE_FORMAT(create_time, '%Y%m%d'), LPAD(record_id, 6, '0'))
WHERE report_no IS NULL OR report_no = ''; 