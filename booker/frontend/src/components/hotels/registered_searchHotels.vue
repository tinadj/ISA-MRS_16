<template>
    <b-container>
    <b-row>
        <b-col lg="2">
        <b-input-group>          
            <b-form-input placeholder="Hotel Name" v-model="name"></b-form-input>
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
            <v-date-picker mode="range" v-model="dates"/>
        </b-col>

        <b-col lg="2.5">
            <b-form-select v-model="criteria" :options="criteriaOptions" ></b-form-select>
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
                        <li v-for="item in hotels">
                            <RegisteredUserHotelsInfo v-bind:item="item"></RegisteredUserHotelsInfo>
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
    import RegisteredUserHotelsInfo from './registered_hotelInfo'

    export default {
        name: 'RegisteredUserSearchHotels',
        components: {
            RegisteredUserHotelsInfo
        },
        data()
        {
            return {
                hotels: '',
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
                ]
            }
        },
        methods:
        {
            search() {
                AXIOS.defaults.headers.common['Autorization'] = "Bearer " + localStorage.getItem('token');

                this.noResultMsg = false

                if (this.dates == null)
                {
                    const searchParams = {
                    'name': this.name,
                    'city': this.city,
                    'state' : this.state,
                    'checkin': null,
                    'checkout':  null,
                    'criteria': this.criteria
                    }

                    AXIOS.post('/hotels/search', searchParams)
                    .then(response => { 
                        this.hotels = response.data
                        if(this.hotels.length == 0)
                        {
                            this.noResultMsg = true
                        } 
                    })
                    .catch(err => console.log(err))

                }
                else 
                {
                    const searchParams = 
                    {
                        'name': this.name,
                        'city': this.city,
                        'state' : this.state,
                        'checkin' : this.dates.start,
                        'checkout' : this.dates.end,
                        'criteria' : this.criteria
                    }

                    AXIOS.post('/hotels/search', searchParams)
                    .then(response => {
                        this.hotels = response.data
                        if (this.hotels.length == 0)
                        {
                            this.noResultMsg = true
                        }
                    })
                }
            }

                
        },
        mounted () {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            AXIOS.get('/hotels/all')
            .then(response => { this.hotels = response.data})
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