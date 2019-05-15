<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Edit information about room</b></h6>
        <form @submit="edit">

          <b-form-group 
            label="Floor: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-1">
            <b-form-input id="input-1" v-model="floor" placeholder="Floor"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Room Number: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-2">
            <b-form-input id="input-2" v-model="roomNum" placeholder="Room number"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Beds: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-3">
            <b-form-input id="input-3" v-model="beds" placeholder="Beds"></b-form-input>
          </b-form-group>
          
          <b-form-group 
            label="Discount: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-5">
            <b-form-input id="input-5" v-model="discount" placeholder="Discount" type="number"></b-form-input>
          </b-form-group>

          <b-form-checkbox id="balcony_checkbox" v-model="balcony_status" name="balcony_checkbox" value="true" unchecked-value="false">
            Balcony inclusion
          </b-form-checkbox>

          <!-- Extra Services -->

          <b-form-checkbox id="breakfast_cb" v-model="breakfast" name="breakfast_cb" value="true" unchecked-value="false">
            Breakfast 
          </b-form-checkbox>
          <b-form-checkbox id="hotel_restaurant_cb" v-model="hotel_restaurant" name="hotel_restaurant_cb" value="true" unchecked-value="false">
            Hotel Restaurant
          </b-form-checkbox>
          <b-form-checkbox id="airport_transfer_cb" v-model="airport_transfer" name="airport_transfer_cb" value="true" unchecked-value="false">
            Airport Transfer
          </b-form-checkbox>
          <b-form-checkbox id="parking_cb" v-model="parking" name="parking_cb" value="true" unchecked-value="false">
            Parking
          </b-form-checkbox>
          <b-form-checkbox id="pool_cb" v-model="pool" name="pool_cb" value="true" unchecked-value="false">
            Pool Access
          </b-form-checkbox>
          <b-form-checkbox id="wellness_spa_cb" v-model="wellness_spa" name="wellness_spa_cb" value="true" unchecked-value="false">
            Wellness and Spa
          </b-form-checkbox>
          <b-form-checkbox id="wifi_cb" v-model="wifi" name="wifi_cb" value="true" unchecked-value="false">
            Wifi Connection
          </b-form-checkbox>
          <b-form-checkbox id="tv_cb" v-model="tv" name="tv_cb" value="true" unchecked-value="false">
            Television
          </b-form-checkbox>
          <b-form-checkbox id="minibar_cb" v-model="minibar" name="minibar_cb" value="true" unchecked-value="false">
            Minibar
          </b-form-checkbox>

          <!-- -------------- -->

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
  name: 'editRoom',
  data () {
    return {
      id: this.$route.params.r_id,
      floor: '',
      roomNum: '',
      beds: '',
      balcony_status: 'false',
      breakfast : 'false',
      hotel_restaurant : 'false',
      airport_transfer : 'false',
      parking : 'false',
      pool : 'false',
      wellness_spa : 'false',
      wifi : 'false',
      tv : 'false',
      minibar : 'false',
      discount: '',
      hotelId: '',
      success: false,
      error: false
    }
  },
  methods: {
    edit(e) {
      e.preventDefault()

      const room = {
        'id': this.id,
        'floor': this.floor,
        'roomNum': this.roomNum,
        'beds': this.beds,
        'balcony': this.balcony_status,
        'breakfast' : this.breakfast,
        'hotel_restaurant' : this.hotel_restaurant,
        'airport_transfer' : this.airport_transfer,
        'parking' : this.parking,
        'pool' : this.pool,
        'wellness_spa' : this.wellness_spa,
        'wifi' : this.wifi,
        'tv' : this.tv,
        'minibar' : this.minibar,
        'discount': this.discount,
        'hotelId':this.$route.params.id
    }
      AXIOS.put('/room/update', room)
      .then(response => {
        this.success = true;
        this.error = false;
      })
      .catch(err => {
        this.success = false;
        this.error = true;
      })
      this.$router.push('/' + this.$route.params.id + '/hotel-admin/rooms')
    },
      onCancel (e) {
        e.preventDefault()
        this.$router.push('/' + this.$route.params.id + '/hotel-admin/rooms')
    }
  },
  mounted() {
    let api = '/room/' + this.id

    AXIOS.get(api)
    .then(response => { 
      this.floor = response.data.floor
      this.roomNum = response.data.roomNum
      this.beds = response.data.beds
      this.discount = response.data.discount
      this.hotel = response.data.hotel
      this.balcony_status = response.data.balcony

      for(let i in response.data.extraServices)
      {
        if(response.data.extraServices[i] == 'BREAKFAST')
          this.breakfast = true
        else if(response.data.extraServices[i] == 'POOL')
          this.pool = true
        else if(response.data.extraServices[i] == 'WELLNESS_SPA')
          this.wellness_spa = true
        else if(response.data.extraServices[i] == 'TV')
          this.tv = true
        else if(response.data.extraServices[i] == 'MINIBAR')
          this.minibar = true
        else if(response.data.extraServices[i] == 'HOTEL_RESTAURANT')
          this.hotel_restaurant = true
        else if(response.data.extraServices[i] == 'AIRPORT_TRANSFER')
          this.airport_transfer = true
        else if(response.data.extraServices[i] == 'PARKING')
          this.parking = true
        else if(response.data.extraServices[i] == 'WIFI')
          this.wifi = true      
      }
    })
  }
}
</script>

<style>
</style>