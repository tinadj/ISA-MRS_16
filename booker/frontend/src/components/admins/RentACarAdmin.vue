<template>
  <b-container>
    <b-row>
      <b-nav class="w-100">
        <b-nav-item :to="{ path: 'info'}">Information</b-nav-item>
        <b-nav-item :to="{ path: 'edit'}">Edit information</b-nav-item>
        <b-nav-item :to="{ path: 'vehicles'}" apend active>Vehicles</b-nav-item>
        <b-nav-item :to="{ path: 'add-vehicle'}" apend>Add vehicle</b-nav-item>
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
