<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Add New Room</b></h6>
        <form @submit="add" inline>
          <b-form-group>
           <b-form-input v-model="floor" placeholder="Floor" type="number"></b-form-input>
          </b-form-group>
          
          <b-form-group>
            <b-form-input v-model="roomNum" placeholder="Room Number" type="number"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="beds" placeholder="Number of Beds" type="number"></b-form-input>
          </b-form-group>       
          
          <b-form-group>
            <b-form-input v-model="price" placeholder="Price per Night" type="number"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="discount" placeholder="Discount" type="number"></b-form-input>
          </b-form-group>

          <b-form-checkbox id="balcony_checkbox" v-model="balcony" name="balcony_checkbox" value="true" unchecked-value="false">
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

          <b-button variant="outline-primary" type="submit" class="mr-1">Add</b-button>
          <b-button :to="{ path: 'rooms'}">Cancel</b-button>

        </form><br>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
    </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default { 
  name: 'addRooms',
  data () {
    return {
      floor: '',
      roomNum: '',
      beds: '',
      balcony: 'false',
      breakfast : 'false',
      hotel_restaurant : 'false',
      airport_transfer : 'false',
      parking : 'false',
      pool : 'false',
      wellness_spa : 'false',
      wifi : 'false',
      tv : 'false',
      minibar : 'false',
      price: '',
      discount: '',
      success: false,
      error: false
    }
    
  },
  methods: {
    add (e) {
      e.preventDefault()

      const room = {
        'floor': this.floor,
        'roomNum': this.roomNum,
        'beds': this.beds,
        'balcony': this.balcony,
        'breakfast' : this.breakfast,
        'hotel_restaurant' : this.hotel_restaurant,
        'airport_transfer' : this.airport_transfer,
        'parking' : this.parking,
        'pool' : this.pool,
        'wellness_spa' : this.wellness_spa,
        'wifi' : this.wifi,
        'tv' : this.tv,
        'minibar' : this.minibar,
        'price' : this.price,
        'discount': this.discount,
        'hotelId':this.$route.params.id
      }

      console.log(room)

      AXIOS.post('/room/add', room)
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
  },
  mounted() 
  {
    let api = '/hotels/' + this.$route.params.id;

    AXIOS.get(api)
    .then(response => {
      this.hotel = response.data
    })
  }
}
</script>

<style>

</style>
