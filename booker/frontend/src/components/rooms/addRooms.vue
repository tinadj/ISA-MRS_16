/* eslint-disable */
<template>
    <div>
        <form @submit="add">
          <b-form-input v-model="floor" placeholder="Floor" type="number"></b-form-input>
          <b-form-input v-model="roomNum" placeholder="Room Number" type="number"></b-form-input>
          <b-form-input v-model="beds" placeholder="Number of Beds" type="number"></b-form-input>
          <b-form-input v-model="balcony" placeholder="Included Balcony"></b-form-input>
          <b-form-input v-model="discount" placeholder="Discount" type="number"></b-form-input>

          <b-button variant="outline-primary" type="submit">Add</b-button>
          <b-button @click="onCancel">Cancel</b-button>
        </form>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
    </div>
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
      balcony: '',
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
        'discount': this.discount,
        'hotelId':this.$route.params.id
      }

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
