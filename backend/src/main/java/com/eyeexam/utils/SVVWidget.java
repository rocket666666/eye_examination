package com.eyeexam.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SVV检查绘图组件
 * 用于绘制 SVV（Subjective Visual Vertical）检查的自定义 JPanel：
 * - 保留原始刻度（时针式短刻度）
 * - 抗锯齿 + 3D 圆柱管样式线条（穿过圆心、双向延长）
 * - 支持鼠标滚轮微调 ±0.1°
 * - 支持自动旋转和手动微调
 */
public class SVVWidget extends JPanel {
    
    // 当前角度（0°~360° 周期）
    private double angle = 0.0;
    
    // 双击后固定，不再旋转
    private boolean fixed = false;
    
    // 自动旋转状态与方向
    private boolean autoRotate = false;
    private String rotateDirection = null; // "CW" 或 "CCW"
    
    // 速度设置
    private final double baseSpeed = 50.0;   // 50°/秒
    private final double fineSpeed = 0.1;    // 0.1°/步
    
    // 定时器：用于匀速自动旋转
    private Timer rotationTimer;
    private final int timerInterval = 10;    // 毫秒
    
    // 是否显示参考圆盘（刻度 + 圆环）
    private boolean showCircle = true;
    
    // 用于通知主窗口更新角度显示
    private Runnable angleUpdateCallback;
    
    public SVVWidget() {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.WHITE);
        
        // 设置焦点策略，不接收键盘焦点
        setFocusable(false);
        
        // 添加鼠标事件监听
        addMouseWheelListener(this::handleMouseWheel);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && !fixed) {
                    rotateBy(0.001); // 极微调
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    fixed = true;
                    stopAutoRotate();
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        // 开启抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        int w = getWidth();
        int h = getHeight();
        double cx = w / 2.0;
        double cy = h / 2.0;
        double radius = Math.min(w, h) * 0.4;
        
        // 绘制参考圆盘与刻度
        if (showCircle) {
            drawReferenceCircle(g2d, cx, cy, radius);
        }
        
        // 绘制 3D 圆柱管风格的线段
        draw3DLine(g2d, cx, cy, radius);
        
        g2d.dispose();
    }
    
    /**
     * 绘制参考圆盘和刻度
     */
    private void drawReferenceCircle(Graphics2D g2d, double cx, double cy, double radius) {
        // 绘制外圆
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.draw(new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2));
        
        // 绘制刻度
        for (int deg = 0; deg < 360; deg++) {
            double rad = Math.toRadians(deg);
            double x1 = cx + radius * Math.cos(rad);
            double y1 = cy + radius * Math.sin(rad);
            double inner = radius - 30;
            double x2 = cx + inner * Math.cos(rad);
            double y2 = cy + inner * Math.sin(rad);
            
            if (deg % 2 == 0) {
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(2.0f));
            } else {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1.0f));
            }
            
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }
    
    /**
     * 绘制3D圆柱管风格的线段
     */
    private void draw3DLine(Graphics2D g2d, double cx, double cy, double radius) {
        double angleRad = Math.toRadians(angle);
        
        double xEnd1 = cx + radius * Math.cos(angleRad);
        double yEnd1 = cy + radius * Math.sin(angleRad);
        double xEnd2 = cx - radius * Math.cos(angleRad);
        double yEnd2 = cy - radius * Math.sin(angleRad);
        
        // 创建渐变色
        GradientPaint gradient = new GradientPaint(
            (float) xEnd1, (float) yEnd1, new Color(200, 200, 200),
            (float) xEnd2, (float) yEnd2, new Color(80, 80, 80)
        );
        
        g2d.setPaint(gradient);
        g2d.setStroke(new BasicStroke(20.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(new Line2D.Double(xEnd1, yEnd1, xEnd2, yEnd2));
    }
    
    /**
     * 处理鼠标滚轮事件
     */
    private void handleMouseWheel(MouseWheelEvent e) {
        if (fixed) return;
        
        double delta = -e.getWheelRotation(); // 反转滚轮方向
        if (delta > 0) {
            rotateBy(fineSpeed);  // 顺时针
        } else if (delta < 0) {
            rotateBy(-fineSpeed); // 逆时针
        }
    }
    
    /**
     * 切换顺时针自动旋转
     */
    public void toggleAutoCW() {
        if (fixed) return;
        
        if (autoRotate && "CW".equals(rotateDirection)) {
            stopAutoRotate();
        } else {
            startAutoRotate("CW");
        }
    }
    
    /**
     * 切换逆时针自动旋转
     */
    public void toggleAutoCCW() {
        if (fixed) return;
        
        if (autoRotate && "CCW".equals(rotateDirection)) {
            stopAutoRotate();
        } else {
            startAutoRotate("CCW");
        }
    }
    
    /**
     * 开始自动旋转
     */
    public void startAutoRotate(String direction) {
        stopAutoRotate(); // 先停止之前的旋转
        
        autoRotate = true;
        rotateDirection = direction;
        
        rotationTimer = new Timer();
        rotationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (fixed) {
                    stopAutoRotate();
                    return;
                }
                
                double deltaDeg = baseSpeed * (timerInterval / 1000.0);
                if ("CW".equals(rotateDirection)) {
                    rotateBy(deltaDeg);
                } else {
                    rotateBy(-deltaDeg);
                }
            }
        }, 0, timerInterval);
    }
    
    /**
     * 停止自动旋转
     */
    public void stopAutoRotate() {
        autoRotate = false;
        rotateDirection = null;
        
        if (rotationTimer != null) {
            rotationTimer.cancel();
            rotationTimer = null;
        }
    }
    
    /**
     * 按指定角度旋转
     */
    public void rotateBy(double delta) {
        if (fixed) return;
        
        angle = (angle + delta) % 360;
        if (angle < 0) angle += 360;
        
        SwingUtilities.invokeLater(() -> {
            repaint();
            if (angleUpdateCallback != null) {
                angleUpdateCallback.run();
            }
        });
    }
    
    /**
     * 获取与水平轴的夹角
     */
    public double getAngleWithHorizontal() {
        double rawH = Math.abs(angle % 180);
        return Math.min(rawH, 180 - rawH);
    }
    
    /**
     * 获取与垂直轴的夹角
     */
    public double getAngleWithVertical() {
        double rawV = Math.abs((angle - 90) % 180);
        return Math.min(rawV, 180 - rawV);
    }
    
    /**
     * 切换参考圆盘显示
     */
    public void toggleCircle() {
        showCircle = !showCircle;
        repaint();
    }
    
    // Getters and Setters
    public double getAngle() { return angle; }
    public boolean isFixed() { return fixed; }
    public boolean isAutoRotate() { return autoRotate; }
    public String getRotateDirection() { return rotateDirection; }
    public double getFineSpeed() { return fineSpeed; }
    public boolean isShowCircle() { return showCircle; }
    
    public void setAngleUpdateCallback(Runnable callback) {
        this.angleUpdateCallback = callback;
    }
    
    /**
     * 重置状态（用于新病人）
     */
    public void reset() {
        stopAutoRotate();
        fixed = false;
        angle = 0.0;
        showCircle = true;
        repaint();
    }
}