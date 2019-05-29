<template>
  <div>
    <b-table striped hover :items="destinations"></b-table>
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
  }
}
</script>

<style scoped>
  ul {
    list-style-type: none;
  }
</style>
