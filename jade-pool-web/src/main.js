import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
/* 引入 阿里 icon */
import './assets/iconfont/iconfont.css'
/* 引入underscore   cnpm install vue-underscore */
import underscore from 'vue-underscore';
/* xterm */
/*
* cnpm install --save xterm
* cnpm install --save xterm-addon-fit
*/

/*
* echarts
* cnpm install echarts --save
* */
import echarts from 'echarts'
Vue.use(echarts);
Vue.prototype.$echarts = echarts;

/* axios Ajax请求*/
import api from './service';
Vue.prototype.$api = api;

/*
  tesseract
* cnpm install --save tesseract.js
* */
Vue.config.productionTip = false;

/* elementUI */
Vue.use(ElementUI);

/* underscore */
/* cnpm install --save vue-underscore */
Vue.use(underscore);

router.beforeEach((to, from, next) => {
  if(to.meta.title) {
    document.title = to.meta.title;
  }
  next();
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
