<template>
    <b-container>
    <b-col>
        
        <b-row lg="2">
            <VueHotelDatepicker placeholder="Date Input" format="DD.MM.YYYY."/>
        </b-row>

        <b-row>
            <b-form-input placeholder="Number of Guests" v-model="numOfGuests" type="number" style="border-color: #eeeeee;" ></b-form-input>
        </b-row>

        <b-row>
            <b-form-input placeholder="Number of Guests" v-model="numOfGuests" type="number" style="border-color: #eeeeee;" ></b-form-input>
        </b-row>

        
    </b-col>

    <b-col>
        <b-row>
            <b-card-group>
                <b-card style="max-width: 57.5rem;" border-variant="dark">
                    <ul id="example-1">
                        <li v-for="item in rooms">
                            <Info v-bind:item="item"></Info>
                        </li>
                    </ul>
                </b-card>
            </b-card-group> 
        </b-row>
    </b-col>
    </b-container>

</template>

<script>
    import {AXIOS} from '../../http-common'
    import VueHotelDatepicker from '@northwalker/vue-hotel-datepicker'
    import Info from './Info'

    export default {
        name: 'searchHotels',
        components: {
            VueHotelDatepicker,
            Info
        },
        data()
        {
            return {
                rooms: ''
            }
        },
        mounted () {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            
        }
    }
</script>

<style>
    ul 
    {
        list-style-type: none; 
    }
</style>