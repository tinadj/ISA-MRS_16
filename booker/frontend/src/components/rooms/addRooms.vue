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
          
          <b-form-checkbox id="balcony_checkbox" v-model="balcony_status" name="balcony_checkbox" value="true" unchecked-value="false">
            Balcony inclusion
          </b-form-checkbox>
          
          <b-form-group>
            <b-form-input v-model="discount" placeholder="Discount" type="number"></b-form-input>
          </b-form-group>

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
      balcony_status: 'false',
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
        'balcony': this.balcony_status,
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
