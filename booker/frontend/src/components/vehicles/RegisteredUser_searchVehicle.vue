<template>
<b-card-group deck>
    <b-card border-variant="light" style="max-width: 20rem;">
        <b-form-group>          
            <b-form-input v-model="searchParams.pickUpLocation" placeholder="Pick-up Location"></b-form-input>
        </b-form-group>
        
        <b-form-group>
            <v-date-picker v-model="searchParams.pickUpDate"/>
        </b-form-group>
        

        <b-form-group>          
            <b-form-input v-model="searchParams.dropOffLocation" placeholder="Drop-off Location"></b-form-input>
        </b-form-group>
        
        <b-form-group>
            <v-date-picker v-model="searchParams.dropOffDate"/>
        </b-form-group>

        <b-form-group>
            <b-form-select v-model="searchParams.vehicleType" :options="typeOptions" :state="typeValid"></b-form-select>
        </b-form-group>

        <b-form-group>
            <label class='labeltext'>Price Range (<font-awesome-icon :icon="euroIcon"/> per day)</label>
            <ejs-slider v-model="searchParams.priceRange" :tooltip="{ isVisible: true}" type="Range" :ticks="{ placement: 'After', largeStep: 10}"></ejs-slider>
        </b-form-group>
        
        <b-button variant="outline-primary" v-on:click="search" class="mr-3">Search</b-button>
        <b-button @click="onCancel">Cancel</b-button>

    </b-card>
    <b-card border-variant="light" style="max-width: 40rem;">
        <b-alert v-model="noResultMsg" variant="light">{{message}}!</b-alert>
        <ul>
            <li v-for="item in vehicles">
                <RegisteredUserVehicleInfo v-bind:item="item"></RegisteredUserVehicleInfo>
            </li>
        </ul>
    </b-card>
</b-card-group>
</template>

<script>
    import {AXIOS} from '../../http-common'
    // Slider
    import Vue from "vue";
    import { SliderPlugin } from "@syncfusion/ej2-vue-inputs"
     Vue.use(SliderPlugin);

    import { faEuroSign } from '@fortawesome/free-solid-svg-icons'
    import RegisteredUserVehicleInfo from './RegisteredUser_VehicleInfo'
   

    export default {
        name: "RegisteredUserSearchVehicles",
        components: {
            RegisteredUserVehicleInfo
        },
        data()
        {
            return {
                message: '',
                noResultMsg: false,
                racID:  this.$route.params.rac_id,
                vehicles: '',
                searchParams: {
                    pickUpLocation: '',
                    pickUpDate: '',
                    dropOffLocation: '',
                    dropOffDate: '',
                    vehicleType: '',
                    passengerNum: '',
                    vehicleType: null,
                    priceRange: [0, 0]
                },
                typeOptions: [
                    {value: null, text: "Choose vehicle type"},
                    {value: 0, text: "Economy"},
                    {value: 1, text: "Compact"},
                    {value: 2, text: "Mid size"},
                    {value: 3, text: "Full size"},
                    {value: 4, text: "Luxury"},
                    {value: 5, text: "Minivan"},
                    {value: 6, text: "SUV"},
                ],
                typeValid: null,
                euroIcon: faEuroSign
            }
        },
        methods: {
            search() {
            },
            getVehicleMaxPrice() {
                maxPrice = 0
                for (let v = 0; v < this.vehicles.length; i++) {
                    if (v.price > maxPrice) {
                        maxPrice = v.price
                    }
                }
                return maxPrice
            },
            onCancel(e) {
                e.preventDefault()
                this.$router.push("rent-a-cars")
            }
                
        },
        mounted() {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            AXIOS.get('/rent-a-cars/' + this.racID + '/vehicles')
            .then(response => { 
                this.vehicles = response.data
                if (this.vehicles.length == 0) {
                    this.noResultMsg = true
                    this.message = "There are no available vehicles!"
                } else {
                    maxPrice = this.getVehicleMaxPrice()
                    this.searchParams.priceRange = [0, maxPrice]
                }
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