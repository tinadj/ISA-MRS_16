<template>
  <b-container>
    <b-row>
      <b-nav tabs>
        <b-nav-item :to="{ path: 'info'}">Information</b-nav-item>

        <b-nav-item :to="{ path: 'edit'}">Edit information</b-nav-item>
        <b-nav-item :to="{ path: 'destinations'}">Destinations</b-nav-item>
        <b-nav-item :to="{ path: 'add-destination'}">Add Destinations</b-nav-item>
        <b-nav-item :to="{ path: 'profile-page'}">Profile</b-nav-item>
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
