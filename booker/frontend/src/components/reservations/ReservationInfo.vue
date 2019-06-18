<template>
    <div>
        <b-container>
            <b-row>
                <b-col lg="4">
                    <label>Reservation number: #{{reservation.id}}</label>
                </b-col>
                <b-col lg="8">
                    <button v-b-toggle="'flight'" class="m-1 btn btn-info">Flight</button>
                    <button v-b-toggle="'hotel'" class="m-1 btn btn-info">Hotel</button>
                    <button v-b-toggle="'rent-a-car'" class="m-1 btn btn-info">Rent a Car</button>
                </b-col>
            </b-row>
        </b-container>

        <!-- Elements to collapse -->
        <b-collapse id="flight">
            <b-card>Flight reservation!</b-card>
        </b-collapse>

        <b-collapse id="hotel">
            <b-card>Hotel reservation!</b-card>
        </b-collapse>

        <b-collapse id="rent-a-car">
            <RentACarReservationInfo v-bind:reservation="reservation.rentACarReservation"></RentACarReservationInfo>
        </b-collapse>
    </div>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import RentACarReservationInfo from './RentACarReservationInfo'

    export default {
        name: 'ReservationInfo',
        props: ["reservation"],
        components: {
            RentACarReservationInfo
        },
        data() {
            return {

            }
        },
        methods: {
            
        },
        mounted() {
          AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
          
        }
    }
</script>