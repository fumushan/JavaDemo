import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import httpMethods from './assets/common/http.js'

import UEditorHelper from './assets/ueditor'

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(httpMethods);
Vue.use(UEditorHelper);

new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
});
