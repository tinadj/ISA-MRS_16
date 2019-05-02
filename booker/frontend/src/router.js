import Vue from 'vue'
import Router from 'vue-router'

import addAirline from '@/components/airlines/addAirline'
import editAirline from '@/components/airlines/editAirline'
import airlinesTable from '@/components/airlines/airlinesTable'
import editProfile from '@/components/account/editProfile'
import changePassword from '@/components/account/changePassword'
import AirlineInfo from '@/components/airlines/airlineInfo'

import AirlineView from '@/components/airlines/index'
import HotelView from '@/components/hotels/index'
import RentACarView from '@/components/rentACar/index'

import addHotel from '@/components/hotels/addHotel'
import hotelsTable from '@/components/hotels/hotelsTable'
import editHotel from '@/components/hotels/editHotel'

import AdminsTable from '@/components/admins/AdminsTable'
import RegisterAdmin from '@/components/admins/RegisterAdmin'

import addRentACar from '@/components/rentACar/addRentACar'
import RentACarsTable from '@/components/rentACar/rentACarTable'
import RentACarInfo from '@/components/rentACar/RentACarInfo'
import editRentACar from '@/components/rentACar/editRentACar'
import VehiclesTable from '@/components/rentACar/VehiclesTable'
import AddVehicle from '@/components/rentACar/AddVehicle'
import EditVehicle from '@/components/rentACar/EditVehicle'
import BranchOfficesTable from '@/components/rentACar/BranchOfficesTable'
import AddBranchOffice from '@/components/rentACar/AddBranchOffice'
import EditBranchOffice from '@/components/rentACar/EditBranchOffice'

import AirlineAdmin from '@/components/admins/AirlineAdmin'
import HotelAdmin from '@/components/admins/HotelAdmin'
import RentACarAdmin from '@/components/admins/RentACarAdmin'
import ChangePassword from '@/components/admins/ChangePassword'

import Login from '@/components/users/Login'
import ConfirmLogin from '@/components/users/ConfirmLogin'
import Register from '@/components/users/Register'
import RegisteredUserHomePage from '@/components/users/RegisteredUserHomePage'

import IndexPage from '@/components/users/IndexPage'



Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: IndexPage
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/login/:token',
      component: ConfirmLogin
    },
    {
      path: '/register',
      component: Register
    },
    {
      path: '/home/:id',
      component: RegisteredUserHomePage
    },
    {
      path: '/change-pass/:id',
      component: ChangePassword
    },
    // Sys admin
    {
      path: '/airlines',
      component: AirlineView,
      children: [
        {
          path: '/',
          component: airlinesTable
        },
        {
          path: 'add',
          component: addAirline
        }
      ]
    },
    {
      path: '/hotels',
      component: HotelView,
      children: [
        {
          path: '/',
          component: hotelsTable
        },
        {
        path: 'add',
        component: addHotel
        },
      
      ]
    },
    {
      path: '/rent-a-cars',
      component: RentACarView,
      children: [
        {
          path: '/',
          component: RentACarsTable
        },
        {
          path: 'add',
          component: addRentACar
        }
      ]
    },
    {
      path: '/admins',
      component: RentACarView,
      children: [
		{
          path: '/',
          component: AirlineInfo
        },
        {
          path: '/',
          component: AdminsTable
        }
      ]
    },
    {
      path: '/register-admin',
      component: RentACarView,
      children: [
        {
          path: '/',
          component: RegisterAdmin
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
      // Hotel admin
      path: '/hotel-admin',
      component: HotelAdmin,
      children: [
        {
          path: 'edit/:id',
          component: editHotel
        }
      ]
    },
    {
      // Rent a car admin
      path: '/:id/rent-a-car-admin/',
      component: RentACarAdmin,
      children: [
        {
          path: '/',
          component: RentACarInfo
        },
        {
          path: 'info',
          component: RentACarInfo
        },
        {
          path: 'edit',
          component: editRentACar
        },
        {
          path: 'vehicles',
          component: VehiclesTable,
        },
        {
          path: 'add-vehicle',
          component: AddVehicle
        },
        {
          path: 'edit-vehicle/:v_id',
          component: EditVehicle
        },
        {
          path: 'branch-offices',
          component: BranchOfficesTable
        },
        {
          path: 'add-branch-office',
          component: AddBranchOffice
        },
        {
          path: 'edit-branch-office/:bo_id',
          component: EditBranchOffice
        }
      ]
    }
  ]
})
