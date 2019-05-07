<template>
  <b-card-group>
    <b-card border-variant="light" class="mb-2 mx-auto">
      <table align="center">
          <tr>
            <th>ID</th>
            <th>Vehicle name</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Production year</th>
            <th>Seats </th>
            <th>Type</th>
            <th>Price per day</th>
            <th>Description</th>
            <th></th>
            <th></th>
          </tr>
          <tr v-for="vehicle in vehicles">
            <td>{{vehicle.id}}</td>
            <td>{{vehicle.name}}</td>
            <td>{{vehicle.brand}}</td>
            <td>{{vehicle.model}}</td>
            <td>{{vehicle.productionYear}}</td>
            <td>{{vehicle.seatsNum}}</td>
            <td>{{vehicle.type}}</td>
            <td>{{vehicle.price}}</td>
            <td>
              <b-link @click="descriptionModal = !descriptionModal">Show</b-link>
              <b-modal v-model="descriptionModal" title="Vehicle description" hide-footer>{{vehicle.description}}</b-modal>
            </td>
            <td><b-link :to="{ path: 'edit-vehicle/' + vehicle.id }"  v-b-tooltip.hover title="Edit"><font-awesome-icon :icon="editIcon" /></b-link></td>
            <td><b-link v-on:click="showModal(vehicle.id)" v-b-tooltip.hover title="Remove"><font-awesome-icon :icon="removeIcon" /></b-link></td>
          </tr>
        </table>
        <b-modal ref="confirmation" hide-footer>
          <div class="d-block text-center">
            <h3>Are you sure you want to remove this vehicle?</h3>
          </div>
          <b-button class="mt-3" variant="outline-primary" block v-on:click="removeVehicle">Yes</b-button>
          <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
        </b-modal>
      </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons'
import { faEdit } from '@fortawesome/free-solid-svg-icons'

export default {
  data () {
    return {
      vehicles: [],
      remove_id: -1, // id of vehicle for removing
      removeIcon: faTrashAlt,
      editIcon: faEdit,
      descriptionModal: false
    }
  },
  methods: {
    showModal: function(id) {
      this.$refs['confirmation'].show()
      this.remove_id = id;
    },
    removeVehicle: function() {
      AXIOS.delete('vehicles/remove/' + this.remove_id)
      .then(response => this.$router.go())
      .catch(err => console.log(err))

      this.$refs['confirmation'].hide()
      this.remove_id = -1;
    },
    hideModal: function() {
      this.remove_id = -1;
      this.$refs['confirmation'].hide()
    }
  },
  mounted () {
    AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
    
    let api = 'rent-a-cars/' +  this.$route.params.id + '/vehicles'
      AXIOS.get(api)
      .then(response => { this.vehicles = response.data})
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
