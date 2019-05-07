<template>
    <b-container>
    <b-row>
        <b-col lg="2">
        <b-input-group>          
            <b-form-input placeholder="Hotel Name" v-model="name" style="border-color: #eeeeee;" ></b-form-input>
        </b-input-group>
        </b-col>

        <b-col lg="2">
        <b-input-group>      
            <b-form-input placeholder="City" v-model="city" style="border-color: #eeeeee;" ></b-form-input>
        </b-input-group>
        </b-col>

        <b-col lg="2">
        <b-input-group>      
            <b-form-input placeholder="State" v-model="state" style="border-color: #eeeeee;" ></b-form-input>
        </b-input-group>
        </b-col>

        <b-col lg="2.5">
            <v-date-picker mode="range" v-model="dates"/>
        </b-col>

        <b-col lg="3">
             <b-button v-on:click="search" variant="outline-primary">Search</b-button>
        </b-col>
    </b-row>

    <br>

    <b-row>
        <b-col>
            <b-card-group>
                <b-card style="max-width: 57.5rem;" border-variant="dark">
                    <ul id="example-1">
                        <li v-for="item in hotels">
                            <Info v-bind:item="item"></Info>
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
    import Info from './Info'

    export default {
        name: 'searchHotels',
        components: {
            Info
        },
        data()
        {
            return {
                name: '',
                city: '',
                state: '',
                hotels: '',
                dates: ''
            }
        },
        methods:
        {
            search() {
                const searchParams = {
                    'name': this.name,
                    'city': this.city,
                    'state' : this.state,
                    'checkin': this.dates.start,
                    'checkout':  this.dates.end
                }

                AXIOS.post('/hotels/search', searchParams)
                .then(response => { this.hotels = response.data })
                .catch(err => console.log(err))

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

<style>
    ul 
    {
        list-style-type: none; 
    }
</style>