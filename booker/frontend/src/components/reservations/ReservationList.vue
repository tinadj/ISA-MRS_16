<template>
    <b-container>
    <b-row>
        <b-col lg="2">
            <b-form-select v-model="criteria" :options="criteriaOptions" ></b-form-select>
        </b-col>

        <b-col lg="1">
            <b-button variant="outline-secondary" v-on:click="sort">Sort</b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <br>
            <b-alert variant="light">Reservation history is empty!</b-alert>
            <ul>
                <li v-for="item in reservations">
                    <ReservationInfo v-bind:reservation="item"></ReservationInfo>
                    <br>
                </li>
            </ul>
        </b-col>
    </b-row>
    </b-container>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import ReservationInfo from './ReservationInfo'

    export default {
        name: "ReservationList",
        components: {
            ReservationInfo
        },
        data()
        {
            return {
                reservations: [],
                criteria: 0,
                criteriaOptions: 
                [
                    {value: 0, text: "Latest"},
                    {value: 1, text: "Oldest"},
                ]
            }
        },
        methods: {
            sort: function(e) {
                e.preventDefault()

                AXIOS.get('reservations/sort/' + this.criteria)
                .then(response => this.reservations = response.data)
                .catch(err => console.log(err))
            }
        },
        mounted() {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            
            AXIOS.get('/reservations/logged-in-user')
            .then(response => {
                this.reservations = response.data
            })
            .catch(err => console.log(err))
          
        }
    }
</script>

<style scoped>
ul {
  list-style-type: none;
}
</style>