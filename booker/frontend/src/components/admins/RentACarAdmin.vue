<template>
  <b-container>
    <b-row>
      <b-nav class="w-100">
        <b-nav-item active>Information</b-nav-item>
        <b-nav-item>Edit information</b-nav-item>
        <b-nav-item>Vehicles</b-nav-item>
        <b-nav-item>Add vehicle</b-nav-item>
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
