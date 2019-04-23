<template>
  <div>
    <br />
    <div>
      <b-link class="btn btn-primary float-right mb-2" :to="{ path: 'add'}" append>Add new airline</b-link>
    </div>
    <table class="center table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>City</th>
          <th>State</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="airline in airlines" v-bind:key="airline.id">
          <td>{{airline.id}}</td>
          <td>{{airline.name}}</td>
          <td>{{airline.address.city}}</td>
          <td>{{airline.address.state}}</td>
          <td>{{airline.description}}</td>
        </tr>
      </tbody>
    </table>

  </div>
</template>

<script>
import {AXIOS} from '../../http-common'
export default {
  data () {
    return {
      airlines: []
    }
  },
  mounted () {
    AXIOS.get('/airlines/all')
    .then(response => { this.airlines = response.data})
    .catch(err => console.log(err))
  }
}
</script>

<style scoped>
body {
  font-family: Helvetica Neue, Arial, sans-serif;
  font-size: 14px;
  color: #444;
}

table {
  border: 2px solid #666666;
  border-radius: 3px;
  background-color: #fff;
}

table.center {
    margin-left:auto; 
    margin-right:auto;
  }

th {
  background-color: #666666;
  color: rgba(255,255,255,0.66);
}

td {
  background-color: #f9f9f9;
}

th, td {
  min-width: 120px;
  padding: 10px 20px;
}
</style>
