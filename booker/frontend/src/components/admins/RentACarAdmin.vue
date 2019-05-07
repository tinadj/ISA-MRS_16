<template>
  <b-container>
    <b-row>
      <b-nav tabs>
        <b-nav-item :to="{ path: 'info'}"><font-awesome-icon :icon="infoIcon"/> Information</b-nav-item>
        <b-nav-item :to="{ path: 'edit' }"><font-awesome-icon :icon="editIcon"/> Edit information</b-nav-item>
        <b-nav-item :to="{ path: 'vehicles'}"><font-awesome-icon :icon="carIcon"/> Vehicles</b-nav-item>
        <b-nav-item :to="{ path: 'add-vehicle'}"><font-awesome-icon :icon="addIcon"/> Add vehicle</b-nav-item>
        <b-nav-item :to="{ path: 'branch-offices'}"><font-awesome-icon :icon="officeIcon"/> Branch offices</b-nav-item>
        <b-nav-item :to="{ path: 'add-branch-office'}"><font-awesome-icon :icon="addIcon"/> Add branch office</b-nav-item>
        <b-nav-item :to="{ path: 'profile-page'}"><font-awesome-icon :icon="profileIcon"/> Profile</b-nav-item>
        <b-nav-item :to="{ path: 'sign-out'}"><font-awesome-icon :icon="singOutIcon"/> Sign out</b-nav-item>
      </b-nav>
    </b-row>

    <b-row align-v="center">
      <b-col>
        <br>
        <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { faInfoCircle, faCarSide, faEdit, faPlusCircle, faBriefcase, faUserCircle, faSignOutAlt, faFileAlt } from '@fortawesome/free-solid-svg-icons';

export default {
  data () {
    return {
      infoIcon: faInfoCircle,
      carIcon: faCarSide,
      editIcon: faEdit,
      addIcon: faPlusCircle,
      officeIcon: faBriefcase,
      profileIcon: faUserCircle,
      singOutIcon: faSignOutAlt,
      reportIcon: faFileAlt
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
