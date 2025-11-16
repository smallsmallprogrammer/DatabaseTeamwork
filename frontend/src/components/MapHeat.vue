<template>
  <el-card>
    <div v-for="c in data" :key="c.countyId" style="padding:8px;cursor:pointer" @click="$emit('click',c)">
      {{c.countyName}} — {{(c.value*100).toFixed(2)}}%
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
const props = defineProps<{ data:any[] }>();
</script>