<template>
  <b-card-group deck>
    <b-card border-variant="light" header-tag="header">
      <h6 slot="header" class="mb-0"><b>Hotel information</b></h6>
      <table align="center">
        <tr>
          <th>Hotel id</th>
          <td>{{hotel.id}}</td>
        </tr>
        <tr>
          <th>Name</th>
          <td>{{hotel.name}}</td>
        </tr>
        <tr>
          <th>City</th>
          <td>{{hotel.address.city}}</td>
        </tr>
        <tr>
          <th>State</th>
          <td>{{hotel.address.state}}</td>
        </tr>
        <tr>
          <th>Description</th>
          <td>{{hotel.description}}</td>
        </tr>
      </table>
    
    </b-card>

    <b-card border-variant="light">
      <table align="center">
        <tr>
          <td>
            <yandex-map
              :coords="[this.hotel.latitude, this.hotel.longitude]"
              zoom="12"
              style="width: 350px; height: 350px;"
              :behaviors="[]"
              :controls="['zoomControl']"
               map-type="hybrid"
            >
                <ymap-marker
                  marker-id="1"
                  marker-type="placemark"
                  :coords="[this.hotel.latitude, this.hotel.longitude]"
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
export default {
  name: 'HotelInfo',
  components: { yandexMap, ymapMarker },
  data () {
    return {
      hotel: ''
    }
  },
  mounted () {
    let api = 'hotels/' +  this.$route.params.id
      AXIOS.get(api)
      .then(response => {
         this.hotel = response.data
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
