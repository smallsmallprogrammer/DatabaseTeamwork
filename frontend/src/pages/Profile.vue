<template>
  <el-card style="max-width: 800px; margin: 20px auto;">
    <div slot="header">
      <h2>个人中心</h2>
    </div>
    <div v-if="loading" class="loading-container">
      <el-loading-spinner size="large" />
      <p>加载用户信息中...</p>
    </div>
    <div v-else-if="error" class="error-container">
      <el-icon class="error-icon"><WarningFilled /></el-icon>
      <p>{{ error }}</p>
    </div>
    <div v-else class="profile-info">
      <el-descriptions column="1" border>
        <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ userInfo.fullname }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ userInfo.roleName }}</el-descriptions-item>
        <el-descriptions-item label="注册时间">{{ formatDate(userInfo.registerTime) }}</el-descriptions-item>
      </el-descriptions>
      <el-button style="margin-top: 20px;" @click="handleEdit">编辑资料</el-button>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { WarningFilled } from '@element-plus/icons-vue';
import api from '@/api';

const userInfo = ref({});
const loading = ref(true);
const error = ref('');

const fetchUserInfo = async () => {
  try {
    const res = await api.get('/users/current');
    userInfo.value = res.data;
    loading.value = false;
  } catch (e: any) {
    error.value = e.response?.data?.message || '获取用户信息失败';
    loading.value = false;
  }
};

const formatDate = (dateString: string) => {
  if (!dateString) return '';
  return new Date(dateString).toLocaleString();
};

const handleEdit = () => {
  // 编辑资料逻辑
};

onMounted(() => {
  fetchUserInfo();
});
</script>

<style scoped>
.loading-container, .error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 0;
}

.error-icon {
  color: #f56c6c;
  font-size: 24px;
  margin-bottom: 16px;
}
</style>