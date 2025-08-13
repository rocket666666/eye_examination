-- ========================================
-- 耳鼻咽喉科检查系统数据库初始化脚本
-- 数据库: eye_examination
-- 版本: 1.0
-- 创建时间: 2024-01-01
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS eye_examination DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE eye_examination;

-- ========================================
-- 系统管理相关表
-- ========================================

-- 部门表
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
    dept_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    ancestors VARCHAR(255) DEFAULT '' COMMENT '祖级列表',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    dept_code VARCHAR(50) COMMENT '部门编码',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用，0禁用',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志：0存在，1删除',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    user_name VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(255) COMMENT '头像',
    gender TINYINT DEFAULT 0 COMMENT '性别：0未知，1男，2女',
    birthday DATE COMMENT '生日',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用，0禁用',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志：0存在，1删除',
    dept_id BIGINT COMMENT '部门ID',
    login_ip VARCHAR(50) COMMENT '最后登录IP',
    login_date DATETIME COMMENT '最后登录时间',
    pwd_update_date DATETIME COMMENT '密码最后更新时间',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) UNIQUE NOT NULL COMMENT '角色名称',
    role_key VARCHAR(50) UNIQUE NOT NULL COMMENT '角色权限字符串',
    role_sort INT DEFAULT 0 COMMENT '显示顺序',
    data_scope TINYINT DEFAULT 1 COMMENT '数据范围：1全部，2自定义，3本部门，4本部门及以下，5仅本人',
    menu_check_strictly TINYINT DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
    dept_check_strictly TINYINT DEFAULT 1 COMMENT '部门树选择项是否关联显示',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用，0禁用',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志：0存在，1删除',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单权限表
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
    menu_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    path VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    component VARCHAR(255) COMMENT '组件路径',
    query VARCHAR(255) COMMENT '路由参数',
    is_frame TINYINT DEFAULT 1 COMMENT '是否为外链：1是，0否',
    is_cache TINYINT DEFAULT 0 COMMENT '是否缓存：1缓存，0不缓存',
    menu_type CHAR(1) DEFAULT '' COMMENT '菜单类型：M目录，C菜单，F按钮',
    visible TINYINT DEFAULT 1 COMMENT '菜单状态：1显示，0隐藏',
    status TINYINT DEFAULT 1 COMMENT '菜单状态：1启用，0禁用',
    perms VARCHAR(100) COMMENT '权限标识',
    icon VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- 用户角色关联表
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 角色部门关联表
DROP TABLE IF EXISTS sys_role_dept;
CREATE TABLE sys_role_dept (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    PRIMARY KEY (role_id, dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门关联表';

-- 系统配置表
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config (
    config_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '参数主键',
    config_name VARCHAR(100) DEFAULT '' COMMENT '参数名称',
    config_key VARCHAR(100) DEFAULT '' COMMENT '参数键名',
    config_value VARCHAR(500) DEFAULT '' COMMENT '参数键值',
    config_type CHAR(1) DEFAULT 'N' COMMENT '系统内置：Y是，N否',
    create_by VARCHAR(50) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

-- 字典类型表
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type (
    dict_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字典主键',
    dict_name VARCHAR(100) DEFAULT '' COMMENT '字典名称',
    dict_type VARCHAR(100) DEFAULT '' COMMENT '字典类型',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用，0禁用',
    create_by VARCHAR(50) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data (
    dict_code BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字典编码',
    dict_sort INT DEFAULT 0 COMMENT '字典排序',
    dict_label VARCHAR(100) DEFAULT '' COMMENT '字典标签',
    dict_value VARCHAR(100) DEFAULT '' COMMENT '字典键值',
    dict_type VARCHAR(100) DEFAULT '' COMMENT '字典类型',
    css_class VARCHAR(100) COMMENT '样式属性',
    list_class VARCHAR(100) COMMENT '表格回显样式',
    is_default CHAR(1) DEFAULT 'N' COMMENT '是否默认：Y是，N否',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用，0禁用',
    create_by VARCHAR(50) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 操作日志表
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log (
    oper_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志主键',
    title VARCHAR(50) DEFAULT '' COMMENT '模块标题',
    business_type INT DEFAULT 0 COMMENT '业务类型：0其它，1新增，2修改，3删除',
    method VARCHAR(100) DEFAULT '' COMMENT '方法名称',
    request_method VARCHAR(10) DEFAULT '' COMMENT '请求方式',
    operator_type INT DEFAULT 0 COMMENT '操作类别：0其它，1后台用户，2手机端用户',
    oper_name VARCHAR(50) DEFAULT '' COMMENT '操作人员',
    dept_name VARCHAR(50) DEFAULT '' COMMENT '部门名称',
    oper_url VARCHAR(255) DEFAULT '' COMMENT '请求URL',
    oper_ip VARCHAR(50) DEFAULT '' COMMENT '主机地址',
    oper_location VARCHAR(255) DEFAULT '' COMMENT '操作地点',
    oper_param VARCHAR(2000) DEFAULT '' COMMENT '请求参数',
    json_result VARCHAR(2000) DEFAULT '' COMMENT '返回参数',
    status INT DEFAULT 1 COMMENT '操作状态：1成功，0失败',
    error_msg VARCHAR(2000) DEFAULT '' COMMENT '错误消息',
    oper_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';

-- 系统访问记录表
DROP TABLE IF EXISTS sys_logininfor;
CREATE TABLE sys_logininfor (
    info_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '访问ID',
    user_name VARCHAR(50) DEFAULT '' COMMENT '用户账号',
    ipaddr VARCHAR(50) DEFAULT '' COMMENT '登录IP地址',
    login_location VARCHAR(255) DEFAULT '' COMMENT '登录地点',
    browser VARCHAR(50) DEFAULT '' COMMENT '浏览器类型',
    os VARCHAR(50) DEFAULT '' COMMENT '操作系统',
    status CHAR(1) DEFAULT '0' COMMENT '登录状态：0成功，1失败',
    msg VARCHAR(255) DEFAULT '' COMMENT '提示消息',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';

-- ========================================
-- 耳鼻咽喉科检查业务相关表
-- ========================================

-- 患者信息表
DROP TABLE IF EXISTS patient_info;
CREATE TABLE patient_info (
    patient_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '患者ID',
    patient_code VARCHAR(20) UNIQUE NOT NULL COMMENT '患者编号',
    full_name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender TINYINT DEFAULT 0 COMMENT '性别：0未知，1男，2女',
    birth_date DATE COMMENT '出生日期',
    age INT COMMENT '年龄',
    phone VARCHAR(20) COMMENT '联系电话',
    id_card VARCHAR(18) COMMENT '身份证号',
    address VARCHAR(200) COMMENT '地址',
    emergency_contact VARCHAR(50) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    medical_history TEXT COMMENT '病史',
    allergy_history TEXT COMMENT '过敏史',
    family_history TEXT COMMENT '家族史',
    status TINYINT DEFAULT 1 COMMENT '状态：1正常，0禁用',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志：0存在，1删除',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者信息表';

-- 检查项目表
DROP TABLE IF EXISTS exam_item;
CREATE TABLE exam_item (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '检查项目ID',
    item_code VARCHAR(20) UNIQUE NOT NULL COMMENT '项目编码',
    item_name VARCHAR(100) NOT NULL COMMENT '项目名称',
    item_type VARCHAR(50) COMMENT '项目类型',
    description TEXT COMMENT '项目描述',
    price DECIMAL(10,2) DEFAULT 0.00 COMMENT '价格',
    unit VARCHAR(10) COMMENT '单位',
    normal_range VARCHAR(200) COMMENT '正常范围',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用，0禁用',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志：0存在，1删除',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检查项目表';

-- 检查记录表
DROP TABLE IF EXISTS exam_record;
CREATE TABLE exam_record (
    record_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '检查记录ID',
    record_no VARCHAR(30) UNIQUE NOT NULL COMMENT '检查单号',
    patient_id BIGINT NOT NULL COMMENT '患者ID',
    doctor_id BIGINT COMMENT '医生ID',
    exam_date DATE NOT NULL COMMENT '检查日期',
    exam_time TIME COMMENT '检查时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1待检查，2检查中，3已完成，4已取消',
    total_amount DECIMAL(10,2) DEFAULT 0.00 COMMENT '总金额',
    -- 以下为报告相关合并字段
    report_no VARCHAR(50) COMMENT '报告编号',
    report_title VARCHAR(200) COMMENT '报告标题',
    report_content TEXT COMMENT '报告内容',
    conclusion TEXT COMMENT '检查结论',
    suggestion TEXT COMMENT '建议',
    report_doctor VARCHAR(100) COMMENT '报告医生',
    report_date DATE COMMENT '报告日期',
    report_status TINYINT DEFAULT 1 COMMENT '报告状态：1草稿，2已发布',
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志：0存在，1删除',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检查记录表(含报告)';

-- 检查记录项目明细表
DROP TABLE IF EXISTS exam_record_item;
CREATE TABLE exam_record_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    record_id BIGINT NOT NULL COMMENT '检查记录ID',
    item_id BIGINT NOT NULL COMMENT '检查项目ID',
    item_result TEXT COMMENT '检查结果',
    item_value VARCHAR(200) COMMENT '检查数值',
    is_normal TINYINT DEFAULT 1 COMMENT '是否正常：1正常，0异常',
    doctor_advice TEXT COMMENT '医生建议',
    exam_doctor VARCHAR(50) COMMENT '检查医生',
    exam_time DATETIME COMMENT '检查时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1待检查，2已完成',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检查记录项目明细表';

-- 检查报告表
-- 已合并到 exam_record 表，删除独立的检查报告表
-- DROP TABLE IF EXISTS exam_report;

-- ========================================
-- 初始化数据
-- ========================================

-- 初始化部门数据
INSERT INTO sys_dept VALUES(100, 0, '0', '耳鼻咽喉科医院', 'EYE_HOSPITAL', 0, '院长', '15888888888', 'admin@eye.com', 1, 0, 'admin', NOW(), '', NULL);
INSERT INTO sys_dept VALUES(101, 100, '0,100', '医疗部', 'MEDICAL_DEPT', 1, '医疗主任', '15888888888', 'medical@eye.com', 1, 0, 'admin', NOW(), '', NULL);
INSERT INTO sys_dept VALUES(102, 100, '0,100', '行政部', 'ADMIN_DEPT', 2, '行政主任', '15888888888', 'admin@eye.com', 1, 0, 'admin', NOW(), '', NULL);
INSERT INTO sys_dept VALUES(103, 101, '0,100,101', '耳鼻咽喉科门诊', 'EYE_CLINIC', 1, '门诊主任', '15888888888', 'clinic@eye.com', 1, 0, 'admin', NOW(), '', NULL);
INSERT INTO sys_dept VALUES(104, 101, '0,100,101', '检查科', 'EXAM_DEPT', 2, '检查主任', '15888888888', 'exam@eye.com', 1, 0, 'admin', NOW(), '', NULL);

-- 初始化用户数据（密码都是123456的BCrypt加密）
INSERT INTO sys_user VALUES(1, 'admin', '$2a$10$7JB720yubVSKvNFLXuEhfu2lNPdvgJDqgp7MzNzUxfHpKcH9fUWGm', '系统管理员', '管理员', 'admin@eye.com', '15888888888', '', 1, '1990-01-01', 1, 0, 100, '', NULL, NOW(), 'admin', NOW(), '', NULL, '系统管理员账号');
INSERT INTO sys_user VALUES(2, 'doctor', '$2a$10$7JB720yubVSKvNFLXuEhfu2lNPdvgJDqgp7MzNzUxfHpKcH9fUWGm', '医生', '张医生', 'doctor@eye.com', '15666666666', '', 1, '1985-05-15', 1, 0, 103, '', NULL, NOW(), 'admin', NOW(), '', NULL, '耳鼻咽喉科医生');
INSERT INTO sys_user VALUES(3, 'nurse', '$2a$10$7JB720yubVSKvNFLXuEhfu2lNPdvgJDqgp7MzNzUxfHpKcH9fUWGm', '护士', '李护士', 'nurse@eye.com', '15777777777', '', 2, '1992-08-20', 1, 0, 103, '', NULL, NOW(), 'admin', NOW(), '', NULL, '护士');
INSERT INTO sys_user VALUES(4, 'technician', '$2a$10$7JB720yubVSKvNFLXuEhfu2lNPdvgJDqgp7MzNzUxfHpKcH9fUWGm', '技师', '王技师', 'tech@eye.com', '15555555555', '', 1, '1988-03-10', 1, 0, 104, '', NULL, NOW(), 'admin', NOW(), '', NULL, '检查技师');

-- 初始化角色数据
INSERT INTO sys_role VALUES(1, '超级管理员', 'admin', 1, 1, 1, 1, 1, 0, 'admin', NOW(), '', NULL, '超级管理员');
INSERT INTO sys_role VALUES(2, '医生', 'doctor', 2, 2, 1, 1, 1, 0, 'admin', NOW(), '', NULL, '医生角色');
INSERT INTO sys_role VALUES(3, '护士', 'nurse', 3, 3, 1, 1, 1, 0, 'admin', NOW(), '', NULL, '护士角色');
INSERT INTO sys_role VALUES(4, '技师', 'technician', 4, 4, 1, 1, 1, 0, 'admin', NOW(), '', NULL, '检查技师角色');

-- 初始化菜单数据
INSERT INTO sys_menu VALUES(1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', 1, 1, '', 'system', 'admin', NOW(), '', NULL, '系统管理目录');
INSERT INTO sys_menu VALUES(2, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', 1, 1, 'system:user:list', 'user', 'admin', NOW(), '', NULL, '用户管理菜单');
INSERT INTO sys_menu VALUES(3, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', 1, 1, 'system:role:list', 'peoples', 'admin', NOW(), '', NULL, '角色管理菜单');
INSERT INTO sys_menu VALUES(4, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', 1, 1, 'system:menu:list', 'tree-table', 'admin', NOW(), '', NULL, '菜单管理菜单');
INSERT INTO sys_menu VALUES(5, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', 1, 1, 'system:dept:list', 'tree', 'admin', NOW(), '', NULL, '部门管理菜单');
INSERT INTO sys_menu VALUES(6, '字典管理', 1, 5, 'dict', 'system/dict/index', '', 1, 0, 'C', 1, 1, 'system:dict:list', 'dict', 'admin', NOW(), '', NULL, '字典管理菜单');
INSERT INTO sys_menu VALUES(7, '参数设置', 1, 6, 'config', 'system/config/index', '', 1, 0, 'C', 1, 1, 'system:config:list', 'edit', 'admin', NOW(), '', NULL, '参数设置菜单');
INSERT INTO sys_menu VALUES(8, '日志管理', 1, 7, 'log', '', '', 1, 0, 'M', 1, 1, '', 'log', 'admin', NOW(), '', NULL, '日志管理菜单');
INSERT INTO sys_menu VALUES(9, '操作日志', 8, 1, 'operlog', 'system/operlog/index', '', 1, 0, 'C', 1, 1, 'system:operlog:list', 'form', 'admin', NOW(), '', NULL, '操作日志菜单');
INSERT INTO sys_menu VALUES(10, '登录日志', 8, 2, 'logininfor', 'system/logininfor/index', '', 1, 0, 'C', 1, 1, 'system:logininfor:list', 'logininfor', 'admin', NOW(), '', NULL, '登录日志菜单');

-- 耳鼻咽喉科检查管理菜单
INSERT INTO sys_menu VALUES(100, '耳鼻咽喉科检查', 0, 2, 'exam', NULL, '', 1, 0, 'M', 1, 1, '', 'eye', 'admin', NOW(), '', NULL, '耳鼻咽喉科检查管理');
INSERT INTO sys_menu VALUES(101, '患者管理', 100, 1, 'patient', 'exam/patient/index', '', 1, 0, 'C', 1, 1, 'exam:patient:list', 'user', 'admin', NOW(), '', NULL, '患者管理菜单');
INSERT INTO sys_menu VALUES(102, '检查项目', 100, 2, 'item', 'exam/item/index', '', 1, 0, 'C', 1, 1, 'exam:item:list', 'list', 'admin', NOW(), '', NULL, '检查项目菜单');
INSERT INTO sys_menu VALUES(103, '检查记录', 100, 3, 'record', 'exam/record/index', '', 1, 0, 'C', 1, 1, 'exam:record:list', 'documentation', 'admin', NOW(), '', NULL, '检查记录菜单');
INSERT INTO sys_menu VALUES(104, '检查报告', 100, 4, 'report', 'exam/report/index', '', 1, 0, 'C', 1, 1, 'exam:report:list', 'clipboard', 'admin', NOW(), '', NULL, '检查报告菜单');

-- 初始化用户角色关联
INSERT INTO sys_user_role VALUES(1, 1);
INSERT INTO sys_user_role VALUES(2, 2);
INSERT INTO sys_user_role VALUES(3, 3);
INSERT INTO sys_user_role VALUES(4, 4);

-- 初始化角色菜单关联
-- 超级管理员拥有所有菜单权限
INSERT INTO sys_role_menu VALUES(1, 1);
INSERT INTO sys_role_menu VALUES(1, 2);
INSERT INTO sys_role_menu VALUES(1, 3);
INSERT INTO sys_role_menu VALUES(1, 4);
INSERT INTO sys_role_menu VALUES(1, 5);
INSERT INTO sys_role_menu VALUES(1, 6);
INSERT INTO sys_role_menu VALUES(1, 7);
INSERT INTO sys_role_menu VALUES(1, 8);
INSERT INTO sys_role_menu VALUES(1, 9);
INSERT INTO sys_role_menu VALUES(1, 10);
INSERT INTO sys_role_menu VALUES(1, 100);
INSERT INTO sys_role_menu VALUES(1, 101);
INSERT INTO sys_role_menu VALUES(1, 102);
INSERT INTO sys_role_menu VALUES(1, 103);
INSERT INTO sys_role_menu VALUES(1, 104);

-- 医生角色菜单权限
INSERT INTO sys_role_menu VALUES(2, 100);
INSERT INTO sys_role_menu VALUES(2, 101);
INSERT INTO sys_role_menu VALUES(2, 102);
INSERT INTO sys_role_menu VALUES(2, 103);
INSERT INTO sys_role_menu VALUES(2, 104);

-- 护士角色菜单权限
INSERT INTO sys_role_menu VALUES(3, 100);
INSERT INTO sys_role_menu VALUES(3, 101);
INSERT INTO sys_role_menu VALUES(3, 103);

-- 技师角色菜单权限
INSERT INTO sys_role_menu VALUES(4, 100);
INSERT INTO sys_role_menu VALUES(4, 101);
INSERT INTO sys_role_menu VALUES(4, 102);
INSERT INTO sys_role_menu VALUES(4, 103);

-- 初始化系统配置
INSERT INTO sys_config VALUES(1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', NOW(), '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO sys_config VALUES(2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', NOW(), '', NULL, '初始化密码 123456');
INSERT INTO sys_config VALUES(3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', NOW(), '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO sys_config VALUES(4, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', NOW(), '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO sys_config VALUES(5, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', NOW(), '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');

-- 初始化字典类型
INSERT INTO sys_dict_type VALUES(1, '用户性别', 'sys_user_sex', 1, 'admin', NOW(), '', NULL, '用户性别列表');
INSERT INTO sys_dict_type VALUES(2, '菜单状态', 'sys_show_hide', 1, 'admin', NOW(), '', NULL, '菜单状态列表');
INSERT INTO sys_dict_type VALUES(3, '系统开关', 'sys_normal_disable', 1, 'admin', NOW(), '', NULL, '系统开关列表');
INSERT INTO sys_dict_type VALUES(4, '任务状态', 'sys_job_status', 1, 'admin', NOW(), '', NULL, '任务状态列表');
INSERT INTO sys_dict_type VALUES(5, '任务分组', 'sys_job_group', 1, 'admin', NOW(), '', NULL, '任务分组列表');
INSERT INTO sys_dict_type VALUES(6, '系统是否', 'sys_yes_no', 1, 'admin', NOW(), '', NULL, '系统是否列表');

-- 初始化字典数据
INSERT INTO sys_dict_data VALUES(1, 1, '男', '1', 'sys_user_sex', '', '', 'Y', 1, 'admin', NOW(), '', NULL, '性别男');
INSERT INTO sys_dict_data VALUES(2, 2, '女', '2', 'sys_user_sex', '', '', 'N', 1, 'admin', NOW(), '', NULL, '性别女');
INSERT INTO sys_dict_data VALUES(3, 3, '未知', '0', 'sys_user_sex', '', '', 'N', 1, 'admin', NOW(), '', NULL, '性别未知');
INSERT INTO sys_dict_data VALUES(4, 1, '显示', '1', 'sys_show_hide', '', 'primary', 'Y', 1, 'admin', NOW(), '', NULL, '显示菜单');
INSERT INTO sys_dict_data VALUES(5, 2, '隐藏', '0', 'sys_show_hide', '', 'danger', 'N', 1, 'admin', NOW(), '', NULL, '隐藏菜单');
INSERT INTO sys_dict_data VALUES(6, 1, '正常', '1', 'sys_normal_disable', '', 'primary', 'Y', 1, 'admin', NOW(), '', NULL, '正常状态');
INSERT INTO sys_dict_data VALUES(7, 2, '停用', '0', 'sys_normal_disable', '', 'danger', 'N', 1, 'admin', NOW(), '', NULL, '停用状态');
INSERT INTO sys_dict_data VALUES(8, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 1, 'admin', NOW(), '', NULL, '系统默认是');
INSERT INTO sys_dict_data VALUES(9, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 1, 'admin', NOW(), '', NULL, '系统默认否');

-- 初始化检查项目数据
INSERT INTO exam_item VALUES(1, 'VA001', '视力检查', '基础检查', '检查患者远视力和近视力', 20.00, '次', '1.0以上', 1, 1, 0, 'admin', NOW(), '', NULL, '标准视力检查');
INSERT INTO exam_item VALUES(2, 'IOP001', '眼压检查', '基础检查', '测量眼内压力', 30.00, '次', '10-21mmHg', 2, 1, 0, 'admin', NOW(), '', NULL, '眼压测量');
INSERT INTO exam_item VALUES(3, 'REF001', '验光检查', '屈光检查', '检查屈光不正', 50.00, '次', '正视眼', 3, 1, 0, 'admin', NOW(), '', NULL, '电脑验光');
INSERT INTO exam_item VALUES(4, 'FUN001', '眼底检查', '深度检查', '检查视网膜、视神经等', 80.00, '次', '未见异常', 4, 1, 0, 'admin', NOW(), '', NULL, '散瞳眼底检查');
INSERT INTO exam_item VALUES(5, 'OCT001', 'OCT检查', '影像检查', '光学相干断层扫描', 150.00, '次', '结构正常', 5, 1, 0, 'admin', NOW(), '', NULL, 'OCT检查');
INSERT INTO exam_item VALUES(6, 'VF001', '视野检查', '功能检查', '检查视野范围', 100.00, '次', '视野正常', 6, 1, 0, 'admin', NOW(), '', NULL, '静态视野检查');

-- 创建索引
CREATE INDEX idx_sys_user_dept_id ON sys_user(dept_id);
CREATE INDEX idx_sys_user_username ON sys_user(username);
CREATE INDEX idx_sys_menu_parent_id ON sys_menu(parent_id);
CREATE INDEX idx_patient_info_code ON patient_info(patient_code);
CREATE INDEX idx_patient_info_phone ON patient_info(phone);
CREATE INDEX idx_exam_record_patient_id ON exam_record(patient_id);
CREATE INDEX idx_exam_record_doctor_id ON exam_record(doctor_id);
CREATE INDEX idx_exam_record_date ON exam_record(exam_date);
CREATE INDEX idx_exam_record_item_record_id ON exam_record_item(record_id);
CREATE INDEX idx_exam_record_item_item_id ON exam_record_item(item_id);
-- 报告相关索引（合并至 exam_record）
CREATE INDEX idx_exam_record_report_no ON exam_record(report_no);
CREATE INDEX idx_exam_record_report_date ON exam_record(report_date);
CREATE INDEX idx_exam_record_report_status ON exam_record(report_status);

-- 创建外键约束
ALTER TABLE sys_user ADD CONSTRAINT fk_sys_user_dept_id FOREIGN KEY (dept_id) REFERENCES sys_dept(dept_id);
ALTER TABLE sys_user_role ADD CONSTRAINT fk_sys_user_role_user_id FOREIGN KEY (user_id) REFERENCES sys_user(user_id);
ALTER TABLE sys_user_role ADD CONSTRAINT fk_sys_user_role_role_id FOREIGN KEY (role_id) REFERENCES sys_role(role_id);
ALTER TABLE sys_role_menu ADD CONSTRAINT fk_sys_role_menu_role_id FOREIGN KEY (role_id) REFERENCES sys_role(role_id);
ALTER TABLE sys_role_menu ADD CONSTRAINT fk_sys_role_menu_menu_id FOREIGN KEY (menu_id) REFERENCES sys_menu(menu_id);
ALTER TABLE sys_role_dept ADD CONSTRAINT fk_sys_role_dept_role_id FOREIGN KEY (role_id) REFERENCES sys_role(role_id);
ALTER TABLE sys_role_dept ADD CONSTRAINT fk_sys_role_dept_dept_id FOREIGN KEY (dept_id) REFERENCES sys_dept(dept_id);
ALTER TABLE exam_record ADD CONSTRAINT fk_exam_record_patient_id FOREIGN KEY (patient_id) REFERENCES patient_info(patient_id);
ALTER TABLE exam_record ADD CONSTRAINT fk_exam_record_doctor_id FOREIGN KEY (doctor_id) REFERENCES sys_user(user_id);
ALTER TABLE exam_record_item ADD CONSTRAINT fk_exam_record_item_record_id FOREIGN KEY (record_id) REFERENCES exam_record(record_id);
ALTER TABLE exam_record_item ADD CONSTRAINT fk_exam_record_item_item_id FOREIGN KEY (item_id) REFERENCES exam_item(item_id);
-- 删除 exam_report 外键（因表已合并、删除）

-- 初始化完成提示
SELECT '数据库初始化完成！' AS message;
SELECT '默认管理员账号：admin，密码：123456' AS admin_info;
SELECT '数据库名称：eye_examination' AS database_info;
SELECT '总共创建表数量：' AS table_count_info, COUNT(*) AS count FROM information_schema.tables WHERE table_schema = 'eye_examination'; 