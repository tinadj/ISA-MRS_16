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
            <b-button variant="outline-primary" :to="{ path: 'add'}" append>Add new Hotel</b-button>
        </b-col>
    </b-row>

    <br>

    <b-row>
        <b-col>
            <b-card-group>
                <b-card style="max-width: 57.5rem;" border-variant="light">
                    <ul id="example-1">
                        <li v-for="item in hotels">
                            <SysAdminHotelInfo v-bind:item="item"></SysAdminHotelInfo>
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
    import SysAdminHotelInfo from './SysAdmin_HotelInfo'
    export default {
        name: 'SysAdminHotelsTable',
        components: {
            SysAdminHotelInfo
        },
        data()
        {
            return {
                hotels: '',
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
        methods:
        {
            sort() {
            }
        },
        mounted () {
            AXIOS.get('/hotels/all')
            .then(response => { this.hotels = response.data})
            .catch(err => console.log(err))
        }
    }
</script>

<style>
    ul 
    {
        list-style-type: none; 
    }
</style>

