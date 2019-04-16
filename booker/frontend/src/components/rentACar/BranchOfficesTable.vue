<template>
  <div>
    <br/>
    <table class="center">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>City</th>
          <th>State</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="office in offices">
          <td>{{office.id}}</td>
          <td>{{office.name}}</td>
          <td>{{office.address.city}}</td>
          <td>{{office.address.state}}</td>
          <td><b-link :to="{ path: 'edit-branch-office/' + office.id }">Edit</b-link></td>
          <td><b-link v-on:click="showModal(office.id)">Remove</b-link></td>
        </tr>
      </tbody>
    </table>
    <b-modal ref="confirmation" hide-footer>
      <div class="d-block text-center">
        <h3>Are you sure you want to remove this branch office?</h3>
      </div>
      <b-button class="mt-3" variant="outline-primary" block v-on:click="removeBranchOffice">Yes</b-button>
      <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
    </b-modal>
  </div>
</template>

<script>
import {AXIOS} from '../../http-common'
export default {
  name: 'BranchOfficesTable',
  data () {
    return {
      offices: [],
      remove_id: -1 // id of branch office for removing  
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
