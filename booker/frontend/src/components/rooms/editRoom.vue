<template>
  <div>
    <form @submit="edit">
      <b-form-input v-model="floor" placeholder="Floor"></b-form-input>
      <b-form-input v-model="roomNum" placeholder="Room Number"></b-form-input>
      <b-form-input v-model="beds" placeholder="Number of Beds"></b-form-input>
      <b-form-input v-model="balcony" placeholder="Included Balcony"></b-form-input>
      <b-form-input v-model="discount" placeholder="Discount" type="number"></b-form-input>
      
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
  name: 'editRoom',
  data () {
    return {
      id: this.$route.params.r_id,
      floor: '',
      roomNum: '',
      beds: '',
      balcony: '',
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
        'balcony': this.balcony,
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