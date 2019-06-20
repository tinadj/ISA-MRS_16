<template>
<b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;" align="center">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active">
					<div class="row">
						
                        <div class="col-xs-12 col-sm-3 center">
							<span class="profile-picture">
								<img class="img-thumbnail" alt="Picture"  src="../../assets/no-image.jpg">
							</span>

						</div><!-- /.col -->

						<div class="col-xs-12 col-sm-9">

							<div class="profile-user-info">

								<div class="profile-info-row">
									<div class="profile-info-value">
										<span>
                                            {{item.brand}} {{item.model}}, {{item.productionYear}}<br>
                                            <star-rating v-model="this.rating" :inline="true" :star-size="17" :show-rating="false" :read-only="true" :round-start-rating="false"></star-rating>
                                        </span> 
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-value">
										<span>{{item.seatsNum}} <font-awesome-icon :icon="personIcon"/> <b> | </b> Vehicle type: {{vehicleType}}</span>
									</div>
								</div>

                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span><font-awesome-icon :icon="locationIcon"/> Currently in: {{item.currentlyIn.address.city}}, {{item.currentlyIn.address.state}}&nbsp;&nbsp;<b-link v-on:click="showVehicleLocationModal">Change</b-link></span>
                                        <b-modal ref="vehicle-location" hide-footer>
                                            <b-form-select v-model="currentlyIn" :options="branchOffices" :state="branchOfficeValid"></b-form-select>
                                            <b-button class="mt-3" variant="outline-primary" block v-on:click="changeVehicleLocation">Save</b-button>
                                            <b-button class="mt-2" block v-on:click="hideVehicleLocationModal">Cancel</b-button>
                                        </b-modal>
									</div>
								</div>

                                <!-- Adding discount -->
                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span><font-awesome-icon :icon="discountIcon"/> Discount: {{item.discount}}% &nbsp
                                        <b-link v-on:click="showDiscountModal" class="mr-3">Change</b-link></span>
                                        <b-modal ref="discount" hide-footer>
                                            <b-input type="number" v-model="discount" placeholder="Discount"></b-input>
                                            <b-button class="mt-3" variant="outline-primary" block v-on:click="addDiscount">Save</b-button>
                                            <b-button class="mt-2" block v-on:click="hideDiscount">Cancel</b-button><br>
                                            <b-alert variant="danger" v-model="errorDiscount" dismissible>Invalid input!</b-alert>
                                        </b-modal>
                                    </div>
								</div>

                                <!-- Link Show more details -->
                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span>
                                            <b-link v-on:click="details = !details"><font-awesome-icon :icon="infoIcon"/>  Show more details...</b-link>
                                            <b-modal v-model="details" hide-footer>
                                                <template slot="modal-title">Description</template>
                                                <b-card-group deck>
                                                    <b-card border-variant="light">
                                                        <p class="text-left">                         
                                                            <font-awesome-icon :icon="descriptionIcon"/> 
                                                            {{item.description}}
                                                        </p>      
                                                    </b-card>
                                                </b-card-group>
                                            </b-modal>
                                        </span>
									</div>
								</div>

                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span>
                                            <b>{{item.price}} </b><font-awesome-icon :icon="euroIcon"/><br>
                                            (price per day)
                                        </span>
									</div>
								</div>

                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span>
                                            <b-button variant="outline-primary" v-on:click="edit" class="mr-3">Edit</b-button>
                                            <b-button variant="outline-danger"  v-on:click="showModal">Remove</b-button>
                                            <b-modal ref="confirmation" hide-footer>
                                                <div class="d-block text-center">
                                                    <h3>Are you sure you want to remove this vehicle?</h3>
                                                </div>
                                                <b-button class="mt-3" variant="outline-primary" block v-on:click="removeVehicle">Yes</b-button>
                                                <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
                                            </b-modal>
                                            <b-modal v-model="reservedVehicle" hide-footer>
                                                <div class="d-block text-center">
                                                    <h3>Vehicle is reserved!</h3>
                                                </div>
                                            </b-modal>
                                        </span>
									</div>
								</div>
							</div>
						</div><!-- /.col -->
					</div><!-- /.row -->				
          </div>
        </div>
      </div>
    </b-card >
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'
import { faEuroSign, faUser, faAlignLeft, faInfoCircle, faMapMarkerAlt, faTag } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'AdminVehicleInfo',
    props: ["item"],
    data() {
        return {
            rating: 0,
            details: false,
            vehicleType: '',
            totalPrice: 0,
            locationIcon: faMapMarkerAlt,
            discountIcon: faTag,
            euroIcon: faEuroSign,
            personIcon: faUser,
            infoIcon: faInfoCircle,
            descriptionIcon: faAlignLeft,
            reservedVehicle: false,
            branchOffices: [
                {value: null, text: "Choose branch office"}
            ],
            currentlyIn: null,
            branchOfficeValid: null,
            discount: '',
            errorDiscount: false
        } 
    },
    methods: {
        getVehicleType() {
            this.item.type = this.item.type.replace(/_/g,' ')
            return this.item.type.charAt(0).toUpperCase() + this.item.type.slice(1).toLowerCase()
        },
        showModal: function(id) {
            AXIOS.get('/vehicles/is-reserved/' + this.item.id)
            .then(response => {
                if (response.data == "OK")
                     this.$refs['confirmation'].show()
                else if (response.data = "FORBIDDEN")
                    this.reservedVehicle = true
                else
                    console.log(response)
            })
            .catch(err => console.log(err)) 
        },
        showDiscountModal: function() {
            this.$refs['discount'].show()
        },
        showVehicleLocationModal: function(id) {
            // pokupi sve lokacije branch office-a
            AXIOS.get('/rent-a-cars/' + this.$route.params.id)
            .then(response => {
                console.log(response.data.branchOffices)
                for (let i in response.data.branchOffices) {
                    this.branchOffices.push(
                        {value: response.data.branchOffices[i].id, text: response.data.branchOffices[i].name}
                    )
                }
                // proveri da li je vozilo rezervisano, ako nije tada moze promeniti lokaciju
                AXIOS.get('/vehicles/is-reserved/' + this.item.id)
                .then(response => {
                    if (response.data == "OK")
                        this.$refs['vehicle-location'].show()
                    else if (response.data = "FORBIDDEN")
                        this.reservedVehicle = true
                    else
                        console.log(response)
                })
                .catch(err => console.log(err))
                })
            .catch(err => console.log(err))
        },
        addDiscount: function() {
            this.errorDiscount = false

            if (this.discount < 0 || this.discount > 100) {
                this.errorDiscount = true
            } else {
                AXIOS.put('/vehicles/discount/' + this.item.id + '/' +  this.discount)
                .then(response => this.$router.go())
                .catch(err => this.errorDiscount = true)
            }
        },
        changeVehicleLocation: function() {
            this.branchOfficesValid = null

            if (this.currentlyIn != null) {
                AXIOS.put('/vehicles/update-vehicle-location/' + this.item.id + '/' +  this.currentlyIn)
                .then(response => this.$router.go())
                .catch(err=> console.log(err))
                this.$refs['vehicle-location'].hide()
            } else {
                this.branchOfficesValid = false
            }
        },
        removeVehicle: function() {
            AXIOS.delete('/vehicles/remove/' + this.item.id)
            .then(response => this.$router.go())
            .catch(err => console.log(err))

            this.$refs['confirmation'].hide()
        },
        hideModal: function() {
            this.$refs['confirmation'].hide()
        },
        hideDiscount: function() {
            this.$refs['discount'].hide()
        },
        hideVehicleLocationModal: function() {
            this.$refs['vehicle-location'].hide()
        },
        edit: function() {
            AXIOS.get('/vehicles/is-reserved/' + this.item.id)
            .then(response => {
                if (response.data == "OK")
                    this.$router.push("edit-vehicle-" + this.item.id)
                else if (response.data = "FORBIDDEN")
                    this.reservedVehicle = true
                else
                    console.log(response)
            })
            .catch(err => console.log(err))
        },
        // Racunanje prosecne ocene vozila
        getRating: async function() {
            await AXIOS.get('/vehicles/rating/' + this.item.id)
            .then(response => {
                this.rating = response.data
            })
            .catch(err => console.log(err))
        },
       
    },
    mounted() {
        this.getRating()
        
        this.vehicleType = this.getVehicleType()
    }
}
</script>


<style scoped>
    ul {
        list-style-type: none;
        margin: 0; /* To remove default bottom margin */ 
        padding: 0;
    }

  body{margin-top:20px;}

  .align-center, .center {
      text-align: center!important;
  }

  .profile-user-info {
      display: table;
      width: 98%;
      width: calc(100% - 24px);
      margin: 0 auto
  }

  .profile-info-row {
      display: table-row
  }


  .profile-info-value {
      display: table-cell;
      border-top: 1px dotted #D5E4F1
  }



  .profile-info-value {
      padding: 6px 4px 6px 6px
  }

  .profile-info-value>span+span:before {
      display: inline;
      content: ",";
      margin-left: 1px;
      margin-right: 3px;
      color: #666;
      border-bottom: 1px solid #FFF
  }

   .profile-info-value>span, .name {
      color: #666;
  }

  .profile-info-value>span+span.editable-container:before {
      display: none
  }

  .profile-info-row:first-child .profile-info-name,
  .profile-info-row:first-child .profile-info-value {
      border-top: none
  }

  .profile-user-info-striped {
      border: 1px solid #DCEBF7
  }

  .profile-user-info-striped .profile-info-name {
      color: #336199;
      background-color: #EDF3F4;
      border-top: 1px solid #F7FBFF
  }

  .profile-user-info-striped .profile-info-value {
      border-top: 1px dotted #DCEBF7;
      padding-left: 12px
  }

  .profile-picture {
      border: 1px solid #CCC;
      background-color: #FFF;
      padding: 4px;
      display: inline-block;
      max-width: 120%;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      box-shadow: 1px 1px 1px rgba(0, 0, 0, .15)
  }
</style>
