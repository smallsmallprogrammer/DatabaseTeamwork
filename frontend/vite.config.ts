import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';


export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src') // 这里映射 @ 到 src 目录
    }
  },
  server: {
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
});
