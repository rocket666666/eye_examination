<template>
  <div class="patient-list-selector">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :model="searchForm" inline>
        <el-form-item label="姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入患者姓名"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="编号">
          <el-input
            v-model="searchForm.code"
            placeholder="请输入患者编号"
            clearable
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

    <!-- 患者列表 -->
    <div class="patient-table">
      <el-table
        :data="patientList"
        v-loading="loading"
        stripe
        @row-click="handleRowClick"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column prop="patientCode" label="患者编号" width="180" />
        <el-table-column prop="fullName" label="姓名" width="180" />
        <el-table-column prop="gender" label="性别" width="90">
          <template #default="{ row }">
            <span>{{ getGenderText(row.gender) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="90" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column label="操作" width="80" fixed="right">
          <template #default="{ row }">
            <el-button 
              type="primary" 
              size="small" 
              @click.stop="selectPatient(row)"
            >
              选择
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination-container">
              <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import request from '@/utils/request'

// 定义事件
const emit = defineEmits<{
  selectPatient: [patient: any]
}>()

// 响应式数据
const loading = ref(false)
const patientList = ref<any[]>([])

// 搜索表单
const searchForm = reactive({
  name: '',
  code: ''
})

// 分页
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

// 获取患者列表
const getPatientList = async () => {
  loading.value = true
  try {
    const params: any = {
      page: pagination.page,
      size: pagination.size
    }
    
    // 添加搜索条件
    if (searchForm.name && searchForm.name.trim()) {
      params.name = searchForm.name.trim()
    }
    if (searchForm.code && searchForm.code.trim()) {
      params.code = searchForm.code.trim()
    }
    
    const response = await request.get('/patients', { params })
    const { code, data, message: actualMessage } = response
    
    if (code === 200) {
      const pageData = data
      // 处理分页数据的不同结构
      if (pageData && typeof pageData === 'object') {
        if (Array.isArray(pageData)) {
          // 如果返回的是数组，说明没有分页
          patientList.value = pageData
          pagination.total = pageData.length
        } else if (pageData.records) {
          // MyBatis Plus 分页结构
          patientList.value = pageData.records || []
          pagination.total = pageData.total || 0
        } else if (pageData.content) {
          // Spring Data 分页结构
          patientList.value = pageData.content || []
          pagination.total = pageData.totalElements || 0
        } else {
          // 其他情况，直接使用data
          patientList.value = Array.isArray(pageData) ? pageData : []
          pagination.total = Array.isArray(pageData) ? pageData.length : 0
        }
      } else {
        patientList.value = []
        pagination.total = 0
      }
    } else {
      console.log('响应失败，code不等于200')
      ElMessage.error(actualMessage || '获取患者列表失败')
      patientList.value = []
      pagination.total = 0
    }
  } catch (error) {
    console.error('获取患者列表错误:', error)
    ElMessage.error('获取患者列表失败，请检查网络连接')
    patientList.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  getPatientList()
}

// 重置搜索
const handleReset = () => {
  searchForm.name = ''
  searchForm.code = ''
  pagination.page = 1
  getPatientList()
}

// 分页变化
const handleSizeChange = (size: number) => {
  pagination.size = size
  pagination.page = 1
  getPatientList()
}

const handleCurrentChange = (page: number) => {
  pagination.page = page
  getPatientList()
}

// 性别文本转换
const getGenderText = (gender: string) => {
  const genderMap = { M: '男', F: '女', U: '保密' }
  return genderMap[gender as keyof typeof genderMap] || '保密'
}

// 时间格式化函数
const formatDateTime = (dateTime: string) => {
  if (!dateTime) return '—'
  try {
    const date = new Date(dateTime)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    const hours = String(date.getHours()).padStart(2, '0')
    const minutes = String(date.getMinutes()).padStart(2, '0')
    const seconds = String(date.getSeconds()).padStart(2, '0')
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
  } catch (error) {
    return dateTime
  }
}

// 选择患者
const selectPatient = (patient: any) => {
  emit('selectPatient', patient)
}

// 行点击事件
const handleRowClick = (row: any) => {
  selectPatient(row)
}

// 初始化
onMounted(() => {
  getPatientList()
})
</script>

<style scoped>
.patient-list-selector {
  max-height: 400px;
  overflow-y: auto;
}

.search-bar {
  background: #f8f9fa;
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 12px;
}

.search-bar .el-form {
  margin: 0;
}

.search-bar .el-form-item {
  margin-bottom: 0;
}

.patient-table {
  margin-bottom: 12px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 12px;
}

/* 表格行悬停效果 */
.el-table tbody tr:hover > td {
  background-color: #f0f9ff !important;
}

/* 选中行样式 */
.el-table tbody tr.current-row > td {
  background-color: #e1f3ff !important;
}
</style> 