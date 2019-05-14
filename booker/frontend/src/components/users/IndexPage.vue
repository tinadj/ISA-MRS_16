<template>
    <b-container>
    <b-row>
        <b-col>
            <b-nav tabs>
            <b-nav-item :to="{ path: 'searcha-irlines' }"><font-awesome-icon :icon="planeIcon"/> Airlines</b-nav-item>
            <b-nav-item :to="{ path: 'search-hotels'}"><font-awesome-icon :icon="bedIcon"/> Hotels</b-nav-item>
            <b-nav-item :to="{ path: 'search-rent-a-cars'}"><font-awesome-icon :icon="carIcon"/> Rent a car Services</b-nav-item>
            </b-nav>
        </b-col>
        <b-col>
            <div class="pull-right">
                <b-button variant="outline-primary" :to="{ path: 'register'}" class="mr-3">Register</b-button>
                <b-button variant="outline-primary" :to="{ path: 'login'}">Sign in</b-button>
            </div>
        </b-col>
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
import { faPlane, faBed, faCar } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'IndexPage',
    data() {
      return {
        planeIcon: faPlane,
        bedIcon: faBed,
        carIcon: faCar
      }  
    },
    methods: {  
    },
    mounted() {
        AXIOS.post("/auth/default-sys-admin")
        .catch(err => console.log(err))
    }
}
</script>

