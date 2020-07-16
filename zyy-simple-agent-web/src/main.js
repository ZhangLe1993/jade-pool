import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';


/* axios Ajax请求*/
import api from './service';
Vue.prototype.$api = api;

Vue.config.productionTip = false;

/* elementUI */
Vue.use(ElementUI);

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
