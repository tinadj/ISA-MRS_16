<template>
    <div class="container">
      <ul class="nav nav-tabs margins">
        <li class="nav-item">
          <b-link  class="nav-link" :to="{ path: '/airlines'}">Airlines</b-link>
        </li>
        <li class="nav-item">
          <b-link  class="nav-link active" :to="{ path: '/hotels'}">Hotels</b-link>
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
import AddHotel from '../../components/hotels/addHotel.vue'

export default {
  data () {
    return {
      hotels: [],
      addWin: false
    }
  },
  components: {
    AddHotel
  },
  methods: {
    addHotel (newHotel) {
      this.hotels = [...this.hotels, newHotel]
    }
  },
  mounted () {
  }
}
</script>
