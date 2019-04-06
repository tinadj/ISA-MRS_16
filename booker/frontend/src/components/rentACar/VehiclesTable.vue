<template>
  <div>
    <br/>
    <table class="center">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Brand</th>
          <th>Model</th>
          <th>Production year</th>
          <th>Number of seats </th>
          <th>Type</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="vehicle in vehicles">
          <td>{{vehicle.id}}</td>
          <td>{{vehicle.name}}</td>
          <td>{{vehicle.brand}}</td>
          <td>{{vehicle.model}}</td>
          <td>{{vehicle.poductionYear}}</td>
          <td>{{vehicle.seatNum}}</td>
          <td>{{vehicle.type}}</td>
          <td>{{vehicle.description}}</td>
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
      vehicles: []
    }
  },
  mounted () {
    let api = 'rent-a-cars/' + + this.$route.params.id + '/vehicles'
    AXIOS.get(api)
    .then(response => { this.vehicles = response.data})
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
