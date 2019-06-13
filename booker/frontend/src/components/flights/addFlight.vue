<template>
      <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Add new Flight</b></h6>
        <form @submit="add" inline>
          <b-form-group>
            Departure Destination
             <b-form-select v-model="selectedDeparture" :options="destinations"></b-form-select>
          </b-form-group>

          <b-form-group>
            Arrival Destination
             <b-form-select v-model="selectedArrivalDeparture" :options="destinations"></b-form-select>
           </b-form-group>

            <b-form-group>
               Departure Date
               <v-date-picker mode="range" v-model="departureDate"/>
             </b-form-group>

            <b-form-group>
              Arrival Date
              <v-date-picker mode="range" v-model="arrivalDate"/>
            </b-form-group>

            <b-form-group>
              <b-form-input v-model="number" placeholder="Transfer Number" type="number" step="1"></b-form-input>
            </b-form-group>

            <b-form-group>
              <b-form-input v-model="firstClass" placeholder="Number of seats in First Class" type="number" step="1"></b-form-input>
            </b-form-group>

            <b-form-group>
              <b-form-input v-model="businessClass" placeholder="Number of seats in Business Class" type="number" step="1"></b-form-input>
            </b-form-group>

            <b-form-group>
              <b-form-input v-model="economyClass" placeholder="Number of seats in Economy Class" type="number" step="1"></b-form-input>
            </b-form-group>

          <b-button variant="outline-primary" type="submit" class="mr-1">Add</b-button>
        </form><br>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
      </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'AddDestination',
  data () {
    return {
      selectedDeparture: '',
      departureDate: '',
      arrivalDate: '',
      selectedArrivalDeparture: '',
      departureDestination: '',
      destinations: [],
      number: null,
      firstClass: null,
      businessClass: null,
      economyClass: null,
      success: false,
      error: false
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

    }
  },
   mounted () {
      AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

      let api = 'airlines/' +  this.$route.params.id + '/destinations'
        AXIOS.get(api)
        .then(response => {
          this.departureDestination = response.data

           for(let i = 0; i < this.departureDestination.length; i++){
             let newItem = {}
             newItem.value = this.departureDestination[i].id
             newItem.text = this.departureDestination[i].state + ', ' + this.departureDestination[i].city
             this.destinations.push(newItem)
           }
          console.log(this.departureDestination)
          if (this.departureDestination.length == 0) {
            this.noResult = true
          }
        })
        .catch(err => console.log(err))
    }
}
</script>

<style>

</style>
