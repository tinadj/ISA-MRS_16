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
                                    <div class="profile-info-name"> Hotel: </div>
									<div class="profile-info-value">
										<span>
                                            <h5>{{reservation.hotel}} &nbsp
                                                <star-rating  :inline="true" :star-size="17" :show-rating="false"  :round-start-rating="false"></star-rating>
                                            </h5>
                                        </span> 
									</div>
								</div>                               

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Check In Date: </div>
									<div class="profile-info-value">
										<span>
                                           {{dateToStr(reservation.checkinDate)}}
                                        </span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> Check Out Date: </div>
									<div class="profile-info-value">
										<span>
                                           {{dateToStr(this.checkoutDate)}}
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
        name: 'HotelReservationInfo',
        props: ['reservation'],
        data() {
            return {
                details: false,
                checkoutDate: '',
                error: false,
                errorMessage: '',

                // Ikonice
                euroIcon: faEuroSign
                
            }
        },
        methods: {
            cancel: function(e) {
                
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
            let checkinDate = new Date(this.reservation.checkinDate)
            this.checkoutDate = this.addDays(checkinDate, this.reservation.nights)
            
            
        
            
        }
    }
</script>
