<template>
  <el-form :model="form" @submit.prevent="handleRegister">
    <el-form-item label="用户名">
      <el-input v-model="form.username"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item label="姓名">
      <el-input v-model="form.fullname"></el-input>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="form.email"></el-input>
    </el-form-item>
    <el-button type="primary" @click="handleRegister">注册</el-button>
  </el-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import axios from 'axios';

const form = reactive({
  username: '',
  password: '',
  fullname: '',
  email: '',
  roleId: 3, // 普通用户
});

const handleRegister = async () => {
  try {
    const res = await axios.post('/api/auth/register', form);
    alert('注册成功，Token: ' + res.data.token);
    localStorage.setItem('token', res.data.token);
  } catch (e: any) {
    alert('注册失败: ' + e.response?.data?.message || e.message);
  }
};
</script>
