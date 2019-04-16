import Vue from 'vue'
import Router from 'vue-router'

import HelloWorld from '@/components/HelloWorld'

import AirlineView from '@/components/airlines/index'
import addAirline from '@/components/airlines/addAirline'
import editAirline from '@/components/airlines/editAirline'
import airlinesTable from '@/components/airlines/airlinesTable'

import HotelView from '@/components/hotels/index'
import addHotel from '@/components/hotels/addHotel'
import hotelsTable from '@/components/hotels/hotelsTable'
import editHotel from '@/components/hotels/editHotel'

import RentACarView from '@/components/rentACar/index'
import addRentACar from '@/components/rentACar/addRentACar'
import RentACarsTable from '@/components/rentACar/rentACarTable'
import editRentACar from '@/components/rentACar/editRentACar'
import VehiclesTable from '@/components/rentACar/VehiclesTable'
import AddVehicle from '@/components/rentACar/AddVehicle'

import AirlineAdmin from '@/components/admins/AirlineAdmin'
import HotelAdmin from '@/components/admins/HotelAdmin'
import RentACarAdmin from '@/components/admins/RentACarAdmin'

import addRooms from '@/components/rooms/addRooms'


Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: HelloWorld
    },
    {
      // Airlines
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
      // Airline admin
      path: '/airline-admin',
      component: AirlineAdmin,
      children: [
        {
          path: 'edit/:id',
          component: editAirline
        }
      ]
    },
    {
      // Hotels
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
    },
    {
      // Hotel admin
      path: '/hotel-admin',
      component: HotelAdmin,
      children: [
        {
          path: 'edit/:id',
          component: editHotel
        },
        {
          path: 'add-rooms/:id',
          component: addRooms
        }
      ]
    },
    {
      // Rent a cars
      path: '/rent-a-cars',
      component: RentACarView,
      children: [
        {
          path: 'add',
          component: addRentACar
        },
        {
          path: '/',
          component: RentACarsTable
        }
      ]
    },
    {
      // Rent a car admin
      path: '/rent-a-car-admin',
      component: RentACarAdmin,
      children: [
        {
          path: 'edit/:id',
          component: editRentACar
        },
        {
          path: 'vehicles/:id',
          component: VehiclesTable
        },
        {
          path: 'add-vehicle/:id',
          component: AddVehicle
        }
      ]
    },
    
  ]
})
