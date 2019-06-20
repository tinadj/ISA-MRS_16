<template>
    <b-container>
    <b-row>
    </b-row>
    <b-row>
        <b-col>
            <br>
            <b-alert variant="light">There are no quick reservations!</b-alert>
            <ul>
                <li v-for="item in reservations">
                    <RACAdminQuickReservationInfo v-bind:reservation="item" :key="componentKey"></RACAdminQuickReservationInfo>
                    <br>
                </li>
            </ul>
        </b-col>
    </b-row>
    </b-container>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import RACAdminQuickReservationInfo from './RACAdmin_QuickReservationInfo'

    export default {
        name: "RACAdminQuickReservationsTable",
        components: {
            RACAdminQuickReservationInfo
        },
        data()
        {
            return {
                reservations: [],
                componentKey: 0
            }
        },
        methods: {
        },
        mounted() {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            
            AXIOS.get('/rent-a-car-quick-reservations/get/' + this.$route.params.id)
            .then(response => {
                console.log(this.reservations)
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