<template>
    <b-container>
    <b-row>
        <b-col>
            <br>
            <b-card-group>
                <b-card style="max-width: 62rem;">
                    <ul>
                        <li v-for="item in airlines">
                            <RegisteredUserAirlineInfo v-bind:item="item"></RegisteredUserAirlineInfo>
                        </li>
                    </ul>
                </b-card>
            </b-card-group>
        </b-col>
    </b-row>
    </b-container>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import RegisteredUserAirlineInfo from './RegisteredUser_AirlineInfo'

    export default {
        name: "RegisteredUserSearchAirline",
        components: {
            RegisteredUserAirlineInfo
        },
        data()
        {
            return {
                airlines: '',
            }
        },
        methods: {
            search() {
            }
                
        },
        mounted() {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            AXIOS.get('/airlines/all')
            .then(response => { this.airlines = response.data})
            .catch(err => console.log(err))
        }
    }
</script>

<style scoped>
ul {
  list-style-type: none;
}
</style>