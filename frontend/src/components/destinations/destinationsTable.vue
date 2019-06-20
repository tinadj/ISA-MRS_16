<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">City</th>
          <th scope="col">State</th>
          <th scope="col"></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(dest, index) in destinations">
          <th scope="row">{{ dest.id }}</th>
          <td>{{ dest.city }}</td>
          <td>{{ dest.state }}</td>
          <td><button type="button" class="btn btn-danger" @click="deleteDestination(dest.id, index)">Delete</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {AXIOS} from '../../http-common'

export default {
  components: {
  },
  data () {
    return {
      destinations: [],
      noResult: false
    }
  },
  mounted () {
    AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');

    let api = 'airlines/' +  this.$route.params.id + '/destinations'
      AXIOS.get(api)
      .then(response => {
        this.destinations = response.data

        if (this.destinations.length == 0) {
          this.noResult = true
        }
      })
      .catch(err => console.log(err))
  },
  methods : {
    deleteDestination (dest, index) {
    console.log("tff")
        let api = '/destinations/remove/' +  dest + "/" +this.$route.params.id;
          AXIOS.delete(api)
          .then(response => {
            this.destinations.splice(index, 1);

          })
          .catch(err => console.log(err))
      }

  }
}
</script>

<style scoped>
  ul {
    list-style-type: none;
  }
</style>
