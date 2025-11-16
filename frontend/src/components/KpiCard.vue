<template>
  <el-card>
    <div style="font-size:14px;color:#888">{{title}}</div>
    <div style="font-size:24px;font-weight:700;margin-top:8px">{{value}}</div>
  </el-card>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
const props = defineProps<{ title:string, value:any }>();
</script>