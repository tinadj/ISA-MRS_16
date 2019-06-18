<template>
    <b-container>
    <b-row>
      <b-nav tabs>
        <b-nav-item :to="{ path: 'airlines' }"><font-awesome-icon :icon="planeIcon"/> Airlines</b-nav-item>
        <b-nav-item :to="{ path: 'hotels'}"><font-awesome-icon :icon="bedIcon"/> Hotels</b-nav-item>
        <b-nav-item :to="{ path: 'rent-a-cars'}"><font-awesome-icon :icon="carIcon"/> Rent a car Services</b-nav-item>
        <b-nav-item><font-awesome-icon :icon="friendsIcon"/> Friends</b-nav-item>
        <b-nav-item :to="{path: 'reservations'}"><font-awesome-icon :icon="reservationsIcon"/> My reservations</b-nav-item>
        <b-nav-item :to="{ path: 'profile-page'}"><font-awesome-icon :icon="profileIcon"/> Profile</b-nav-item>
        <b-nav-item :to="{ path: 'sign-out'}"><font-awesome-icon :icon="signOutIcon"/> Sign out</b-nav-item>
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
import { faPlane, faBed, faCar, faUserCircle, faSignOutAlt, faUser, faCalendarCheck } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'RegisteredUserHomePage',
    data() {
      return {
        user: '',
        planeIcon: faPlane,
        bedIcon: faBed,
        carIcon: faCar,
        friendsIcon: faUser,
        reservationsIcon: faCalendarCheck,
        profileIcon: faUserCircle,
        signOutIcon: faSignOutAlt
      }  
    },
    methods: {  
    },
    mounted() {
        AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

        AXIOS.get('/users/' + this.$route.params.id)
        .then(response => { this.user = response.data})
        .catch(err => console.log(err))
    }
}
</script>

