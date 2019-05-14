<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Edit information about branch office</b></h6>
      <form @submit="edit">

        <b-form-group 
            label="Name: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-1">
            <b-form-input id="input-1" v-model="name" placeholder="Name"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="City: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-2">
            <b-form-input id="input-2" v-model="city" placeholder="City"></b-form-input>
        </b-form-group>

        <b-form-group 
            label="State: " 
            label-cols="4" 
            label-cols-lg="2" 
            label-for="input-3">
            <b-form-input id="input-3" v-model="state" placeholder="State"></b-form-input>
        </b-form-group>

        <b-button variant="outline-primary" type="submit" class="mr-1">Save</b-button>
        <b-button @click="onCancel">Cancel</b-button>
      </form><br>

      <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
      <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>
    </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'EditBranchOffice',
  data () {
    return {
      id: this.$route.params.bo_id,
      name: '',
      city: '',
      state: '',
      rentACar: '',
      success: false,
      error: false,
      errorMessage: ''
    }
  },
  methods: {
    edit(e) {
      e.preventDefault()

      this.error = false

      if (this.noEmptyFiedls()) {
        const branch_office = {
          'id': this.id,
          'name': this.name,
          'address': {
              'city': this.city,
              'state': this.state
          },
          'rentACar': {
            'id':  this.$route.params.id
          }
        }
        AXIOS.put('/branch-offices/update', branch_office)
        .then(response => {
          this.success = true;
          this.error = false;
        })
        .catch(err => {
          this.errorMessage = "Something went wrong!"
          this.success = false;
          this.error = true
        })
        this.$router.push("/" + this.$route.params.id + "/rent-a-car-admin/branch-offices")
      } else {
        this.errorMessage = "Some fields are empty!"
        this.error = true
      }
    },
      onCancel (e) {
        e.preventDefault()
        this.$router.push("/" + this.$route.params.id + "/rent-a-car-admin/branch-offices")
    },
      noEmptyFiedls() {
        if (this.name.length == 0 || this.city.length == 0 || this.state.length == 0 ||
              this.latitude.length == 0 || this.longitude.length == 0) {
                  this.errorMessage = "Some fields are empty!"
                  return false 
        }
        return true
    }
  },
  mounted() {
    let api = '/branch-offices/' + this.id

    AXIOS.get(api)
    .then(response => { 
      this.name = response.data.name
      this.city = response.data.address.city
      this.state = response.data.address.state
      this.rentACar = response.data.rentACar
    })
  }
}
</script>

<style>
</style>