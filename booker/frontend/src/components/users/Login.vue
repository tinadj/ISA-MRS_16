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
            <b-alert variant="danger" v-model="error" dismissible>{{this.errorMessage}}</b-alert>
        </b-card>
    </b-card-group>
</template>

<script>
import { AXIOS } from '../../http-common'

export default {
    name: 'Login',
    data() {
        return {
            username: '',
            password: '',
            error: null,
            errorMessage: ''
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
                        localStorage.setItem('token', response.data.accessToken);
                        this.getRole();
                    } 
                })
                .catch(err => {
                    if (err.response.status == 400) {
                        this.errorMessage = "Wrong username or password!";
                        this.error = true
                    } else if (err.response.status == 403) {
                        this.errorMessage = "Check your email!";
                        this.error = true
                    }
                    
                })
        },
        getRole() {
                
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                AXIOS.get("/users/get-role-and-id")
                .then(response => {
                    if(response.data.role == "AIRLINE_ADMIN") {
                        this.isChangedPass(response.data.userID)
                        this.$router.push("/" + response.data.adminOf + "/airline-admin/")
                    } else if (response.data.role == "HOTEL_ADMIN") {
                        this.isChangedPass(response.data.userID)
                        this.$router.push("/" + response.data.adminOf+ "/hotel-admin/")
                    } else if (response.data.role == "RAC_ADMIN") {
                        this.isChangedPass(response.data.userID)
                        this.$router.push("/" + response.data.adminOf + "/rent-a-car-admin/")
                    } else if (response.data.role == "SYS_ADMIN") {
                        this.isChangedPass(response.data.userID)
                        this.$router.push("/airlines")
                    } else if (response.data.role == "USER") {   
                        this.$router.push("/" + response.data.userID + "/home/reservations")
                    } else {
                        this.$router.push("/login")
                    } 
                });
        },
        isChangedPass(id) {
            AXIOS.get("/users/" + id)
            .then(response => {
                if (response.data.passChanged == false) {
                    this.$router.push("/change-pass/" + response.data.id)
                }
            })
            .catch(err => console.log(err))
        }
    }
}
</script>

