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
                                    <div class="profile-info-name"> Vehicle: </div>
									<div class="profile-info-value">
										<span>
                                            <h6>{{reservation.vehicle.brand}}, {{reservation.vehicle.model}}, {{reservation.vehicle.productionYear}} &nbsp
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
                                           {{dateToStr(reservation.dropOffDate)}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Pick up location: </div>
									<div class="profile-info-value">
										<span>
                                           {{reservation.vehicle.currentlyIn.address.city}}, {{reservation.vehicle.currentlyIn.address.state}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Drop off location: </div>
									<div class="profile-info-value">
										<span>
                                            {{reservation.vehicle.currentlyIn.address.city}}, {{reservation.vehicle.currentlyIn.address.state}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Discount: </div>
									<div class="profile-info-value">
										<span>
                                            {{reservation.discount}}%
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"></div>
                                     <div class="profile-info-value">
                                        <button class="btn btn-outline-secondary" v-if="!pastReservation" v-on:click="cancel">Cancel vehicle reservation</button>
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

    export default {
        name: 'RACAdmin_QuickReservationInfo',
        props: ['reservation'],
        data() {
            return {
                pastReservation: false,
                error: false,
                errorMessage: '',

            }
        },
        methods: {
            cancel: function(e) {
                this.error = false
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                AXIOS.delete('/rent-a-car-quick-reservations/remove/' + this.reservation.id)
                .then( response => {
                    if (response.status == 200) {
                        this.$router.go()
                    } else if (response.status == 403) {
                        this.errorMessage = "It is not possible to remove this quick reservation!"
                        this.error = true
                    } else {
                        this.errorMessage = "Something went wrong!"
                        this.error = true
                    }
                }).
                catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.error = true
                })
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
        },
        mounted() {
            // Provera da li je rezervacija prosla
            let pickUpDate = new Date(this.reservation.pickUpDate)
            this.pastReservation = new Date(this.reservation.dropOffDate.toDateString()) < new Date(new Date().toDateString())
        }
    }
</script>
