<template>
  <div>
    <form @submit="edit">
      <b-form-input v-model="name" placeholder="Name"></b-form-input>
      <b-form-input v-model="city" placeholder="City"></b-form-input>
      <b-form-input v-model="state" placeholder="State"></b-form-input>

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
  name: 'EditBranchOffice',
  data () {
    return {
      id: this.$route.params.bo_id,
      name: '',
      city: '',
      state: '',
      rentACar: '',
      success: false,
      error: false
    }
  },
  methods: {
    edit(e) {
      e.preventDefault()

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
        this.success = false;
        this.error = true
      })
      this.$router.push('/rent-a-car-admin/' + this.$route.params.id + "/branch-offices")
    },
      onCancel (e) {
        e.preventDefault()
        this.$router.push('/rent-a-car-admin/' + this.$route.params.id + "/branch-offices")
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