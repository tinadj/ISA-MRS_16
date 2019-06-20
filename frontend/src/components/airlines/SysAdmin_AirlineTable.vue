<template>
    <b-container>
    <b-row>
        <b-col lg="3">
            <b-form-select v-model="criteria" :options="criteriaOptions" ></b-form-select>
        </b-col>

        <b-col lg="1">
            <b-button variant="outline-secondary" v-on:click="sort">Sort</b-button>
        </b-col>

        <b-col lg="2">
            <b-button variant="outline-primary" :to="{ path: 'add'}" append>Add new Airline</b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <br>
            <b-card-group>
                <b-card style="max-width: 62rem;">
                    <b-alert variant="light">There are no registered Airlines!</b-alert>
                    <ul>
                        <li v-for="item in airlines">
                            <SysAdminAirlineInfo v-bind:item="item"></SysAdminAirlineInfo>
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
    import SysAdminAirlineInfo from './SysAdmin_AirlineInfo'

    export default {
        name: "SysAdminAirlineTable",
        components: {
            SysAdminAirlineInfo
        },
        data()
        {
            return {
                airlines: '',
                criteria: 0,
                criteriaOptions: [
                    {value: 0, text: "Name Ascending"},
                    {value: 1, text: "Name Descending"},
                    {value: 2, text: "City Ascending"},
                    {value: 3, text: "City Descending"},
                    {value: 4, text: "State Ascending"},
                    {value: 5, text: "State Descending"}
                ]
            }
        },
        methods: {
            sort() {
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