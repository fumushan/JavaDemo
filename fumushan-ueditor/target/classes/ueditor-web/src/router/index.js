import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home.vue'
import UEditor from  '../components/ueditor.vue'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      redirect: "/home"
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/UEditor',
      name: 'UEditor',
      component: UEditor
    },
  ]
})
