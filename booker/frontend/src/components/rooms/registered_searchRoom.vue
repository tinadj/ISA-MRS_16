<template>
<b-card-group deck>
    <b-card border-variant="light" style="max-width: 20rem;">
        <!-- CheckIN Date -->
        <b-form-group>
            <v-date-picker  v-model="searchParams.checkinDate" 
            :min-date='new Date()' 
            :first-day-of-week="2"
            :input-props='{
                placeholder: "Check In Date",
                readonly: true
            }'/>
        </b-form-group>

        <!-- CheckOUT Date -->
        <b-form-group>
            <v-date-picker v-model="searchParams.checkoutDate" 
            :min-date='new Date()' 
            :first-day-of-week="2"
            :input-props='{
                placeholder: "Check Out Date",
                readonly: true
            }'/>
        </b-form-group>

        <!-- Extraservices Checkboxes -->

        <b-card>
            
            <b-form-checkbox id="balcony_checkbox" v-model="searchParams.balcony" name="balcony_checkbox" value="true" unchecked-value="false">
                Balcony inclusion
            </b-form-checkbox>

            <b-form-checkbox id="breakfast_cb" v-model="searchParams.breakfast" name="breakfast_cb" value="true" unchecked-value="false">
                Breakfast 
            </b-form-checkbox>

            <b-form-checkbox id="hotel_restaurant_cb" v-model="searchParams.hotel_restaurant" name="hotel_restaurant_cb" value="true" unchecked-value="false">
                Hotel Restaurant
            </b-form-checkbox>

            <b-form-checkbox id="airport_transfer_cb" v-model="searchParams.airport_transfer" name="airport_transfer_cb" value="true" unchecked-value="false">
                Airport Transfer
            </b-form-checkbox>

            <b-form-checkbox id="parking_cb" v-model="searchParams.parking" name="parking_cb" value="true" unchecked-value="false">
                Parking
            </b-form-checkbox>

            <b-form-checkbox id="pool_cb" v-model="searchParams.pool" name="pool_cb" value="true" unchecked-value="false">
                Pool Access
            </b-form-checkbox>

            <b-form-checkbox id="wellness_spa_cb" v-model="searchParams.wellness_spa" name="wellness_spa_cb" value="true" unchecked-value="false">
                Wellness and Spa
            </b-form-checkbox>

            <b-form-checkbox id="wifi_cb" v-model="searchParams.wifi" name="wifi_cb" value="true" unchecked-value="false">
                Wifi Connection
            </b-form-checkbox>

            <b-form-checkbox id="tv_cb" v-model="searchParams.tv" name="tv_cb" value="true" unchecked-value="false">
                Television
            </b-form-checkbox>

            <b-form-checkbox id="minibar_cb" v-model="searchParams.minibar" name="minibar_cb" value="true" unchecked-value="false">
                Minibar
            </b-form-checkbox>

        </b-card>

        <br>


        <!-- Price Range per Night -->
        <b-form-group>
            <label class='labeltext3'>Price Range (<font-awesome-icon :icon="euroIcon"/> per night)</label>
            <ejs-slider v-model="searchParams.priceRange" :tooltip="{ isVisible: true}" type="Range" :ticks="{ placement: 'After', largeStep: 10}"></ejs-slider>
        </b-form-group>

        <!-- Sort Criteria -->
        <b-form-group>
            <b-form-select v-model="searchParams.criteria" :options="criteriaOptions" ></b-form-select>
        </b-form-group>

        <b-button variant="outline-primary" v-on:click="search" class="mr-3">Search</b-button>
        <b-button @click="onCancel">Cancel</b-button>
        <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>

    </b-card>

    <b-card border-variant="light" style="max-width: 40rem;">
        <b-alert v-model="noResultMsg" variant="light">{{message}}</b-alert>
        <ul>
            <li v-for="item in rooms">
                <RegisteredUserRoomInfo v-bind:item="item" v-bind:params="searchParams" :key="componentKey"></RegisteredUserRoomInfo>
            </li>
        </ul>
    </b-card>

</b-card-group>
</template>


<script>
    import {AXIOS} from '../../http-common'
    /* Slider */
    import Vue from "vue";
    import { SliderPlugin } from "@syncfusion/ej2-vue-inputs"
     Vue.use(SliderPlugin);

    import { faEuroSign } from '@fortawesome/free-solid-svg-icons'
    import RegisteredUserRoomInfo from './registered_RoomInfo'

    export default {
        name: "RegisteredUserSearchRooms",
        components: {
            RegisteredUserRoomInfo
        },
        data()
        {
            return {
                message: '',
                noResultMsg: false,
                hotelID: this.$route.params.hotel_id,
                rooms: '',
                searchParams: {
                    checkinDate: null,
                    checkoutDate: null,
                    beds: '',
                    balcony: false,
                    breakfast: false,
                    hotel_restaurant: false,
                    airport_transfer: false,
                    parking: false,
                    pool: false,
                    wellness_spa: false,
                    wifi: false,
                    tv: false,
                    minibar: false,
                    priceRange: [0, 100],
                    criteria: 0
                },
                criteriaOptions: [
                    {value: 0, text: "Price Ascending"},
                    {value: 1, text: "Price Descending"},
                    {value: 2, text: "Number of Beds Ascending"},
                    {value: 3, text: "Number of Beds Descending"}
                ],
                euroIcon: faEuroSign,
                error: false,
                errorMessage: '',
                componentKey: 0
            }
        },
        methods: {
            // Proverava da li su uneti svi parametri za rezervaciju
            valid()
            {
                if(this.searchParams.checkinDate == null || this.searchParams.checkoutDate == null)
                {
                    this.errorMessage = "You need to fill in all fields!"
                    return false
                }
                if(this.searchParams.checkinDate == this.searchParams.checkoutDate)
                {
                    this.errorMessage = "Dates must be different!"
                    return false
                }
                return true
            },
            search() {
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                this.noResultMsg = false
                this.error = false
                this.rooms = []

                if(this.valid() == true)
                {
                    const searchParams = {
                    'hotelID' : this.hotelID,
                    'checkinDate' : this.searchParams.checkinDate,
                    'checkoutDate' : this.searchParams.checkoutDate,
                    'beds' : this.searchParams.beds,
                    'balcony' : this.searchParams.balcony,
                    'breakfast' : this.searchParams.breakfast,
                    'hotel_restaurant' : this.searchParams.hotel_restaurant,
                    'airport_transfer' : this.searchParams.airport_transfer,
                    'parking' : this.searchParams.parking,
                    'pool' : this.searchParams.pool,
                    'wellness_spa' : this.searchParams.wellness_spa,
                    'wifi' : this.searchParams.wifi,
                    'tv' : this.searchParams.tv,
                    'minibar' : this.searchParams.minibar, 
                    'minPrice' : this.searchParams.priceRange[0],
                    'maxPrice' : this.searchParams.priceRange[1],
                    'criteria' : this.searchParams.criteria
                    }

                    console.log(searchParams)

                    AXIOS.post('/room/search', searchParams)
                    .then(response => {
                        this.rooms = response.data
                        if(this.rooms.length == 0)
                        {
                            this.message = "There are no results that match your search!"
                            this.noResultMsg = true
                        }
                    })   
                    .catch(err => {
                        this.errorMessage = "Something went wrong!"
                        this.error = true
                    })
                    
                }
                else{
                    this.noResultMsg = true
                }

                this.componentKey += 1
                
            },
            onCancel(e)
            {
                e.preventDefault()
                this.$router.push("hotels")
            },
            getRoomMaxPrice()
            {
                maxPrice = 0
                for(let r = 0; r < this.rooms.length; r++)
                {
                    if(r.price > maxPrice)
                    {
                        maxPrice = r.price
                    }
                }
                return maxPrice
            }
        },
        mounted()
        {
            this.noResultMsg = true
            this.message = "Search for rooms!"

            /*
            AXIOS.get('/hotels/' + this.hotelID + '/rooms')
            .then(response => {
                this.rooms = response.data
                if(this.rooms.length == 0)
                {
                    this.noResultMsg = true
                    this.message = "There are no available rooms right now!"
                }
                else
                {
                    maxPrice = this.getRoomMaxPrice()
                    this.searchParams.priceRange = [0, maxPrice]
                }
            })
            .catch(err => console.log(err))
            */
        }
    }


</script>



<style scoped>

ul {
    list-style-type: none;
    }
</style>