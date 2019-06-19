<template>
  <b-container>
      <b-row>
        <b-col lg="3">
            <b-button variant="outline-primary" :to="{ path: 'add-vehicle'}">Add new Vehicle</b-button>
        </b-col>
    </b-row>
    <b-row>
      <b-col>
        <br>
        <b-card-group>
          <b-card border-variant="light" class="mb-2 mx-auto">
            <b-alert v-model="noResult" variant="light">There are no vehicles!</b-alert>
            <ul>
              <li v-for="item in vehicles">
                <AdminVehicleInfo v-bind:item="item" :key="componentKey"></AdminVehicleInfo>
              </li>
            </ul>        
          </b-card>
        </b-card-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import {AXIOS} from '../../http-common'
import AdminVehicleInfo from './Admin_VehicleInfo'

export default {
  components: {
    AdminVehicleInfo
  },
  data () {
    return {
      vehicles: [],
      noResult: false,
      componentKey: 0
    }
  },
  mounted () {
      AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
      
      let api = 'rent-a-cars/' +  this.$route.params.id + '/vehicles'
        AXIOS.get(api)
        .then(response => { 
          this.vehicles = response.data

          if (this.vehicles.length == 0) {
            this.noResult = true
          }
        })
        .catch(err => console.log(err))
        this.componentKey += 1
  }
}
</script>

<style scoped>
  ul {
    list-style-type: none;
  }
</style>
