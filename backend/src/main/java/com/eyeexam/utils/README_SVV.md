# SVV检查工具 (Java Swing版本)

## 简介

SVV (Subjective Visual Vertical) 检查工具是一个专业的医疗检查软件，用于评估患者的主观垂直视觉感知能力。该工具主要用于：

- 前庭系统功能评估
- 脑干病变诊断  
- 眩晕病因分析
- 平衡障碍检查

## 功能特性

### 1. SVV检查界面 (`SVVWidget`)
- ✅ **图形绘制**：带刻度的参考圆盘 + 3D渐变线条
- ✅ **角度调节**：自动旋转(50°/秒) + 精细微调(±0.1°) + 极微调(0.001°)
- ✅ **抗锯齿渲染**：Graphics2D高质量渲染
- ✅ **双击固定**：锁定线条防止意外操作

### 2. 主操作界面 (`SVVMainFrame`)
- ✅ **实时角度显示**：顶部显示X/Y轴夹角
- ✅ **多种控制方式**：
  - 按钮控制：旋转、微调、停止等
  - 快捷键：`←/→` 旋转，`↑` 保存，`↓` 停止
  - 鼠标滚轮：±0.1°微调

### 3. 数据管理
- ✅ **保存结果**：记录当前角度测量值
- ✅ **患者信息**：输入姓名、日期、MRN
- ✅ **导出功能**：保存到桌面TXT文件
- ✅ **新病人模式**：清空数据重新开始

## 快捷键操作

```
← : 顺时针自动旋转
→ : 逆时针自动旋转  
↑ : 保存当前结果
↓ : 停止旋转
滚轮: ±0.1°微调
双击: 固定线条
```

## 使用方法

### 1. 独立运行
```bash
# 编译
cd backend/src/main/java
javac -cp . com/eyeexam/utils/*.java

# 运行
java -cp . com.eyeexam.utils.SVVChecker
```

### 2. 集成到现有项目
```java
// 在Spring Controller中调用
@GetMapping("/svv-checker")
public void launchSVVChecker() {
    SVVChecker.showSVVChecker();
}
```

### 3. 演示运行
```bash
java -cp . com.eyeexam.utils.SVVDemo
```

## 文件结构

```
backend/src/main/java/com/eyeexam/utils/
├── SVVWidget.java           # 核心绘图组件
├── SVVMainFrame.java        # 主窗口界面
├── PatientInfoDialog.java   # 患者信息对话框
├── SVVChecker.java         # 主入口类
├── SVVDemo.java            # 演示类
└── README_SVV.md           # 说明文档
```

## 医学应用

**SVV检查**是评估患者**垂直感知能力**的重要神经学测试：

1. **正常范围**：健康人的SVV偏差通常在±2°以内
2. **病理性偏差**：
   - 前庭系统疾病：可能出现显著偏差
   - 脑干病变：SVV倾斜可能持续存在
   - 急性前庭综合征：初期可有明显偏差

## 技术特点

- **Java Swing框架**：提供原生桌面应用体验
- **高精度测量**：支持0.1°精度的角度调节  
- **3D视觉效果**：渐变色圆柱管样式增强视觉体验
- **数据持久化**：自动生成包含患者信息的检查报告
- **跨平台支持**：Windows、macOS、Linux

## 注意事项

1. 确保Java运行环境支持Swing GUI
2. 如果字体显示异常，请确保系统安装了中文字体
3. 检查结果文件会保存到用户桌面
4. 建议在安静的环境中进行检查以确保结果准确性

## 开发者信息

- 基于Python PyQt5版本移植到Java Swing
- 遵循现有项目的代码规范和架构风格
- 支持与Spring Boot后端集成