<template>
  <div class="svv-canvas-container">
    <!-- 左侧：绘图区域 -->
    <div class="left-panel">
      <!-- 顶部信息显示 -->
      <div class="info-bar">
        <div class="angle-display">
          <span class="angle-label">X轴夹角:</span>
          <span class="angle-value">{{ horizontalAngle.toFixed(1) }}°</span>
          <span class="angle-label">Y轴夹角:</span>
          <span class="angle-value">{{ verticalAngle.toFixed(1) }}°</span>
        </div>
        <div class="status-display">
          <span :class="['status', { 'rotating': autoRotate, 'fixed': fixed }]">
            {{ statusText }}
          </span>
        </div>
      </div>

      <!-- SVV绘图画布 -->
      <div 
        class="canvas-wrapper" 
        @wheel="handleWheel" 
        @click="handleCanvasClick" 
        @dblclick="handleDoubleClick"
      >
        <canvas
          ref="svvCanvas"
          :width="canvasSize"
          :height="canvasSize"
          @contextmenu.prevent
        ></canvas>
      </div>
    </div>

    <!-- 右侧：控制面板 -->
    <div class="right-panel">
      <!-- 控制面板 -->
      <div class="control-panel">
        <h3 class="panel-title">控制面板</h3>
        
        <!-- 开始检测按钮 -->
        <div class="start-detection-section">
          <el-button 
            type="success"
            size="large"
            @click="toggleFullscreen"
            class="start-detection-btn"
          >
            <el-icon><FullScreen /></el-icon>
            {{ isFullscreen ? '退出全屏' : '开始检测' }}
          </el-button>
        </div>
        
        <div class="control-section">
          <h4 class="section-title">旋转控制</h4>
          <div class="control-group">
            <el-button 
              :type="autoRotate && rotateDirection === 'CW' ? 'primary' : 'default'"
              :disabled="fixed"
              @click="toggleAutoCW"
              size="small"
              class="control-btn"
            >
              <el-icon><DArrowRight /></el-icon>
              顺时针旋转
            </el-button>
            
            <el-button 
              :type="autoRotate && rotateDirection === 'CCW' ? 'primary' : 'default'"
              :disabled="fixed"
              @click="toggleAutoCCW"
              size="small"
              class="control-btn"
            >
              <el-icon><DArrowLeft /></el-icon>
              逆时针旋转
            </el-button>

            <el-button 
              :disabled="fixed"
              @click="fineRotateCW"
              size="small"
              class="control-btn"
            >
              <el-icon><Plus /></el-icon>
              顺时针微调
            </el-button>

            <el-button 
              :disabled="fixed"
              @click="fineRotateCCW"
              size="small"
              class="control-btn"
            >
              <el-icon><Minus /></el-icon>
              逆时针微调
            </el-button>

            <el-button 
              type="warning"
              @click="stopRotation"
              size="small"
              class="control-btn"
            >
              <el-icon><Close /></el-icon>
              停止旋转
            </el-button>
          </div>
        </div>

        <div class="control-section">
          <h4 class="section-title">功能操作</h4>
          <div class="control-group">
            <el-button 
              :type="showCircle ? 'primary' : 'default'"
              @click="toggleCircle"
              size="small"
              class="control-btn"
            >
              <el-icon><View /></el-icon>
              {{ showCircle ? '隐藏参考盘' : '显示参考盘' }}
            </el-button>

            <el-button 
              type="success"
              @click="saveResult"
              size="small"
              class="control-btn"
            >
              <el-icon><Check /></el-icon>
              保存结果
            </el-button>

            <el-button 
              type="info"
              @click="exportResults"
              size="small"
              class="control-btn"
            >
              <el-icon><Download /></el-icon>
              导出结果
            </el-button>

            <el-button 
              type="danger"
              @click="newPatient"
              size="small"
              class="control-btn"
            >
              <el-icon><RefreshRight /></el-icon>
              新病人
            </el-button>
          </div>
        </div>

        <!-- 快捷键提示 -->
        <div class="control-section">
          <h4 class="section-title">快捷键说明</h4>
          <div class="shortcuts-info">
            <div class="shortcut-item">
              <kbd>←</kbd> 顺时针自动旋转
            </div>
            <div class="shortcut-item">
              <kbd>→</kbd> 逆时针自动旋转
            </div>
            <div class="shortcut-item">
              <kbd>↑</kbd> 保存结果
            </div>
            <div class="shortcut-item">
              <kbd>↓</kbd> 停止旋转
            </div>
            <div class="shortcut-item">
              <kbd>滚轮</kbd> ±0.1°微调
            </div>
            <div class="shortcut-item">
              <kbd>双击</kbd> 固定线条
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 导出对话框 -->
    <el-dialog v-model="exportDialogVisible" title="导出检查结果" width="500px">
      <el-form :model="patientInfo" label-width="100px">
        <el-form-item label="患者姓名" required>
          <el-input v-model="patientInfo.name" placeholder="请输入患者姓名" />
        </el-form-item>
        <el-form-item label="检查日期" required>
          <el-input v-model="patientInfo.date" placeholder="请输入检查日期" />
        </el-form-item>
        <el-form-item label="MRN" required>
          <el-input v-model="patientInfo.mrn" placeholder="请输入患者MRN" />
        </el-form-item>
      </el-form>
      
      <div class="results-preview">
        <h4>已保存的测量结果：</h4>
        <div v-if="savedResults.length === 0" class="no-results">
          暂无保存的结果
        </div>
        <div v-else class="results-list">
          <div v-for="(result, index) in savedResults" :key="index" class="result-item">
            {{ index + 1 }}. X轴: {{ result.horizontal.toFixed(1) }}°, Y轴: {{ result.vertical.toFixed(1) }}°
          </div>
        </div>
      </div>

      <template #footer>
        <el-button @click="exportDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmExport">确认导出</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
/**
 * SVV检查组件配置说明：
 * 
 * 🔧 可配置参数：
 * - fullscreenScale: 全屏时canvas放大比例（默认120%）
 * - canvasSize: 画布尺寸（默认600px）
 * - baseSpeed: 自动旋转速度（默认50°/秒）
 * - fineSpeed: 微调步长（默认0.1°）
 */

import { ref, reactive, onMounted, onUnmounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { 
  DArrowRight, 
  DArrowLeft, 
  Plus, 
  Minus, 
  Close, 
  View, 
  Check, 
  Download, 
  RefreshRight,
  FullScreen 
} from '@element-plus/icons-vue'

// 响应式数据
const svvCanvas = ref<HTMLCanvasElement>()
const baseCanvasSize = 600 // 基础canvas尺寸
const angle = ref(0.0) // 当前角度 0°-360°
const fixed = ref(false) // 是否固定
const autoRotate = ref(false) // 自动旋转状态
const rotateDirection = ref<'CW' | 'CCW' | null>(null) // 旋转方向
const showCircle = ref(true) // 显示参考圆盘
const savedResults = ref<Array<{ horizontal: number; vertical: number }>>([])
const isFullscreen = ref(false) // 全屏状态

// 全屏放大设置 - 可在此处修改放大比例
const fullscreenScale = ref(120) // 全屏时的放大百分比（120 = 120%），可随时修改此数值

// 速度设置
const baseSpeed = 50.0 // 50°/秒
const fineSpeed = 0.1 // 0.1°/步

// 定时器
let rotationTimer: number | null = null
const timerInterval = 10 // 毫秒

// 患者信息和导出对话框
const exportDialogVisible = ref(false)
const patientInfo = reactive({
  name: '',
  date: '',
  mrn: ''
})

// 计算属性
const canvasSize = computed(() => {
  return isFullscreen.value 
    ? Math.round(baseCanvasSize * (fullscreenScale.value / 100))
    : baseCanvasSize
})

const horizontalAngle = computed(() => {
  const rawH = Math.abs(angle.value % 180)
  return Math.min(rawH, 180 - rawH)
})

const verticalAngle = computed(() => {
  const rawV = Math.abs((angle.value - 90) % 180)
  return Math.min(rawV, 180 - rawV)
})

const statusText = computed(() => {
  if (fixed.value) return '已固定'
  if (autoRotate.value) {
    return rotateDirection.value === 'CW' ? '顺时针旋转中' : '逆时针旋转中'
  }
  return '就绪'
})

// 绘图函数
const drawSVV = () => {
  const canvas = svvCanvas.value
  if (!canvas) return

  const ctx = canvas.getContext('2d')
  if (!ctx) return

  const currentSize = canvasSize.value

  // 清空画布
  ctx.clearRect(0, 0, currentSize, currentSize)

  const cx = currentSize / 2
  const cy = currentSize / 2
  const radius = Math.min(currentSize, currentSize) * 0.4

  // 绘制参考圆盘
  if (showCircle.value) {
    drawReferenceCircle(ctx, cx, cy, radius)
  }

  // 绘制3D线条
  draw3DLine(ctx, cx, cy, radius)
}

const drawReferenceCircle = (ctx: CanvasRenderingContext2D, cx: number, cy: number, radius: number) => {
  // 绘制外圆
  ctx.strokeStyle = '#000000'
  ctx.lineWidth = 2
  ctx.beginPath()
  ctx.arc(cx, cy, radius, 0, 2 * Math.PI)
  ctx.stroke()

  // 绘制刻度
  for (let deg = 0; deg < 360; deg++) {
    const rad = (deg * Math.PI) / 180
    const x1 = cx + radius * Math.cos(rad)
    const y1 = cy + radius * Math.sin(rad)
    const inner = radius - 30
    const x2 = cx + inner * Math.cos(rad)
    const y2 = cy + inner * Math.sin(rad)

    if (deg % 2 === 0) {
      ctx.strokeStyle = '#00ff00'
      ctx.lineWidth = 2
    } else {
      ctx.strokeStyle = '#000000'
      ctx.lineWidth = 1
    }

    ctx.beginPath()
    ctx.moveTo(x1, y1)
    ctx.lineTo(x2, y2)
    ctx.stroke()
  }
}

const draw3DLine = (ctx: CanvasRenderingContext2D, cx: number, cy: number, radius: number) => {
  const angleRad = (angle.value * Math.PI) / 180

  const xEnd1 = cx + radius * Math.cos(angleRad)
  const yEnd1 = cy + radius * Math.sin(angleRad)
  const xEnd2 = cx - radius * Math.cos(angleRad)
  const yEnd2 = cy - radius * Math.sin(angleRad)

  // 创建渐变
  const gradient = ctx.createLinearGradient(xEnd1, yEnd1, xEnd2, yEnd2)
  gradient.addColorStop(0, 'rgb(200, 200, 200)')
  gradient.addColorStop(0.5, 'rgb(80, 80, 80)')
  gradient.addColorStop(1, 'rgb(200, 200, 200)')

  ctx.strokeStyle = gradient
  ctx.lineWidth = 8
  ctx.lineCap = 'round'
  ctx.lineJoin = 'round'

  ctx.beginPath()
  ctx.moveTo(xEnd1, yEnd1)
  ctx.lineTo(xEnd2, yEnd2)
  ctx.stroke()
}

// 控制函数
const toggleAutoCW = () => {
  if (fixed.value) return
  
  if (autoRotate.value && rotateDirection.value === 'CW') {
    stopRotation()
  } else {
    startAutoRotate('CW')
  }
}

const toggleAutoCCW = () => {
  if (fixed.value) return
  
  if (autoRotate.value && rotateDirection.value === 'CCW') {
    stopRotation()
  } else {
    startAutoRotate('CCW')
  }
}

const startAutoRotate = (direction: 'CW' | 'CCW') => {
  stopRotation()
  
  autoRotate.value = true
  rotateDirection.value = direction
  
  rotationTimer = window.setInterval(() => {
    if (fixed.value) {
      stopRotation()
      return
    }
    
    const deltaDeg = baseSpeed * (timerInterval / 1000.0)
    if (direction === 'CW') {
      rotateBy(deltaDeg)
    } else {
      rotateBy(-deltaDeg)
    }
  }, timerInterval)
}

const stopRotation = () => {
  autoRotate.value = false
  rotateDirection.value = null
  
  if (rotationTimer) {
    clearInterval(rotationTimer)
    rotationTimer = null
  }
}

const rotateBy = (delta: number) => {
  if (fixed.value) return
  
  angle.value = (angle.value + delta) % 360
  if (angle.value < 0) angle.value += 360
  
  drawSVV()
}

const fineRotateCW = () => {
  rotateBy(fineSpeed)
}

const fineRotateCCW = () => {
  rotateBy(-fineSpeed)
}

const toggleCircle = () => {
  showCircle.value = !showCircle.value
  drawSVV()
}

// 全屏控制
const toggleFullscreen = async () => {
  const canvasWrapper = document.querySelector('.canvas-wrapper') as HTMLElement
  
  if (!canvasWrapper) return
  
  try {
    if (!isFullscreen.value) {
      // 进入全屏
      if (canvasWrapper.requestFullscreen) {
        await canvasWrapper.requestFullscreen()
      } else if ((canvasWrapper as any).webkitRequestFullscreen) {
        await (canvasWrapper as any).webkitRequestFullscreen()
      } else if ((canvasWrapper as any).msRequestFullscreen) {
        await (canvasWrapper as any).msRequestFullscreen()
      }
      isFullscreen.value = true
      // 全屏后重新绘制canvas（尺寸变化了）
      setTimeout(() => {
        drawSVV()
      }, 100)
      ElMessage.success('已进入全屏模式')
    } else {
      // 退出全屏
      if (document.exitFullscreen) {
        await document.exitFullscreen()
      } else if ((document as any).webkitExitFullscreen) {
        await (document as any).webkitExitFullscreen()
      } else if ((document as any).msExitFullscreen) {
        await (document as any).msExitFullscreen()
      }
      isFullscreen.value = false
      // 退出全屏后重新绘制canvas（尺寸变化了）
      setTimeout(() => {
        drawSVV()
      }, 100)
      ElMessage.success('已退出全屏模式')
    }
  } catch (error) {
    ElMessage.error('全屏操作失败')
    console.error('Fullscreen error:', error)
  }
}

// 监听全屏状态变化
const handleFullscreenChange = () => {
  const fullscreenElement = document.fullscreenElement || 
                           (document as any).webkitFullscreenElement || 
                           (document as any).msFullscreenElement
  
  const newFullscreenState = !!fullscreenElement
  
  if (isFullscreen.value !== newFullscreenState) {
    isFullscreen.value = newFullscreenState
    // 状态变化时重新绘制canvas（尺寸变化了）
    setTimeout(() => {
      drawSVV()
    }, 100)
  }
}

// 事件处理
const handleWheel = (event: WheelEvent) => {
  event.preventDefault()
  if (fixed.value) return
  
  const delta = -event.deltaY / Math.abs(event.deltaY)
  if (delta > 0) {
    rotateBy(fineSpeed)
  } else {
    rotateBy(-fineSpeed)
  }
}

const handleCanvasClick = () => {
  if (fixed.value) return
  rotateBy(0.001) // 极微调
}

const handleDoubleClick = () => {
  fixed.value = true
  stopRotation()
  ElMessage.success('线条已固定，双击解除固定')
}

const handleKeyDown = (event: KeyboardEvent) => {
  switch (event.key) {
    case 'ArrowLeft':
      event.preventDefault()
      toggleAutoCW()
      break
    case 'ArrowRight':
      event.preventDefault()
      toggleAutoCCW()
      break
    case 'ArrowUp':
      event.preventDefault()
      saveResult()
      break
    case 'ArrowDown':
      event.preventDefault()
      stopRotation()
      break
  }
}

// 数据管理
const saveResult = () => {
  const result = {
    horizontal: horizontalAngle.value,
    vertical: verticalAngle.value
  }
  savedResults.value.push(result)
  
  ElMessage.success(`已保存：X轴 ${result.horizontal.toFixed(1)}°，Y轴 ${result.vertical.toFixed(1)}°`)
}

const exportResults = () => {
  if (savedResults.value.length === 0) {
    ElMessage.warning('没有保存的结果，请先进行测量并保存')
    return
  }
  exportDialogVisible.value = true
}

const confirmExport = () => {
  if (!patientInfo.name || !patientInfo.date || !patientInfo.mrn) {
    ElMessage.warning('请填写完整的患者信息')
    return
  }
  
  // 生成导出内容
  const content = generateExportContent()
  
  // 下载文件
  downloadFile(content, `${patientInfo.name}_${patientInfo.date}_SVV检查报告.txt`)
  
  exportDialogVisible.value = false
  ElMessage.success('导出成功！')
}

const generateExportContent = () => {
  let content = `SVV（主观垂直视觉）检查报告\n`
  content += `================================\n\n`
  content += `患者信息：\n`
  content += `姓名：${patientInfo.name}\n`
  content += `检查日期：${patientInfo.date}\n`
  content += `MRN：${patientInfo.mrn}\n\n`
  content += `检查结果：\n`
  content += `测量次数：${savedResults.value.length}\n\n`
  content += `详细数据（角度单位：度）：\n`
  
  savedResults.value.forEach((result, index) => {
    content += `${index + 1}. X轴夹角: ${result.horizontal.toFixed(1)}°, Y轴夹角: ${result.vertical.toFixed(1)}°\n`
  })
  
  content += `\n参考范围：正常人SVV偏差应在±2°以内\n`
  content += `检查说明：SVV检查用于评估前庭系统功能和脑干病变\n`
  
  return content
}

const downloadFile = (content: string, filename: string) => {
  const blob = new Blob([content], { type: 'text/plain;charset=utf-8' })
  const url = URL.createObjectURL(blob)
  const link = document.createElement('a')
  link.href = url
  link.download = filename
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  URL.revokeObjectURL(url)
}

const newPatient = () => {
  fixed.value = false
  angle.value = 0.0
  showCircle.value = true
  savedResults.value = []
  stopRotation()
  
  // 清空患者信息
  patientInfo.name = ''
  patientInfo.date = ''
  patientInfo.mrn = ''
  
  drawSVV()
  ElMessage.success('已重置，可以开始新的检查')
}

// 生命周期
onMounted(() => {
  drawSVV()
  window.addEventListener('keydown', handleKeyDown)
  // 添加全屏状态监听
  document.addEventListener('fullscreenchange', handleFullscreenChange)
  document.addEventListener('webkitfullscreenchange', handleFullscreenChange)
  document.addEventListener('msfullscreenchange', handleFullscreenChange)
})

onUnmounted(() => {
  stopRotation()
  window.removeEventListener('keydown', handleKeyDown)
  // 移除全屏状态监听
  document.removeEventListener('fullscreenchange', handleFullscreenChange)
  document.removeEventListener('webkitfullscreenchange', handleFullscreenChange)
  document.removeEventListener('msfullscreenchange', handleFullscreenChange)
})
</script>

<style scoped>
.svv-canvas-container {
  display: flex;
  flex-direction: row;
  gap: 20px;
  padding: 20px;
  min-height: calc(100vh - 40px);
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  margin: 20px;
  position: relative;
  overflow: hidden;
}

.svv-canvas-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
  z-index: -1;
}

.left-panel {
  flex: 7;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.right-panel {
  flex: 3;
  display: flex;
  flex-direction: column;
}

.info-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 15px 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.angle-display {
  display: flex;
  gap: 20px;
  align-items: center;
}

.angle-label {
  font-size: 14px;
  color: #666;
}

.angle-value {
  font-size: 18px;
  font-weight: 600;
  color: #409eff;
  min-width: 60px;
}

.status-display .status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 500;
  background: #f0f2f5;
  color: #666;
}

.status.rotating {
  background: #e1f3ff;
  color: #409eff;
}

.status.fixed {
  background: #fff2e8;
  color: #e6a23c;
}

.canvas-wrapper {
  position: relative;
  cursor: grab;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.canvas-wrapper:active {
  cursor: grabbing;
}

/* 全屏状态下的样式 */
.canvas-wrapper:fullscreen {
  display: flex;
  align-items: center;
  justify-content: center;
  background: #000;
  padding: 20px;
  box-sizing: border-box;
}

.canvas-wrapper:fullscreen canvas {
  max-width: 100vw;
  max-height: 100vh;
  object-fit: contain;
  transition: all 0.3s ease;
}

canvas {
  display: block;
  background: white;
}

.control-panel {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  height: fit-content;
}

.panel-title {
  margin: 0 0 20px 0;
  color: #333;
  font-size: 18px;
  font-weight: 600;
  text-align: center;
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 10px;
}

.start-detection-section {
  margin-bottom: 25px;
  text-align: center;
}

.start-detection-btn {
  width: 100% !important;
  height: 50px !important;
  font-size: 16px !important;
  font-weight: 600 !important;
  border-radius: 8px !important;
  transition: all 0.3s ease !important;
}

.start-detection-btn :deep(.el-button__content) {
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  gap: 8px !important;
}

.start-detection-btn:hover {
  transform: translateY(-2px) !important;
  box-shadow: 0 6px 20px rgba(103, 194, 58, 0.3) !important;
}

.control-section {
  margin-bottom: 25px;
}

.control-section:last-child {
  margin-bottom: 0;
}

.section-title {
  margin: 0 0 12px 0;
  color: #555;
  font-size: 14px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.control-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* 重置所有control-btn的基础样式 */
.control-btn {
  width: 100% !important;
  padding: 0 !important;
  margin: 0 !important;
  border-radius: 4px !important;
  height: 32px !important;
  display: flex !important;
  align-items: center !important;
  justify-content: flex-start !important;
  position: relative !important;
  overflow: hidden !important;
}

/* 完全重写按钮内容布局 */
.control-btn :deep(.el-button__content) {
  display: flex !important;
  align-items: center !important;
  justify-content: flex-start !important;
  width: 100% !important;
  height: 100% !important;
  padding: 0 12px !important;
  margin: 0 !important;
  gap: 8px !important;
  position: relative !important;
}

/* 统一图标样式 */
.control-btn :deep(.el-icon) {
  width: 16px !important;
  height: 16px !important;
  margin: 0 !important;
  padding-left: 4px !important;
  padding-right: 4px !important;
  flex-shrink: 0 !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

/* 统一文字样式 */
.control-btn :deep(span:not([class*="el-icon"])) {
  flex: 1 !important;
  text-align: left !important;
  margin: 0 !important;
  padding: 0 !important;
  white-space: nowrap !important;
  overflow: hidden !important;
  text-overflow: ellipsis !important;
}

.shortcuts-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.shortcut-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 12px;
  color: #666;
}

.shortcut-item kbd {
  display: inline-block;
  padding: 2px 6px;
  background: #f1f2f3;
  border: 1px solid #ccc;
  border-radius: 3px;
  font-size: 12px;
  font-family: monospace;
  min-width: 30px;
  text-align: center;
}

.results-preview {
  margin-top: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.results-preview h4 {
  margin: 0 0 10px 0;
  color: #333;
}

.no-results {
  color: #999;
  text-align: center;
  padding: 20px;
}

.results-list {
  max-height: 200px;
  overflow-y: auto;
}

.result-item {
  padding: 5px 0;
  border-bottom: 1px solid #eee;
  color: #555;
}

.result-item:last-child {
  border-bottom: none;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .svv-canvas-container {
    flex-direction: column;
  }
  
  .left-panel {
    flex: none;
  }
  
  .right-panel {
    flex: none;
  }
  
  .control-panel {
    margin-top: 20px;
  }
  
  .control-group {
    flex-direction: row;
    flex-wrap: wrap;
    gap: 12px;
  }
  
  .control-btn {
    flex: 1;
    min-width: 120px;
  }
}

@media (max-width: 768px) {
  .svv-canvas-container {
    padding: 15px;
    margin: 10px;
    flex-direction: column;
  }
  
  .info-bar {
    flex-direction: column;
    gap: 10px;
    text-align: center;
  }
  
  .angle-display {
    justify-content: center;
  }
  
  .control-group {
    flex-direction: column;
  }
  
  .control-btn {
    width: 100%;
    min-width: none;
  }
  
  .start-detection-btn {
    height: 45px !important;
    font-size: 14px !important;
  }
  
  .shortcuts-info {
    gap: 10px;
  }
}
</style>