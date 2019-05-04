<template>
</template>

<script>
import { AXIOS } from '../../http-common'

export default {
    name: 'Logout',
    data() {
        return {

        }
    },
    mounted() {
        localStorage.removeItem('token')
        this.$router.push('/');
    }
}


</script>