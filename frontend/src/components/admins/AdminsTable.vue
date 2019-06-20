<template>
    <b-container>
    <b-row>
        <b-col>
            <br>
            <b-card-group>
                <b-card style="max-width: 62rem;">
                    <b-alert variant="light">There are no registered admins!</b-alert>
                    <ul>
                        <li v-for="item in admins">
                            <SysAdminAdminInfo v-bind:item="item"></SysAdminAdminInfo>
                        </li>
                    </ul>
                </b-card>
            </b-card-group>
        </b-col>
    </b-row>
    </b-container>
</template>

<script>
    import {AXIOS} from '../../http-common'
    import SysAdminAdminInfo from './SysAdmin_AdminInfo'

    export default {
        name: "SysAdminRACTable",
        components: {
            SysAdminAdminInfo
        },
        data()
        {
            return {
                admins: ''
            }
        },
        mounted() {
          AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
          AXIOS.get('/users/get-admins')
          .then(response => { this.admins = response.data})
          .catch(err => console.log(err))
        }
    }
</script>

<style scoped>
::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: #eeeeee;
  opacity: 1; /* Firefox */
}
ul {
  list-style-type: none;
}
</style>