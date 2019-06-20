<template>
  <b-container>
      <b-row>
        <b-col lg="3">
            <b-button variant="outline-primary" :to="{ path: 'add-branch-office'}">Add new Branch Office</b-button>
        </b-col>
    </b-row>
    <b-row>
      <b-col>
        <br>
        <b-card-group>
          <b-card border-variant="light" class="mb-2 mx-auto">
            <b-alert v-model="noResult" variant="light">There are no branch offices!</b-alert>
            <ul>
              <li v-for="item in offices">
                <AdminBranchOfficeInfo v-bind:item="item"></AdminBranchOfficeInfo>
              </li>
            </ul> 
          </b-card>
        </b-card-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import {AXIOS} from '../../http-common'
import AdminBranchOfficeInfo from './Admin_BranchOfficeInfo'


export default {
  name: 'BranchOfficesTable',
  components: {
    AdminBranchOfficeInfo
  },
  data () {
    return {
      offices: [],
      noResult: false
    }
  },
  mounted () {
    AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
    let api = 'rent-a-cars/' +  this.$route.params.id + '/branch-offices'
      AXIOS.get(api)
      .then(response => { 
        this.offices = response.data
        if (this.offices.length == 0) {
          this.noResult = true
        }
      })
      .catch(err => console.log(err))
  }
}
</script>

<style scoped>
 ul {
    list-style-type: none;
  }

</style>

