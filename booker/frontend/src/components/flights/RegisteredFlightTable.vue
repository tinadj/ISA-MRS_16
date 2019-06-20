<template>
 <b-container>
    <b-row>
       <b-col lg="3">
       <b-input-group>
           <b-form-input placeholder="Departure city" v-model="departure"></b-form-input>
       </b-input-group>
       </b-col>

       <b-col lg="3">
       <b-input-group>
           <b-form-input placeholder="Arrival city" v-model="arrival"></b-form-input>
       </b-input-group>
       </b-col>

       <b-col lg="2">
          Departure date
           <v-date-picker v-model="departureDate"/>
       </b-col>

       <b-col lg="2">
           Return date
            <v-date-picker v-model="returnDate"/>
        </b-col>

       <b-col lg="1">
           <b-button variant="outline-primary" v-on:click="search">Search</b-button>
       </b-col>
   </b-row>
   <b-row>
     <b-col>
       <br>
       <b-card-group>
         <b-card border-variant="light" class="mb-2 mx-auto">
           <b-alert v-model="noResult" variant="light">There are no flights!</b-alert>
           <ul>
             <li v-for="item in offices">
               <RegisteredFlightInfo v-bind:item="item"></RegisteredFlightInfo>
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
import RegisteredFlightInfo from './RegisteredFlightInfo'


export default {
 name: 'RegistredFlightTable',
 components: {
   RegisteredFlightInfo
 },
 data () {
   return {
     offices: [],
     noResult: false,
     departure: '',
     arrival: '',
     departureDate: '',
     returnDate:''
   }
 },
 mounted () {
   AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
       let api = 'flights/all'
         AXIOS.get(api)
         .then(response => {
           this.offices = response.data
           console.log(response.data)
           if (this.offices.length == 0) {
             this.noResult = true
           }
         })
         .catch(err => console.log(err))
 },
 methods: {
       search() {
           AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

           this.noResultMsg = false

           if (this.returnDate == null && this.departureDate != null) {
               const searchParams = {
               'departure': this.departure,
               'arrival': this.arrival,
               'departureDate': this.departureDate,
               'returnDate':  null
               }

               AXIOS.post('/flights/search', searchParams)
               .then(response => {
                   this.offices = response.data
                    if (this.offices.length == 0) {
                      this.noResult = true
                    }

               })
               .catch(err => console.log(err))

           } else if(this.returnDate != null && this.departureDate == null){
              const searchParams = {
               'departure': this.departure,
               'arrival': this.arrival,
               'departureDate': null,
               'returnDate':  this.returnDate
               }

               AXIOS.post('/flights/search', searchParams)
               .then(response => {
                   this.offices = response.data
                    if (this.offices.length == 0) {
                      this.noResult = true
                    }

               })
               .catch(err => console.log(err))
           }
            else {
               const searchParams = {
               'departure': this.departure,
               'arrival': this.arrival,
               'departureDate': this.departureDate,
               'returnDate':  this.returnDate
               }

               AXIOS.post('/flights/search', searchParams)
               .then(response => {
                   this.offices = response.data
                   if (this.offices.length == 0) {
                     this.noResult = true
                   }
               })
               .catch(err => console.log(err))
           }
           this.this.componentKey += 1
       }
   }
}
</script>

<style scoped>
ul {
   list-style-type: none;
 }

</style>

