<template>
  <div class="help-container">
    <div class="help-header">
      <h1 class="help-title">帮助说明</h1>
      <p class="help-desc">眼科检查系统使用指南</p>
    </div>

    <div class="help-content">
      <el-card class="help-card">
        <template #header>
          <div class="card-header">
            <el-icon><QuestionFilled /></el-icon>
            <span>系统简介</span>
          </div>
        </template>
        <p>
          眼科检查系统是一个专业的医疗检查工具平台，目前主要提供SVV（主观垂直视觉）检查功能。
          系统采用现代化的Web界面结合专业的桌面检查工具，为医护人员提供便捷、准确的检查体验。
        </p>
      </el-card>

      <el-card class="help-card">
        <template #header>
          <div class="card-header">
            <el-icon><View /></el-icon>
            <span>SVV检查工具</span>
          </div>
        </template>
        <div class="svv-help">
          <h4>功能介绍</h4>
          <p>SVV检查工具用于评估患者的主观垂直视觉感知能力，是重要的神经学检查工具。</p>
          
          <h4>适用场景</h4>
          <ul>
            <li>前庭系统功能评估</li>
            <li>脑干病变诊断</li>
            <li>眩晕病因分析</li>
            <li>平衡障碍检查</li>
          </ul>

          <h4>操作说明</h4>
          <ol>
            <li>在"眼部检查"页面点击"开始SVV检查"</li>
            <li>确认启动对话框，工具将自动启动</li>
            <li>使用快捷键或鼠标调整线条角度</li>
            <li>多次测量并保存结果</li>
            <li>填写患者信息并导出报告</li>
          </ol>
        </div>
      </el-card>

      <el-card class="help-card">
        <template #header>
          <div class="card-header">
            <el-icon><User /></el-icon>
            <span>患者管理</span>
          </div>
        </template>
        <div class="patient-help">
          <h4>添加患者</h4>
          <p>在患者管理页面可以录入新患者信息，包括基本信息、联系方式等。</p>
          
          <h4>查看患者列表</h4>
          <p>支持按姓名、MRN等条件搜索患者，方便快速定位。</p>
        </div>
      </el-card>

      <el-card class="help-card">
        <template #header>
          <div class="card-header">
            <el-icon><Setting /></el-icon>
            <span>系统要求</span>
          </div>
        </template>
        <div class="system-requirements">
          <h4>硬件要求</h4>
          <ul>
            <li>处理器：Intel Core i3 或同等性能</li>
            <li>内存：4GB RAM 或更高</li>
            <li>显示器：1024×768 分辨率或更高</li>
            <li>网络：稳定的局域网连接</li>
          </ul>

          <h4>软件要求</h4>
          <ul>
            <li>操作系统：Windows 10/11, macOS 10.14+, Linux</li>
            <li>Java运行环境：JRE 8 或更高版本</li>
            <li>浏览器：Chrome 80+, Firefox 75+, Safari 13+</li>
          </ul>
        </div>
      </el-card>

      <el-card class="help-card">
        <template #header>
          <div class="card-header">
            <el-icon><Phone /></el-icon>
            <span>技术支持</span>
          </div>
        </template>
        <div class="support-info">
          <p>如果您在使用过程中遇到任何问题，请联系技术支持团队：</p>
          <ul>
            <li>技术支持邮箱：support@eyeexam.com</li>
            <li>服务热线：400-123-4567</li>
            <li>在线客服：工作日 9:00-18:00</li>
          </ul>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { QuestionFilled, View, User, Setting, Phone } from '@element-plus/icons-vue'
</script>

<style scoped>
.help-container {
  min-height: calc(100vh - 110px);
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.help-header {
  text-align: center;
  margin-bottom: 40px;
}

.help-title {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  margin-bottom: 10px;
}

.help-desc {
  font-size: 16px;
  color: #666;
  margin: 0;
}

.help-content {
  max-width: 800px;
  margin: 0 auto;
}

.help-card {
  margin-bottom: 20px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.card-header .el-icon {
  font-size: 20px;
  color: #409eff;
}

.help-card :deep(.el-card__body) {
  padding: 24px;
  line-height: 1.6;
  color: #555;
}

.help-card h4 {
  color: #333;
  margin-bottom: 10px;
  margin-top: 20px;
  font-size: 16px;
  font-weight: 600;
}

.help-card h4:first-child {
  margin-top: 0;
}

.help-card p {
  margin-bottom: 15px;
}

.help-card ul,
.help-card ol {
  margin-bottom: 15px;
  padding-left: 20px;
}

.help-card li {
  margin-bottom: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .help-container {
    padding: 15px;
  }
  
  .help-title {
    font-size: 24px;
  }
  
  .help-card :deep(.el-card__body) {
    padding: 20px;
  }
}
</style>