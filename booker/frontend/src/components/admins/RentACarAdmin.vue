<template>
  <b-container>
    <b-row>
      <b-nav tabs>
        <b-nav-item :to="{ path: 'info'}">Information</b-nav-item>
        <b-nav-item :to="{ path: 'edit' }">Edit information</b-nav-item>
        <b-nav-item :to="{ path: 'vehicles'}">Vehicles</b-nav-item>
        <b-nav-item :to="{ path: 'add-vehicle'}">Add vehicle</b-nav-item>
        <b-nav-item :to="{ path: 'branch-offices'}">Branch offices</b-nav-item>
        <b-nav-item :to="{ path: 'add-branch-office'}">Add branch office</b-nav-item>
        <b-nav-item>Profile</b-nav-item>
        <b-nav-item>Log out</b-nav-item>
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
import { faCarSide } from '@fortawesome/free-solid-svg-icons'

export default {
  data () {
    return {
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
