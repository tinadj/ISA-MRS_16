<template>
<b-card-group deck>
    <b-card border-variant="light" style="max-width: 20rem;">
        <b-form-group>          
            <b-form-select v-model="searchParams.pickUpLocation" :options="branchOfficesPickUp"></b-form-select>
        </b-form-group>
        
        <b-form-group>
            <v-date-picker v-model="searchParams.pickUpDate" 
            :min-date='new Date()' 
            :first-day-of-week="2" 
            locale="en_US"
            :input-props='{
                placeholder: "Pick up date",
                readonly: true
            }'/>
        </b-form-group>

        <b-form-group>          
            <b-form-select v-model="searchParams.dropOffLocation" :options="branchOfficesDropOff"></b-form-select>
        </b-form-group>
        
        <b-form-group>
            <v-date-picker  v-model="searchParams.dropOffDate" 
            :min-date='new Date()' 
            :first-day-of-week="2" 
            locale="en_US"
            :input-props='{
                placeholder: "Drop off date",
                readonly: true
            }'/>
        </b-form-group>

        <b-form-group>
            <b-form-select v-model="searchParams.vehicleType" :options="typeOptions" :state="typeValid"></b-form-select>
        </b-form-group>

        <b-form-group>
            <b-form-input v-model="searchParams.passangerNum" placeholder="Number of passanger"></b-form-input>
        </b-form-group>

        <b-form-group>
            <label class='labeltext'>Price Range (<font-awesome-icon :icon="euroIcon"/> per day)</label>
            <ejs-slider v-model="searchParams.priceRange" :tooltip="{ isVisible: true}" type="Range" :ticks="{ placement: 'After', largeStep: 10}"></ejs-slider>
        </b-form-group>

        <b-form-group>
            <b-form-select v-model="searchParams.criteria" :options="criteriaOptions" ></b-form-select>
        </b-form-group>
        
        <b-button variant="outline-primary" v-on:click="search" class="mr-3">Search</b-button>
        <b-button @click="onCancel">Cancel</b-button>

    </b-card>
    <b-card border-variant="light" style="max-width: 40rem;">
        <b-alert v-model="noResultMsg" variant="light">{{message}}</b-alert>
        <ul>
            <li v-for="item in vehicles">
                <UnregisteredUserVehicleInfo v-bind:item="item" v-bind:params="searchParams" :key="componentKey"></UnregisteredUserVehicleInfo>
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
    import UnregisteredUserVehicleInfo from './UnregisteredUser_VehicleInfo'
   

    export default {
        name: "UnregisteredUserSearchVehicles",
        components: {
            UnregisteredUserVehicleInfo
        },
        data()
        {
            return {
                message: '',
                noResultMsg: false,
                racID:  this.$route.params.rac_id,
                vehicles: '',
                searchParams: {
                    pickUpLocation: null,
                    pickUpDate: '',
                    dropOffLocation: null,
                    dropOffDate: '',
                    vehicleType: '',
                    vehicleType: null,
                    priceRange: [0, 100],
                    criteria: 0,
                    passangerNum: null
                },
                typeOptions: [
                    {value: null, text: "Choose vehicle type"},
                    {value: 0, text: "Economy"},
                    {value: 1, text: "Compact"},
                    {value: 2, text: "Mid size"},
                    {value: 3, text: "Full size"},
                    {value: 4, text: "Luxury"},
                    {value: 5, text: "Minivan"},
                    {value: 6, text: "SUV"}
                ],
                criteriaOptions: [
                    {value: 0, text: "Price Ascending"},
                    {value: 1, text: "Price Descending"},
                    {value: 2, text: "Year Ascending"},
                    {value: 3, text: "Year Descending"},
                    {value: 4, text: "Number of Seats Ascending"},
                    {value: 5, text: "Number of Seats Descending"}
                ],
                typeValid: null,
                euroIcon: faEuroSign,
                branchOfficesPickUp: [
                    {value: null, text: "Choose pick up location"}
                ],
                branchOfficesDropOff: [
                    {value: null, text: "Choose drop off location"}
                ],
                componentKey: 0
            }
        },
        methods: {
            search() {
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                this.noResultMsg = false

                if (this.valid()) {
                    const searchParams = {
                        'racID': this.racID,
                        'pickUpLocation': this.searchParams.pickUpLocation,
                        'pickUpDate': this.searchParams.pickUpDate,
                        'dropOffDate': this.searchParams.dropOffDate,
                        'dropOffLocation': this.searchParams.dropOffLocation,
                        'vehicleType': this.searchParams.vehicleType,
                        'minPrice': this.searchParams.priceRange[0],
                        'maxPrice': this.searchParams.priceRange[1],
                        'criteria': this.searchParams.criteria,
                        'passangerNum': this.searchParams.passangerNum
                        } 
                        
                    AXIOS.post('/vehicles/search', searchParams)
                    .then(response => { 
                        this.vehicles = response.data
                        console.log(response.data)
                        if (this.vehicles.length == 0) {
                            this.message = "There are no results that match your search!"
                            this.noResultMsg = true
                        }
                    })
                    .catch(err => console.log(err))
                    this.componentKey += 1
                } else {
                    this.noResultMsg = true
                }
            },
            valid() {
                console.log(this.searchParams.pickUpDate == this.searchParams.dropOffDate)
                if (this.searchParams.pickUpDate == null || this.searchParams.dropOffDate == null) {
                    this.message = "You have to choose dates!"
                    return false
                }
                if (this.searchParams.pickUpDate.getTime() == this.searchParams.dropOffDate.getTime()) {
                    this.message = "Dates must be different!"
                    return false
                }
                return true
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
                this.$router.push("search-rent-a-cars")
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

            AXIOS.get('/rent-a-cars/' + this.$route.params.rac_id + '/branch-offices')
            .then(response => { 
                for (let i in response.data) {
                    this.branchOfficesPickUp.push(
                        {value: response.data[i].id, text: response.data[i].name}
                    )
                    this.branchOfficesDropOff.push(
                    {   value: response.data[i].id, text: response.data[i].name}
                    )
                }
            })
        }
    }
</script>

<style scoped>

ul {
    list-style-type: none;
    }
</style>