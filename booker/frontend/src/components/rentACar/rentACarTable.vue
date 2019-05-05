<template>
  <b-card-group>
    <b-card border-variant="light" class="mb-2 mx-auto">
      <b-button variant="outline-primary" :to="{ path: 'add'}" append>Add new Rent a Car Service</b-button>
      <br><br>
        <table class="center">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>City</th>
              <th>State</th>
              <th>Number of vehicles</th>
              <th>Number of branch offices</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="rentACar in rentACars">
              <td>{{rentACar.id}}</td>
              <td>{{rentACar.name}}</td>
              <td>{{rentACar.address.city}}</td>
              <td>{{rentACar.address.state}}</td>
              <td>{{rentACar.vehicles.length}}</td>
              <td>{{rentACar.branchOffices.length}}</td>
              <td>{{rentACar.description}}</td>
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
