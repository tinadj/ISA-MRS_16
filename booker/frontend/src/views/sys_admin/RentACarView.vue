<template>
    <div class="container">
        <div class="row">
            <table class="table table-sm col-sm">
                <thead>
                  <th>Name</th>
                  <th>Address</th>
                  <th>Description</th>
                </thead>
                <tbody>
                  <tr v-for="rent_a_car in rent_a_car_services" :key="rent_a_car.name">
                    <td>{{rent_a_car.name}}</td>
                    <td>{{rent_a_car.address}}</td>
                    <td>{{rent_a_car.description}}</td>
                  </tr>
                </tbody>
            </table>
            <div class="col-sm">
                <button type="button" v-on:click="addWin = true">Add new Rent-a-car Service</button>
                <AddRentACar v-if="addWin" v-on:close="addWin = false" v-on:addRentACar='addRentACar'></AddRentACar>
            </div>
        </div>

    </div>
</template>

<script>
import AddRentACar from '../../components/rentACar/addRentACar.vue'

export default {
  data () {
    return {
      rent_a_car_services: [],
      addWin: false
    }
  },
  components: {
    AddRentACar
  },
  methods: {
    addRentACar (newRentACar) {
      this.rent_a_car_services = [...this.rent_a_car_services, newRentACar]
    }
  },
  mounted () {
  }
}
</script>
