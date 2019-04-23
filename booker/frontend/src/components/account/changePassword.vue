<template>
  <div class="row">
    <form @submit="edit" class="col-md-8">
      <b-form-input type="password" v-model="newPassword" placeholder="New Password"></b-form-input>
      <b-form-input type="password" v-model="confirmPassword" placeholder="Confirm Password"></b-form-input>
      
      <b-button variant="outline-primary" type="submit">Change Password</b-button>
      <b-button @click="onCancel">Cancel</b-button>
    </form>
    <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
    <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
  </div>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'changePassword',
  data () {
    return {
      newPassword: '',
      confirmPassword: '',
      success: false,
      error: false
    }
  },
  mounted(){

  }
  ,
  methods: {
      onCancel (e) {
      e.preventDefault()
      this.$router.push('/airline-admin/profile')
    }
  }
}
</script>

<style>

</style>
