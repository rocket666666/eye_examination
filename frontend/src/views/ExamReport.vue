<template>
  <div class="list-page-container">
    <div class="list-page-content">
      <!-- 面包屑导航 -->
      <div class="breadcrumb-container">
        <div class="breadcrumb-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item @click="$router.push('/dashboard')">首页</el-breadcrumb-item>
            <el-breadcrumb-item>检查报告</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="breadcrumb-right">
          <el-button type="primary" @click="backToDashboard" class="back-btn">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
        </div>
      </div>
      
      <!-- 主要内容区域 -->
      <div class="main-content">
        <!-- 搜索条件 -->
        <div class="search-bar">
          <el-form :model="searchForm" inline>
            <el-form-item label="患者姓名">
              <el-input
                v-model="searchForm.patientName"
                placeholder="请输入患者姓名"
                clearable
                style="width: 200px"
              />
            </el-form-item>
            <el-form-item label="报告日期">
              <el-date-picker
                v-model="searchForm.reportDate"
                type="date"
                placeholder="选择报告日期"
                style="width: 200px"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearch">
                <el-icon><Search /></el-icon>
                搜索
              </el-button>
              <el-button @click="handleReset">
                <el-icon><Refresh /></el-icon>
                重置
              </el-button>
            </el-form-item>
          </el-form>
        </div>
        
        <!-- 表格 -->
        <div class="table-container">
          <el-table :data="reportList" v-loading="loading" stripe>
            <el-table-column prop="recordNo" label="报告编号" width="180" />
            <el-table-column prop="remark" label="报告总结" />
            <el-table-column prop="patientName" label="患者姓名" width="120" />
            <el-table-column prop="doctorName" label="报告医生" width="120" />
            <el-table-column prop="examDate" label="报告日期" width="120">
              <template #default="scope">
                {{ formatDate(scope.row.examDate) }}
              </template>
            </el-table-column>
            <el-table-column prop="examTime" label="报告时间" width="120">
              <template #default="scope">
                {{ formatTime(scope.row.examTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="reportStatus" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.reportStatus === 1 ? 'warning' : 'success'">
                  {{ scope.row.reportStatus === 1 ? '未完成' : '已完成' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
              <template #default="scope">
                <el-button size="small" type="primary" @click="handleView(scope.row)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
    
    <!-- 报告详情对话框 -->
    <el-dialog
      v-model="reportDetailVisible"
      title="检查报告详情"
      width="62%"
      :close-on-click-modal="false"
    >
      <div v-if="currentReport" class="report-detail">
        <!-- 报告基本信息 -->
        <el-card class="mb-4">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="报告编号">{{ currentReport.recordNo }}</el-descriptions-item>
            <el-descriptions-item label="报告总结">{{ currentReport.remark }}</el-descriptions-item>
            <el-descriptions-item label="报告医生">{{ currentReport.doctorName }}</el-descriptions-item>
            <el-descriptions-item label="患者姓名">{{ currentReport.patientName || '未知' }}</el-descriptions-item>
            <el-descriptions-item label="报告日期">{{ formatDate(currentReport.examDate) }}</el-descriptions-item>
            <el-descriptions-item label="报告时间">{{ formatTime(currentReport.examTime) }}</el-descriptions-item>
            <el-descriptions-item label="状态" :span="2">
              <el-tag :type="currentReport.reportStatus === 1 ? 'warning' : 'success'">
                {{ currentReport.reportStatus === 1 ? '未完成' : '已完成' }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
        
        <!-- 检查结果列表 -->
        <el-card>
          <el-table :data="examResults" stripe>
            <el-table-column prop="itemResult" label="检查数据" />
            <el-table-column prop="status" label="检查结果" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 2 ? 'success' : 'danger'">
                  {{ scope.row.status === 2 ? '正常' : '不正常' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="reportDetailVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Search, Refresh, ArrowLeft } from '@element-plus/icons-vue'
import request from '@/utils/request'
import '@/styles/list-common.css'

const router = useRouter()
const loading = ref(false)
const reportList = ref([])

// 搜索表单
const searchForm = reactive({
  patientName: '',
  reportDate: ''
})

const getReportList = async () => {
  loading.value = true
  try {
    const params: any = {}
    if (searchForm.patientName) {
      params.patientName = searchForm.patientName
    }
    if (searchForm.reportDate) {
      params.reportDate = searchForm.reportDate
    }
    
    const response = await request.get('/exam-record/list', { params })
    reportList.value = response.data || []
  } catch (error: any) {
    console.error('获取报告列表失败:', error)
    if (error.response?.status === 401) {
      ElMessage.error('API需要认证，请检查后端配置')
    } else if (error.response?.status === 404) {
      ElMessage.error('API端点不存在，请检查后端路由配置')
    } else {
      ElMessage.error(`获取报告列表失败: ${error.message || '未知错误'}`)
    }
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  getReportList()
}

const handleReset = () => {
  searchForm.patientName = ''
  searchForm.reportDate = ''
  getReportList()
}

const handleView = (row: any) => {
  // 显示报告详情对话框
  showReportDetail(row)
}

const backToDashboard = () => {
  router.push('/dashboard')
}

// 报告详情对话框相关
const reportDetailVisible = ref(false)
const currentReport = ref<any>(null)
const examResults = ref<any[]>([])

const showReportDetail = async (report: any) => {
  currentReport.value = report
  reportDetailVisible.value = true
  
  // 获取检查结果数据
  try {
    const response = await request.get(`/exam-record/${report.recordId}`)
    if (response.data && response.data.items) {
      examResults.value = response.data.items || []
    }
  } catch (error) {
    console.error('获取检查结果失败:', error)
    examResults.value = []
  }
}

const formatDate = (date: string) => {
  if (!date) return ''
  const s = String(date)
  // 已是 YYYY/MM/DD 或 YYYY-MM-DD，直接规范化输出
  const m = s.match(/^(\d{4})[-\/]?(\d{2})[-\/]?(\d{2})$/)
  if (m) {
    return `${m[1]}/${m[2]}/${m[3]}`
  }
  const d = new Date(s)
  if (!isNaN(d.getTime())) {
    return d.toLocaleDateString()
  }
  return s
}

const formatTime = (time: string) => {
  if (!time) return ''
  const m = String(time).match(/\d{2}:\d{2}(:\d{2})?/)
  if (m) {
    // 统一补成 HH:mm:ss 格式
    const parts = m[0].split(':')
    if (parts.length === 2) parts.push('00')
    return parts.map(p => p.padStart(2, '0')).join(':')
  }
  // 尝试解析 ISO 或 Date 可识别格式
  const d = new Date(`1970-01-01T${time}`)
  if (!isNaN(d.getTime())) {
    const hh = String(d.getHours()).padStart(2, '0')
    const mm = String(d.getMinutes()).padStart(2, '0')
    const ss = String(d.getSeconds()).padStart(2, '0')
    return `${hh}:${mm}:${ss}`
  }
  return String(time)
}

onMounted(() => {
  getReportList()
})
</script>

<style scoped>
/* 页面特有样式 */
.report-detail {
  max-height: 70vh;
  overflow-y: auto;
}

.mb-4 {
  margin-bottom: 16px;
}

.conclusion-content,
.suggestion-content {
  line-height: 1.6;
  color: #333;
  padding: 12px;
  background: #f9f9f9;
  border-radius: 4px;
  border-left: 4px solid #409eff;
}

.dialog-footer {
  text-align: right;
}
</style> 