import Vue from "vue";
import App from "./App";
import router from "./router";
import VueRouter from 'vue-router';

Vue.use(VueRouter);

// Vue.component('myc', {  //gobal components
//   template: '<h1>Are you working?</h1>',
// })
const router = new VueRouter({
  mode: 'history',
  base: __dirname,
  routes: [
    { path: '/', component: App },
  ]
});
Vue.config.productionTip = false;

new Vue({
  el: "#app",
  components: { App },
  template: "<App/>",
  render: h => h(App)
}).$mount('#app');

// new Vue({
//   el: '#app',
//   router,
//   render: h => h(App)
// })
