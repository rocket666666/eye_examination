package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.utils.SVVChecker;
import org.springframework.web.bind.annotation.*;

/**
 * SVV检查工具控制器
 */
@RestController
@RequestMapping("/api/svv")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SVVController {
    
    /**
     * 启动SVV检查工具
     */
    @PostMapping("/launch")
    public Result<String> launchSVVChecker() {
        try {
            // 在新线程中启动SVV检查工具，避免阻塞Web请求
            Thread svvThread = new Thread(() -> {
                try {
                    SVVChecker.showSVVChecker();
                } catch (Exception e) {
                    System.err.println("启动SVV检查工具失败: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            svvThread.setDaemon(true); // 设置为守护线程
            svvThread.setName("SVV-Checker-Thread");
            svvThread.start();
            
            return Result.success("SVV检查工具启动成功");
            
        } catch (Exception e) {
            return Result.error("启动SVV检查工具失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查SVV工具运行状态
     */
    @GetMapping("/status")
    public Result<String> checkSVVStatus() {
        try {
            // 检查是否有SVV相关的线程在运行
            ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
            ThreadGroup parent;
            while ((parent = rootGroup.getParent()) != null) {
                rootGroup = parent;
            }
            
            Thread[] threads = new Thread[rootGroup.activeCount()];
            rootGroup.enumerate(threads);
            
            boolean svvRunning = false;
            for (Thread thread : threads) {
                if (thread != null && thread.getName().contains("SVV")) {
                    svvRunning = true;
                    break;
                }
            }
            
            if (svvRunning) {
                return Result.success("SVV检查工具正在运行");
            } else {
                return Result.success("SVV检查工具未运行");
            }
            
        } catch (Exception e) {
            return Result.error("检查SVV工具状态失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取SVV工具信息
     */
    @GetMapping("/info")
    public Result<SVVToolInfo> getSVVInfo() {
        try {
            SVVToolInfo info = new SVVToolInfo();
            info.setName("SVV检查工具");
            info.setVersion("1.0.0");
            info.setDescription("主观垂直视觉检查工具，用于前庭系统功能评估和脑干病变诊断");
            info.setFeatures(new String[]{
                "高精度角度测量(±0.1°)",
                "3D圆柱管样式线条",
                "多种控制方式(键盘+鼠标)",
                "自动旋转和微调功能",
                "患者信息管理",
                "检查结果导出"
            });
            info.setSystemRequirements(new String[]{
                "Java Runtime Environment 8+",
                "Windows 10/11, macOS 10.14+, Linux",
                "1024×768 分辨率或更高",
                "4GB RAM 或更高"
            });
            
            return Result.success(info);
            
        } catch (Exception e) {
            return Result.error("获取SVV工具信息失败: " + e.getMessage());
        }
    }
    
    /**
     * SVV工具信息类
     */
    public static class SVVToolInfo {
        private String name;
        private String version;
        private String description;
        private String[] features;
        private String[] systemRequirements;
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String[] getFeatures() { return features; }
        public void setFeatures(String[] features) { this.features = features; }
        
        public String[] getSystemRequirements() { return systemRequirements; }
        public void setSystemRequirements(String[] systemRequirements) { this.systemRequirements = systemRequirements; }
    }
}