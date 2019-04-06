<template>
  <div>
    <form @submit="edit">
        {{ $route.params.id }}
      <b-form-input v-model="name" placeholder="Name"></b-form-input>
      <b-form-input v-model="city" placeholder="City"></b-form-input>
      <b-form-input v-model="city" placeholder="State"></b-form-input>
      <b-form-input v-model="description" placeholder="Description"></b-form-input>

      <b-button variant="outline-primary" type="submit">Edit</b-button>
      <b-button @click="onCancel">Cancel</b-button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'editAirline',
  data () {
    return {
      id: '',
      name: '',
      city: '',
      state: '',
      description: ''
    }
  },
  mounted(){

  }
  ,
  methods: {
    edit (e) {
      e.preventDefault()
      this.$router.push('/airlines')
    },
    onCancel (e) {
      e.preventDefault()
      this.$router.push('/airlines')
    }
  }
}
</script>

<style>

</style>
