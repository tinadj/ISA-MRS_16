<template>
    <b-container>
    <b-row>
        <b-col lg="3">
            <b-form-select v-model="criteria" :options="criteriaOptions" ></b-form-select>
        </b-col>

        <b-col lg="1">
            <b-button variant="outline-primary" v-on:click="sort">Sort</b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col>
            <br>
            <b-card-group>
                <b-card style="max-width: 62rem;">
                    <b-alert variant="light">There are no registered Rent a Car Services!</b-alert>
                    <ul>
                        <li v-for="item in rentACars">
                            <SysAdminRACInfo v-bind:item="item"></SysAdminRACInfo>
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
    import SysAdminRACInfo from './SysAdmin_RACInfo'

    export default {
        name: "SysAdminRACTable",
        components: {
            SysAdminRACInfo
        },
        data()
        {
            return {
                rentACars: '',
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
        methods: {
            sort() {
                const searchParams = {
                  'name': '',
                  'city': '',
                  'state': '',
                  'criteria': this.criteria
                } 

                AXIOS.post('/rent-a-cars/search', searchParams)
                .then(response => { 
                  this.rentACars = response.data
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