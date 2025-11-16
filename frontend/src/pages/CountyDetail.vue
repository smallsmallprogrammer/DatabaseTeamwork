<template>
  <div style="padding:16px">
    <el-button @click="$router.push('/')">返回</el-button>
    <h3>{{countyName}}</h3>
    <div id="chart" style="height:360px"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import api from '@/api';
import { useRoute } from 'vue-router';

const route = useRoute();
const id = Number(route.params.id);
const countyName = ref('County');

onMounted(async () => {
  const res = await api.getCountyIndicators(id);
  const data = res.data;
  if (data.length>0) {
    countyName.value = data.get && data.get(0) ? data[0].county?.countyName || 'County' : (data[0].county?.countyName || 'County');
  }
  const years = data.map((d:any)=>d.year);
  const gdp = data.map((d:any)=>d.gdp || 0);
  const chart = echarts.init(document.getElementById('chart') as HTMLDivElement);
  chart.setOption({
    xAxis: { type: 'category', data: years },
    yAxis: { type: 'value' },
    series: [{ data: gdp, type: 'line', name: 'GDP' }]
  });
});
</script>