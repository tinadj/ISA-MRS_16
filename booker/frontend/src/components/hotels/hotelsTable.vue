<template>
  <div>
    <br />
    <div>
      <b-link class="btn btn-primary float-right mb-2" :to="{ path: 'add'}" append>Add new hotel</b-link>
    </div>
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
        <tr v-for="hotel in hotels" v-bind:key="hotel.id">
          <td>{{hotel.id}}</td>
          <td>{{hotel.name}}</td>
          <td>{{hotel.address.city}}</td>
          <td>{{hotel.address.state}}</td>
          <td>{{hotel.description}}</td>
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
      hotels: []
    }
  },
  mounted () {
    AXIOS.get('/hotels/all')
    .then(response => { this.hotels = response.data})
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
