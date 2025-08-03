<template>
  <div class="admin-dashboard">
    <div class="dashboard-stats">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <div class="stats-icon">
                <el-icon size="40"><User /></el-icon>
              </div>
              <div class="stats-info">
                <h3>用户总数</h3>
                <p>156</p>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <div class="stats-icon">
                <el-icon size="40"><View /></el-icon>
              </div>
              <div class="stats-info">
                <h3>今日检查</h3>
                <p>23</p>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <div class="stats-icon">
                <el-icon size="40"><Document /></el-icon>
              </div>
              <div class="stats-info">
                <h3>报告生成</h3>
                <p>89</p>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <div class="stats-icon">
                <el-icon size="40"><Calendar /></el-icon>
              </div>
              <div class="stats-info">
                <h3>本月预约</h3>
                <p>1,234</p>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    
    <div class="dashboard-charts">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card>
            <template #header>
              <h3>系统使用情况</h3>
            </template>
            <div class="chart-placeholder">
              <el-icon size="60"><TrendCharts /></el-icon>
              <p>图表功能开发中...</p>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <template #header>
              <h3>最近活动</h3>
            </template>
            <el-timeline>
              <el-timeline-item timestamp="2024-01-15 10:30" placement="top">
                <el-card>
                  <h4>系统登录</h4>
                  <p>管理员 admin 登录系统</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2024-01-15 09:45" placement="top">
                <el-card>
                  <h4>新用户注册</h4>
                  <p>用户 doctor01 注册成功</p>
                </el-card>
              </el-timeline-item>
              <el-timeline-item timestamp="2024-01-15 08:20" placement="top">
                <el-card>
                  <h4>检查完成</h4>
                  <p>患者 张三 完成眼科检查</p>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { User, View, Document, Calendar, TrendCharts } from '@element-plus/icons-vue'
</script>

<style scoped>
.admin-dashboard {
  padding: 20px;
}

.dashboard-stats {
  margin-bottom: 30px;
}

.stats-card {
  height: 120px;
}

.stats-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stats-icon {
  margin-right: 20px;
  color: #409eff;
  background: #ecf5ff;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stats-info h3 {
  margin: 0 0 10px 0;
  color: #666;
  font-size: 14px;
  font-weight: 400;
}

.stats-info p {
  margin: 0;
  color: #333;
  font-size: 28px;
  font-weight: 600;
}

.dashboard-charts {
  margin-bottom: 30px;
}

.chart-placeholder {
  height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #ccc;
}

.chart-placeholder p {
  margin: 10px 0 0 0;
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .admin-dashboard {
    padding: 10px;
  }
  
  .dashboard-stats :deep(.el-col) {
    margin-bottom: 20px;
  }
  
  .stats-content {
    flex-direction: column;
    text-align: center;
  }
  
  .stats-icon {
    margin: 0 0 15px 0;
  }
  
  .dashboard-charts :deep(.el-col) {
    margin-bottom: 20px;
  }
}
</style> 