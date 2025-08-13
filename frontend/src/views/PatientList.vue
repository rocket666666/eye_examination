<template>
  <div class="list-page-container">

    <div class="list-page-content">
      <!-- 面包屑导航 -->
      <div class="breadcrumb-container">
        <div class="breadcrumb-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item @click="$router.push('/dashboard')">首页</el-breadcrumb-item>
            <el-breadcrumb-item>患者管理</el-breadcrumb-item>
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

        <!-- 操作按钮栏 -->
        <div class="action-bar">
          <div class="action-left">
            <el-button type="success" @click="handleAdd" class="action-btn">
              <el-icon><Plus /></el-icon>
              新增患者
            </el-button>
            <el-button type="warning" @click="handleSelectedEdit" class="action-btn" :disabled="selectedPatients.length !== 1">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button type="danger" @click="handleSelectedDelete" class="action-btn" :disabled="selectedPatients.length === 0">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </div>
          <!-- <div class="action-right">
            <el-button type="info" @click="handleExport" class="action-btn">
              <el-icon><Download /></el-icon>
              导出数据
            </el-button>
            <el-button type="primary" @click="handleImport" class="action-btn">
              <el-icon><Upload /></el-icon>
              导入数据
            </el-button>
          </div> -->
        </div>

        <!-- 患者列表 -->
        <div class="table-container">
          <el-table
            :data="patientList"
            v-loading="loading"
            stripe
            @sort-change="handleSortChange"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column prop="patientCode" label="患者编号" width="120" />
            <el-table-column prop="fullName" label="姓名" width="100" />
            <el-table-column prop="gender" label="性别" width="80">
              <template #default="{ row }">
                <span>{{ getGenderText(row.gender) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄" width="80" />
            <el-table-column prop="phone" label="联系电话" width="130" />
            <el-table-column prop="lastCheckDate" label="上次检查时间" width="180">
              <template #default="{ row }">
                <span>{{ formatDateTime(row.lastCheckDate) || '暂无' }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="lastRecordCode" label="上次报告编码" width="180">
              <template #default="{ row }">
                <span>{{ row.lastRecordCode || '暂无' }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="createdAt" label="创建时间" width="180">
              <template #default="{ row }">
                <span>{{ formatDateTime(row.createdAt) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="250" fixed="right">
              <template #default="{ row }">
                <el-button link type="primary" @click="handleView(row)">
                  查看
                </el-button>
                <el-button link type="primary" @click="handleEdit(row)">
                  编辑
                </el-button>
                <el-button link type="danger" @click="handleDelete(row)">
                  删除
                </el-button>
                <el-button link type="primary" @click="examination(row)">
                  检查
                </el-button>
              </template>
            </el-table-column>
          </el-table>

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
      </div>
    </div>

    <!-- 患者详情弹窗 -->
    <el-dialog
      v-model="detailVisible"
      title="患者详情"
      width="60%"
      :before-close="handleDetailClose"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="患者编号">
          {{ currentPatient?.patientCode }}
        </el-descriptions-item>
        <el-descriptions-item label="姓名">
          {{ (currentPatient?.firstName || '') + (currentPatient?.lastName || '') }}
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          {{ getGenderText(currentPatient?.gender) }}
        </el-descriptions-item>
        <el-descriptions-item label="姓">
          {{ currentPatient?.firstName || '无' }}
        </el-descriptions-item>
        <el-descriptions-item label="名">
          {{ currentPatient?.lastName || '无' }}
        </el-descriptions-item>
        <el-descriptions-item label="出生日期">
          {{ currentPatient?.dateOfBirth || '无' }}
        </el-descriptions-item>
        <el-descriptions-item label="上次检查时间">
          {{ currentPatient?.lastCheckDate || '暂无' }}
        </el-descriptions-item>
        <el-descriptions-item label="上次报告编码">
          {{ currentPatient?.lastRecordCode || '暂无' }}
        </el-descriptions-item>

        <el-descriptions-item label="备注" span="2">
          {{ currentPatient?.remark || '无' }}
        </el-descriptions-item>
      </el-descriptions>
      
      <template #footer>
        <el-button @click="detailVisible = false">关闭</el-button>
        <el-button type="primary" @click="handleEditFromDetail">编辑</el-button>
      </template>
    </el-dialog>

    <!-- 检查类型选择对话框 -->
    <el-dialog
      v-model="examinationDialogVisible"
      title="选择检查类型"
      width="400px"
      center
      :show-close="true"
      :close-on-click-modal="false"
    >
      <div class="examination-choice-container">
        <div class="choice-title">请选择要进行的检查类型：</div>
        <div class="choice-buttons">
          <el-button 
            type="primary" 
            size="large" 
            class="choice-btn svv-btn"
            @click="goToSVVCheck"
          >
            <el-icon><View /></el-icon>
            SVV检查
          </el-button>
          
          <el-button 
            type="success" 
            size="large" 
            class="choice-btn svh-btn"
            @click="goToSVHCheck"
          >
            <el-icon><View /></el-icon>
            SVH检查
          </el-button>
        </div>
      </div>
      
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="examinationDialogVisible = false">取消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 患者表单弹窗 -->
    <el-dialog
      v-model="formVisible"
      :title="formMode === 'add' ? '新增患者' : '编辑患者'"
      width="500px"
      :before-close="handleFormClose"
    >
      <el-form
        ref="formRef"
        :model="patientForm"
        :rules="formRules"
        label-width="100px"
        size="large"
      >
        <el-form-item label="患者编号">
          <el-input
            value="系统保存时自动生成"
            placeholder="P_YYMMDD_000"
            disabled
          />
        </el-form-item>

        <el-form-item label="姓" prop="firstName">
          <el-input
            v-model="patientForm.firstName"
            placeholder="请输入姓氏"
            clearable
          />
        </el-form-item>

        <el-form-item label="名" prop="lastName">
          <el-input
            v-model="patientForm.lastName"
            placeholder="请输入名字"
            clearable
          />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-select v-model="patientForm.gender" placeholder="请选择性别" style="width: 100%">
            <el-option label="男" value="M" />
            <el-option label="女" value="F" />
            <el-option label="保密" value="U" />
          </el-select>
        </el-form-item>

        <el-form-item label="出生日期" prop="dateOfBirth">
          <el-date-picker
            v-model="patientForm.dateOfBirth"
            type="date"
            placeholder="请选择出生日期"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>



        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="patientForm.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注信息（可选）"
            maxlength="300"
            show-word-limit
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          {{ formMode === 'add' ? '新增' : '保存' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { Plus, Search, Refresh, ArrowLeft, Edit, Delete, Download, Upload } from '@element-plus/icons-vue'
import request from '@/utils/request'
import { useAuthStore } from '@/stores/auth'
import '@/styles/list-common.css'

const router = useRouter()
const authStore = useAuthStore()

// 响应式数据
const loading = ref(false)
const submitting = ref(false)

const patientList = ref<any[]>([])
const detailVisible = ref(false)
const formVisible = ref(false)
const formMode = ref<'add' | 'edit'>('add')
const currentPatient = ref<any>(null)
const formRef = ref<FormInstance>()
const selectedPatients = ref<any[]>([])
const examinationDialogVisible = ref(false)

// 搜索表单
const searchForm = reactive({
  name: '',
  code: ''
})

// 分页信息
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

// 患者表单
const patientForm = reactive({
  id: null,
  patientCode: '',
  firstName: '',
  lastName: '',
  gender: 'M',
  dateOfBirth: '',
  remark: ''
})

// 表单验证规则
const formRules = {
  patientCode: [
    { required: true, message: '请生成患者编号', trigger: 'blur' }
  ],
  firstName: [
    { required: true, message: '请输入患者姓氏', trigger: 'blur' }
  ],
  lastName: [
    { required: true, message: '请输入患者名字', trigger: 'blur' }
  ],
  dateOfBirth: [
    { required: true, message: '请选择出生日期', trigger: 'change' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ]
}

// 返回九宫格
const backToDashboard = () => {
  router.push('/dashboard')
}



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
    
    console.log('请求参数:', params)
    
    const response = await request.get('/patients', { params }) as any
    console.log('API响应 - 完整对象:', response)
    console.log('API响应 - response类型:', typeof response)
    console.log('API响应 - response.code:', response.code)
    console.log('API响应 - response.data:', response.data)
    
    // 先尝试直接访问 response.code (如果响应拦截器返回了处理后的数据)
    const actualCode = response.code || response.data?.code
    const actualData = response.data || response.data?.data
    const actualMessage = response.message || response.data?.message
    
    console.log('实际code:', actualCode)
    console.log('实际data:', actualData)
    
    if (actualCode === 200) {
      const pageData = actualData
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

// 排序变化
const handleSortChange = () => {
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

// 新增患者
const handleAdd = () => {
  formMode.value = 'add'
  resetForm()
  formVisible.value = true
}

// 查看患者详情
const handleView = (row: any) => {
  currentPatient.value = row
  detailVisible.value = true
}

// 编辑患者
const handleEdit = (row: any) => {
  formMode.value = 'edit'
  Object.assign(patientForm, row)
  formVisible.value = true
}

// 从详情页编辑
const handleEditFromDetail = () => {
  detailVisible.value = false
  handleEdit(currentPatient.value)
}

// 删除患者
const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除患者 "${row.fullName}" 吗？`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

                    const response = await request.post(`/patients/delete/${row.id}`) as any
    if (response.code === 200) {
      ElMessage.success('删除成功')
      getPatientList()
    } else {
        ElMessage.error(response.message || response.msg || '删除失败')
      }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
      console.error(error)
    }
  }
}

// 添加检查记录
const handleAddCheckRecord = (row: any) => {
  ElMessage.info('检查记录功能正在开发中...')
}

// 选择变化
const handleSelectionChange = (selection: any[]) => {
  selectedPatients.value = selection
}

// 编辑选中的患者（只能选择1个）
const handleSelectedEdit = () => {
  if (selectedPatients.value.length === 0) {
    ElMessage.warning('请选择要编辑的患者')
    return
  }
  if (selectedPatients.value.length > 1) {
    ElMessage.warning('只能选择一个患者进行编辑')
    return
  }
  
  handleEdit(selectedPatients.value[0])
}

// 删除选中的患者
const handleSelectedDelete = async () => {
  if (selectedPatients.value.length === 0) {
    ElMessage.warning('请选择要删除的患者')
    return
  }
  
  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedPatients.value.length} 位患者吗？`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // 执行删除操作
    let successCount = 0
    for (const patient of selectedPatients.value) {
      try {
        const response = await request.post(`/patients/delete/${patient.id}`) as any
        if (response.code === 200) {
          successCount++
        }
      } catch (error) {
        console.error(`删除患者 ${patient.fullName} 失败:`, error)
      }
    }
    
    if (successCount > 0) {
      ElMessage.success(`成功删除 ${successCount} 位患者`)
      getPatientList()
      selectedPatients.value = []
    } else {
      ElMessage.error('删除失败')
    }
    
  } catch (error) {
    // 用户取消操作
  }
}

// 导出数据
const handleExport = () => {
  ElMessage.info('导出功能正在开发中...')
}

// 导入数据
const handleImport = () => {
  ElMessage.info('导入功能正在开发中...')
}

// 检查患者
const examination = (row: any) => {
  // 将患者信息存储到本地存储，适配现有的缓存方式
  localStorage.setItem('selectedPatient', JSON.stringify(row))
  
  // 显示检查类型选择对话框
  examinationDialogVisible.value = true
}

// 跳转到SVV检查页面
const goToSVVCheck = () => {
  examinationDialogVisible.value = false
  router.push('/svv-check')
}

// 跳转到SVH检查页面
const goToSVHCheck = () => {
  examinationDialogVisible.value = false
  router.push('/svh-check')
}



// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    submitting.value = true
    try {
            let response: any
      if (formMode.value === 'add') {
        // 构建提交数据，自动生成fullName
        const submitData = {
          firstName: patientForm.firstName,
          lastName: patientForm.lastName,
          gender: patientForm.gender,
          dateOfBirth: patientForm.dateOfBirth,
          remark: patientForm.remark,
          fullName: patientForm.firstName + patientForm.lastName,
          status: 1 // 默认正常状态
        }
        response = await request.post('/patients/add', submitData) as any
      } else {
        response = await request.post(`/patients/edit/${patientForm.id}`, patientForm) as any
      }
      
      if (response.code === 200) {
        ElMessage.success(formMode.value === 'add' ? '新增成功' : '编辑成功')
        formVisible.value = false
        getPatientList()
      } else {
        ElMessage.error(response.message || response.msg || '操作失败')
      }
    } catch (error) {
      ElMessage.error('操作失败')
      console.error(error)
    } finally {
      submitting.value = false
    }
  })
}

// 重置表单
const resetForm = () => {
  Object.assign(patientForm, {
    id: null,
    firstName: '',
    lastName: '',
    gender: 'M',
    dateOfBirth: '',
    remark: ''
  })
  formRef.value?.resetFields()
}

// 关闭弹窗
const handleDetailClose = () => {
  detailVisible.value = false
}

const handleFormClose = () => {
  formVisible.value = false
  resetForm()
}

// 初始化
onMounted(() => {
  getPatientList()
})
</script>

<style scoped>
/* 页面特有样式 */
/* 姓名输入组样式 */
.name-input-group {
  display: flex;
  align-items: flex-start;
  width: 100%;
}

.inline-form-item {
  margin-bottom: 0;
}

/* 检查类型选择对话框样式 */
.examination-choice-container {
  text-align: center;
  padding: 20px 0;
}

.choice-title {
  font-size: 16px;
  color: #333;
  margin-bottom: 30px;
  font-weight: 500;
}

.choice-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
}

.choice-btn {
  width: 200px !important;
  height: 80px !important;
  display: flex !important;
  flex-direction: column !important;
  align-items: center !important;
  justify-content: center !important;
  gap: 8px !important;
  border-radius: 12px !important;
  font-size: 18px !important;
  font-weight: 600 !important;
  transition: all 0.3s ease !important;
  position: relative !important;
  overflow: hidden !important;
}

.choice-btn:hover {
  transform: translateY(-4px) !important;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15) !important;
}

.choice-btn .el-icon {
  font-size: 24px !important;
  margin-bottom: 4px !important;
}

.btn-description {
  font-size: 12px !important;
  font-weight: 400 !important;
  opacity: 0.8 !important;
  margin-top: 4px !important;
}

.svv-btn {
  background: linear-gradient(135deg, #409eff 0%, #337ecc 100%) !important;
  border: none !important;
}

.svv-btn:hover {
  background: linear-gradient(135deg, #66b1ff 0%, #409eff 100%) !important;
}

.svh-btn {
  background: linear-gradient(135deg, #67c23a 0%, #5daf34 100%) !important;
  border: none !important;
}

.svh-btn:hover {
  background: linear-gradient(135deg, #85ce61 0%, #67c23a 100%) !important;
}

.dialog-footer {
  text-align: center;
  padding-top: 20px;
}
</style> 