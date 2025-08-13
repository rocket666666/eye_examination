<template>
  <div class="examination-container">
    <!-- 面包屑导航 -->
    <div class="breadcrumb-container">
      <div class="breadcrumb-left">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item @click="$router.push('/dashboard')">首页</el-breadcrumb-item>
          <el-breadcrumb-item>眼部检查</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="breadcrumb-right">
        <el-button type="primary" @click="backToDashboard" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回
        </el-button>
      </div>
    </div>

    <!-- 检查工具卡片 -->
    <div class="examination-grid">
      
      <!-- SVV检查工具 -->
      <div class="exam-card svv-card">
        <div class="card-header">
          <div class="card-icon">
            <el-icon><View /></el-icon>
          </div>
          <h3 class="card-title">SVV检查工具</h3>
          <p class="card-subtitle">主观垂直视觉检查</p>
        </div>
        
        <div class="card-content">
          <div class="feature-list">
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>前庭系统功能评估</span>
            </div>
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>脑干病变诊断</span>
            </div>
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>眩晕病因分析</span>
            </div>
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>平衡障碍检查</span>
            </div>
          </div>
          
          <div class="tool-info">
            <div class="info-item">
              <span class="label">检查精度:</span>
              <span class="value">±0.1°</span>
            </div>
            <div class="info-item">
              <span class="label">正常范围:</span>
              <span class="value">±3°以内</span>
            </div>
          </div>
        </div>
        
        <div class="card-actions">
          <el-button 
            type="primary" 
            size="large" 
            @click="goToSVVCheck"
            class="start-btn"
          >
            <el-icon><VideoPlay /></el-icon>
            开始SVV检查
          </el-button>
          
          <el-button 
            type="info" 
            size="large" 
            @click="showSVVHelp"
            class="help-btn"
          >
            <el-icon><QuestionFilled /></el-icon>
            使用说明
          </el-button>
        </div>
      </div>

      <!-- SVH检查工具 -->
      <div class="exam-card svh-card">
        <div class="card-header">
          <div class="card-icon">
            <el-icon><Aim /></el-icon>
          </div>
          <h3 class="card-title">SVH检查工具</h3>
          <p class="card-subtitle">主观水平视觉检查</p>
        </div>
        
        <div class="card-content">
          <div class="feature-list">
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>水平视觉感知评估</span>
            </div>
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>侧向平衡功能检查</span>
            </div>
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>前庭系统侧向评估</span>
            </div>
            <div class="feature-item">
              <el-icon><Check /></el-icon>
              <span>与SVV形成互补检查</span>
            </div>
          </div>
          
          <div class="tool-info">
            <div class="info-item">
              <span class="label">检查精度:</span>
              <span class="value">±0.1°</span>
            </div>
            <div class="info-item">
              <span class="label">正常范围:</span>
              <span class="value">±3°以内</span>
            </div>
          </div>
        </div>
        
        <div class="card-actions">
          <el-button 
            type="primary" 
            size="large" 
            @click="goToSVHCheck"
            class="start-btn"
          >
            <el-icon><VideoPlay /></el-icon>
            开始SVH检查
          </el-button>
          
          <el-button 
            type="info" 
            size="large" 
            @click="showSVHHelp"
            class="help-btn"
          >
            <el-icon><QuestionFilled /></el-icon>
            使用说明
          </el-button>
        </div>
      </div>


    </div>

    <!-- 患者选择对话框 -->
    <el-dialog 
      v-model="patientSelectVisible" 
      title="选择患者" 
      width="800px"
    >
      <div class="patient-select-content">
        <div class="select-tip">
          <el-icon><InfoFilled /></el-icon>
          <span>请选择要进行检查的患者，或点击"新增患者"添加新患者</span>
        </div>
        
        <div class="patient-list-container">
          <PatientListSelector @select-patient="selectPatient" />
        </div>
      </div>
      
      <template #footer>
        <el-button @click="patientSelectVisible = false">取消</el-button>
        <el-button type="primary" @click="$router.push('/patient')">
          <el-icon><Plus /></el-icon>
          新增患者
        </el-button>
      </template>
    </el-dialog>

    <!-- SVV帮助对话框 -->
    <el-dialog 
      v-model="svvHelpDialogVisible" 
      title="SVV检查工具使用说明" 
      width="600px"
    >
      <div class="help-content">
        <h4>什么是SVV检查？</h4>
        <p>SVV（主观垂直视觉）检查是评估患者垂直感知能力的重要神经学测试，主要用于前庭系统功能评估和脑干病变诊断。本系统提供了先进的在线SVV检查工具。</p>
        
        <h4>功能特点：</h4>
        <ul class="feature-list">
          <li>✅ 高精度角度测量（±0.1°精度）</li>
          <li>✅ 3D渐变线条视觉效果</li>
          <li>✅ 多种操作方式（键盘+鼠标+按钮）</li>
          <li>✅ 实时角度显示</li>
          <li>✅ 数据保存和导出功能</li>
          <li>✅ 响应式设计，支持各种设备</li>
        </ul>
        
        <h4>操作说明：</h4>
        <ul class="shortcut-list">
          <li><kbd>←/→</kbd> 自动旋转控制</li>
          <li><kbd>↑</kbd> 保存当前结果</li>
          <li><kbd>↓</kbd> 停止旋转</li>
          <li><kbd>滚轮</kbd> 精细微调</li>
          <li><kbd>双击</kbd> 固定线条</li>
          <li><kbd>按钮</kbd> 完整功能控制</li>
        </ul>
        
        <h4>使用流程：</h4>
        <ol class="procedure-list">
          <li>点击"开始SVV检查"进入检查界面</li>
          <li>患者面向显示器，保持头部稳定</li>
          <li>使用控制方式调整线条角度</li>
          <li>保存多次测量结果</li>
          <li>填写患者信息并导出报告</li>
        </ol>
        
        <div class="notice">
          <el-icon><Warning /></el-icon>
          <span>建议：在安静环境中进行检查，确保显示器亮度适中，患者视线与屏幕垂直。</span>
        </div>
      </div>
      
      <template #footer>
        <el-button @click="svvHelpDialogVisible = false">知道了</el-button>
      </template>
    </el-dialog>

    <!-- SVH帮助对话框 -->
    <el-dialog 
      v-model="helpDialogVisible" 
      title="SVH检查工具使用说明" 
      width="600px"
    >
      <div class="help-content">
        <h4>什么是SVH检查？</h4>
        <p>SVH（主观水平视觉）检查是评估患者水平感知能力的重要神经学测试，主要用于前庭系统侧向功能评估和平衡障碍诊断。本系统提供了先进的在线SVH检查工具。</p>
        
        <h4>功能特点：</h4>
        <ul class="feature-list">
          <li>✅ 高精度角度测量（±0.1°精度）</li>
          <li>✅ 3D渐变线条视觉效果</li>
          <li>✅ 多种操作方式（键盘+鼠标+按钮）</li>
          <li>✅ 实时角度显示</li>
          <li>✅ 数据保存和导出功能</li>
          <li>✅ 响应式设计，支持各种设备</li>
        </ul>
        
        <h4>操作说明：</h4>
        <ul class="shortcut-list">
          <li><kbd>←/→</kbd> 自动旋转控制</li>
          <li><kbd>↑</kbd> 保存当前结果</li>
          <li><kbd>↓</kbd> 停止旋转</li>
          <li><kbd>滚轮</kbd> 精细微调</li>
          <li><kbd>双击</kbd> 固定线条</li>
          <li><kbd>按钮</kbd> 完整功能控制</li>
        </ul>
        
        <h4>使用流程：</h4>
        <ol class="procedure-list">
          <li>点击"开始SVH检查"进入检查界面</li>
          <li>患者面向显示器，保持头部稳定</li>
          <li>使用控制方式调整线条角度</li>
          <li>保存多次测量结果</li>
          <li>填写患者信息并导出报告</li>
        </ol>
        
        <div class="notice">
          <el-icon><Warning /></el-icon>
          <span>建议：在安静环境中进行检查，确保显示器亮度适中，患者视线与屏幕垂直。</span>
        </div>
      </div>
      
      <template #footer>
        <el-button @click="helpDialogVisible = false">知道了</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { 
  View, 
  Check, 
  VideoPlay, 
  QuestionFilled, 
  Camera, 
  Aim,
  Warning,
  ArrowLeft,
  InfoFilled,
  Plus
} from '@element-plus/icons-vue'
import PatientListSelector from '@/components/PatientListSelector.vue'

const router = useRouter()

// 响应式数据
const helpDialogVisible = ref(false)
const svvHelpDialogVisible = ref(false) // Added for SVV help dialog

// 患者选择对话框
const patientSelectVisible = ref(false)
const selectedPatient = ref<any>(null)
const targetPage = ref<string>('') // 添加目标页面跟踪

// 检查是否有选中的患者
const checkSelectedPatient = () => {
  const cachedPatient = null
  if (cachedPatient) {
    selectedPatient.value = JSON.parse(cachedPatient)
    return true
  }
  return false
}

// 跳转到SVV检查页面
const goToSVVCheck = () => {
  if (checkSelectedPatient()) {
    router.push('/svv-check')
  } else {
    targetPage.value = '/svv-check'
    patientSelectVisible.value = true
  }
}

// 跳转到SVH检查页面
const goToSVHCheck = () => {
  if (checkSelectedPatient()) {
    router.push('/svh-check')
  } else {
    targetPage.value = '/svh-check'
    patientSelectVisible.value = true
  }
}

// 选择患者
const selectPatient = (patient: any) => {
  selectedPatient.value = patient
  localStorage.setItem('selectedPatient', JSON.stringify(patient))
  patientSelectVisible.value = false
  // 根据目标页面进行跳转
  if (targetPage.value) {
    router.push(targetPage.value)
    targetPage.value = '' // 清空目标页面
  }
}

// 显示SVV帮助
const showSVVHelp = () => {
  svvHelpDialogVisible.value = true
}

// 显示SVH帮助
const showSVHHelp = () => {
  helpDialogVisible.value = true
}

// 返回Dashboard
const backToDashboard = () => {
  router.push('/dashboard')
}
</script>

<style scoped>
.examination-container {
  min-height: calc(100vh - 110px);
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.breadcrumb-container {
  background: white;
  padding: 8px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-height: 40px;
}

.breadcrumb-left {
  display: flex;
  align-items: center;
}

.breadcrumb-right {
  display: flex;
  align-items: center;
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}

.page-title {
  font-size: 32px;
  font-weight: 700;
  color: #333;
  margin-bottom: 10px;
}

.page-desc {
  font-size: 16px;
  color: #666;
  margin: 0;
}

.examination-grid {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.exam-card {
  background: white;
  border-radius: 16px;
  padding: 25px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  overflow: hidden;
  position: relative;
  max-width: 380px;
  width: 100%;
}

.exam-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
}

.svv-card {
  border-top: 4px solid #409eff;
}

.svh-card {
  border-top: 4px solid #67c23a;
}

.svh-card .card-icon {
  background: linear-gradient(135deg, #67c23a 0%, #85ce61 100%);
}

.svh-card .start-btn {
  background: linear-gradient(135deg, #67c23a 0%, #85ce61 100%);
  border-color: #67c23a;
}

.svh-card .start-btn:hover {
  background: linear-gradient(135deg, #5daf34 0%, #73c24a 100%);
  border-color: #5daf34;
}

.placeholder-card {
  opacity: 0.7;
  border-top: 4px solid #c0c4cc;
}

.card-header {
  text-align: center;
  margin-bottom: 20px;
}

.card-icon {
  width: 70px;
  height: 70px;
  margin: 0 auto 20px;
  border-radius: 50%;
  background: linear-gradient(135deg, #409eff 0%, #66b1ff 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: white;
}

.card-icon.inactive {
  background: linear-gradient(135deg, #c0c4cc 0%, #d3d4d6 100%);
}

.card-title {
  font-size: 24px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.card-subtitle {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.card-content {
  margin-bottom: 30px;
}

.feature-list {
  margin-bottom: 20px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
  font-size: 14px;
  color: #555;
}

.feature-item .el-icon {
  color: #67c23a;
  font-size: 16px;
}

.tool-info {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 15px;
  border-left: 4px solid #409eff;
}

.info-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.info-item:last-child {
  margin-bottom: 0;
}

.label {
  font-size: 14px;
  color: #666;
}

.value {
  font-size: 14px;
  font-weight: 600;
  color: #409eff;
}

.placeholder-text {
  text-align: center;
  color: #999;
  margin: 0;
  padding: 20px 0;
}

.card-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}

.start-btn {
  flex: 1;
  height: 50px;
  font-size: 16px;
  font-weight: 600;
}

.help-btn {
  height: 50px;
  font-size: 16px;
}

/* 帮助对话框样式 */
.help-content h4 {
  color: #333;
  margin-bottom: 10px;
  margin-top: 20px;
}

.help-content h4:first-child {
  margin-top: 0;
}

.help-content p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
}

.feature-list {
  list-style: none;
  padding: 0;
  margin: 15px 0;
}

.feature-list li {
  margin-bottom: 8px;
  color: #555;
  line-height: 1.6;
}

.shortcut-list {
  list-style: none;
  padding: 0;
  margin: 15px 0;
}

.shortcut-list li {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  color: #555;
}

.shortcut-list kbd {
  background: #f1f2f3;
  border: 1px solid #ccc;
  border-radius: 3px;
  padding: 2px 6px;
  font-size: 12px;
  margin-right: 10px;
  min-width: 40px;
  text-align: center;
}

.procedure-list {
  color: #555;
  line-height: 1.8;
  padding-left: 20px;
}

.procedure-list li {
  margin-bottom: 8px;
}

.notice {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  background: #fff7e6;
  border: 1px solid #ffd591;
  border-radius: 6px;
  padding: 15px;
  margin-top: 20px;
}

.notice .el-icon {
  color: #fa8c16;
  font-size: 18px;
  margin-top: 2px;
}

.notice span {
  color: #d46b08;
  font-size: 14px;
  line-height: 1.5;
}

/* 面包屑导航样式 */
:deep(.el-breadcrumb__item:last-child .el-breadcrumb__inner) {
  color: #606266;
}

:deep(.el-breadcrumb__item:not(:last-child) .el-breadcrumb__inner:hover) {
  color: #409eff;
  cursor: pointer;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .examination-container {
    padding: 15px;
  }
  
  .breadcrumb-container {
    flex-direction: column;
    gap: 5px;
    align-items: stretch;
  }
  
  .breadcrumb-right {
    justify-content: center;
  }
  
  .examination-grid {
    flex-direction: column;
    align-items: center;
    gap: 20px;
  }
  
  .exam-card {
    padding: 20px;
    max-width: 350px;
  }
  
  .card-actions {
    flex-direction: column;
  }
  
  .page-title {
    font-size: 24px;
  }
}

/* 患者选择对话框样式 */
.patient-select-content {
  padding: 10px 0;
}

.select-tip {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  background: #f0f9ff;
  border: 1px solid #bae6fd;
  border-radius: 6px;
  margin-bottom: 20px;
  color: #0369a1;
  font-size: 14px;
}

.select-tip .el-icon {
  color: #0ea5e9;
  font-size: 16px;
}

.patient-list-container {
  max-height: 400px;
  overflow-y: auto;
}
</style>