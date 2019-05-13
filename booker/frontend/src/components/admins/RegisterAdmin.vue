<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 60rem;" class="mb-2 mx-auto">
            <table align="center">
                <tr>
                    <td colspan="2" >
                        <div class="justify-content-center">
                            <b-form-radio-group
                            v-model="adminRole"
                            v-on:input="updateSelect"
                            :options="roles"
                            buttons
                            button-variant="outline-primary"
                        ></b-form-radio-group>
                        </div>
                    </td>
                </tr>
                <tr><td colspan="2"><br></td></tr>
                <tr>
                    <td>
                        <b-form-group
                            label-for="input-username"
                        >
                        <b-form-input id="input-username" v-model="username" placeholder="Username"></b-form-input>
                        </b-form-group>
                        
                    </td>
                    <td>
                        <b-form-group
                            label-for="input-email"
                        >
                        <b-form-input id="input-email" type="email"  v-model="email" :state="emailValid" placeholder="Email"></b-form-input>
                        </b-form-group>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b-form-group
                            label-for="input-firstName"
                        >
                        <b-form-input id="input-firstName" v-model="firstName" placeholder="First name"></b-form-input>
                        </b-form-group>
                    </td>
                    <td>
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
                    <td colspan="2">
                        <b-form-group>
                            <b-form-select v-model="id" :options="options" :state="selectedValid"></b-form-select>
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
            adminRole: 3,
            roles: [
                { text: "Airline Admin", value: 0},
                { text: "Hotel Admin", value: 1},
                { text: "Rent a Car Admin", value: 2},
                { text: "Sys Admin", value: 3}
            ],
            id: null,
            options: [],
            selectedValid: null
        }
    },
    methods: {
        register() {
            this.success = false
            this.error = false
            this.emailValid = null
            this.selectedValid = null

            if (this.validate() == true) {
                const user = {
                    'username': this.username,
                    'password': '123',
                    'name': this.firstName,
                    'lastName': this.lastName,
                    'email': this.email,
                    'city': this.city,
                    'phoneNum': this.phoneNum,
                    'itemID': this.id,
                }
                
                // Registracija sys admina
                if (this.adminRole == 3) {
                    AXIOS.get('/auth/check-mail/' + this.email)
                    .then(response => {
                        if (response.data == "CONFLICT") {
                            this.errorMessage = "Email address is taken!"
                            this.emailValid = false
                            this.error = true
                        } else {
                            AXIOS.post("/auth/register-sys-admin", user)
                            .then( response => {
                                if (response.data == "CONFLICT") {
                                    this.errorMessage = "Username is taken!"
                                    this.success = false
                                    this.error = true
                                } else {
                                    this.success = true
                                    this.error = false
                                }
                            })
                            .catch( err => {
                                this.success = true
                                this.error = false
                            })                      
                        }
                    })
                // Registracija ostalih admina
                } else {
                    if (this.adminRole == 0) {
                        user["adminOf"] = "airline"
                    } else if (this.adminRole == 1) {
                        user["adminOf"] = "hotel"
                    } else if (this.adminRole == 2) {
                        user["adminOf"] = "rent-a-car"
                    }
                    
                    AXIOS.get('/auth/check-mail/' + this.email)
                    .then(response => {
                        if (response.data == "CONFLICT") {
                            this.errorMessage = "Email address is taken!"
                            this.emailValid = false
                            this.error = true
                        } else {
                            AXIOS.post("/auth/register-admin", user)
                            .then( response => {
                                if (esponse.data == "CONFLICT") {
                                    this.errorMessage = "Username is taken!"
                                    this.success = false
                                    this.error = true
                                } else {
                                    this.success = true
                                    this.error = false
                                }
                            })
                            .catch( err => {
                                this.success = true
                                this.error = false
                            })                     
                        }
                    })

                    
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
            if (this.adminRole != 3 && this.id == null) {
                this.errorMessage = "Selected item isn't valid!"
                this.selectedValid = false
                return false
            }
            return true
        },
        // Check if email input is valid
        validateEmail() {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(String(this.email).toLowerCase());
        },
        // Adding options in select input depending of choosen admin role
        updateSelect() {
            this.options = []
            var i;

             if (this.adminRole == 0) {
                AXIOS.get("/airlines/all")
                .then(response => {
                    this.options.push(
                        {value: null, text: "Choose Airline"}
                    )
                    for (i = 0; i < response.data.length; i++) {
                        this.options.push(
                            {value: response.data[i].id, text: response.data[i].name}
                        )
                    }
                })
            } else if (this.adminRole == 1) {
                AXIOS.get("/hotels/all")
                .then(response => {
                    this.options.push(
                        {value: null, text: "Choose Hotel"}
                    )
                    for (i = 0; i < response.data.length; i++) {
                        this.options.push(
                            {value: response.data[i].id, text: response.data[i].name}
                        )
                    }
                })
            } else if (this.adminRole == 2) {
                AXIOS.get("/rent-a-cars/all")
                .then(response => {
                    this.options.push(
                        {value: null, text: "Choose Rent a Car Service"}
                    )
                    for (i = 0; i < response.data.length; i++) {
                        this.options.push(
                            {value: response.data[i].id, text: response.data[i].name}
                        )
                    }
                })
            }
        }
    }
}
</script>

<style scoped>
table, tr, td {
    border: none;
}
</style>
