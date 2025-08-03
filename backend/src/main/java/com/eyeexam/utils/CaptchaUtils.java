package com.eyeexam.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码生成工具类
 */
public class CaptchaUtils {
    
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    
    /**
     * 生成验证码图片
     * @return CaptchaResult 包含验证码文本和图片字节数组
     */
    public static CaptchaResult generateCaptcha() {
        // 创建图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        // 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Random random = new Random();
        
        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // 生成验证码文本
        StringBuilder captchaText = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = CHARS.charAt(random.nextInt(CHARS.length()));
            captchaText.append(c);
            
            // 设置随机颜色
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            
            // 设置字体
            Font font = new Font("Arial", Font.BOLD, 20 + random.nextInt(10));
            g.setFont(font);
            
            // 设置字符位置（添加随机偏移）
            int x = 15 + i * 18 + random.nextInt(5);
            int y = 25 + random.nextInt(10);
            
            // 绘制字符
            g.drawString(String.valueOf(c), x, y);
        }
        
        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
        
        // 添加干扰点
        for (int i = 0; i < 50; i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g.fillOval(x, y, 1, 1);
        }
        
        g.dispose();
        
        // 将图片转为字节数组
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            baos.close();
            
            return new CaptchaResult(captchaText.toString(), imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("生成验证码图片失败", e);
        }
    }
    
    /**
     * 验证码结果类
     */
    public static class CaptchaResult {
        private final String text;
        private final byte[] imageBytes;
        
        public CaptchaResult(String text, byte[] imageBytes) {
            this.text = text;
            this.imageBytes = imageBytes;
        }
        
        public String getText() {
            return text;
        }
        
        public byte[] getImageBytes() {
            return imageBytes;
        }
    }
} 