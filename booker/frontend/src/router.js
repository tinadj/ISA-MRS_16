import Vue from 'vue'
import Router from 'vue-router'

import addAirline from '@/components/airlines/addAirline'

import editAirline from '@/components/airlines/editAirline'
import SysAdminAirlineTable from '@/components/airlines/SysAdmin_AirlineTable'
import editProfile from '@/components/account/editProfile'
import changePassword from '@/components/account/changePassword'
import AirlineInfo from '@/components/airlines/airlineInfo'
import RegisteredUserSearchAirline from '@/components/airlines/RegisteredUser_searchAirline'
import UnregisteredUserSearchAirline from '@/components/airlines/UnregisteredUser_searchAirline'

import AirlineView from '@/components/airlines/index'
import HotelView from '@/components/hotels/index'
import RentACarView from '@/components/rentACar/index'
import destinationsTable from '@/components/destinations/destinationsTable'


// Destinations

import addDestination from '@/components/destinations/addDestination'

// Hotel

import addHotel from '@/components/hotels/addHotel'
import SysAdminHotelsTable from '@/components/hotels/SysAdmin_HotelsTable'
import editHotel from '@/components/hotels/editHotel'
import HotelInfo from '@/components/hotels/HotelInfo'
import registered_searchHotels from '@/components/hotels/registered_searchHotels'
import UnregisteredUserSearchHotels from '@/components/hotels/unregistered_searchHotels'

import AdminsTable from '@/components/admins/AdminsTable'
import RegisterAdmin from '@/components/admins/RegisterAdmin'
import SysAdminDiscounts from '@/components/admins/SysAdmin_Discounts'
import SysAdminEditDiscounts from '@/components/admins/SysAdmin_EditDiscounts'

// Rent a car
import addRentACar from '@/components/rentACar/addRentACar'
import SysAdminRACTable from '@/components/rentACar/SysAdmin_RACTable'
import RentACarInfo from '@/components/rentACar/RentACarInfo'
import editRentACar from '@/components/rentACar/editRentACar'
import RegisteredUserSearchRAC from '@/components/rentACar/RegisteredUser_searchRAC'
import UnregisteredUserSearchRAC from '@/components/rentACar/UnregisteredUser_searchRAC'
import RACReports from '@/components/reports/RACReports'
import RACIncome from '@/components/reports/RACIncome'

// Branch offices
import BranchOfficesTable from '@/components/branchOffices/BranchOfficesTable'
import AddBranchOffice from '@/components/branchOffices/AddBranchOffice'
import EditBranchOffice from '@/components/branchOffices/EditBranchOffice'

// Vehicles
import VehiclesTable from '@/components/vehicles/VehiclesTable'
import AddVehicle from '@/components/vehicles/AddVehicle'
import EditVehicle from '@/components/vehicles/EditVehicle'
import RegisteredUserSearchVehicle from '@/components/vehicles/RegisteredUser_searchVehicle'
import UnregisteredUserSearchVehicle from '@/components/vehicles/UnregisteredUser_searchVehicle'

// Admins
import AirlineAdmin from '@/components/admins/AirlineAdmin'
import HotelAdmin from '@/components/admins/HotelAdmin'
import RentACarAdmin from '@/components/admins/RentACarAdmin'
import ChangePasswordFirstLogin from '@/components/admins/ChangePasswordFirstLogin'

import Login from '@/components/users/Login'
import Logout from '@/components/users/Logout'
import ProfilePage from '@/components/account/ProfilePage'
import UserProfilePage from '@/components/account/UserProfilePage'
import ConfirmLogin from '@/components/users/ConfirmLogin'
import Register from '@/components/users/Register'
import RegisteredUserHomePage from '@/components/users/RegisteredUserHomePage'

import IndexPage from '@/components/users/IndexPage'

// Rooms

import addRooms from '@/components/rooms/addRooms'
import RoomsTable from '@/components/rooms/RoomsTable'
import editRoom from '@/components/rooms/editRoom'
import editExtraServices from '@/components/rooms/editExtraServices'

// Reservations
import ReservationList from '@/components/reservations/ReservationList'


Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: IndexPage,
      children: [
        {
          path: 'search-airlines',
          component: UnregisteredUserSearchAirline
        },
        {
          path: 'search-hotels'
        },
        {
          path: 'search-rent-a-cars',
          component: UnregisteredUserSearchRAC
        },
        {
          path: 'vehicles-:rac_id',
          component: UnregisteredUserSearchVehicle
        },
        {
          path: 'search-hotels',
          component: UnregisteredUserSearchHotels
        }

      ]
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
      // Registered user
      path: '/:id/home/',
      component: RegisteredUserHomePage,
      children: [
        {
          path: 'airlines',
          component: RegisteredUserSearchAirline
        },
        {
          path: 'hotels',
          component: registered_searchHotels
        },
        {
          path: 'rent-a-cars',
          component: RegisteredUserSearchRAC
        },
        {
          path: 'reservations',
          component: ReservationList
        },
        {
          path: 'profile-page',
          component: UserProfilePage
        },
        {
          path: 'edit-profile',
          component: editProfile
        },
        {
          path: 'change-pass',
          component: changePassword
        },
        {
          path: 'sign-out',
          component: Logout
        },
        {
          path: 'vehicles-:rac_id',
          component: RegisteredUserSearchVehicle
        }
      ]
    },
    {
      path: '/change-pass/:id',
      component: ChangePasswordFirstLogin
    },
    // Sys admin
    {
      path: '/airlines',
      component: AirlineView,
      children: [
        {
          path: '/',
          component: SysAdminAirlineTable
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
          component: SysAdminHotelsTable
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
          component: SysAdminRACTable
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
      path: '/discounts',
      component: RentACarView,
      children: [
        {
          path: '/',
          component: SysAdminDiscounts
        },
        {
          path: 'edit',
          component: SysAdminEditDiscounts
        }
      ]
    },
    {
      path: '/sign-out',
      component: Logout
    },
    {
      // Airline admin
      path: '/:id/airline-admin/',
      component: AirlineAdmin,
      children: [
		{
          path: '/',
          component: AirlineInfo
        },
        {
          path: 'info',
          component: AirlineInfo
        },
        {
          path: 'edit',
          component: editAirline
        },
        {
          path: 'profile-page',
          component: ProfilePage
        },
        {
          path: 'edit-profile',
          component: editProfile
        },
        {
          path: 'change-pass',
          component: changePassword
        },
        {
          path: 'destinations',
          component: destinationsTable,
        },
        {
          path: 'add-destination',
          component: addDestination,
        },
        {
          path: 'sign-out',
          component: Logout
        }
      ]
    },
    {
      // Hotel admin
      path: '/:id/hotel-admin/',
      component: HotelAdmin,
      children: [
        {
          path: 'edit',
          component: editHotel
        },
        {
          path: 'info',
          component: HotelInfo
        },
        {
          path: 'add-rooms',
          component: addRooms
        },
        {
          path: 'rooms',
          component: RoomsTable
        },
        {
          path: 'edit-room/:r_id',
          component: editRoom
        },
        {
          path: 'edit-extraservices',
          component: editExtraServices
        },
        {
          path: 'profile-page',
          component: ProfilePage
        },
        {
          path: 'edit-profile',
          component: editProfile
        },
        {
          path: 'change-pass',
          component: changePassword
        },
        {
          path: 'sign-out',
          component: Logout
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
          path: 'edit-vehicle-:v_id',
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
          path: 'edit-branch-office-:bo_id',
          component: EditBranchOffice
        },
        {
          path: 'profile-page',
          component: ProfilePage
        },
        {
          path: 'edit-profile',
          component: editProfile
        },
        {
          path: 'change-pass',
          component: changePassword
        },
        {
          path: 'sign-out',
          component: Logout
        },
        {
          path: 'rac-reports',
          component: RACReports
        },
        {
          path: 'rac-income',
          component: RACIncome
        }
      ]
    },
    
  ]
})
