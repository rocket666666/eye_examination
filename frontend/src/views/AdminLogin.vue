<template>
  <div class="admin-login-container">
    <div class="admin-login-card">
      <div class="admin-login-header">
        <h2>眼科检查系统</h2>
        <p>管理员登录</p>
      </div>
      
      <el-form 
        ref="adminLoginFormRef" 
        :model="adminLoginForm" 
        :rules="adminLoginRules" 
        class="admin-login-form"
        @keyup.enter="handleAdminLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="adminLoginForm.username"
            placeholder="请输入管理员用户名"
            size="large"
            clearable
            :prefix-icon="User"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="adminLoginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            clearable
            show-password
            :prefix-icon="Lock"
          />
        </el-form-item>
        
        <el-form-item prop="captcha">
          <div class="captcha-container">
            <el-input
              v-model="adminLoginForm.captcha"
              placeholder="请输入验证码"
              size="large"
              clearable
              :prefix-icon="Key"
              style="flex: 1; margin-right: 10px;"
            />
            <div class="captcha-image" @click="refreshCaptcha">
              <span v-if="!captchaUrl">点击获取验证码</span>
              <img v-else :src="captchaUrl" alt="验证码" />
            </div>
          </div>
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            size="large" 
            :loading="loading"
            @click="handleAdminLogin"
            style="width: 100%;"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="admin-login-footer">
        <el-link type="primary" @click="$router.push('/login')">
          返回业务登录
        </el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { User, Lock, Key } from '@element-plus/icons-vue'
import { useAuthStore } from '@/stores/auth'
import type { LoginRequest } from '@/types/auth'

const router = useRouter()
const authStore = useAuthStore()

// 表单引用
const adminLoginFormRef = ref<FormInstance>()

// 加载状态
const loading = ref(false)

// 验证码URL
const captchaUrl = ref('')

// 管理员登录表单
const adminLoginForm = reactive<LoginRequest>({
  username: '',
  password: '',
  captcha: '',
  uuid: ''
})

// 表单验证规则
const adminLoginRules = {
  username: [
    { required: true, message: '请输入管理员用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 4, max: 6, message: '验证码长度在 4 到 6 个字符', trigger: 'blur' }
  ]
}

// 刷新验证码
const refreshCaptcha = () => {
  const uuid = Date.now().toString()
  adminLoginForm.uuid = uuid
  captchaUrl.value = `/api/captcha/image?uuid=${uuid}`
}

// 管理员登录处理
const handleAdminLogin = async () => {
  if (!adminLoginFormRef.value) return
  
  await adminLoginFormRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    
    try {
      await authStore.loginAction(adminLoginForm)
      ElMessage.success('登录成功')
      router.push('/admin/dashboard')
    } catch (error: any) {
      ElMessage.error(error.message || '登录失败')
      // 登录失败后刷新验证码
      refreshCaptcha()
    } finally {
      loading.value = false
    }
  })
}

// 页面挂载
onMounted(() => {
  // 如果已登录，跳转到对应页面
  if (authStore.isAuthenticated) {
    if (authStore.isAdmin) {
      router.push('/admin/dashboard')
    } else {
      router.push('/dashboard')
    }
  }
  
  // 初始化验证码
  refreshCaptcha()
})
</script>

<style scoped>
.admin-login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
}

.admin-login-card {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.admin-login-header {
  text-align: center;
  margin-bottom: 30px;
}

.admin-login-header h2 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 24px;
  font-weight: 600;
}

.admin-login-header p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.admin-login-form {
  margin-bottom: 20px;
}

.captcha-container {
  display: flex;
  align-items: center;
  width: 100%;
}

.captcha-image {
  width: 100px;
  height: 40px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #666;
  background: #f5f7fa;
}

.captcha-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.admin-login-footer {
  text-align: center;
  margin-top: 20px;
}

/* 响应式设计 */
@media (max-width: 480px) {
  .admin-login-card {
    width: 90%;
    padding: 30px 20px;
  }
  
  .captcha-container {
    flex-direction: column;
    align-items: stretch;
  }
  
  .captcha-image {
    margin-top: 10px;
    width: 100%;
  }
}
</style> 