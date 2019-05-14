<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Edit prices for room extraservices (IN EUROS)</b></h6>
        <form @submit="edit">

          <b-form-group 
            label="Breakfast: " 
            label-cols="2" 
            label-cols-lg="2" 
            label-for="input-1">
            <b-form-input id="input-1" v-model="breakfast" placeholder="Breakfast Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Hotel Restaurant: " 
            label-cols="2" 
            label-cols-lg="2" 
            label-for="input-2">
            <b-form-input id="input-2" v-model="hotel_restaurant" placeholder="Hotel Restaurant Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Airport Transfer: " 
            label-cols="2" 
            label-cols-lg="2" 
            label-for="input-3">
            <b-form-input id="input-3" v-model="airport_transfer" placeholder="Price for Airport Transfer" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Parking: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-4">
            <b-form-input id="input-4" v-model="parking" placeholder="Parking Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Pool Access: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-5">
            <b-form-input id="input-5" v-model="pool" placeholder="Pool Access Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Wellness and Spa: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-6">
            <b-form-input id="input-6" v-model="wellness_spa" placeholder="Wellness and Spa Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Wifi Inclusion: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-7">
            <b-form-input id="input-7" v-model="wifi" placeholder="Wifi Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="TV Inclusion: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-8">
            <b-form-input id="input-8" v-model="tv" placeholder="TV Inclusion Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Minibar Inclusion: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-9">
            <b-form-input id="input-9" v-model="minibar" placeholder="Minibar Price" type="number" step="0.01"></b-form-input>
          </b-form-group>

          <b-button variant="outline-primary" type="submit" class="mr-1">Save</b-button>
          <b-button @click="onCancel">Cancel</b-button>
        </form><br>

        <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
    </b-card>
  </b-card-group>
</template>


<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'editExtraServices',
  data () {
    return {
      id : '',
      breakfast : 0.0,
      hotel_restaurant : 0.0,
      airport_transfer : 0.0,
      parking : 0.0,
      pool : 0.0,
      wellness_spa : 0.0,
      wifi : 0.0,
      tv : 0.0,
      minibar : 0.0,
      success : false,
      error : false
    }
  },
  mounted()
  {
    let api = '/hotels/' + this.$route.params.id + '/extraprices';
    AXIOS.get(api)
    .then(response => {
      this.id = response.data.id;
      this.breakfast = response.data.breakfast;
      this.hotel_restaurant = response.data.hotel_restaurant;
      this.airport_transfer = response.data.airport_transfer;
      this.parking = response.data.parking;
      this.pool = response.data.pool;
      this.wellness_spa = response.data.wellness_spa;
      this.wifi = response.data.wifi;
      this.tv = response.data.tv;
      this.minibar = response.data.minibar;
    })
  },
  methods: {
    edit (e) {
      e.preventDefault()

      const extraprices  = {
        'id': this.$route.params.id,
        'breakfast': this.breakfast,
        'hotel_restaurant': this.hotel_restaurant,
        'airport_transfer': this.airport_transfer,
        'parking': this.parking,
        'pool': this.pool,
        'wellness_spa': this.wellness_spa,
        'wifi': this.wifi,
        'tv': this.tv,
        'minibar': this.minibar
      }
      AXIOS.put('/hotels/updateExtraServices', extraprices)
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
      this.$router.push('/hotel-admin/' + this.$route.params.id + '/')
    }
  }
}
</script>


<style>
</style>