<template>
    <b-card>
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
                                    <div class="profile-info-name"> Rent a car service: </div>
									<div class="profile-info-value">
										<span>
                                            <h5>{{reservation.rentACar}} &nbsp
                                                <star-rating v-model="racRating" :inline="true" :star-size="17" :show-rating="false" :read-only="!returnedVehicle" :round-start-rating="false" v-on:rating-selected ="rateRAC"></star-rating>
                                            </h5>
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Vehicle: </div>
									<div class="profile-info-value">
										<span>
                                            <h6>{{reservation.vehicle.brand}}, {{reservation.vehicle.model}}, {{reservation.vehicle.productionYear}} &nbsp
                                                <star-rating v-model="vehicleRating" :inline="true" :star-size="17" :show-rating="false" :read-only="!returnedVehicle" :round-start-rating="false" v-on:rating-selected ="rateVehicle"></star-rating>
                                            </h6>
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Pick up date: </div>
									<div class="profile-info-value">
										<span>
                                           {{dateToStr(reservation.pickUpDate)}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Drop off date: </div>
									<div class="profile-info-value">
										<span>
                                           {{dateToStr(this.returnDate)}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Pick up location: </div>
									<div class="profile-info-value">
										<span>
                                           {{reservation.pickUpLocation.address.city}}, {{reservation.pickUpLocation.address.state}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Drop off location: </div>
									<div class="profile-info-value">
										<span>
                                             {{reservation.dropOffLocation.address.city}}, {{reservation.dropOffLocation.address.state}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Price: </div>
									<div class="profile-info-value">
										<span>
                                            {{reservation.totalPrice}} <font-awesome-icon :icon="euroIcon"/>
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"></div>
                                     <div class="profile-info-value">
                                        <button class="btn btn-outline-secondary" v-if="!returnedVehicle" v-on:click="cancel">Cancel reservation</button>
                                    </div>
							    </div>
                                <br>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"></div>
                                     <div class="profile-info-value">
                                        <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>
                                    </div>
							    </div>

							</div>
						</div><!-- /.col -->
					</div><!-- /.row -->				
                </div>
            </div>
        </div>
    </b-card>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import { faEuroSign } from '@fortawesome/free-solid-svg-icons'

    export default {
        name: 'RentACarReservationInfo',
        props: ['reservation'],
        data() {
            return {
                details: false,
                racRating: 0,
                vehicleRating: 0,
                returnDate: '',
                returnedVehicle: false,
                error: false,
                errorMessage: '',

                // Ikonice
                euroIcon: faEuroSign
                
            }
        },
        methods: {
            cancel: function(e) {
                this.error = false
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                AXIOS.post('/rac-reservations/cancel/' + this.reservation.id)
                .then( response => {
                    console.log(response.data)
                    if (response.data == true) {
                        this.$router.go()
                    } else {
                        this.errorMessage = "It is not possible to cancel this reservation!"
                        this.error = true
                    }
                }).
                catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.error = true
                })
            },
            // Ocenjivanje vozila
            rateVehicle: async function() {
                await AXIOS.post('vehicles/rate/' + this.reservation.vehicle.id + '/' + this.vehicleRating)
                .then(response => this.vehicleRating = response.data)
                .catch(err => console.log(err))
            },
            // Ocenjivanje rent a car-a
            rateRAC: async function() {
                await AXIOS.post('rent-a-cars/rate/' + this.reservation.rentACar + '/' + this.racRating)
                .then(response => this.racRating = response.data)
                .catch(err => console.log(err))
            },
            // Konvertuje datum u string format dd.MM.yyyy
            dateToStr: function(date) {
                let converted = new Date(date)
                return converted.getDate() + "." + (converted.getMonth() + 1) + "." + converted.getFullYear()
            },
            // 'Sabira' datum i broj dana
            addDays: function(date, days) {
                var result = new Date(date);
                result.setDate(result.getDate() + days);
                return result;
            },
            // Racunanje prosecne ocene vozila
            getVehicleRating: async function() {
                await AXIOS.get('/vehicles/rating/' + this.reservation.vehicle.id)
                .then(response => {
                    this.vehicleRating = response.data
                })
                .catch(err => console.log(err))
            },
            // Racunanje prosecne ocene rent a car servisa
            getRACRating: async function() {
                await AXIOS.get('/rent-a-cars/rating/' + this.reservation.rentACar)
                .then(response => {
                    this.racRating = response.data
                })
                .catch(err => console.log(err))
            }
        },
        mounted() {
            // Provera da li je rezervacija prosla
            let pickUpDate = new Date(this.reservation.pickUpDate)
            this.returnDate = this.addDays(pickUpDate, this.reservation.days)
            
            this.returnedVehicle = new Date(this.returnDate.toDateString()) < new Date(new Date().toDateString())
        
            // Ocena vozila
            this.getVehicleRating()
            // Ocena rent a car-a
            this.getRACRating()
        }
    }
</script>
