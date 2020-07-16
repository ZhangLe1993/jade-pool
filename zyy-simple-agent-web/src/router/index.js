import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/Index.vue'
import Login from '../views/Login.vue'
Vue.use(VueRouter);

  const routes = [
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta:  {title: '登录'},
    },
    {
      path: '/index',
      name: 'Index',
      component: Index,
      meta:  {title: '统一配置中心'},
    },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
