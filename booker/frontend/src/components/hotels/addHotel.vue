<template>
  <div>
    <form @submit="add">
      <b-form-input v-model="name" placeholder="Name"></b-form-input>
      <b-form-input v-model="city" placeholder="City"></b-form-input>
      <b-form-input v-model="city" placeholder="State"></b-form-input>
      <b-form-input v-model="description" placeholder="Description"></b-form-input>

      <b-button variant="outline-primary" type="submit">Add</b-button>
      <b-button @click="onCancel">Cancel</b-button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AddHotel',
  data () {
    return {
      id: '',
      name: '',
      city: '',
      state: '',
      description: ''
    }
  },
  methods: {
    add (e) {
      e.preventDefault()

      this.$router.push('/hotels')
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
