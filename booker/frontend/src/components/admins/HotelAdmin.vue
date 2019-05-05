<template>
  <b-container>
    <b-row>
      <b-nav tabs>
        <b-nav-item :to="{ path: 'info'}"><font-awesome-icon :icon="infoIcon"/> Information</b-nav-item>
        <b-nav-item :to="{ path: 'edit' }"><font-awesome-icon :icon="editIcon"/> Edit information</b-nav-item>
        <b-nav-item :to="{ path: 'rooms'}" ><font-awesome-icon :icon="bedIcon" />Rooms</b-nav-item>
        <b-nav-item :to="{ path: 'add-rooms'}"><font-awesome-icon :icon="addIcon"/>Add Rooms</b-nav-item>
        <b-nav-item :to="{ path: 'profile-page'}"><font-awesome-icon :icon="profileIcon"/> Profile</b-nav-item>
        <b-nav-item :to="{ path: 'sign-out'}"><font-awesome-icon :icon="singOutIcon"/> Sign out</b-nav-item>
      </b-nav>
    </b-row>

    <b-row align-v="center">
      <b-col>
          <br />
          <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { faInfoCircle,  faEdit, faPlusCircle, faBed, faUserCircle, faSignOutAlt } from '@fortawesome/free-solid-svg-icons';


export default {
  data () {
    return {
      infoIcon: faInfoCircle,
      editIcon: faEdit,
      addIcon: faPlusCircle,
      bedIcon: faBed,
      profileIcon: faUserCircle,
      singOutIcon: faSignOutAlt
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
