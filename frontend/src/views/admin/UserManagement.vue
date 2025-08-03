<template>
  <div class="user-management">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        新增用户
      </el-button>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-form :model="searchForm" inline>
          <el-form-item label="用户名">
            <el-input 
              v-model="searchForm.username" 
              placeholder="请输入用户名"
              clearable
            />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
              <el-option label="正常" value="0" />
              <el-option label="停用" value="1" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">
              <el-icon><Search /></el-icon>
              搜索
            </el-button>
            <el-button @click="handleReset">
              <el-icon><RefreshRight /></el-icon>
              重置
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <el-table 
        :data="tableData" 
        stripe 
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="userId" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickName" label="昵称" width="120" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === '0' ? 'success' : 'danger'">
              {{ scope.row.status === '0' ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="scope">
            <el-button 
              size="small" 
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button 
              size="small" 
              :type="scope.row.status === '0' ? 'warning' : 'success'"
              @click="handleToggleStatus(scope.row)"
            >
              {{ scope.row.status === '0' ? '停用' : '启用' }}
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, RefreshRight } from '@element-plus/icons-vue'

// 搜索表单
const searchForm = reactive({
  username: '',
  status: ''
})

// 表格数据
const tableData = ref([
  {
    userId: 1,
    username: 'admin',
    nickName: '管理员',
    email: 'admin@example.com',
    phone: '13800138000',
    status: '0',
    createTime: '2024-01-01 10:00:00'
  },
  {
    userId: 2,
    username: 'doctor',
    nickName: '医生',
    email: 'doctor@example.com',
    phone: '13800138001',
    status: '0',
    createTime: '2024-01-02 14:30:00'
  },
  {
    userId: 3,
    username: 'nurse',
    nickName: '护士',
    email: 'nurse@example.com',
    phone: '13800138002',
    status: '1',
    createTime: '2024-01-03 09:15:00'
  }
])

// 加载状态
const loading = ref(false)

// 分页
const pagination = reactive({
  current: 1,
  size: 10,
  total: 3
})

// 搜索
const handleSearch = () => {
  console.log('搜索:', searchForm)
  // 这里应该调用API搜索
}

// 重置
const handleReset = () => {
  searchForm.username = ''
  searchForm.status = ''
  handleSearch()
}

// 新增
const handleAdd = () => {
  ElMessage.info('新增用户功能开发中...')
}

// 编辑
const handleEdit = (row: any) => {
  ElMessage.info(`编辑用户 ${row.username} 功能开发中...`)
}

// 切换状态
const handleToggleStatus = async (row: any) => {
  const action = row.status === '0' ? '停用' : '启用'
  try {
    await ElMessageBox.confirm(
      `确定要${action}用户 ${row.username} 吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // 这里应该调用API更新状态
    row.status = row.status === '0' ? '1' : '0'
    ElMessage.success(`${action}成功`)
  } catch (error) {
    // 用户取消操作
  }
}

// 删除
const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除用户 ${row.username} 吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    // 这里应该调用API删除
    const index = tableData.value.findIndex(item => item.userId === row.userId)
    if (index > -1) {
      tableData.value.splice(index, 1)
      pagination.total--
    }
    ElMessage.success('删除成功')
  } catch (error) {
    // 用户取消操作
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  pagination.size = size
  // 重新加载数据
}

// 当前页变化
const handleCurrentChange = (current: number) => {
  pagination.current = current
  // 重新加载数据
}

// 页面挂载
onMounted(() => {
  // 初始化数据
})
</script>

<style scoped>
.user-management {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #333;
}

.search-bar {
  margin-bottom: 20px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 4px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-management {
    padding: 10px;
  }
  
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }
  
  .search-bar :deep(.el-form) {
    flex-direction: column;
  }
  
  .search-bar :deep(.el-form-item) {
    width: 100%;
    margin-bottom: 15px;
  }
  
  :deep(.el-table) {
    font-size: 12px;
  }
}
</style> 