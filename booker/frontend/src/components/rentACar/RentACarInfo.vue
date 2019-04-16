<template>
  <div>
    <br/>
    <table class="center">
      <tbody>
        <tr>
            <th>ID</th>
            <td>{{rentACar.id}}</td>
        </tr>
        <tr>
            <th>Name</th>
            <td>{{rentACar.name}}</td>
        </tr>
        <tr>
            <th>City</th>
            <td>{{rentACar.address.city}}</td>
        </tr>
        <tr>
            <th>State</th>
            <td>{{rentACar.address.state}}</td>
        </tr>
        <tr>
            <th>Description</th>
            <td>{{rentACar.description}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {AXIOS} from '../../http-common'
export default {
  name: 'RentACarInfo',
  data () {
    return {
      rentACar: ''
    }
  },
  mounted () {
    let api = 'rent-a-cars/' +  this.$route.params.id
      AXIOS.get(api)
      .then(response => { this.rentACar = response.data})
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
