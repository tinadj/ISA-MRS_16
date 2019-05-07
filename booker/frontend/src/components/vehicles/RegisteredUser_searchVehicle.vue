<template>
<b-card-group>
    <b-card>
        <b-col>
        <b-row lg="2">
        <b-input-group>          
            <b-form-input placeholder="Rent a Car Name"></b-form-input>
        </b-input-group>
        </b-row>

        <b-row lg="2">
        <b-input-group>      
            <b-form-input placeholder="City" ></b-form-input>
        </b-input-group>
        </b-row>

        <b-row lg="2">
        <b-input-group>      
            <b-form-input placeholder="State"></b-form-input>
        </b-input-group>
        </b-row>

        <b-row lg="3">
            <!--<v-date-picker mode="range"/>-->
        </b-row>

        <b-row lg="3">
             <b-button variant="outline-primary" v-on:click="search">Search</b-button>
        </b-row>
        </b-col>
    </b-card>
    <b-card>
        <b-alert v-model="noResultMsg" variant="light">There are no results that match your search!</b-alert>
        <ul>
            <li v-for="item in vehicles">
                <p>{{item.name}}</p>
            </li>
        </ul>
    </b-card>
</b-card-group>
</template>

<script>
    import {AXIOS} from '../../http-common'

    export default {
        name: "RegisteredUserSearchVehicles",
        components: {
            
        },
        data()
        {
            return {
                racID:  this.$route.params.id,
                vehicles: '',
                name: '',
                city: '',
                state: '',
                dates: '',
                noResultMsg: false
            }
        },
        methods: {
            search() {
                
        },
        mounted() {
            AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
            AXIOS.get('/rent-a-cars/' + this.racID + '/vehicles')
            .then(response => { this.vehicles = response.data})
            .catch(err => console.log(err))
        }
    }
}
</script>

<style scoped>
ul {
  list-style-type: none;
}
</style>