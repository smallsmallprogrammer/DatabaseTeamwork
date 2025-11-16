<template>
  <el-container>
    <el-header style="display:flex;align-items:center;justify-content:space-between">
      <h2>脱贫监测仪表盘（MVP）</h2>
      <el-date-picker v-model="year" type="year" placeholder="选择年份" @change="fetchSummary"/>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="6">
          <kpi-card title="关注县数" :value="metrics.atRiskCount"/>
        </el-col>
        <el-col :span="6">
          <kpi-card title="平均 GDP 增长" :value="(metrics.avgGdpYoy*100).toFixed(2) + '%'" />
        </el-col>
        <el-col :span="12">
          <el-button type="primary" @click="scan">立即扫描告警</el-button>
          <el-button @click="$router.push('/alerts')">查看告警</el-button>
        </el-col>
      </el-row>

      <el-row style="margin-top:20px">
        <el-col :span="16">
          <map-heat :data="mapData" @click="onCountyClick" />
        </el-col>
        <el-col :span="8">
          <el-card>
            <h4>未确认告警</h4>
            <div v-for="a in alerts" :key="a.eventId">{{a.county?.countyName}} — {{a.rule?.ruleName}} — {{a.year}}</div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import api from '@/api';
import KpiCard from '@/components/KpiCard.vue';
import MapHeat from '@/components/MapHeat.vue';

const year = ref<number | null>(new Date().getFullYear());
const metrics = ref<any>({ atRiskCount: 0, avgGdpYoy: 0 });
const mapData = ref<any[]>([]);
const alerts = ref<any[]>([]);

async function fetchSummary() {
  const res = await api.getSummary(year.value || undefined);
  metrics.value = res.data;
  mapData.value = res.data.map || [];
  const a = await api.getAlerts();
  alerts.value = a.data;
}

function onCountyClick(county:any) {
  window.location.href = `/counties/${county.countyId}`;
}

async function scan() {
  await api.scanAlerts();
  await fetchSummary();
}

onMounted(fetchSummary);
</script>