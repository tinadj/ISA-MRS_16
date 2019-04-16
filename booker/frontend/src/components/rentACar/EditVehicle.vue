<template>
  <div>
    <form @submit="edit">
      <b-form-input v-model="name" placeholder="Name"></b-form-input>
      <b-form-input v-model="brand" placeholder="Brand"></b-form-input>
      <b-form-input v-model="model" placeholder="Model"></b-form-input>
      <b-form-input v-model="productionYear" placeholder="Production year" type="number"></b-form-input>
      <b-form-input v-model="seatsNum" placeholder="Number of seats" type="number"></b-form-input>
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
  name: 'EditVehicle',
  data () {
    return {
      id: this.$route.params.v_id,
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
    edit(e) {
      e.preventDefault()

      const vehicle = {
        'id': this.id,
        'name': this.name,
        'brand': this.brand,
        'model': this.model,
        'productionYear': this.productionYear,
        'seatsNum': this.seatsNum,
        'rentACar': {
          'id':  this.$route.params.id
        },
        'description': this.description
     }
      AXIOS.put('/vehicles/update', vehicle)
      .then(response => {
        this.success = true;
        this.error = false;
      })
      .catch(err => {
        this.success = false;
        this.error = true
      })
      this.$router.push('/rent-a-car-admin/' + this.$route.params.id + "/vehicles")
    },
      onCancel (e) {
        e.preventDefault()
        this.$router.push('/rent-a-car-admin/' + this.$route.params.id + "/vehicles")
    }
  },
  mounted() {
    let api = '/vehicles/' + this.id

    AXIOS.get(api)
    .then(response => { 
      this.name = response.data.name
      this.brand = response.data.brand
      this.model = response.data.model
      this.productionYear = response.data.productionYear
      this.seatsNum = response.data.seatsNum
      this.description = response.data.description
      this.rentACar = response.data.rentACar
    })
  }
}
</script>

<style>
</style>