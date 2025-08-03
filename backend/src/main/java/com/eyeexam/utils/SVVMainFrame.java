package com.eyeexam.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SVV检查主窗口
 * 提供完整的SVV检查界面和功能
 */
public class SVVMainFrame extends JFrame implements KeyListener {
    
    private SVVWidget svvWidget;
    private JLabel infoLabel;
    private List<AngleRecord> savedAngles;
    
    // 角度记录类
    private static class AngleRecord {
        final double horizontalAngle;
        final double verticalAngle;
        
        AngleRecord(double h, double v) {
            this.horizontalAngle = h;
            this.verticalAngle = v;
        }
    }
    
    public SVVMainFrame() {
        super("SVV Checker - Java Swing 高分辨率版");
        
        savedAngles = new ArrayList<>();
        initComponents();
        setupLayout();
        setupEvents();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);
        
        // 设置窗口获取焦点以捕获键盘事件
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void initComponents() {
        // 创建SVV绘图组件
        svvWidget = new SVVWidget();
        svvWidget.setAngleUpdateCallback(this::updateInfoLabel);
        
        // 创建顶部信息标签
        infoLabel = new JLabel("X 轴夹角: 0.0°    Y 轴夹角: 0.0°");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        infoLabel.setPreferredSize(new Dimension(0, 30));
        infoLabel.setBorder(BorderFactory.createEtchedBorder());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部信息面板
        add(infoLabel, BorderLayout.NORTH);
        
        // 中间绘图区域
        add(svvWidget, BorderLayout.CENTER);
        
        // 底部按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 初始更新信息标签
        updateInfoLabel();
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // 创建按钮
        JButton btnCW = new JButton("▶ 顺时针旋转");
        JButton btnCCW = new JButton("◀ 逆时针旋转");
        JButton btnFineCW = new JButton("✦ 顺时针微调");
        JButton btnFineCCW = new JButton("✦ 逆时针微调");
        JButton btnStop = new JButton("■ 停止旋转");
        JButton btnToggleCircle = new JButton("◎ 显示/隐藏参考盘");
        JButton btnSave = new JButton("保存结果");
        JButton btnExport = new JButton("输出结果");
        JButton btnNewPatient = new JButton("新病人");
        
        // 添加按钮事件
        btnCW.addActionListener(e -> svvWidget.toggleAutoCW());
        btnCCW.addActionListener(e -> svvWidget.toggleAutoCCW());
        btnFineCW.addActionListener(e -> svvWidget.rotateBy(svvWidget.getFineSpeed()));
        btnFineCCW.addActionListener(e -> svvWidget.rotateBy(-svvWidget.getFineSpeed()));
        btnStop.addActionListener(e -> svvWidget.stopAutoRotate());
        btnToggleCircle.addActionListener(e -> {
            svvWidget.toggleCircle();
            updateInfoLabel();
        });
        btnSave.addActionListener(e -> saveResult());
        btnExport.addActionListener(e -> exportResult());
        btnNewPatient.addActionListener(e -> newPatient());
        
        // 添加到面板
        panel.add(btnCW);
        panel.add(btnCCW);
        panel.add(btnFineCW);
        panel.add(btnFineCCW);
        panel.add(btnStop);
        panel.add(btnToggleCircle);
        panel.add(btnSave);
        panel.add(btnExport);
        panel.add(btnNewPatient);
        
        return panel;
    }
    
    private void setupEvents() {
        // 窗口关闭时清理资源
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                svvWidget.stopAutoRotate();
                System.exit(0);
            }
        });
    }
    
    /**
     * 更新信息标签显示
     */
    private void updateInfoLabel() {
        double ah = svvWidget.getAngleWithHorizontal();
        double av = svvWidget.getAngleWithVertical();
        infoLabel.setText(String.format("X 轴夹角: %.1f°    Y 轴夹角: %.1f°", ah, av));
    }
    
    /**
     * 保存当前结果
     */
    private void saveResult() {
        double ah = svvWidget.getAngleWithHorizontal();
        double av = svvWidget.getAngleWithVertical();
        savedAngles.add(new AngleRecord(ah, av));
        
        JOptionPane.showMessageDialog(this,
            String.format("已保存：X 轴 %.1f°，Y 轴 %.1f°", ah, av),
            "已保存",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 导出结果到文件
     */
    private void exportResult() {
        if (savedAngles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "未发现任何已保存的角度数据，请先点击\"保存结果\"。",
                "没有保存数据",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PatientInfoDialog dialog = new PatientInfoDialog(this);
        dialog.setVisible(true);
        
        if (!dialog.isConfirmed()) {
            return;
        }
        
        String name = dialog.getPatientName();
        String examDate = dialog.getExamDate();
        String mrn = dialog.getMRN();
        
        // 生成安全的文件名
        String safeName = name.replaceAll("[\\\\/:*?\"<>|]", "");
        String safeDate = examDate.replaceAll("[\\\\/:*?\"<>|]", "");
        String filename = safeName + "_" + safeDate + ".txt";
        
        // 获取桌面路径
        String desktop = System.getProperty("user.home") + File.separator + "Desktop";
        String fullPath = desktop + File.separator + filename;
        
        try {
            writeResultToFile(fullPath, name, examDate, mrn);
            JOptionPane.showMessageDialog(this,
                "已将结果保存到文件：\n" + fullPath,
                "导出成功",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "写入文件时出错：\n" + e.getMessage(),
                "导出失败",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 写入结果到文件
     */
    private void writeResultToFile(String filePath, String name, String examDate, String mrn) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("姓名: " + name + "\n");
            writer.write("检查日期: " + examDate + "\n");
            writer.write("MRN: " + mrn + "\n\n");
            writer.write("已保存的角度数据 (X 轴, Y 轴, 单位: 度)：\n");
            
            for (int i = 0; i < savedAngles.size(); i++) {
                AngleRecord record = savedAngles.get(i);
                writer.write(String.format("%d. X: %.1f°, Y: %.1f°\n", 
                    i + 1, record.horizontalAngle, record.verticalAngle));
            }
        }
    }
    
    /**
     * 新病人：清空数据
     */
    private void newPatient() {
        savedAngles.clear();
        svvWidget.reset();
        updateInfoLabel();
        JOptionPane.showMessageDialog(this,
            "已清除所有已保存的夹角数据。",
            "新病人",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // 键盘事件处理
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:   // ← 顺时针自动旋转
                svvWidget.toggleAutoCW();
                break;
            case KeyEvent.VK_RIGHT:  // → 逆时针自动旋转
                svvWidget.toggleAutoCCW();
                break;
            case KeyEvent.VK_UP:     // ↑ 保存结果
                saveResult();
                break;
            case KeyEvent.VK_DOWN:   // ↓ 停止旋转
                svvWidget.stopAutoRotate();
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
}