<template>
  <div>
    <header class="app-header">
      <h1>贫困县数据仪表盘</h1>
      <nav>
        <router-link to="/">首页</router-link>
        <router-link to="/dashboard">仪表盘</router-link>
        <router-link to="/alerts">告警</router-link>
        <!-- 登录状态下显示个人中心和退出按钮 -->
        <span v-if="token">
          <router-link to="/profile">个人中心</router-link>
          <el-button type="text" @click="logout">退出</el-button>
        </span>
        <!-- 未登录显示登录按钮 -->
        <router-link v-else to="/login">登录</router-link>
      </nav>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const token = ref(localStorage.getItem('token'));

// 监听token变化，更新全局请求头
watchEffect(() => {
  if (token.value) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token.value}`;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
});

const logout = () => {
  localStorage.removeItem('token');
  token.value = null;
  router.push('/login');
};
</script>