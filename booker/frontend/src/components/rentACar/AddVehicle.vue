<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Add new vehicle</b></h6>
        <form @submit="add" inline>
          <b-form-group>
            <b-form-input v-model="name" placeholder="Name"></b-form-input>
          </b-form-group>
          
          <b-form-group>
            <b-form-input v-model="brand" placeholder="Brand"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="model" placeholder="Model"></b-form-input>
          </b-form-group>       
          
          <b-form-group>
            <b-form-input v-model="productionYear" placeholder="Production year" type="number"></b-form-input>
          </b-form-group>
          
          <b-form-group>
            <b-form-select v-model="type" :options="typeOptions" :state="typeValid"></b-form-select>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="seatsNum" placeholder="Number of seats" type="number"></b-form-input>
          </b-form-group>
          
          <b-form-group>
            <b-form-input v-model="price" placeholder="Price per day" type="number"></b-form-input>
          </b-form-group>
          
          <b-form-group>
            <b-form-textarea v-model="description" placeholder="Description" rows="3"></b-form-textarea>
          </b-form-group>

          <b-button variant="outline-primary" type="submit" class="mr-1">Add</b-button>
          <b-button :to="{ path: 'vehicles'}">Cancel</b-button>

        </form><br>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
    </b-card>
  </b-card-group>
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
      price: '',
      type: null,
      description: '',
      rentACar: '',
      success: false,
      error: false,
      typeOptions: [
        {value: null, text: "Choose vehicle type"},
        {value: 0, text: "Economy"},
        {value: 1, text: "Compact"},
        {value: 2, text: "Mid size"},
        {value: 3, text: "Full size"},
        {value: 4, text: "Luxury"},
        {value: 5, text: "Minivan"},
        {value: 6, text: "SUV"},
      ],
      typeValid: null // indikator da li je izabran tip vozila (nije ostalo na null)
    }
    
  },
  methods: {
    add (e) {
      e.preventDefault()

      this.typeValid = null

      if (this.type != null) {
          const vehicle = {
          'name': this.name,
          'brand': this.brand,
          'model': this.model,
          'productionYear': this.productionYear,
          'type': this.type,
          'seatsNum': this.seatsNum,
          'price': this.price,
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
      } else {
        this.typeValid = false
      }
      
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
