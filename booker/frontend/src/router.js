import Vue from 'vue'
import Router from 'vue-router'

import HelloWorld from '@/components/HelloWorld'
import sysAdminView from '@/views/sysAdminView'
import AirlineView from '@/components/airlines/index'
import addAirline from '@/components/airlines/addAirline'
import airlinesTable from '@/components/airlines/airlinesTable'

import HotelView from '@/components/hotels/index'
import addHotel from '@/components/hotels/addHotel'
import hotelsTable from '@/components/hotels/hotelsTable'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: HelloWorld
    },
    {
      path: '/sysAdminView',
      component: sysAdminView
    },
    {
      path: '/airlines',
      component: AirlineView,
      children: [
        {
          path: 'add',
          component: addAirline
        },
        {
          path: '/',
          component: airlinesTable
        }
      ]
    },
    {
      path: '/hotels',
      component: HotelView,
      children: [
        {
          path: 'add',
          component: addHotel
        },
        {
          path: '/',
          component: hotelsTable
        }
      ]
    }
  ]
})
