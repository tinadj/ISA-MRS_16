<template>
    <div>
        <form @submit="add">
          <b-form-input v-model="name" placeholder="Name"></b-form-input>
          <b-form-input v-model="city" placeholder="City"></b-form-input>
          <b-form-input v-model="state" placeholder="State"></b-form-input>

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
  name: 'AddBranchOffice',
  data () {
    return {
      name: '',
      city: '',
      state: '',
      rentACar: '',
      success: false,
      error: false
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

      const branch_office = {
        'name': this.name,
        'address': {
            'city': this.city,
            'state': this.state
        },
        'rentACar': this.rentACar,
      }

      AXIOS.post('/branch-offices/add', branch_office)
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
