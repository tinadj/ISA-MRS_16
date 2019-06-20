<template>
  <b-card-group>
    <b-card header-tag="header" border-variant="light" style="max-width: 40rem;" class="mb-2 mx-auto">
      <h6 slot="header" class="mb-0"><b>Define quick reservation from vehicle</b></h6>
        <form>

          <b-form-group>
            <v-date-picker v-model="pickUp" 
            :min-date='new Date()' 
            :first-day-of-week="2"
            :input-props='{
                placeholder: "Pick up date",
                readonly: true
            }'/>
        </b-form-group>

          <b-form-group>
            <v-date-picker v-model="dropOff" 
            :min-date='new Date()' 
            :first-day-of-week="2"
            :input-props='{
                placeholder: "Drop off date",
                readonly: true
            }'/>
        </b-form-group>

        <b-form-group >
            <b-form-input id="input-4" v-model="discount" placeholder="Discount" type="number"></b-form-input>
          </b-form-group>

          <b-button variant="outline-primary" type="submit" class="mr-1" v-on:click="save">Save</b-button>
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
  name: 'DefineQuickReservation',
  data () {
    return {
      id: this.$route.params.v_id,
      pickUp: null,
      dropOff: null,
      discount: 0,
      success: false,
      error: false,
      errorMessage: ''
    }
  },
  methods: {
    save(e) {
        e.preventDefault()
        
        this.error = false
        this.success = false

        if (this.valid()) {
                const params = {
                'vehicleID': this.id,
                'pickUpDate': this.pickUp,
                'dropOffDate': this.dropOff,
                'discount': this.discount
                }

                AXIOS.post('rent-a-car-quick-reservations/add', params)
                .then(response => {
                    if (response.status == 200) {
                        this.success = true
                    } else if (response.status = 403) {
                        this.errorMessage = "Vehicle is reserved in this period!"
                        this.error = true
                    } else {
                        this.errorMessage = "Something went wrong!"
                        this.error = true
                    }
                })
                .catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.error = true
                })

        } else {
            this.error = true
        }
    },
    onCancel(e) {
        e.preventDefault()
        this.$router.push("/" + this.$route.params.id + "/rent-a-car-admin/vehicles")
    },
    valid() {
        if (this.pickUp == null || this.dropOff == null) {
            this.errorMessage = "Dates are not selected!"
            return false
        }
        if (this.pickUp.getTime() == this.dropOff.getTime()) {
            this.errorMessage = "Dates must be different!"
            return false
        }
        if (this.discount == 0 || this.discount.length == 0 || this.discount > 100) {
            this.errorMessage = "Discount input is not valid!"
            return false
        }
        return true
    }
  },
  mounted() {
    
  }
}
</script>

<style>
</style>