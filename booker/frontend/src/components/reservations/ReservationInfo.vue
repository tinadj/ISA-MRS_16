<template>
    <div>
        <b-container>
            <b-row>
                <b-col lg="4">
                    <label>Reservation number: #{{reservation.id}}</label>
                </b-col>
                <b-col lg="8">
                    <button v-b-toggle="flightToggle" class="m-1 btn btn-info">Flight</button>
                    <button v-b-toggle="hotelToggle" class="m-1 btn btn-info">Hotel</button>
                    <button v-if="racExists" v-b-toggle="racToggle" class="m-1 btn btn-info">Rent a Car</button>
                </b-col>
            </b-row>
        </b-container>

        <!-- Elements to collapse -->
        <b-collapse v-bind:id="flightToggle">
            <b-card>Flight reservation!</b-card>
        </b-collapse>

        <b-collapse v-bind:id="hotelToggle">
            <HotelReservationInfo v-if="hotelExists" v-bind:reservation="reservation.hotelReservation"></HotelReservationInfo>
        </b-collapse>

        <b-collapse  v-bind:id="racToggle">
            <RentACarReservationInfo v-if="racExists" v-bind:reservation="reservation.rentACarReservation"></RentACarReservationInfo>
        </b-collapse>
    </div>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import RentACarReservationInfo from './RentACarReservationInfo'
    import HotelReservationInfo from './HotelReservationInfo'

    export default {
        name: 'ReservationInfo',
        props: ["reservation"],
        components: {
            RentACarReservationInfo,
            HotelReservationInfo
        },
        data() {
            return {
                flightToggle: 'flight' + this.reservation.id,
                hotelToggle: 'hotel' + this.reservation.id,
                racToggle: 'rent-a-car' + this.reservation.id,

                hotelExists: true,
                racExists: true

            }
        },
        methods: {
        },
        mounted() {
            
            if (this.reservation.hotelReservation == null)
                this.hotelExists = false
            
            if (this.reservation.rentACarReservation == null)
                this.racExists = false
        }
    }
</script>