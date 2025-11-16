<template>
  <div style="padding:16px">
    <el-button @click="$router.push('/')">返回</el-button>
    <h3>告警列表</h3>
    <el-table :data="events">
      <el-table-column prop="eventId" label="ID" width="80"/>
      <el-table-column prop="rule.ruleName" label="规则"/>
      <el-table-column prop="county.countyName" label="县"/>
      <el-table-column prop="year" label="年份"/>
      <el-table-column prop="metricValue" label="指标值"/>
      <el-table-column prop="triggeredAt" label="触发时间"/>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import api from '@/api';
const events = ref([]);
onMounted(async ()=>{
  const res = await api.getAlerts();
  events.value = res.data;
});
</script>