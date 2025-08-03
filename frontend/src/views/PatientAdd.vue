<template>
  <div class="patient-add-container">
    <div class="patient-content">
      <!-- 面包屑导航 -->
      <div class="breadcrumb-container">
        <div class="breadcrumb-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item @click="$router.push('/dashboard')">首页</el-breadcrumb-item>
            <el-breadcrumb-item @click="$router.push('/patient')">患者管理</el-breadcrumb-item>
            <el-breadcrumb-item>新增患者</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="breadcrumb-right">
          <el-button type="primary" @click="backToPatientList" class="back-btn">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
        </div>
      </div>

      <!-- 表单容器 -->
      <div class="form-container">
        <div class="form-header">
          <h2>新增患者信息</h2>
          <p>请填写以下患者基本信息</p>
        </div>

        <el-form
          ref="formRef"
          :model="patientForm"
          :rules="formRules"
          label-width="100px"
          size="large"
          class="patient-form"
        >

          <el-form-item label="患者编号" prop="patientCode">
            <el-input
              v-model="patientForm.patientCode"
              placeholder="系统自动生成 P_YYMMDD_000"
              disabled
            >
            </el-input>
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

          <el-row :gutter="40">
            <el-col :span="24">
              <el-form-item label="备注" prop="remark">
                <el-input
                  v-model="patientForm.remark"
                  type="textarea"
                  :rows="4"
                  placeholder="请输入备注信息（可选）"
                  maxlength="500"
                  show-word-limit
                />
              </el-form-item>
            </el-col>
          </el-row>

          <!-- 操作按钮 -->
          <div class="form-actions">
            <el-button size="large" @click="handleCancel">
              取消
            </el-button>
            <el-button type="primary" size="large" @click="handleSubmit" :loading="submitting">
              {{ submitting ? '保存中...' : '保存' }}
            </el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import request from '@/utils/request'

const router = useRouter()

// 响应式数据
const submitting = ref(false)
const formRef = ref<FormInstance>()

// 患者表单
const patientForm = reactive({
  patientCode: '',
  firstName: '',
  lastName: '',
  gender: 'M',
  dateOfBirth: '',
  remark: ''
})

// 表单验证规则
const formRules = {
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

// 返回患者列表
const backToPatientList = () => {
  router.push('/patient')
}



// 取消操作
const handleCancel = async () => {
  // 检查是否有未保存的内容
  const hasData = patientForm.firstName || patientForm.lastName || patientForm.dateOfBirth || patientForm.remark
  
  if (hasData) {
    try {
      await ElMessageBox.confirm('确定要取消吗？未保存的内容将会丢失', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '继续编辑',
        type: 'warning'
      })
    } catch (error) {
      return // 用户选择继续编辑
    }
  }
  
  backToPatientList()
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    submitting.value = true
    try {
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
      
      const response = await request.post('/patients/add', submitData)
      
      if (response.data.code === 200) {
        ElMessage.success('新增患者成功')
        backToPatientList()
      } else {
        ElMessage.error(response.data.msg || '新增患者失败')
      }
    } catch (error) {
      ElMessage.error('新增患者失败')
      console.error(error)
    } finally {
      submitting.value = false
    }
  })
}

// 初始化
onMounted(() => {
  // 页面加载完成
})
</script>

<style scoped>
.patient-add-container {
  background: #f5f5f5;
  min-height: 100vh;
}

.patient-content {
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 0px;
  padding-bottom: 20px;
  min-height: calc(100vh - 110px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.breadcrumb-container {
  background: white;
  padding: 8px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 5px;
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

.form-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  margin-top: 15px;
}

.form-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 30px 40px;
  text-align: center;
}

.form-header h2 {
  margin: 0 0 10px 0;
  font-size: 24px;
  font-weight: 600;
}

.form-header p {
  margin: 0;
  opacity: 0.9;
  font-size: 14px;
}

.patient-form {
  padding: 40px;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 40px;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
}

:deep(.el-breadcrumb__item:last-child .el-breadcrumb__inner) {
  color: #606266;
}

:deep(.el-breadcrumb__item:not(:last-child) .el-breadcrumb__inner:hover) {
  color: #409eff;
  cursor: pointer;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .patient-content {
    padding-left: 15px;
    padding-right: 15px;
    padding-top: 15px;
    padding-bottom: 15px;
  }
  
  .breadcrumb-container {
    flex-direction: column;
    gap: 5px;
    align-items: stretch;
  }
  
  .breadcrumb-right {
    justify-content: center;
  }
  
  .form-header {
    padding: 20px;
  }
  
  .form-header h2 {
    font-size: 20px;
  }
  
  .patient-form {
    padding: 20px;
  }
  
  .el-col {
    margin-bottom: 10px;
  }
}

/* 姓名输入组样式 */
.name-input-group {
  display: flex;
  align-items: flex-start;
  width: 100%;
}

.inline-form-item {
  margin-bottom: 0;
}
</style> 