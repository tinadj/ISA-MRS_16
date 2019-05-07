<template>
  <b-card-group deck>
    <b-card  border-variant="light" header-tag="header">
      <h6 slot="header" class="mb-0"><b>Rent a car Information</b></h6>
      <AdminRACInfo v-bind:item="rentACar"></AdminRACInfo>
    </b-card >

    <b-card border-variant="light" style="max-width: 25rem;">
      <table align="center">
        <tr>
          <td>
            <yandex-map
              :coords="[this.rentACar.latitude, this.rentACar.longitude]"
              zoom="12"
              style="width: 300px; height: 300px;"
              :behaviors="[]"
              :controls="['zoomControl']"
               map-type="hybrid"
            >
                <ymap-marker
                  marker-id="1"
                  marker-type="placemark"
                  :coords="[this.rentACar.latitude, this.rentACar.longitude]"
                  :marker-fill="{color: '#000000', opacity: 0.4}"
                  :marker-stroke="{color: '#ff0000', width: 5}"
                ></ymap-marker>

            </yandex-map>
          </td>
        </tr>
      </table>
      
    </b-card>
  </b-card-group>
</template>

<script>
import {AXIOS} from '../../http-common'
import { yandexMap, ymapMarker } from 'vue-yandex-maps'
import AdminRACInfo from './Admin_RACInfo'

export default {
  name: 'RentACarInfo',
  components: { yandexMap, ymapMarker, AdminRACInfo },
  data () {
    return {
      rentACar: ''
      
    }
  },
  mounted () {
    let api = 'rent-a-cars/' +  this.$route.params.id
      AXIOS.get(api)
      .then(response => { 
        this.rentACar = response.data
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
