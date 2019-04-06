<template>
  <b-container>
    <b-row>
      <b-nav class="w-100">
        <b-nav-item :to="{ path: '/airlines' }">Airlines</b-nav-item>
        <b-nav-item :to="{ path: '/hotels' }" active>Hotels</b-nav-item>
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
      hotels: []
    }
  },
  methods: {

  },
  mounted () {
  }
}
</script>
