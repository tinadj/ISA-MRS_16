<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Edit information about vehicle</b></h6>
        <form @submit="edit">

          <b-form-group 
            label="Name: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-1">
            <b-form-input id="input-1" v-model="name" placeholder="Name"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Brand: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-2">
            <b-form-input id="input-2" v-model="brand" placeholder="Brand"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Model: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-3">
            <b-form-input id="input-3" v-model="model" placeholder="Model"></b-form-input>
          </b-form-group>
          
          <b-form-group 
            label="Production year: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-4">
            <b-form-input id="input-4" v-model="productionYear" placeholder="Production year" type="number"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Vehicle type: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-5">
            <b-form-select id="input-5" v-model="type" :options="typeOptions" :state="typeValid"></b-form-select>
          </b-form-group>

          <b-form-group 
            label="Number of seats: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-6">
            <b-form-input id="input-6" v-model="seatsNum" placeholder="Number of seats" type="number"></b-form-input>
          </b-form-group>
          
          <b-form-group 
            label="Price: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-7">
            <b-form-input id="input-7" v-model="price" placeholder="Price" type="number"></b-form-input>
          </b-form-group>

          <b-form-group 
            label="Description: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-8">
            <b-form-textarea id="input-8" v-model="description" placeholder="Description" rows="3"></b-form-textarea>
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
  name: 'EditVehicle',
  data () {
    return {
      id: this.$route.params.v_id,
      name: '',
      brand: '',
      model: '',
      productionYear: '',
      type: '',
      seatsNum: '',
      price: '',
      description: '',
      rentACar: '',
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
      typeValid: null,
      success: false,
      error: false
    }
  },
  methods: {
    edit(e) {
      e.preventDefault()
      
      this.typeValid = null

      if (this.type != null) {
        const vehicle = {
        'id': this.id,
        'name': this.name,
        'brand': this.brand,
        'model': this.model,
        'productionYear': this.productionYear,
        'type': this.type,
        'seatsNum': this.seatsNum,
        'price': this.price,
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
          this.$router.push("/rent-a-car-admin/" + this.$route.params.id + "/vehicles")
      } else {
        this.typeValid = false
      }
      
    },
      onCancel (e) {
        e.preventDefault()
        this.$router.push("/rent-a-car-admin/" + this.$route.params.id + "/vehicles")
    },
      // konvertuje enum u int vrednost
      getSelectedType(i) {
        switch(i) {
          case "ECONOMY": return 0;
          case "COMPACT": return 1;
          case "MID_SIZE": return 2;
          case "FULL_SIZE": return 3;
          case "LUXURY": return 4;
          case "MINIVAN": return 5;
          case "SUV": return 6;
          default: return null;
        }
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
      this.price = response.data.price
      this.description = response.data.description
      this.rentACar = response.data.rentACar
      this.type = this.getSelectedType(response.data.type)
    })
  }
}
</script>

<style>
</style>