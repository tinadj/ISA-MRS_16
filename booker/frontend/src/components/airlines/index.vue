<template>
    <div class="container">
      <ul class="nav nav-tabs margins row">
        <li class="nav-item">
          <b-link  class="nav-link active" :to="{ path: '/airlines'}">Airlines</b-link>
        </li>
        <li class="nav-item">
          <b-link  class="nav-link" :to="{ path: '/hotels'}">Hotels</b-link>
        </li>
        <li class="nav-item">
          <b-link  class="nav-link" :to="{ path: '/rent-a-cars'}">Rent a cars</b-link>
        </li>
      </ul>

      <div class="row">
          <div class="col-sm">
              <br />
              <router-view></router-view>
          </div>
      </div>
    </div>
</template>

<script>
import AddAirline from '../airlines/addAirline.vue'

export default {
  data () {
    return {
      airlines: []
    }
  },
  components: {
    AddAirline
  },
  methods: {
    addAirline (newAirline) {
      this.airlines = [...this.airlines, newAirline]
    }
  },
  mounted () {

  }
}
</script>

<style>

</style>
