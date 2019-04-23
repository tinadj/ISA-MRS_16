<template>
  <b-container>
    <b-row>
      <b-nav class="w-100">
        <b-nav-item>Information</b-nav-item>
        <b-nav-item>Edit information</b-nav-item>
        <b-nav-item v-bind:class="{ 'active': this.$route.path == '/airline-admin/profile' }">Profile</b-nav-item>
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
    console.log(this.$route.path);
  }
}
</script>

<style>

</style>
