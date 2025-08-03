package com.eyeexam.utils;

import javax.swing.*;
import java.awt.Font;

/**
 * SVV检查工具主入口类
 * 主观垂直视觉检查工具
 */
public class SVVChecker {
    
    public static void main(String[] args) {
        // 设置中文字体
        setUIFont();
        
        // 启动应用
        SwingUtilities.invokeLater(() -> {
            SVVMainFrame frame = new SVVMainFrame();
            frame.setVisible(true);
            frame.requestFocus(); // 确保窗口获取焦点以接收键盘事件
        });
    }
    
    /**
     * 设置UI字体为中文字体
     */
    private static void setUIFont() {
        Font font = new Font("微软雅黑", Font.PLAIN, 12);
        
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("Dialog.font", font);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
    }
    
    /**
     * 创建并显示SVV检查窗口（供其他模块调用）
     */
    public static void showSVVChecker() {
        // 设置中文字体
        setUIFont();
        
        SwingUtilities.invokeLater(() -> {
            SVVMainFrame frame = new SVVMainFrame();
            frame.setVisible(true);
            frame.requestFocus();
        });
    }
}