<template>
   <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Add new Rent a Car Service</b></h6>
        <form @submit="add" inline>
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

          <b-form-group>
            <b-form-textarea v-model="description" placeholder="Description" rows="3"></b-form-textarea>
          </b-form-group>

          <b-button variant="outline-primary" type="submit" class="mr-1">Add</b-button>
          <b-button @click="onCancel">Cancel</b-button>
        </form><br>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
      </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'AddRentACar',
  data () {
    return {
      id: '',
      name: '',
      city: '',
      state: '',
      latitude: '',
      longitude: '',
      description: '',
      success: false,
      error: false
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

      const airline = {
        'name': this.name,
        'address': {
     	    'city': this.city,
     	    'state': this.state
          },
        'latitude': this.latitude,
        'longitude': this.longitude,
        'description': this.description
      }

      AXIOS.post('/rent-a-cars/add', airline)
      .then(response => {
        this.success = true;
        this.error = false;
      })
      .catch(err => {
        this.success = false;
        this.error = true
      })
    },
    onCancel (e) {
      e.preventDefault()
      this.$router.push('/rent-a-cars')
    }
  }
}
</script>

<style>

</style>
