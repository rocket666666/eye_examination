# ExamRecord 和 ExamReport 合并说明

## 合并概述

将 `ExamReport`（检查报告）功能合并到 `ExamRecord`（检查记录）中，简化数据结构，避免重复。

## 主要变更

### 1. 实体类变更

#### ExamRecord 实体类新增字段：
- `reportNo` - 报告编号
- `reportTitle` - 报告标题  
- `reportContent` - 报告内容
- `conclusion` - 检查结论
- `suggestion` - 建议
- `reportDoctor` - 报告医生
- `reportDate` - 报告日期
- `reportStatus` - 报告状态（1草稿，2已发布）

### 2. 服务层变更

#### IExamRecordService 新增方法：
- `selectReportList()` - 获取报告列表
- `selectReportById()` - 根据ID获取报告详情
- `insertReport()` - 新增报告
- `updateReport()` - 更新报告
- `deleteReportByIds()` - 删除报告
- `publishReport()` - 发布报告
- `generateReportNo()` - 生成报告编号

### 3. 控制器层变更

#### ExamRecordController 新增接口：
- `GET /api/exam-record/report/list` - 获取报告列表
- `GET /api/exam-record/report/{recordId}` - 获取报告详情
- `POST /api/exam-record/report` - 新增报告
- `PUT /api/exam-record/report` - 更新报告
- `DELETE /api/exam-record/report/{recordIds}` - 删除报告
- `PUT /api/exam-record/report/publish/{recordId}` - 发布报告

### 4. 数据访问层变更

#### ExamRecordMapper 新增方法：
- `selectReportList()` - 查询报告列表
- `selectReportById()` - 查询报告详情

#### ExamRecordMapper.xml 更新：
- 添加报告相关字段映射
- 新增报告查询SQL

### 5. 前端变更

#### ExamReport.vue 更新：
- API路径从 `/exam/report` 改为 `/exam-record/report`
- 字段名称从 `status` 改为 `reportStatus`

### 6. 删除的文件

- `ExamReportController.java`
- `ExamReport.java`
- `IExamReportService.java`
- `ExamReportServiceImpl.java`
- `ExamReportMapper.java`

## 数据库变更

### exam_record 表新增字段：
```sql
ALTER TABLE exam_record 
ADD COLUMN report_no VARCHAR(50) COMMENT '报告编号',
ADD COLUMN report_title VARCHAR(200) COMMENT '报告标题',
ADD COLUMN report_content TEXT COMMENT '报告内容',
ADD COLUMN conclusion TEXT COMMENT '检查结论',
ADD COLUMN suggestion TEXT COMMENT '建议',
ADD COLUMN report_doctor VARCHAR(100) COMMENT '报告医生',
ADD COLUMN report_date DATE COMMENT '报告日期',
ADD COLUMN report_status TINYINT DEFAULT 1 COMMENT '报告状态：1草稿，2已发布';
```

## 迁移脚本

执行 `docker/mysql/exam_record_update.sql` 来更新数据库结构。

## 优势

1. **简化数据结构** - 减少重复的实体类
2. **统一管理** - 检查记录和报告在同一个表中管理
3. **减少复杂度** - 减少Controller和Service的数量
4. **提高性能** - 减少表关联查询

## 注意事项

1. 需要执行数据库迁移脚本
2. 前端API调用路径已更新
3. 报告状态字段从 `status` 改为 `reportStatus`
4. 检查记录状态和报告状态现在是两个独立的字段 