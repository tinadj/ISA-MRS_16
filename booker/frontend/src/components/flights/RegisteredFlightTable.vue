<template>
 <b-container>
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
     noResult: false
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
 }
}
</script>

<style scoped>
ul {
   list-style-type: none;
 }

</style>

