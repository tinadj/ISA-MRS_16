<template>
  <b-card-group>
    <b-card border-variant="light" class="mb-2 mx-auto">
        <table class="center">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Last Name</th>
              <th>Username</th>
              <th>Email</th>
              <th>Admin of</th>
              <th>Activated</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="admin in admins">
            </tr>
          </tbody>
        </table>
    </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  data () {
    return {
      admins: []
    }
  },
  mounted () {
    
  }
}
</script>

<style scoped>
table {
    border: solid 1px #DDEEEE;
    border-collapse: collapse;
    border-spacing: 0;
    font: normal 13px Arial, sans-serif;
    width: 100%;
}
th {
    background-color: #DDEFEF;
    border: solid 1px #DDEEEE;
    color: #6666ff;
    padding: 10px;
    text-align: left;
    text-shadow: 1px 1px 1px #fff;
}
td {
    border: solid 1px #DDEEEE;
    color: #333;
    padding: 10px;
    text-align: left;
    text-shadow: 1px 1px 1px #fff;
}
</style>
