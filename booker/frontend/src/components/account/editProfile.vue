<template>
  <div class="row">
    <div class="col-md-4">
        <img src="../../assets/no-image.jpg" class="img-thumbnail" alt="Profile picture"> 
        <div class="input-group">
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01">
                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
            </div>

            <div class="input-group-prepend">
                <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
            </div>
        </div>
    </div>
    <form @submit="edit" class="col-md-8">
      <b-form-input v-model="firstName" placeholder="First Name"></b-form-input>
      <b-form-input v-model="lastName" placeholder="Last Name"></b-form-input>
      <b-form-input type="email" v-model="email" placeholder="E-mail address"></b-form-input>
      <b-form-input v-model="address" placeholder="Address"></b-form-input>
      <b-form-input v-model="phone" placeholder="Phone Number" type="number"></b-form-input>

      <b-link :to="{ path: 'password'}" class="float-right">Change Password</b-link> <br />

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
  name: 'editProfile',
  data () {
    return {
      id: '',
      firstName: '',
      lastName: '',
      email: '',
      adress: '',
      phone: '',
      description: '',
      success: false,
      error: false
    }
  },
  mounted(){

  }
  ,
  methods: {
  }
}
</script>

<style>

</style>
