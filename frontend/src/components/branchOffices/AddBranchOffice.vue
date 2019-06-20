<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Add new branch office</b></h6>
        <form @submit="add">
          <b-form-group>
            <b-form-input v-model="name" placeholder="Name"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="city" placeholder="City"></b-form-input>
          </b-form-group>
          
          <b-form-group>
            <b-form-input v-model="state" placeholder="State"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="latitude" placeholder="Latitude" type="number" step="0.000001"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="longitude" placeholder="Longitude" type="number" step="0.000001"></b-form-input>
          </b-form-group>

          <b-button variant="outline-primary" type="submit" class="mr-1">Add</b-button>
          <b-button :to="{ path: 'branch-offices'}">Cancel</b-button>

        </form><br>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>
    </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'AddBranchOffice',
  data () {
    return {
      name: '',
      city: '',
      state: '',
      latitude: '',
      longitude: '',
      rentACar: '',
      success: false,
      error: false,
      errorMessage: ''
    }
  },
  methods: {
    add (e) {
      e.preventDefault()
      
      this.error = false

      if (this.noEmptyFiedls()) {
        const branch_office = {
          'name': this.name,
          'address': {
              'city': this.city,
              'state': this.state
          },
          'latitude': this.latitude,
          'longitude': this.longitude,
          'rentACar': this.rentACar,
        }

        AXIOS.post('/branch-offices/add', branch_office)
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
        this.error = true
      }
    },
    noEmptyFiedls() {
      if (this.name.length == 0 || this.city.length == 0 || this.state.length == 0 ||
            this.latitude.length == 0 || this.longitude.length == 0) {
                this.errorMessage = "Some fields are empty!"
                return false 
      }
      return true
    }
  },
  mounted() {
    let api = '/rent-a-cars/' + this.$route.params.id;
    
    AXIOS.get(api)
    .then(response => { 
      this.rentACar = response.data
    })
  }
}
</script>

<style>

</style>
