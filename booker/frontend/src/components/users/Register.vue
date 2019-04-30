<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 60rem;" class="mb-2 mx-auto">
            <table align="center">
                <tr>
                    <td><b-form-input v-model="username" placeholder="Username"></b-form-input></td>
                    <td><b-form-input id="input-email" type="email"  v-model="email" :state="emailValid" placeholder="Email"></b-form-input></td>
                </tr>
                <tr>
                    <td>
                        <br>
                        <b-form-group
                            label-for="input-firstName"
                        >
                        <b-form-input id="input-firstName" v-model="firstName" placeholder="First name"></b-form-input>
                        </b-form-group>
                    </td>
                    <td>
                        <br>
                        <b-form-group
                            label-for="input-lastName"
                        >
                            <b-form-input id="input-lastName" v-model="lastName" placeholder="Last name"></b-form-input>
                        </b-form-group>
                    </td>
                </tr>
                <tr>
                    <td>
                        <br>
                        <b-form-group
                            label-for="input-pass"
                            description="Min 6 characters, 1 uppercase letter, 1 digit"
                        >
                            <b-form-input id="input-pass" type="password" v-model="password" :state="passwordValid" placeholder="Password"></b-form-input>
                        </b-form-group>  
                    </td>
                    <td>
                        <b-form-group
                            label-for="input-repeatPass"
                        >
                            <b-form-input id="input-repeatPass" type="password" v-model="repeatedPassword" :state="repeatedPassValid" placeholder="Password repeat"></b-form-input>
                        </b-form-group>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b-form-group
                            label-for="input-phone"
                        >
                            <b-form-input id="input-phone" type="number" v-model="phoneNum" placeholder="Phone number"></b-form-input>
                        </b-form-group> 
                     </td>
                    <td>
                        <b-form-group
                            label-for="input-city"
                        >
                            <b-form-input id="input-city" v-model="city" placeholder="City"></b-form-input>
                        </b-form-group>
                    </td>
                </tr>
            </table>
            <b-button variant="outline-primary" @click="register" class="mr-3">Register</b-button>
            <b-button variant="outline-primary" :to="{ path: 'login'}">Sign in</b-button><br><br>
            <b-alert variant="success" v-model="success">Activation link is sent on your email address!</b-alert>
            <b-alert variant="danger" v-model="error" dismissible>{{this.errorMessage}}</b-alert>
        </b-card>
    </b-card-group>
    
</template>

<script>
import { AXIOS } from '../../http-common';

export default {
    name: 'Register',
    data() {
        return {
            username: '',
            password: '',
            repeatedPassword: '',
            firstName: '',
            lastName: '',
            email: '',
            city: '',
            phoneNum: '',
            passwordValid: null,
            repeatedPassValid: null,
            emailValid: null,
            errorMessage: '',
            success: false,
            error: false
        }
    },
    methods: {
        register() {
            this.success = false
            this.error = false
            this.passwordValid = null
            this.repeatedPassValid = null
            this.emailValid = null

            if (this.validate() == true) {
                const user = {
                    'username': this.username,
                    'password': this.password,
                    'name': this.firstName,
                    'lastName': this.lastName,
                    'email': this.email,
                    'city': this.city,
                    'phoneNum': this.phoneNum,
                }

                AXIOS.post('/auth/register', user)
                .then(response => {
                    if (response.data == "CONFLICT") {
                        this.errorMessage = "Username is taken!"
                        this.success = false
                        this.error = true
                    } else {
                        this.success = true
                        this.error = false
                    }
                })
                .catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.success = false
                    this.error = true
                })


                
            } else {
                this.success = false
                this.error = true
            }
        },
        // Check if all inputs are valid,
        // if not activate error message
        validate() {
            if (this.username.length == 0 || this.email.length == 0 || this.firstName.length == 0 ||
            this.lastName.length == 0 || this.city.length == 0 || this.phoneNum.length == 0) {
                this.errorMessage = "Some fields are empty!"
                return false 
                }
            if (this.password.length < 6) {
                this.errorMessage = "Password must contain at least 6 characters!"
                this.passwordValid = false
                return false
            } 
            if (this.password.search(/[A-Z]/) < 0) {
                this.errorMessage = "Password must conatain at least 1 capital letter!"
                this.passwordValid = false
                return false
            }
            if (this.password.search(/[0-9]/) < 0) {
                this.errorMessage = "Password must conatain at least 1 digit!"
                this.passwordValid = false
                return false
            }
            if (this.password != this.repeatedPassword) {
                this.errorMessage = "Password doesn't match!"
                this.repeatedPassValid = false
                return false
            }
            if (this.validateEmail() == false) {
                this.errorMessage = "Email address isn't valid!"
                this.emailValid = false
                return false
            }
            return true
        },
        // Check if email input is valid
        validateEmail() {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.email).toLowerCase());
}
    }
}
</script>

<style scoped>
table, tr, td {
    border: none;
}
</style>
