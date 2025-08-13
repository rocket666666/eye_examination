<template>
  <div class="svh-canvas-container">
    <!-- 左侧：绘图区域 -->
    <div class="left-panel">
      <!-- 顶部信息显示 -->
      <div class="info-bar">
        <div class="angle-display">
          <span class="angle-label">水平夹角:</span>
          <span class="angle-value">{{ horizontalAngle.toFixed(1) }}°</span>
          <span class="angle-label">垂直夹角:</span>
          <span class="angle-value">{{ verticalAngle.toFixed(1) }}°</span>
        </div>
        <div class="status-display">
          <span :class="['status', { 'rotating': autoRotate, 'fixed': fixed }]">
            {{ statusText }}
          </span>
        </div>
      </div>

      <!-- SVH绘图画布 -->
      <div 
        class="canvas-wrapper" 
        @wheel="handleWheel" 
        @click="handleCanvasClick" 
        @dblclick="handleDoubleClick"
      >
        <canvas
          ref="svhCanvas"
          :width="canvasSize"
          :height="canvasSize"
          @contextmenu.prevent
        ></canvas>
        
        <!-- 全屏确认按钮 -->
        <div v-if="isFullscreen" class="fullscreen-confirm">
          <div class="confirm-section">
            <el-button 
              type="success"
              size="large"
              @click="confirmCurrentResult"
              class="confirm-btn"
            >
              <el-icon><Check /></el-icon>
              确认当前结果
            </el-button>
            <div class="save-count">
              <span class="count-label">已保存:</span>
              <span class="count-value">{{ savedResults.length }}</span>
              <span class="count-unit">次</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧：控制面板 -->
    <div class="right-panel">
      <!-- 控制面板 -->
      <div class="control-panel">
        <h3 class="panel-title">控制面板</h3>
        
        <!-- 患者信息 -->
        <div class="patient-info-section" v-if="currentPatient">
          <h4 class="section-title">患者信息</h4>
          <div class="patient-info">
            <div class="patient-item">
              <span class="label">姓名：</span>
              <span class="value">{{ currentPatient.fullName }}</span>
            </div>
            <div class="patient-item">
              <span class="label">编号：</span>
              <span class="value">{{ currentPatient.patientCode }}</span>
            </div>
            <div class="patient-item">
              <span class="label">年龄：</span>
              <span class="value">{{ currentPatient.age }}岁</span>
            </div>
            <div class="patient-item">
              <span class="label">性别：</span>
              <span class="value">{{ getGenderText(currentPatient.gender) }}</span>
            </div>
          </div>
        </div>
        
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
        
        <!-- 保存结果按钮 -->
        <div class="save-result-section">
          <el-button 
            type="primary"
            size="large"
            @click="saveExamResult"
            class="save-result-btn"
            :disabled="savedResults.length < 6"
          >
            <el-icon><Check /></el-icon>
            保存结果
          </el-button>
          <div class="result-tip" v-if="savedResults.length < 6">
            <el-icon><Warning /></el-icon>
            <span>至少需要6次测试记录才能保存结果</span>
          </div>
        </div>
        
        <!-- 记录列表 -->
        <div class="records-section">
          <h4 class="section-title">记录列表</h4>
          <div class="records-table">
            <el-table :data="savedResults" style="width: 100%" size="small">
              <el-table-column prop="index" label="序号" width="60">
                <template #default="{ $index }">
                  <span class="record-index">{{ $index + 1 }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="result" label="保存结果" min-width="200">
                <template #default="{ row }">
                  <span class="record-result">
                    水平: {{ row.horizontal.toFixed(1) }}°, 垂直: {{ row.vertical.toFixed(1) }}°
                  </span>
                </template>
              </el-table-column>
            </el-table>
          </div>
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
            {{ index + 1 }}. 水平: {{ result.horizontal.toFixed(1) }}°, 垂直: {{ result.vertical.toFixed(1) }}°
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
 * SVH检查组件配置说明：
 * 
 * 🔧 可配置参数：
 * - fullscreenScale: 全屏时canvas放大比例（默认120%）
 * - canvasSize: 画布尺寸（默认600px）
 * - baseSpeed: 自动旋转速度（默认50°/秒）
 * - fineSpeed: 微调步长（默认0.1°）
 */

import { ref, reactive, onMounted, onUnmounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'
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
const svhCanvas = ref<HTMLCanvasElement>()
const baseCanvasSize = 600 // 基础canvas尺寸
const angle = ref(0.0) // 当前角度 0°-360°
const fixed = ref(false) // 是否固定
const autoRotate = ref(false) // 自动旋转状态
const rotateDirection = ref<'CW' | 'CCW' | null>(null) // 旋转方向
const showCircle = ref(true) // 显示参考圆盘
const savedResults = ref<Array<{ horizontal: number; vertical: number }>>([])
const isFullscreen = ref(false) // 全屏状态
const currentPatient = ref<any>(null) // 当前患者信息

// 初始化SVH指针角度到水平位置并添加随机偏差
const initializeSVHAngle = () => {
  // 生成-5到5度之间的随机值
  const randomAngle = (Math.random() - 0.5) * 10 // -5到5度
  // 将角度设置为水平位置（0度或180度）加上随机值
  const baseAngle = Math.random() > 0.5 ? 0 : 180
  angle.value = (baseAngle + randomAngle) % 360
  if (angle.value < 0) angle.value += 360
}

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
const drawSVH = () => {
  const canvas = svhCanvas.value
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

  // 绘制3D线条 - SVH使用水平线条
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

  // 创建渐变 - SVH使用蓝色系
  const gradient = ctx.createLinearGradient(xEnd1, yEnd1, xEnd2, yEnd2)
  gradient.addColorStop(0, 'rgb(100, 150, 255)')
  gradient.addColorStop(0.5, 'rgb(50, 100, 200)')
  gradient.addColorStop(1, 'rgb(100, 150, 255)')

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
  
  drawSVH()
}

const fineRotateCW = () => {
  rotateBy(fineSpeed)
}

const fineRotateCCW = () => {
  rotateBy(-fineSpeed)
}

const toggleCircle = () => {
  showCircle.value = !showCircle.value
  drawSVH()
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
        drawSVH()
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
        drawSVH()
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
      drawSVH()
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
  // 保存当前结果
  const result = {
    horizontal: horizontalAngle.value,
    vertical: verticalAngle.value
  }
  savedResults.value.push(result)
  
  ElMessage.success(`已保存：水平 ${result.horizontal.toFixed(1)}°，垂直 ${result.vertical.toFixed(1)}°`)
}

// 全屏确认当前结果
const confirmCurrentResult = () => {
  const result = {
    horizontal: horizontalAngle.value,
    vertical: verticalAngle.value
  }
  
  // 直接将结果添加到保存列表中
  savedResults.value.push(result)
  
  ElMessage.success(`已保存：水平 ${result.horizontal.toFixed(1)}°，垂直 ${result.vertical.toFixed(1)}°`)
  
  // 自动调整指针到垂直位置并添加随机值
  setTimeout(() => {
    // 生成-5到5度之间的随机值
    const randomAngle = (Math.random() - 0.5) * 10 // -5到5度
    // 将角度设置为垂直位置（90度或270度）加上随机值
    const baseAngle = Math.random() > 0.5 ? 90 : 270
    angle.value = (baseAngle + randomAngle) % 360
    if (angle.value < 0) angle.value += 360
    
    // 重新绘制画布
    drawSVH()
    
    ElMessage.info('指针已自动调整到垂直位置')
  }, 500) // 延迟500ms执行，让用户看到保存成功的消息
}

const saveExamResult = async () => {
  if (savedResults.value.length < 6) {
    ElMessage.warning('至少需要6次测试记录才能保存结果')
    return
  }
  
  if (!currentPatient.value) {
    ElMessage.error('请先选择患者')
    return
  }
  
  try {
    // 计算平均值
    const avgHorizontal = savedResults.value.reduce((sum, result) => sum + result.horizontal, 0) / savedResults.value.length
    const avgVertical = savedResults.value.reduce((sum, result) => sum + result.vertical, 0) / savedResults.value.length
    
    // 构建检查记录数据
    const examRecord = {
      patientId: currentPatient.value.id,
      examDate: new Date().toISOString().split('T')[0],
      examTime: new Date().toTimeString().split(' ')[0],
      status: 3, // 已完成
      recordType: 'SVH',
      totalAmount: 0,
      remark: `SVH检查 - 平均水平: ${avgHorizontal.toFixed(1)}°, 平均垂直: ${avgVertical.toFixed(1)}°`
    }
    
    // 构建检查项目明细数据
    const examRecordItems = savedResults.value.map((result, index) => ({
      itemId: 8, // SVH检查项目ID
      itemResult: `水平: ${result.horizontal.toFixed(1)}°, 垂直: ${result.vertical.toFixed(1)}°`,
      itemValue: JSON.stringify({
        horizontal: result.horizontal,
        vertical: result.vertical
      }),
      horizontal: result.horizontal,
      vertical: result.vertical,
      isNormal: (Math.abs(result.horizontal) <= 2 && Math.abs(result.vertical) <= 2) ? 1 : 0,
      status: 2, // 已完成
      examTime: new Date().toISOString()
    }))
    
    // 调用后端API保存数据
    const response = await request.post('/exam-record/save', {
      examRecord,
      examRecordItems,
      patientId: currentPatient.value.id
    }) as any
    
    if (response.code === 200) {
      ElMessage.success('检查结果保存成功！')
      // 清空测试记录
      savedResults.value = []
      // 更新患者信息
      if (response.data && response.data.patientInfo) {
        currentPatient.value = { ...currentPatient.value, ...response.data.patientInfo }
      }
    } else {
      ElMessage.error(response.message || '保存失败')
    }
  } catch (error) {
    console.error('保存检查结果失败:', error)
    ElMessage.error('保存失败，请检查网络连接')
  }
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
  downloadFile(content, `${patientInfo.name}_${patientInfo.date}_SVH检查报告.txt`)
  
  exportDialogVisible.value = false
  ElMessage.success('导出成功！')
}

const generateExportContent = () => {
  let content = `SVH（主观水平视觉）检查报告\n`
  content += `================================\n\n`
  content += `患者信息：\n`
  content += `姓名：${patientInfo.name}\n`
  content += `检查日期：${patientInfo.date}\n`
  content += `MRN：${patientInfo.mrn}\n\n`
  content += `检查结果：\n`
  content += `测量次数：${savedResults.value.length}\n\n`
  content += `详细数据（角度单位：度）：\n`
  
  savedResults.value.forEach((result, index) => {
    content += `${index + 1}. 水平夹角: ${result.horizontal.toFixed(1)}°, 垂直夹角: ${result.vertical.toFixed(1)}°\n`
  })
  
  content += `\n参考范围：正常人SVH偏差应在±2°以内\n`
  content += `检查说明：SVH检查用于评估水平视觉感知和前庭系统功能\n`
  
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

// 性别文本转换
const getGenderText = (gender: string) => {
  const genderMap = { M: '男', F: '女', U: '保密' }
  return genderMap[gender as keyof typeof genderMap] || '保密'
}

const newPatient = () => {
  fixed.value = false
  // 重置到水平位置并添加随机偏差
  initializeSVHAngle()
  showCircle.value = true
  savedResults.value = []
  stopRotation()
  
  // 清空患者信息
  patientInfo.name = ''
  patientInfo.date = ''
  patientInfo.mrn = ''
  
  drawSVH()
  ElMessage.success('已重置，可以开始新的检查')
}

// 生命周期
onMounted(() => {
  // 初始化SVH指针角度到水平位置
  initializeSVHAngle()
  
  drawSVH()
  window.addEventListener('keydown', handleKeyDown)
  // 添加全屏状态监听
  document.addEventListener('fullscreenchange', handleFullscreenChange)
  document.addEventListener('webkitfullscreenchange', handleFullscreenChange)
  document.addEventListener('msfullscreenchange', handleFullscreenChange)
  
  // 从localStorage读取患者信息
  const cachedPatient = localStorage.getItem('selectedPatient')
  if (cachedPatient) {
    try {
      currentPatient.value = JSON.parse(cachedPatient)
    } catch (error) {
      console.error('解析患者信息失败:', error)
    }
  }
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
.svh-canvas-container {
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

.svh-canvas-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, #f0f8ff 0%, #e6f3ff 100%);
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

.save-result-section {
  margin-bottom: 25px;
  text-align: center;
}

.save-result-btn {
  width: 100% !important;
  height: 50px !important;
  font-size: 16px !important;
  font-weight: 600 !important;
  border-radius: 8px !important;
  transition: all 0.3s ease !important;
}

.save-result-btn :deep(.el-button__content) {
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  gap: 8px !important;
}

.save-result-btn:hover {
  transform: translateY(-2px) !important;
  box-shadow: 0 6px 20px rgba(64, 158, 255, 0.3) !important;
}

.result-tip {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin-top: 10px;
  padding: 8px 12px;
  background: #fff7e6;
  border: 1px solid #ffd591;
  border-radius: 6px;
  color: #d46b08;
  font-size: 12px;
}

.result-tip .el-icon {
  color: #fa8c16;
  font-size: 14px;
}

.fullscreen-confirm {
  position: absolute;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
}

.confirm-btn {
  background: rgba(103, 194, 58, 0.9) !important;
  border: none !important;
  color: white !important;
  font-weight: 600 !important;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3) !important;
}

.confirm-btn:hover {
  background: rgba(103, 194, 58, 1) !important;
  transform: translateY(-2px) !important;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.4) !important;
}

.records-section {
  margin-bottom: 25px;
}

.records-table {
  border: 1px solid #e9ecef;
  border-radius: 6px;
  overflow: hidden;
}

.records-table :deep(.el-table) {
  background: #f8f9fa;
}

.records-table :deep(.el-table__header) {
  background: #e9ecef;
}

.records-table :deep(.el-table__row) {
  background: #f8f9fa;
}

.records-table :deep(.el-table__row:hover) {
  background: #e9ecef;
}

.record-index {
  display: inline-block;
  width: 24px;
  height: 24px;
  background: #409eff;
  color: white;
  border-radius: 50%;
  text-align: center;
  line-height: 24px;
  font-size: 12px;
  font-weight: 600;
}

.record-result {
  color: #333;
  font-size: 14px;
}

.confirm-section {
  display: flex;
  align-items: center;
  gap: 15px;
}

.save-count {
  display: flex;
  align-items: center;
  gap: 5px;
  background: rgba(255, 255, 255, 0.9);
  padding: 8px 12px;
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.count-label {
  color: #666;
  font-size: 14px;
}

.count-value {
  color: #409eff;
  font-size: 18px;
  font-weight: 600;
}

.count-unit {
  color: #666;
  font-size: 14px;
}

.patient-info-section {
  margin-bottom: 25px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.patient-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.patient-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
}

.patient-item .label {
  color: #666;
  font-weight: 500;
}

.patient-item .value {
  color: #333;
  font-weight: 600;
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
  .svh-canvas-container {
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
  .svh-canvas-container {
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