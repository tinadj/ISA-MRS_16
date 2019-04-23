<template>
  <div>
    <br/>
    <table class="center">
      <thead>
        <tr>
          <th>ID</th>
          <th>Floor</th>
          <th>Room Number</th>
          <th>Number of Beds</th>
          <th>Included Balcony</th>
          <th>Discount</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="room in rooms">
          <td>{{room.id}}</td>
          <td>{{room.floor}}</td>
          <td>{{room.roomNum}}</td>
          <td>{{room.beds}}</td>
          <td>{{room.balcony}}</td>
          <td>{{room.discount}}</td>      
          <td><b-link :to="{ path: 'edit-room/' + room.id }">Edit</b-link></td>
          <td><b-link v-on:click="showModal(room.id)">Remove</b-link></td>
        </tr>
      </tbody>
    </table>
    <b-modal ref="confirmation" hide-footer>
      <div class="d-block text-center">
        <h3>Are you sure you want to remove this room?</h3>
      </div>
      <b-button class="mt-3" variant="outline-primary" block v-on:click="removeRoom">Yes</b-button>
      <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
    </b-modal>
  </div>
</template>

<script>
import {AXIOS} from '../../http-common'
export default {
  data () {
    return {
      rooms: [],
      remove_id: -1 // id of room for removing  
    }
  },
  methods: {
    showModal: function(id) {
      this.$refs['confirmation'].show()
      this.remove_id = id;
    },
    removeRoom: function() {
      AXIOS.delete('room/remove/' + this.remove_id)
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
    let api = 'hotels/' +  this.$route.params.id + '/rooms'
      AXIOS.get(api)
      .then(response => { this.rooms = response.data})
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
