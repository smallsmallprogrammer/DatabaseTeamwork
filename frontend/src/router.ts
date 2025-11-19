import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/pages/Home.vue';
import County from '@/pages/County.vue';
import Analysis from '@/pages/Analysis.vue';
import Alerts from '@/pages/Alerts.vue';
import Login from '@/pages/Login.vue';
import Users from '@/pages/Users.vue';
import Dashboard from '@/pages/Dashboard.vue';
import Profile from '@/pages/Profile.vue';

const routes = [
  { path: '/home', component: Home },
  { path: '/county', component: County },
  { path: '/analysis', component: Analysis },
  { path: '/alerts', component: Alerts },
  { path: '/login', component: Login },
  { path: '/users', component: Users },
  { path: '/dashboard', component: Dashboard },
  { path: '/', redirect: '/home' },
  { path: '/profile', component: Profile, meta: { requiresAuth: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

// 增强路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  
  if (requiresAuth && !token) {
    next('/login');
  } else if (to.path === '/login' && token) {
    // 已登录用户访问登录页自动跳转到首页
    next('/');
  } else {
    next();
  }
});
