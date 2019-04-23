<template>
  <div>
	  <b-row>
		<b-nav class="nav nav-tabs margins w-100">
			<b-nav-item :to="{ path: 'info'}">Information</b-nav-item>
			<b-nav-item :to="{ path: 'edit'}">Edit information</b-nav-item>
			<b-nav-item :to="{ path: 'profile'}" active>Profile</b-nav-item>
		</b-nav>
      </b-row>
	  <br />
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
		  <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
		  <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
		</form>
		
	  </div>
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
      address: '',
      phone: '',
      success: false,
      error: false
    }
  },
  mounted(){
	let api = '/users/2';

    AXIOS.get(api)
    .then(response => { 
      this.id = response.data.id
      this.firstName = response.data.name
      this.lastName = response.data.lastname
	  this.email = response.data.email
	  this.address = response.data.address
	  this.phone = response.data.phoneNum
    })
  }
  ,
  methods: {
  edit (e) {
      e.preventDefault()

      const user = {
        'id': 2,
        'name': this.firstName,
        'lastname': this.lastName,
		'email': this.email,
		'address': this.address,
		'phoneNum': this.phone
      }
	  
	  console.log(user)
      AXIOS.put('/users/update', user)
      .then(response => {
        this.success = true;
        this.error = false;
      })
      .catch(err => {
        this.success = false;
        this.error = true
      })
    }
  }
}
</script>

<style>

</style>
