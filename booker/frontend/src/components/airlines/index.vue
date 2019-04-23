<template>
  <b-container>
    <b-row>
      <b-nav class="nav nav-tabs margins w-100">
        <b-nav-item :to="{ path: '/airlines'}" active>Airlines</b-nav-item>
        <b-nav-item :to="{ path: '/hotels'}">Hotels</b-nav-item>
        <b-nav-item :to="{ path: '/rent-a-cars'}">Rent a cars</b-nav-item>
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
      airlines: []
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
