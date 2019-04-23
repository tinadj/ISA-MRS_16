<template>
    <b-container>
    <b-row>
      <b-nav tabs>
        <b-nav-item>Flights</b-nav-item>
        <b-nav-item>Hotels</b-nav-item>
        <b-nav-item>Vehicles</b-nav-item>
        <b-nav-item>Friends</b-nav-item>
        <b-nav-item>My reservations</b-nav-item>
        <b-nav-item>Profile</b-nav-item>
        <b-nav-item>Log out</b-nav-item>
      </b-nav>
    </b-row>

    <b-row align-v="center">
      <b-col>
        <br>
        <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { AXIOS } from '../../http-common';

export default {
    name: 'RegisteredUserHomePage',
    data() {
        user: ''
    },
    methods: {
        
    },
    mounted() {
        var getJwtToken = function() {
            return localStorage.getItem('token');
        };
        AXIOS.defaults.headers.common['Authorization'] = "Bearer " + getJwtToken();

        AXIOS.get('/user/' + this.$route.params.id)
        .then(response => { this.user = response.data})
        .catch(err => console.log(err))
    }
}
</script>

