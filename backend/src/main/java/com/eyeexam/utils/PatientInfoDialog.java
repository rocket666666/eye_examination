package com.eyeexam.utils;

import javax.swing.*;
import java.awt.*;

/**
 * 患者信息输入对话框
 * 用于输入患者姓名、检查日期、MRN等信息
 */
public class PatientInfoDialog extends JDialog {
    
    private JTextField nameField;
    private JTextField dateField;
    private JTextField mrnField;
    private boolean confirmed = false;
    
    public PatientInfoDialog(JFrame parent) {
        super(parent, "填写患者信息", true);
        initComponents();
        setupLayout();
        
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 12);
        
        nameField = new JTextField(20);
        nameField.setFont(font);
        
        dateField = new JTextField(20);
        dateField.setFont(font);
        
        mrnField = new JTextField(20);
        mrnField.setFont(font);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 12);
        
        // 姓名
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("姓名：");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        // 检查日期
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel dateLabel = new JLabel("检查日期：");
        dateLabel.setFont(labelFont);
        formPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);
        
        // MRN
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel mrnLabel = new JLabel("MRN：");
        mrnLabel.setFont(labelFont);
        formPanel.add(mrnLabel, gbc);
        
        gbc.gridx = 1;
        formPanel.add(mrnField, gbc);
        
        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
        
        okButton.setFont(labelFont);
        cancelButton.setFont(labelFont);
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        // 添加到主面板
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 设置按钮事件
        okButton.addActionListener(e -> {
            if (validateInputs()) {
                confirmed = true;
                dispose();
            }
        });
        
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        // 回车键确认
        nameField.addActionListener(e -> {
            if (validateInputs()) {
                confirmed = true;
                dispose();
            }
        });
        
        dateField.addActionListener(e -> {
            if (validateInputs()) {
                confirmed = true;
                dispose();
            }
        });
        
        mrnField.addActionListener(e -> {
            if (validateInputs()) {
                confirmed = true;
                dispose();
            }
        });
    }
    
    private boolean validateInputs() {
        String name = nameField.getText().trim();
        String date = dateField.getText().trim();
        String mrn = mrnField.getText().trim();
        
        if (name.isEmpty() || date.isEmpty() || mrn.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "请填写 姓名、检查日期 和 MRN。", 
                "输入不完整", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getPatientName() {
        return nameField.getText().trim();
    }
    
    public String getExamDate() {
        return dateField.getText().trim();
    }
    
    public String getMRN() {
        return mrnField.getText().trim();
    }
}