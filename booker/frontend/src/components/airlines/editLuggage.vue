<template>
  <div>
	  <b-card-group>
		<b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
		  <h6 slot="header" class="mb-0"><b>Edit Luggage Prices</b></h6>
		  <form @submit="edit">

			<b-form-group
				label="CarryON: "
				label-cols="4"
				label-cols-lg="2"
				label-for="input-1">
				<b-form-input id="input-1" v-model="carryOn" placeholder="CarryON Price" type=number step=0.01></b-form-input>
			</b-form-group>

			<b-form-group
      				label="Checked: "
      				label-cols="4"
      				label-cols-lg="2"
      				label-for="input-1">
      				<b-form-input id="input-1" v-model="checked" placeholder="Checked Price" type=number step=0.01></b-form-input>
      			</b-form-group>

			<b-button variant="outline-primary" type="submit" class="mr-1">Save</b-button>
			<b-button :to="{ path: 'info'} ">Cancel</b-button>

		  </form><br>
		  <b-alert variant="success" v-model="success">Successfully saved!</b-alert>
		  <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>
		</b-card>
	  </b-card-group>
    </div>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  name: 'editLuggage',
  data () {
    return {
      id: '',
      carryOn: '',
      checked: '',
      success: false,
      error: false
    }
  },
  mounted() {
    let api = '/airlines/' + this.$route.params.id;

    AXIOS.get(api)
    .then(response => {
    console.log(response.data)
      this.id = response.data.id
      if(response.data.luggagePrices[0].type === "CHECKED")
        this.checked = response.data.luggagePrices[0].price
      else
        this.carryOn = response.data.luggagePrices[0].price

      if(response.data.luggagePrices[1].type === "CARRY_ON")
        this.checked = response.data.luggagePrices[1].price
      else
        this.carryOn = response.data.luggagePrices[1].price

        console.log(response.data.luggagePrices[1].price)
        console.log(response.data.luggagePrices[0].price)
    })
  }
  ,
  methods: {
    edit (e) {
      e.preventDefault()
      
    }
  }
}
</script>

<style>

</style>
