<template>
    <b-container>
    <b-row>
        <b-col>
            <br>
            <b-card-group>
                <b-card style="max-width: 62rem;">
                    <ul>
                        <li v-for="item in airlines">
                            <UnregisteredUserAirlineInfo v-bind:item="item"></UnregisteredUserAirlineInfo>
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
    import UnregisteredUserAirlineInfo from './UnregisteredUser_AirlineInfo'

    export default {
        name: "UnregisteredUserSearchAirline",
        components: {
            UnregisteredUserAirlineInfo
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