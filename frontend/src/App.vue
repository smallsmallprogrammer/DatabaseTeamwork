<template>
  <div>
    <header class="app-header">
      <h1>贫困县数据仪表盘</h1>
      <nav>
        <router-link to="/">首页</router-link>
        <router-link to="/dashboard">仪表盘</router-link>
        <router-link to="/alerts">告警</router-link>
        <span v-if="token">
          <el-button type="text" @click="logout">退出</el-button>
        </span>
        <router-link v-else to="/login">登录</router-link>
      </nav>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const token = ref(localStorage.getItem('token'));

const logout = () => {
  localStorage.removeItem('token');
  token.value = null;
  router.push('/login');
};
</script>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #2c3e50;
  color: #fff;
}
.app-header nav a {
  margin: 0 10px;
  color: #fff;
  text-decoration: none;
}
.app-header nav a.router-link-active {
  font-weight: bold;
  border-bottom: 2px solid #fff;
}
main {
  padding: 20px;
}
</style>
