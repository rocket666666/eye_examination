<template>
  <div class="svh-check-page">
    <!-- 页面头部 -->
    <div class="page-header">
      <el-button type="info" @click="showInstructions">
          <el-icon><QuestionFilled /></el-icon>
          使用说明
        </el-button>
      
      <div class="header-content">
        <h1 class="page-title">SVH检查工具</h1>
        <p class="page-subtitle">主观水平视觉检查 - 在线版本</p>
      </div>
      
      <div class="header-actions">
        <el-button 
        type="text" 
        @click="goBack"
        class="back-button"
      >
        <el-icon><ArrowLeft /></el-icon>
        返回检查页面
      </el-button>
      </div>
    </div>

    <!-- SVH检查组件 -->
    <SVHCanvas />

    <!-- 使用说明对话框 -->
    <el-dialog 
      v-model="instructionsVisible" 
      title="SVH检查工具使用说明" 
      width="600px"
      :show-close="true"
    >
      <div class="instructions-content">
        <div class="instruction-section">
          <h4>什么是SVH检查？</h4>
          <p>SVH（主观水平视觉）检查是评估患者水平视觉感知能力的重要神经学测试，主要用于前庭系统功能评估和脑干病变诊断，与SVV检查形成互补。</p>
        </div>
        
        <div class="instruction-section">
          <h4>操作方法：</h4>
          <div class="operation-grid">
            <div class="operation-item">
              <div class="operation-icon">🖱️</div>
              <div class="operation-text">
                <strong>鼠标操作</strong><br>
                单击：极微调 (+0.001°)<br>
                双击：固定线条<br>
                滚轮：精细微调 (±0.1°)
              </div>
            </div>
            
            <div class="operation-item">
              <div class="operation-icon">⌨️</div>
              <div class="operation-text">
                <strong>键盘快捷键</strong><br>
                ← → : 自动旋转控制<br>
                ↑ : 保存当前结果<br>
                ↓ : 停止旋转
              </div>
            </div>
            
            <div class="operation-item">
              <div class="operation-icon">🎮</div>
              <div class="operation-text">
                <strong>按钮控制</strong><br>
                精确控制旋转方向<br>
                保存和导出结果<br>
                新病人模式
              </div>
            </div>
          </div>
        </div>

        <div class="instruction-section">
          <h4>检查流程：</h4>
          <ol class="procedure-list">
            <li>患者坐在显示器前，保持头部稳定</li>
            <li>使用控制按钮或快捷键调整线条角度</li>
            <li>让患者调整线条到认为的水平位置</li>
            <li>点击"保存结果"记录当前角度</li>
            <li>重复多次测量以提高准确性</li>
            <li>完成后填写患者信息并导出报告</li>
          </ol>
        </div>

        <div class="instruction-section">
          <h4>参考标准：</h4>
          <div class="reference-standards">
            <div class="standard-item normal">
              <span class="standard-label">正常范围：</span>
              <span class="standard-value">±2°以内</span>
            </div>
            <div class="standard-item mild">
              <span class="standard-label">轻度异常：</span>
              <span class="standard-value">±2° ~ ±5°</span>
            </div>
            <div class="standard-item severe">
              <span class="standard-label">显著异常：</span>
              <span class="standard-value">> ±5°</span>
            </div>
          </div>
        </div>

        <div class="instruction-section">
          <h4>SVH vs SVV 区别：</h4>
          <div class="comparison-table">
            <div class="comparison-row">
              <div class="comparison-label">检查方向</div>
              <div class="comparison-svh">水平视觉感知</div>
              <div class="comparison-svv">垂直视觉感知</div>
            </div>
            <div class="comparison-row">
              <div class="comparison-label">主要用途</div>
              <div class="comparison-svh">水平平衡评估</div>
              <div class="comparison-svv">垂直平衡评估</div>
            </div>
            <div class="comparison-row">
              <div class="comparison-label">病变定位</div>
              <div class="comparison-svh">侧向病变</div>
              <div class="comparison-svv">前后病变</div>
            </div>
            <div class="comparison-row">
              <div class="comparison-label">线条颜色</div>
              <div class="comparison-svh">蓝色系</div>
              <div class="comparison-svv">灰色系</div>
            </div>
          </div>
        </div>

        <div class="instruction-section">
          <h4>注意事项：</h4>
          <ul class="notice-list">
            <li>确保检查环境安静，避免干扰</li>
            <li>患者应保持头部稳定，不要倾斜</li>
            <li>建议进行多次测量取平均值</li>
            <li>如有眩晕症状，请适当休息</li>
            <li>SVH和SVV检查可结合使用，提供更全面的前庭功能评估</li>
          </ul>
        </div>
      </div>
      
      <template #footer>
        <el-button type="primary" @click="instructionsVisible = false">
          开始检查
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowLeft, QuestionFilled } from '@element-plus/icons-vue'
import SVHCanvas from '@/components/SVHCanvas.vue'

const router = useRouter()
const instructionsVisible = ref(false)

const goBack = () => {
  router.push('/examination')
}

const showInstructions = () => {
  instructionsVisible.value = true
}
</script>

<style scoped>
.svh-check-page {
  min-height: 100vh;
  background: #f0f8ff;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 30px;
  background: white;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.back-button {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #409eff;
}

.header-content {
  text-align: center;
  flex: 1;
}

.page-title {
  font-size: 28px;
  font-weight: 700;
  color: #333;
  margin: 0 0 5px 0;
}

.page-subtitle {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.header-actions {
  min-width: 120px;
  display: flex;
  justify-content: flex-end;
}

/* 使用说明样式 */
.instructions-content {
  max-height: 70vh;
  overflow-y: auto;
  padding-right: 10px;
}

.instruction-section {
  margin-bottom: 25px;
}

.instruction-section h4 {
  color: #333;
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 12px;
  border-left: 4px solid #409eff;
  padding-left: 12px;
}

.instruction-section p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
}

.operation-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin: 15px 0;
}

.operation-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.operation-icon {
  font-size: 24px;
  flex-shrink: 0;
}

.operation-text {
  font-size: 14px;
  line-height: 1.5;
}

.operation-text strong {
  color: #333;
  display: block;
  margin-bottom: 5px;
}

.procedure-list {
  color: #555;
  line-height: 1.8;
  padding-left: 20px;
}

.procedure-list li {
  margin-bottom: 8px;
}

.reference-standards {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.standard-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  border-radius: 6px;
  font-size: 14px;
}

.standard-item.normal {
  background: #f0f9ff;
  border: 1px solid #409eff;
}

.standard-item.mild {
  background: #fdf6ec;
  border: 1px solid #e6a23c;
}

.standard-item.severe {
  background: #fef0f0;
  border: 1px solid #f56c6c;
}

.standard-label {
  font-weight: 500;
  color: #333;
}

.standard-value {
  font-weight: 600;
  color: #409eff;
}

/* 对比表格样式 */
.comparison-table {
  border: 1px solid #e9ecef;
  border-radius: 8px;
  overflow: hidden;
  margin: 15px 0;
}

.comparison-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  border-bottom: 1px solid #e9ecef;
}

.comparison-row:last-child {
  border-bottom: none;
}

.comparison-row:first-child {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.comparison-label,
.comparison-svh,
.comparison-svv {
  padding: 12px 15px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.comparison-svh {
  background: #f0f8ff;
  color: #409eff;
  font-weight: 500;
}

.comparison-svv {
  background: #f8fafc;
  color: #666;
  font-weight: 500;
}

.notice-list {
  color: #666;
  line-height: 1.6;
  padding-left: 20px;
}

.notice-list li {
  margin-bottom: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 15px;
    padding: 15px 20px;
  }
  
  .header-content {
    order: -1;
  }
  
  .back-button,
  .header-actions {
    align-self: flex-start;
  }
  
  .page-title {
    font-size: 24px;
  }
  
  .operation-grid {
    grid-template-columns: 1fr;
  }
  
  .reference-standards {
    gap: 8px;
  }
  
  .standard-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 5px;
  }
  
  .comparison-row {
    grid-template-columns: 1fr;
    text-align: center;
  }
  
  .comparison-label,
  .comparison-svh,
  .comparison-svv {
    padding: 8px 12px;
    font-size: 13px;
  }
}
</style> 