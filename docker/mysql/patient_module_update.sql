-- ========================================
-- 患者模块数据库更新脚本
-- 基于患者模块.md技术规范
-- ========================================

USE eye_examination;

-- ========================================
-- 删除现有的外键约束
-- ========================================
-- 查看并删除exam_record表的外键约束
SET FOREIGN_KEY_CHECKS = 0;

-- 删除旧的患者信息表（如果存在）
DROP TABLE IF EXISTS patient_check_record;
DROP TABLE IF EXISTS patient_info;

-- 重新启用外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- ========================================
-- 1. 患者信息表（patient_info）
-- ========================================
CREATE TABLE patient_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '患者ID，自增主键',
    first_name VARCHAR(50) COMMENT '姓（可选）',
    last_name VARCHAR(50) COMMENT '名（可选）',
    full_name VARCHAR(100) COMMENT '姓名',
    patient_code VARCHAR(50) UNIQUE NOT NULL COMMENT '编号，唯一',
    gender CHAR(1) DEFAULT 'U' COMMENT '性别：M男，F女，U未知',
    date_of_birth DATE COMMENT '出生日期',
    age BIGINT COMMENT '年龄（可选）',
    last_check_date DATE COMMENT '上次检查时间',
    last_check_id BIGINT COMMENT '上次检查ID',
    last_report_code VARCHAR(50) COMMENT '上次报告编码',
    phone VARCHAR(20) COMMENT '联系电话',
    status INT DEFAULT 1 COMMENT '状态：1正常，0禁用',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT '是否删除（逻辑删除标记）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者信息表';

-- ========================================
-- 2. 检查记录表（patient_check_record）
-- ========================================
CREATE TABLE patient_check_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    patient_id BIGINT NOT NULL COMMENT '外键，关联患者ID',
    check_date DATE NOT NULL COMMENT '检查时间',
    doctor_name VARCHAR(100) COMMENT '检查医生',
    report_id BIGINT COMMENT '报告ID',
    report_code VARCHAR(50) COMMENT '报告编码',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检查记录表';

-- ========================================
-- 3. 创建索引
-- ========================================
-- 患者信息表索引
CREATE INDEX idx_patient_info_code ON patient_info(patient_code);
CREATE INDEX idx_patient_info_full_name ON patient_info(full_name);
CREATE INDEX idx_patient_info_phone ON patient_info(phone);
CREATE INDEX idx_patient_info_is_deleted ON patient_info(is_deleted);

-- 检查记录表索引
CREATE INDEX idx_check_record_patient_id ON patient_check_record(patient_id);
CREATE INDEX idx_check_record_check_date ON patient_check_record(check_date);

-- ========================================
-- 4. 创建外键约束
-- ========================================
ALTER TABLE patient_check_record 
ADD CONSTRAINT fk_check_record_patient_id 
FOREIGN KEY (patient_id) REFERENCES patient_info(id) 
ON DELETE CASCADE ON UPDATE CASCADE;

-- ========================================
-- 5. 插入测试数据
-- ========================================
-- 插入测试患者数据
INSERT INTO patient_info (
    first_name, last_name, full_name, patient_code, 
    gender, date_of_birth, age, phone, status, is_deleted, remark
) VALUES 
('张', '三', '张三', 'P_250127_001', 'M', '1990-05-15', 34, '13800138001', 1, FALSE, '测试患者1'),
('李', '四', '李四', 'P_250127_002', 'F', '1985-08-20', 39, '13800138002', 1, FALSE, '测试患者2'),
('王', '五', '王五', 'P_250127_003', 'M', '1992-12-10', 32, '13800138003', 1, FALSE, '测试患者3'),
('赵', '六', '赵六', 'P_250127_004', 'F', '1988-03-25', 36, '13800138004', 1, FALSE, '测试患者4'),
('陈', '七', '陈七', 'P_250127_005', 'U', '1995-11-08', 29, '13800138005', 1, FALSE, '测试患者5');

-- 插入测试检查记录
INSERT INTO patient_check_record (
    patient_id, check_date, doctor_name, report_code
) VALUES 
(1, '2025-01-20', '医生A', 'RPT_001'),
(1, '2025-01-25', '医生B', 'RPT_002'),
(2, '2025-01-22', '医生A', 'RPT_003'),
(3, '2025-01-24', '医生C', 'RPT_004'),
(4, '2025-01-26', '医生B', 'RPT_005');

-- 更新患者的最后检查信息
UPDATE patient_info p 
SET 
    last_check_date = (
        SELECT MAX(check_date) 
        FROM patient_check_record r 
        WHERE r.patient_id = p.id
    ),
    last_check_id = (
        SELECT id 
        FROM patient_check_record r 
        WHERE r.patient_id = p.id 
        ORDER BY check_date DESC, id DESC 
        LIMIT 1
    ),
    last_report_code = (
        SELECT report_code 
        FROM patient_check_record r 
        WHERE r.patient_id = p.id 
        ORDER BY check_date DESC, id DESC 
        LIMIT 1
    )
WHERE EXISTS (
    SELECT 1 FROM patient_check_record r WHERE r.patient_id = p.id
);

-- ========================================
-- 6. 验证数据
-- ========================================
-- 查看患者列表
SELECT 
    id, patient_code, full_name, gender, 
    date_of_birth, last_check_date, last_report_code, 
    is_deleted, created_at
FROM patient_info 
WHERE is_deleted = FALSE
ORDER BY id;

-- 查看检查记录
SELECT 
    id, patient_id, check_date, doctor_name, report_code
FROM patient_check_record 
ORDER BY patient_id, check_date;

-- 显示表结构
DESCRIBE patient_info;
DESCRIBE patient_check_record; 