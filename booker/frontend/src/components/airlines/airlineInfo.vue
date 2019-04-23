<template>
  <div>
	  <b-row>
		<b-nav class="nav nav-tabs margins w-100">
			<b-nav-item :to="{ path: 'info'}" active>Information</b-nav-item>
			<b-nav-item :to="{ path: 'edit'}">Edit information</b-nav-item>
			<b-nav-item :to="{ path: 'profile'}">Profile</b-nav-item>
		</b-nav>
      </b-row>
	  <br />
	  <b-card-group deck>
		<b-card  border-variant="light" header-tag="header">
		  <h6 slot="header" class="mb-0"><b>Airline Information</b></h6>
		  <table align="center">
			  <tr>
				  <th>Airline ID</th></th>
				  <td>{{airline.id}}</td>
			  </tr>
			  <tr>
				  <th>Name</th>
				  <td>{{airline.name}}</td>
			  </tr>
			  <tr>
				  <th>City</th>
				  <td>{{airline.address.city}}</td>
			  </tr>
			  <tr>
				  <th>State</th>
				  <td>{{airline.address.state}}</td>
			  </tr>
			  <tr>
				  <th>Description</th>
				  <td>{{airline.description}}</td>
			  </tr>
			  <tr>
				  <th>Number of destinations</th>
				  <td>{{this.destinationNum}}</td>
			  </tr>
			</table>

		</b-card >

		<b-card border-variant="light" >
		  <table align="center">
			<tr>
			  <td>
				<yandex-map
				  :coords="[this.airline.latitude, this.airline.longitude]"
				  zoom="12"
				  style="width: 350px; height: 350px;"
				  :behaviors="[]"
				  :controls="['zoomControl']"
				   map-type="hybrid"
				>
					<ymap-marker
					  marker-id="1"
					  marker-type="placemark"
					  :coords="[this.airline.latitude, this.airline.longitude]"
					  :marker-fill="{color: '#000000', opacity: 0.4}"
					  :marker-stroke="{color: '#ff0000', width: 5}"
					></ymap-marker>

				</yandex-map>
			  </td>
			</tr>
		  </table>
		  
		</b-card>
	  </b-card-group>
	</div>
</template>

<script>
import {AXIOS} from '../../http-common'
import { yandexMap, ymapMarker } from 'vue-yandex-maps'

export default {
  name: 'AirlineInfo',
  components: { yandexMap, ymapMarker },
  data () {
    return {
      airline: '',
      destinationNum : ''
    }
  },
  mounted () {
    let api = 'airlines/' +  this.$route.params.id
      AXIOS.get(api)
      .then(response => { 
        this.airline = response.data
        this.destinationNum = response.data.destinations.length
        })
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
}
th {
    background-color: #f5f5f0;
    border: solid 1px #DDEEEE;
    padding: 10px;
    text-align: left;
    text-shadow: 1px 1px 1px #fff;
}
td {
    border: solid 1px #DDEEEE;
    color: #333;
    padding: 10px;
    text-shadow: 1px 1px 1px #fff;
    width: 100%;
}
</style>
