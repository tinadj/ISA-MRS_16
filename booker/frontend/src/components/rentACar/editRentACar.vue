<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Edit Rent a car Information</b></h6>
      <form @submit="edit">

        <b-form-group 
            label="Name: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-1">
            <b-form-input id="input-1" v-model="name" placeholder="Name"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="City: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-2">
            <b-form-input id="input-1" v-model="city" placeholder="City"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="State: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-3">
            <b-form-input id="input-3" v-model="state" placeholder="State"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="Latitude: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-4">
            <b-form-input id="input-4" v-model="latitude" placeholder="Latitude" type="number" step="0.000001"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="Longitude: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-5">
            <b-form-input id="input-5" v-model="longitude" placeholder="Longitude" type="number" step="0.000001"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="Description: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-6">
           <b-form-textarea id="input-6" v-model="description" placeholder="Description" rows="3"></b-form-textarea>
        </b-form-group>

        <b-button variant="outline-primary" type="submit" class="mr-1">Save</b-button>
        <b-button :to="{ path: 'info'} ">Cancel</b-button>

      </form><br>
      <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
      <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>
    </b-card>
  </b-card-group>
  
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'editRentACar',
  data () {
    return {
      id: '',
      name: '',
      city: '',
      state: '',
      latitude: '',
      longitude: '',
      latitude: '',
      longitude: '',
      description: '',
      success: false,
      error: false,
      errorMessage: ''
    }
  },
  mounted() {
    let api = '/rent-a-cars/' + this.$route.params.id;

    AXIOS.get(api)
    .then(response => { 
      this.id = response.data.id
      this.name = response.data.name
      this.city = response.data.address.city
      this.state = response.data.address.state
      this.latitude = response.data.latitude
      this.longitude = response.data.longitude
      this.description = response.data.description
    })
  }
  ,
  methods: {
    edit (e) {
      e.preventDefault()

      this.error = false

      if (this.noEmptyFiedls()) {
        const rent_a_car = {
          'id': this.$route.params.id,
          'name': this.name,
          'address': {
            'city': this.city,
            'state': this.state
            },
          'latitude': this.latitude,
          'longitude': this.longitude,
          'description': this.description
        }
        AXIOS.put('/rent-a-cars/update', rent_a_car)
        .then(response => {
          this.success = true;
          this.error = false;
        })
        .catch(err => {
          this.errorMessage = "Something went wrong!"
          this.success = false;
          this.error = true
        })
      } else {
        this.errorMessage = "Some fields are empty!"
        this.error
      }
    },
    noEmptyFiedls() {
      if (this.name.length == 0 || this.city.length == 0 || this.state.length == 0 ||
            this.latitude.length == 0 || this.longitude.length == 0) {
                return false 
      }
      return true
    }
  }
}
</script>

<style>

</style>
