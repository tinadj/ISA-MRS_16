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
               <b-form-input v-model="departureDate" type="datetime-local"></b-form-input>
             </b-form-group>

            <b-form-group>
              Arrival Date
               <b-form-input v-model="arrivalDate" type="datetime-local"></b-form-input>
            </b-form-group>

            <b-form-group>
              <b-form-input v-model="number" placeholder="Transfer Number" type="number" step="1"></b-form-input>
            </b-form-group>


            <div class="row">
              <b-form-group class="col-xs-12 col-sm-6" >
                <b-form-input v-model="firstClass" placeholder="Number of seats in First Class" type="number" step="1"></b-form-input>
              </b-form-group>


              <b-form-group  class="col-xs-12 col-sm-6" >
                <b-form-input v-model="firstClassPrice" placeholder="Price for First Class" type="number" step="0.0001"></b-form-input>
              </b-form-group>
            </div>

            <div class="row">
              <b-form-group class="col-xs-12 col-sm-6">
                <b-form-input v-model="businessClass" placeholder="Number of seats in Business Class" type="number" step="1"></b-form-input>
              </b-form-group>
               <b-form-group  class="col-xs-12 col-sm-6" >
                <b-form-input v-model="businessClassPrice" placeholder="Price for Business Class" type="number" step="0.0001"></b-form-input>
              </b-form-group>
            </div>

            <div class="row">
              <b-form-group class="col-xs-12 col-sm-6">
                <b-form-input v-model="economyClass" placeholder="Number of seats in Economy Class" type="number" step="1"></b-form-input>
              </b-form-group>
              <b-form-group  class="col-xs-12 col-sm-6" >
                <b-form-input v-model="economyClassPrice" placeholder="Price for Economy Class" type="number" step="0.0001"></b-form-input>
              </b-form-group>
            </div>

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
      firstClassPrice: null,
      businessClass: null,
      businessClassPrice: null,
      economyClass: null,
      economyClassPrice: null,
      success: false,
      error: false
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

        const flight = {
          'departure': this.selectedDeparture,
          'arrival': this.selectedArrivalDeparture,
          'departureTime': this.departureDate,
          'arrivalTime': this.arrivalDate,
          'transferNumber': this.transferNumber,
          'firstClass': this.firstClass,
          'firstClassPrice': this.firstClassPrice,
          'businessClass': this.businessClass,
          'businessClassPrice': this.businessClassPrice,
          'economyClass': this.economyClass,
          'economyClassPrice': this.economyClassPrice,
          'AirlineID': this.$route.params.id
        }

        AXIOS.post('/flights/add', flight)
        .then(response => {
          this.success = true;
          this.error = false;
        })
        .catch(err => {
          this.success = false;
          this.error = true
        })
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
