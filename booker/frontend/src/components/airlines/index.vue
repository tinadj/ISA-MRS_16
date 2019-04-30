<template>
  <b-container>
    <b-row>
      <b-nav class="nav nav-tabs margins w-100">
        <b-nav-item :to="{ path: '/airlines' }"><font-awesome-icon :icon="planeIcon" /> Airlines</b-nav-item>
        <b-nav-item :to="{ path: '/hotels'}"><font-awesome-icon :icon="bedIcon" /> Hotels</b-nav-item>
        <b-nav-item :to="{ path: '/rent-a-cars'}"><font-awesome-icon :icon="carIcon" /> Rent a Car Services</b-nav-item>
        <b-nav-item :to="{ path: '/admins'}"><font-awesome-icon :icon="adminsIcon" /> Admins</b-nav-item>
        <b-nav-item :to="{ path: '/register-admin'}"><font-awesome-icon :icon="addAdminIcon" /> Register admin</b-nav-item>
        <b-nav-item><font-awesome-icon :icon="discountIcon" /> Define discount</b-nav-item>
        <b-nav-item><font-awesome-icon :icon="signOutIcon" /> Sign out</b-nav-item>
      </b-nav>
    </b-row>

    <b-row>
      <b-col>
          <br />
          <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { faPlane } from '@fortawesome/free-solid-svg-icons'
import { faBed } from '@fortawesome/free-solid-svg-icons'
import { faCar } from '@fortawesome/free-solid-svg-icons'
import { faUsersCog } from '@fortawesome/free-solid-svg-icons'
import { faUserPlus } from '@fortawesome/free-solid-svg-icons'
import { faTag } from '@fortawesome/free-solid-svg-icons'
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons'

export default {
  
  data () {
    return {
      planeIcon: faPlane,
      bedIcon: faBed,
      carIcon: faCar,
      adminsIcon: faUsersCog,
      addAdminIcon: faUserPlus,
      discountIcon: faTag,
      signOutIcon: faSignOutAlt
    }
  },
  methods: {

  },
  mounted () {

  }
}
</script>

<style>

</style>
