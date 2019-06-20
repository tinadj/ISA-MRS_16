<template>
<b-card-group deck>
    <b-card border-variant="light" style="max-width: 20rem;">
        <b-form-group>          
            <b-form-select v-model="searchParams.pickUpLocation" :options="branchOfficesPickUp"></b-form-select>
        </b-form-group>
        
        <b-form-group>
            <v-date-picker  v-model="searchParams.pickUpDate" 
            :min-date='new Date()' 
            :first-day-of-week="2" 
            :input-props='{
                placeholder: "Pick up date",
                readonly: true
            }'/>
        </b-form-group>
        

        <b-form-group>          
            <b-form-select v-model="searchParams.dropOffLocation" :options="branchOfficesDropOff"></b-form-select>
        </b-form-group>
        
        <b-form-group>
            <v-date-picker v-model="searchParams.dropOffDate" 
            :min-date='new Date()' 
            :first-day-of-week="2"
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
        <b-button @click="onCancel">Cancel</b-button><br><br>
        <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>

    </b-card>
    <b-card border-variant="light" style="max-width: 40rem;">
        <b-alert v-model="noResultMsg" variant="light">{{message}}</b-alert>
        <ul>
            <li v-for="item in vehicles">
                <RegisteredUserVehicleInfo v-bind:item="item" v-bind:params="searchParams" :key="componentKey"></RegisteredUserVehicleInfo>
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
                    pickUpLocation: null,
                    pickUpDate: null,
                    dropOffLocation: null,
                    dropOffDate: null,
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
                error: false,
                errorMessage: '',
                componentKey: 0
            }
        },
        methods: {
            // Provera da li su uneti svi parametri za rezervaciju
            valid() {
                if (this.searchParams.pickUpLocation == null || this.searchParams.dropOffLocation == null || this.searchParams.pickUpDate == null || this.searchParams.dropOffDate == null || this.searchParams.passangerNum.length == 0) {
                    this.errorMessage = "You need to fill in all fields!"
                    return false
                }
                if (this.searchParams.pickUpDate.getTime() == this.searchParams.dropOffDate.getTime()) {
                    this.errorMessage = "Dates must be different!"
                    return false
                }
                return true
            },
            search() {
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
                this.vehicles = []
                this.noResultMsg = false
                this.error = false
                
                if (this.valid() == true) {
                    const searchParams = {
                    'racID': this.racID,
                    'pickUpLocation': this.searchParams.pickUpLocation,
                    'dropOffLocation': this.searchParams.dropOffLocation,
                    'pickUpDate': this.searchParams.pickUpDate,
                    'dropOffDate': this.searchParams.dropOffDate,
                    'vehicleType': this.searchParams.vehicleType,
                    'minPrice': this.searchParams.priceRange[0],
                    'maxPrice': this.searchParams.priceRange[1],
                    'criteria': this.searchParams.criteria,
                    'passangerNum': this.searchParams.passangerNum
                    }
                   
                    AXIOS.post('/vehicles/search', searchParams)
                    .then(response => { 
                        this.vehicles = response.data
                        if (this.vehicles.length == 0) {
                            this.message = "There are no results that match your search!"
                            this.noResultMsg = true 
                        }                       
                    })
                    .catch(err => {
                        this.errorMessage = "Something went wrong!"
                        this.error = true
                    })

                }  else {
                    
                    this.error = true
                }
                this.componentKey += 1
            },
            onCancel(e) {
                e.preventDefault()
                this.$router.push("rent-a-cars")
            }     
        },
        mounted() {
            this.noResultMsg = true
            this.message = "Search for vehicles!"

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