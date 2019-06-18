<template>
  <nav class="navbar navbar-inverse navigation navbar-fixed-top" style="background-color: #2d3e50;!important">
   <div class="container">
     <div class="navbar-header">
       <a class="navbar-brand" href="#" v-on:click="redirect">ISA/MRS Tours</a>
     </div>  
   </div>
 </nav>
</template>
 
<script>
import {AXIOS} from '../http-common'

export default {
  name: 'navbar-component',
  methods: {
    redirect() {
        if (localStorage.getItem('token') == null) {
          this.$router.push("/")
        } else {
          AXIOS.get("/users/get-role-and-id")
          .then(response => {
              if(response.data.role == "AIRLINE_ADMIN") {
                this.isChangedPass(response.data.userID)
                this.$router.push("/" + response.data.adminOf + "/airline-admin/")
              } else if (response.data.role == "HOTEL_ADMIN") {
                this.$router.push("/" + response.data.adminOf+ "/hotel-admin/")
              } else if (response.data.role == "RAC_ADMIN") {
                this.$router.push("/" + response.data.adminOf + "/rent-a-car-admin/")
              } else if (response.data.role == "SYS_ADMIN") {
                this.$router.push("/airlines")
              } else if (response.data.role == "USER") {   
                this.$router.push("/" + response.data.userID + "/home/")
              }
          })
        }
      }
  }
}
</script>
