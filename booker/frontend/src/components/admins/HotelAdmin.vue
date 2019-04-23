<template>
  <b-container>
    <b-row>
      <b-nav class="w-100">
        <b-nav-item :to="{ path: 'info'} " apend active>Information</b-nav-item>
        <b-nav-item :to="{ path: 'edit' }" apend>Edit information</b-nav-item>
        <b-nav-item :to="{ path: 'rooms'}" apend active>Rooms</b-nav-item>
        <b-nav-item :to="{ path: 'add-rooms'}" apend>Add Rooms</b-nav-item>
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
