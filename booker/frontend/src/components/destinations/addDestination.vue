<template>
      <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Add new Destination</b></h6>
        <form @submit="add" inline>
          <b-form-group>
            <b-form-input v-model="city" placeholder="City"></b-form-input>
          </b-form-group>

          <b-form-group>
            <b-form-input v-model="state" placeholder="State"></b-form-input>
          </b-form-group>
          <b-button variant="outline-primary" type="submit" class="mr-1">Add</b-button>
          <b-button @click="onCancel">Cancel</b-button>
        </form><br>
        <b-alert variant="success" v-model="success">Successfully added!</b-alert>
        <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
      </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'AddDestination',
  data () {
    return {
      state: '',
      city: '',
      success: false,
      error: false
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

      const destination = {
        'state': this.state,
        'city': this.city,
        'airlineId': this.$route.params.id
      }

      AXIOS.post('/destinations/add', destination)
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
      this.$router.push('/hotels')
    }
  }
}
</script>

<style>

</style>
