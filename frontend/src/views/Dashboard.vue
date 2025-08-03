<template>
  <div class="dashboard-container">
    
    <!-- 九宫格内容 -->
    <div class="dashboard-grid">
      <!-- 第一行 -->
      <div class="grid-item active" @click="handleNavigate('/patient')">
        <div class="grid-icon">
          <el-icon><User /></el-icon>
        </div>
        <div class="grid-title">患者管理</div>
        <div class="grid-desc">患者信息录入与管理</div>
      </div>
      
      <div class="grid-item active" @click="handleNavigate('/examination')">
        <div class="grid-icon">
          <el-icon><View /></el-icon>
        </div>
        <div class="grid-title">眼部检查</div>
        <div class="grid-desc">眼科检查项目管理</div>
      </div>
      
      <div class="grid-item active" @click="handleNavigate('/help')">
        <div class="grid-icon">
          <el-icon><QuestionFilled /></el-icon>
        </div>
        <div class="grid-title">帮助说明</div>
        <div class="grid-desc">系统使用说明文档</div>
      </div>
      
      <!-- 第二行 -->
      <div class="grid-item inactive">
        <div class="grid-icon">
          <el-icon><Document /></el-icon>
        </div>
        <div class="grid-title">检查报告</div>
        <div class="grid-desc">即将开放</div>
      </div>
      
      <div class="grid-item inactive">
        <div class="grid-icon">
          <el-icon><DataAnalysis /></el-icon>
        </div>
        <div class="grid-title">数据统计</div>
        <div class="grid-desc">即将开放</div>
      </div>
      
      <div class="grid-item inactive">
        <div class="grid-icon">
          <el-icon><Setting /></el-icon>
        </div>
        <div class="grid-title">系统设置</div>
        <div class="grid-desc">即将开放</div>
      </div>
      
      <!-- 第三行 -->
      <div class="grid-item inactive">
        <div class="grid-icon">
          <el-icon><Bell /></el-icon>
        </div>
        <div class="grid-title">消息通知</div>
        <div class="grid-desc">即将开放</div>
      </div>
      
      <div class="grid-item inactive">
        <div class="grid-icon">
          <el-icon><Calendar /></el-icon>
        </div>
        <div class="grid-title">预约管理</div>
        <div class="grid-desc">即将开放</div>
      </div>
      
      <div class="grid-item inactive">
        <div class="grid-icon">
          <el-icon><Files /></el-icon>
        </div>
        <div class="grid-title">档案管理</div>
        <div class="grid-desc">即将开放</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { 
  User, 
  View, 
  QuestionFilled, 
  Document, 
  DataAnalysis, 
  Setting, 
  Bell, 
  Calendar, 
  Files 
} from '@element-plus/icons-vue'
const router = useRouter()

// 导航处理
const handleNavigate = (path: string) => {
  router.push(path)
}


</script>

<style scoped>
.dashboard-container {
  min-height: calc(100vh - 110px);
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.grid-item {
  background: white;
  border-radius: 12px;
  padding: 40px 20px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 200px;
}

.grid-item.active:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.grid-item.inactive {
  opacity: 0.6;
  cursor: not-allowed;
}

.grid-icon {
  font-size: 48px;
  margin-bottom: 20px;
  color: #409eff;
}

.grid-item.inactive .grid-icon {
  color: #c0c4cc;
}

.grid-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.grid-item.inactive .grid-title {
  color: #909399;
}

.grid-desc {
  font-size: 14px;
  color: #666;
  line-height: 1.4;
}

.grid-item.inactive .grid-desc {
  color: #c0c4cc;
}



/* 响应式设计 */
@media (max-width: 1024px) {
  .dashboard-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    padding: 30px;
  }
}

@media (max-width: 768px) {
  .dashboard-container {
    padding: 15px;
  }
  
  .dashboard-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .grid-item {
    min-height: 150px;
    padding: 30px 20px;
  }
  
  .grid-icon {
    font-size: 36px;
    margin-bottom: 15px;
  }
  
  .grid-title {
    font-size: 18px;
  }
}
</style> 