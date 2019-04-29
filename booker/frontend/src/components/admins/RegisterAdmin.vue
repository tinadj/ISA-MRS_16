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
                <tr>
                    <td>
                        <b-form-group>
                            <b-form-select v-model="role" :options="roleOptions" :state="roleValid"></b-form-select>
                        </b-form-group>
                    </td>
                    <td>
                        <b-form-group>
                            <b-form-select v-model="id" :options="options"></b-form-select>
                        </b-form-group>
                    </td>
                </tr>
            </table>
            <b-button variant="outline-primary" @click="register" class="mr-3">Register</b-button>
            <b-button :to="{ path: '/admins'}">Cancel</b-button><br><br>
            <b-alert variant="success" v-model="success">Activation link is sent on email address!</b-alert>
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
            firstName: '',
            lastName: '',
            email: '',
            city: '',
            phoneNum: '',
            emailValid: null,
            errorMessage: '',
            success: false,
            error: false,
            role: null,
            roleOptions: [
                {value: null, text: "Choose admin's role"},
                {value: 0, text: "Sys Admin"},
                {value: 1, text: "Airline Admin"},
                {value: 2, text: "Hotel Admin"},
                {value: 3, text: "Rent a Car Admin"}
            ],
            roleValid: null,
        }
    },
    methods: {
        register() {
            this.success = false
            this.error = false
            this.emailValid = null
            this.roleValid = null

            if (this.validate() == true) {
                const user = {
                    'username': this.username,
                    'password': '123',
                    'name': this.firstName,
                    'lastName': this.lastName,
                    'email': this.email,
                    'city': this.city,
                    'phoneNum': this.phoneNum,
                }
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
            if (this.validateEmail() == false) {
                this.errorMessage = "Email address isn't valid!"
                this.emailValid = false
                return false
            }
            if (this.role == null) {
                this.errorMessage = "Choose admin's role!"
                this.roleValid = false
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
