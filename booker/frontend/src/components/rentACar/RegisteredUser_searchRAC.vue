<template>
    <b-container>
    <b-row>
        <b-col lg="2">
        <b-input-group>          
            <b-form-input placeholder="Rent a Car Name" v-model="name"></b-form-input>
        </b-input-group>
        </b-col>

        <b-col lg="2">
        <b-input-group>      
            <b-form-input placeholder="City" v-model="city"></b-form-input>
        </b-input-group>
        </b-col>

        <b-col lg="2">
        <b-input-group>      
            <b-form-input placeholder="State" v-model="state"></b-form-input>
        </b-input-group>
        </b-col>

        <b-col lg="2">
            <v-date-picker mode="range" 
            v-model="dates" 
            :first-day-of-week="2" 
            :input-props='{
                placeholder: "Date range",
                readonly: true
            }'/>
        </b-col>

        <b-col lg="2.5">
            <b-form-select v-model="criteria" :min-date='new Date()' :options="criteriaOptions" ></b-form-select>
        </b-col>

        <b-col lg="1">
            <b-button variant="outline-primary" v-on:click="search">Search</b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <br>
            <b-card-group>
                <b-card style="max-width: 62rem;">
                    <b-alert v-model="noResultMsg" variant="light">There are no results that match your search!</b-alert>
                    <ul>
                        <li v-for="item in rentACars">
                            <RegisteredUserRACInfo v-bind:item="item" :key="componentKey"></RegisteredUserRACInfo>
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
    import RegisteredUserRACInfo from './RegisteredUser_RACInfo'

    export default {
        name: "RegisteredUserSearchRAC",
        components: {
            RegisteredUserRACInfo
        },
        data()
        {
            return {
                rentACars: '',
                name: '',
                city: '',
                state: '',
                dates: '',
                noResultMsg: false,
                criteria: 0,
                criteriaOptions: [
                    {value: 0, text: "Name Ascending"},
                    {value: 1, text: "Name Descending"},
                    {value: 2, text: "City Ascending"},
                    {value: 3, text: "City Descending"},
                    {value: 4, text: "State Ascending"},
                    {value: 5, text: "State Descending"}
                ],
                componentKey: 0
            }
        },
        methods: {
            search() {
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                this.noResultMsg = false

                if (this.dates == null) {
                    const searchParams = {
                    'name': this.name,
                    'city': this.city,
                    'state': this.state,
                    'pickUpDate': null,
                    'returnDate':  null,
                    'criteria': this.criteria
                    } 
                   
                    AXIOS.post('/rent-a-cars/search', searchParams)
                    .then(response => { 
                        this.rentACars = response.data
                        if (this.rentACars.length == 0) {
                            this.noResultMsg = true
                        }
                        
                    })
                    .catch(err => console.log(err))

                } else {
                    const searchParams = {
                    'name': this.name,
                    'city': this.city,
                    'state': this.state,
                    'pickUpDate': this.dates.start,
                    'returnDate':  this.dates.end,
                    'criteria': this.criteria
                    }
                    
                    AXIOS.post('/rent-a-cars/search', searchParams)
                    .then(response => { 
                        this.rentACars = response.data
                        if (this.rentACars.length == 0) {
                            this.noResultMsg = true
                        }
                    })
                    .catch(err => console.log(err))
                }
                this.componentKey += 1
            }
        },
        mounted() {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            AXIOS.get('/rent-a-cars/all')
            .then(response => { this.rentACars = response.data})
            .catch(err => console.log(err))
        }
    }
</script>

<style scoped>
::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: #eeeeee;
  opacity: 1; /* Firefox */
}
ul {
  list-style-type: none;
}
</style>