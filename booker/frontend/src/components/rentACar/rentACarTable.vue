<template>
  <div>
    <br />
    <row>
      <b-link class="btn btn-primary float-right mb-2" :to="{ path: 'add'}" append>Add new rent a car</b-link>
    </row>
    <table class="center">
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
        <tr v-for="rentACar in rentACars" v-bind:key="rentACar.id">
          <td>{{rentACar.id}}</td>
          <td>{{rentACar.name}}</td>
          <td>{{rentACar.address.city}}</td>
          <td>{{rentACar.address.state}}</td>
          <td>{{rentACar.description}}</td>
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
      
      rentACars: []
    }
  },
  mounted () {
    AXIOS.get('/rent-a-cars/all')
    .then(response => { this.rentACars = response.data})
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
