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
          
           <b-form-checkbox id="balcony_checkbox" v-model="balcony_status" name="balcony_checkbox" value="true" unchecked-value="false">
            Balcony inclusion
          </b-form-checkbox>

          <b-form-group 
            label="Discount: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-5">
            <b-form-input id="input-5" v-model="discount" placeholder="Discount" type="number"></b-form-input>
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
  name: 'editRoom',
  data () {
    return {
      id: this.$route.params.r_id,
      floor: '',
      roomNum: '',
      beds: '',
      balcony_status: 'false',
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
        'discount': this.discount,
        'hotelId':  this.$route.params.id
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
      this.$router.push('/hotel-admin/' + this.$route.params.id + "/rooms")
    },
      onCancel (e) {
        e.preventDefault()
        this.$router.push('/hotel-admin/' + this.$route.params.id + "/rooms")
    }
  },
  mounted() {
    let api = '/room/' + this.id

    AXIOS.get(api)
    .then(response => { 
      this.floor = response.data.floor
      this.roomNum = response.data.roomNum
      this.beds = response.data.beds
      this.balcony = response.data.balcony
      this.discount = response.data.discount
      this.hotel = response.data.hotel
    })
  }
}
</script>

<style>
</style>