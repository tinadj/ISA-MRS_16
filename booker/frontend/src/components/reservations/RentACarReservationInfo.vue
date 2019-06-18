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
                                                <star-rating v-model="racRating" :inline="true" :star-size="17" :show-rating="false" :read-only="true" :round-start-rating="false"></star-rating>
                                            </h5>
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Vehicle: </div>
									<div class="profile-info-value">
										<span>
                                            <h6>{{reservation.vehicle.brand}}, {{reservation.vehicle.model}}, {{reservation.vehicle.productionYear}} &nbsp
                                                <star-rating :inline="true" :star-size="17" :show-rating="false" :read-only="true" :round-start-rating="false"></star-rating>
                                            </h6>
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Pick up: </div>
									<div class="profile-info-value">
										<span>
                                           {{reservation.pickUpLocation.address.city}}, {{reservation.pickUpLocation.address.state}} date
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Drop off: </div>
									<div class="profile-info-value">
										<span>
                                             {{reservation.dropOffLocation.address.city}}, {{reservation.dropOffLocation.address.state}}, date
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
                                        <button class="btn btn-outline-secondary" v-on:click="cancel">Cancel reservation</button>
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

                // Ikonice
                euroIcon: faEuroSign
                
            }
        },
        methods: {
            cancel: function(e) {

            }
        },
        mounted() {
            
        }
    }
</script>
