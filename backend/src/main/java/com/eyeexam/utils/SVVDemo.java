package com.eyeexam.utils;

/**
 * SVV检查工具演示类
 * 展示如何使用SVV检查工具
 */
public class SVVDemo {
    
    public static void main(String[] args) {
        // 直接启动SVV检查器
        SVVChecker.main(args);
    }
    
    /**
     * 在Web应用中集成SVV检查器的示例方法
     */
    public static void launchSVVFromWeb() {
        // 可以从Spring Controller调用此方法来启动SVV检查器
        SVVChecker.showSVVChecker();
    }
}