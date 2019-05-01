<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 60rem;" class="mb-2 mx-auto">
            <table align="center">
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
            </table>
            <b-button variant="outline-primary" @click="changePassword" class="mr-3">Change Password</b-button><br><br>
            <b-alert variant="danger" v-model="error" dismissible>{{this.errorMessage}}</b-alert>
        </b-card>
    </b-card-group>
    
</template>

<script>
import { AXIOS } from '../../http-common';

export default {
    data() {
        return {
            password: '',
            repeatedPassword: '',
            passwordValid: null,
            repeatedPassValid: null,
            errorMessage: '',
            error: false
        }
    },
    methods: {
        changePassword() {
            this.error = false
            this.passwordValid = null
            this.repeatedPassValid = null

            const passwordChanger = {
                "oldPassword": "123",
                "newPassword": this.password
            }

            if (this.validate() == true) {
                AXIOS.post("/auth/change-password", passwordChanger)
                .then(response => {
                    this.$router.push("/login")
                })
                .catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.error = true
                })
            } else {
                this.error = true
            }
        },
        // Check if all inputs are valid,
        // if not activate error message
        validate() {
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
            return true
        }
    }
}
</script>

<style scoped>
table, tr, td {
    border: none;
}
</style>
