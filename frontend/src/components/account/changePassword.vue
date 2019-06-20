<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 30rem;" class="mb-2 mx-auto">
            <table align="center">
              <tr>
                    <td>
                        <br>
                        <b-form-group
                            label-for="input-pass"
                        >
                            <b-form-input id="input-oldPass" type="password" v-model="oldPassword" placeholder="Old password"></b-form-input>
                        </b-form-group>  
                    </td>
                </tr>
                <tr>
                    <td>
                        <b-form-group
                            label-for="input-pass"
                            description="Min 6 characters, 1 uppercase letter, 1 digit"
                        >
                            <b-form-input id="input-pass" type="password" v-model="password" :state="passwordValid" placeholder="New password"></b-form-input>
                        </b-form-group>  
                    </td>
                </tr>
                </tr> 
                    <td>
                        <b-form-group
                            label-for="input-repeatPass"
                        >
                            <b-form-input id="input-repeatPass" type="password" v-model="repeatedPassword" :state="repeatedPassValid" placeholder="Repeat new password"></b-form-input>
                        </b-form-group>
                    </td>
                </tr>
            </table>
            <b-button variant="outline-primary" @click="changePassword" class="mr-3">Save</b-button>
            <b-button v-on:click="onCancel">Cancel</b-button><br><br>
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
                "oldPassword": this.oldPassword,
                "newPassword": this.password
            }

            if (this.validate() == true) {
                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

                AXIOS.post("/auth/change-password", passwordChanger)
                .then(response => {
                    this.$router.push("edit-profile")
                })
                .catch(err => {
                    this.errorMessage = "Old password isn't valid!"
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
        },
      onCancel (e) {
        e.preventDefault()
        this.$router.push("edit-profile")
    }
  }
}
</script>

<style scoped>
table, tr, td {
    border: none;
}
</style>
