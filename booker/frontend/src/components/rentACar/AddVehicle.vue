<template>
    <div>
        <form @submit="add">
          <b-form-input v-model="name" placeholder="Name"></b-form-input>
          <b-form-input v-model="brand" placeholder="Brand"></b-form-input>
          <b-form-input v-model="model" placeholder="Model"></b-form-input>
          <b-form-input v-model="productionYear" placeholder="Production year" type="number"></b-form-input>
          <b-form-input v-model="seatsNum" placeholder="Number of seats" type="number"></b-form-input>
          <b-form-textarea v-model="description" placeholder="Description" rows="3"></b-form-textarea>

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
  name: 'AddVehicle',
  data () {
    return {
      name: '',
      brand: '',
      model: '',
      productionYear: '',
      seatsNum: '',
      description: '',
      rentACar: '',
      success: false,
      error: false
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

      const vehicle = {
        'name': this.name,
        'brand': this.brand,
        'model': this.model,
        'productionYear': this.productionYear,
        'seatsNum': this.seatsNum,
        'rentACar': this.rentACar,
        'description': this.description
      }

      AXIOS.post('/vehicles/add', vehicle)
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
