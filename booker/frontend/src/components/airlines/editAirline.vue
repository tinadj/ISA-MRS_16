<template>
  <div>
    <form @submit="edit">
      <b-form-input v-model="name" placeholder="Name"></b-form-input>
      <b-form-input v-model="city" placeholder="City"></b-form-input>
      <b-form-input v-model="state" placeholder="State"></b-form-input>
      <b-form-input v-model="latitude" placeholder="Latitude" type="number"></b-form-input>
      <b-form-input v-model="longitude" placeholder="Longitude" type="number"></b-form-input>
      <b-form-textarea v-model="description" placeholder="Description" rows="3"></b-form-textarea>

      <b-button variant="outline-primary" type="submit">Save</b-button>
      <b-button @click="onCancel">Cancel</b-button>
    </form>
    <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
    <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
  </div>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'editAirline',
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
  mounted(){
    let api = '/airlines/' + this.$route.params.id;
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

      const airline = {
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

      AXIOS.put('/airlines/update', airline)
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
    }
  }
}
</script>

<style>

</style>
