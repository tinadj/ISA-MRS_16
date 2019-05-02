<template>
  <b-card-group>
    <b-card border-variant="light" class="mb-2 mx-auto">
      <table align="center">
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>City</th>
            <th>State</th>
            <th></th>
            <th></th>
          </tr>
          <tr v-for="office in offices">
            <td>{{office.id}}</td>
            <td>{{office.name}}</td>
            <td>{{office.address.city}}</td>
            <td>{{office.address.state}}</td>
            <td><b-link :to="{ path: 'edit-branch-office/' + office.id }"  v-b-tooltip.hover title="Edit"><font-awesome-icon :icon="editIcon" /></b-link></td>
            <td><b-link v-on:click="showModal(office.id)" v-b-tooltip.hover title="Remove"><font-awesome-icon :icon="removeIcon" /></b-link></td>
          </tr>
      </table>
      <b-modal ref="confirmation" hide-footer>
        <div class="d-block text-center">
          <h3>Are you sure you want to remove this branch office?</h3>
        </div>
        <b-button class="mt-3" variant="outline-primary" block v-on:click="removeBranchOffice">Yes</b-button>
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
  name: 'BranchOfficesTable',
  data () {
    return {
      offices: [],
      remove_id: -1, // id of branch office for removing  
      removeIcon: faTrashAlt,
      editIcon: faEdit
    }
  },
  methods: {
    showModal: function(id) {
      this.$refs['confirmation'].show()
      this.remove_id = id;
    },
    removeBranchOffice: function() {
      AXIOS.delete('branch-offices/remove/' + this.remove_id)
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
    let api = 'rent-a-cars/' +  this.$route.params.id + '/branch-offices'
      AXIOS.get(api)
      .then(response => { this.offices = response.data})
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
    width: 70%;
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

