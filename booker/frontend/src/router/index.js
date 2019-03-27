import Vue from 'vue'
import Router from 'vue-router'

import HelloWorld from "@/components/HelloWorld"
import SysAdminView from "@/views/SysAdminView.vue"

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: HelloWorld
    },
    {
      path: '/sys_admin',
      component: SysAdminView
    },
  ]
})
