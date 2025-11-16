<template>
  <el-card style="max-width:400px;margin:50px auto;">
    <h2>{{ isLogin ? '登录' : '注册' }}</h2>

    <el-form :model="form" @submit.prevent="handleSubmit" label-width="80px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="密码">
        <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
      </el-form-item>

      <template v-if="!isLogin">
        <el-form-item label="姓名">
          <el-input v-model="form.fullname"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
      </template>

      <el-form-item>
        <el-button type="primary" @click="handleSubmit">{{ isLogin ? '登录' : '注册' }}</el-button>
        <el-button type="text" @click="toggleMode">
          {{ isLogin ? '去注册' : '去登录' }}
        </el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const isLogin = ref(true);

const form = reactive({
  username: '',
  password: '',
  fullname: '',
  email: '',
  roleId: 3, // 默认普通用户
});

const toggleMode = () => {
  isLogin.value = !isLogin.value;
  form.password = '';
  if (isLogin.value) {
    form.fullname = '';
    form.email = '';
  }
};

const handleSubmit = async () => {
  try {
    let res;
    if (isLogin.value) {
      res = await axios.post('/api/auth/login', {
        username: form.username,
        password: form.password,
      });
    } else {
      res = await axios.post('/api/auth/register', {
        username: form.username,
        password: form.password,
        fullname: form.fullname,
        email: form.email,
        roleId: form.roleId,
      });
    }

    // 保存 token 并跳转首页
    const token = res.data.token;
    localStorage.setItem('token', token);
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    alert(isLogin.value ? '登录成功' : '注册成功');
    router.push('/');
  } catch (e: any) {
    alert(e.response?.data?.message || e.message);
  }
};
</script>

<style scoped>
.el-card {
  padding: 20px;
}
h2 {
  text-align: center;
  margin-bottom: 20px;
}
</style>
