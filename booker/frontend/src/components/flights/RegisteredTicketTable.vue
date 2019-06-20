<template>
 <b-container>
   <b-row>
     <b-col>
       <br>
       <b-card-group>
         <b-card border-variant="light" class="mb-2 mx-auto">
           <b-alert v-model="noResult" variant="light">There are no tickets!</b-alert>
           <ul>
             <li v-for="item in offices">
               <RegisteredTicketInfo v-bind:item="item"></RegisteredTicketInfo>
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
import RegisteredTicketInfo from './RegisteredTicketInfo'


export default {
 name: 'RegistredTicketTable',
 components: {
   RegisteredTicketInfo
 },
 data () {
   return {
     offices: [],
     noResult: false,
   }
 },
 mounted () {
   AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

       let api = 'airlines/' +  this.$route.params.airline + '/tickets'
         AXIOS.get(api)
         .then(response => {
           this.offices = response.data
          console.log(this.offices)
           if (this.destinations.length == 0) {
             this.noResult = true
           }
         })
         .catch(err => console.log(err))
 },
 methods: {

   }
}
</script>

<style scoped>
ul {
   list-style-type: none;
 }

</style>

