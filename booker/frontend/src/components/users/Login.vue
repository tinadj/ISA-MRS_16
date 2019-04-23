<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 20rem;" class="mb-2 mx-auto">
            <b-form-group
                label-for="input-username"
            >
                <b-form-input id="input-username" v-model="username" placeholder="Username"></b-form-input>
            </b-form-group>  

            <b-form-group
                label-for="input-pass"
            >
                <b-form-input id="input-pass" type="password" v-model="password" placeholder="Password"></b-form-input>
            </b-form-group>
                    
            <b-button variant="outline-primary" @click="signIn">Sign in</b-button><br><br>
            <b-link :to="{ path: 'register'}">Don't have account?</b-link><br><br>
            <b-alert variant="danger" v-model="error" dismissible>Wrong username or password!</b-alert>
        </b-card>
    </b-card-group>
</template>

<script>
import { AXIOS } from '../../http-common';
export default {
    name: 'Login',
    data() {
        return {
            username: '',
            password: '',
            error: null
        }
    },
    methods: {
        signIn() {
            this.error = null

            const user = {
                'username': this.username,
                'password': this.password
            }

            AXIOS.post('/auth/login', user)
                .then(response => {
                    if (response.status == 200){
                          console.log(response.data.accessToken);
                          localStorage.setItem('token', response.data.accessToken);
                          this.getRole();
                    }
                })
                .catch(err => {
                    this.error = true
                })
        },
        getRole() {

            var getJwtToken = function() {
                return localStorage.getItem('token');
                };

                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + getJwtToken();

                AXIOS.get("/users/get-role-and-id")
                .then(response => {
                    console.log(response);
                    if(response.data.role == "AIRLINE_ADMIN") {
                        this.$router.push("/airiline-admin/" + response.data.userID)
                    } else if (response.data.role == "HOTEL_ADMIN") {
                        this.$router.push("/hotel-admin" + response.data.userID)
                    } else if (response.data.role == "RAC_ADMIN") {
                        this.$router.push("/rent-a-car-admin" + response.data.userID)
                    } else if (response.data.role == "SYS_ADMIN") {
                        this.$router.push("/sys-admin")
                    } else if (response.data.role == "USER") {   
                        this.$router.push("/home/" + response.data.userID)
                    } else {
                        this.$router.push("/login")
                    } 
                });
        }
    }
}
</script>

