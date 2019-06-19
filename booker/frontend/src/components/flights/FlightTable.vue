<template>
  <b-container>
    <b-row>
      <b-col>
        <br>
        <b-card-group>
          <b-card border-variant="light" class="mb-2 mx-auto">
            <b-alert v-model="noResult" variant="light">There are no flights!</b-alert>
            <ul>
              <li v-for="item in offices">
                <FlightInfo v-bind:item="item"></FlightInfo>
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
import FlightInfo from './FlightInfo'


export default {
  name: 'FlightTable',
  components: {
    FlightInfo
  },
  data () {
    return {
      offices: [],
      noResult: false
    }
  },
  mounted () {
    AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
    let api = 'airlines/' +  this.$route.params.id + '/flights'
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

