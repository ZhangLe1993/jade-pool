import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/Index.vue'
import Agent from '../views/Agent.vue'
import Login from '../views/Login.vue'
import Console from '../views/Console.vue'
import Terminal from '../views/Terminal.vue'
import Tesseract from '../views/Tesseract.vue'
import Table from '../views/Table.vue'
Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Index',
        component: Index,
        meta:  {title: '首页'},
    },
    {
        path: '/index',
        name: 'Index',
        component: Index,
        meta:  {title: '首页'},
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta:  {title: '登录'},
    },
    {
      path: '/agent',
      name: 'Agent',
      component: Agent,
      meta:  {title: '统一配置中心'},
    },
    {
      path: '/console',
      name: 'Console',
      component: Console,
      meta:  {title: '数据开发平台'},
    },
    {
        path: '/terminal',
        name: 'Terminal',
        component: Terminal,
        meta:  {title: '远程链接终端'},
    },
    {
        path: '/tesseract',
        name: 'Tesseract',
        component: Tesseract,
        meta:  {title: '图片识别'},
    },
    {
        path: '/sheet',
        name: 'Table',
        component: Table,
        meta:  {title: '表格'},
    },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

const originalPush = VueRouter.prototype.push
  VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router
