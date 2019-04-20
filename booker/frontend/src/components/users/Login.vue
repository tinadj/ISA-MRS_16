<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 20rem;" class="mb-2 mx-auto">
            <b-form-group
                label-for="input-username"
            >
                <b-form-input id="input-username" placeholder="Username"></b-form-input>
            </b-form-group>  

            <b-form-group
                label-for="input-pass"
            >
                <b-form-input id="input-pass" type="password" v-model="password" placeholder="Password"></b-form-input>
            </b-form-group>
                    
            <b-button variant="outline-primary" @click="signIn">Sign in</b-button><br><br>
            <b-alert variant="danger" v-model="error" dismissible>Wrong username or password!</b-alert>
        </b-card>
    </b-card-group>
</template>

<script>
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
        }
    }
}
</script>

