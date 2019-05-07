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

        <b-col lg="3">
            <v-date-picker mode="range" v-model="dates"/>
        </b-col>

        <b-col lg="3">
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
                            <RegisteredUserRACInfo v-bind:item="item"></RegisteredUserRACInfo>
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
    import RegisteredUserRACInfo from './infoComponents/RegisteredUser_RACInfo'

    export default {
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
                dates: {
                    start: '',
                    end: ''
                },
                noResultMsg: false
            }
        },
        methods: {
            search() {
                this.noResultMsg = false

                const searchParams = {
                    'name': this.name,
                    'city': this.city,
                    'state': this.state,
                    'pickUpDate': this.dates.start,
                    'returnDate':  this.dates.end
                }

                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
                AXIOS.post('/rent-a-cars/search', searchParams)
                .then(response => { 
                    this.rentACars = response.data
                    
                    if (this.rentACars.length == 0) {
                        this.noResultMsg = true
                    }
                    

                })
                .catch(err => console.log(err))

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