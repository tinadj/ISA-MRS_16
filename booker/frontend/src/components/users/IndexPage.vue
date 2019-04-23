<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 60rem;" class="mb-2 mx-auto">
            <b-button variant="outline-primary" :to="{ path: 'register'}" class="mr-3">Register</b-button>
            <b-button variant="outline-primary" :to="{ path: 'login'}">Sign in</b-button><br><br>
        </b-card>
    </b-card-group>
    
</template>

<script>
import { AXIOS } from '../../http-common';
export default {
    name: 'IndexPage',
    mounted() {
        AXIOS.post("/auth/register-sys-admin")
        .then(response => console.log(response))
        .catch(err => console.log(err))
    }
}
</script>

