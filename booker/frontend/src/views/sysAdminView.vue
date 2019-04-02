<template>
    <div class="container">
      <div class="row">
          <nav class="col-sm">
              <ul>
                <li><a href="#" v-on:click.prevent="airline()">Airlines Management</a></li>
                <li> <a href="#" v-on:click.prevent="hotels()">Hotels Management</a></li>
                <li> <a href="#" v-on:click.prevent="rentACar()">Rent-a-car Management</a></li>
              </ul>
          </nav>
          <component  class="component-view col-sm" :is="currentView"></component>
      </div>
    </div>
</template>

<script>
import RentACarView from './sys_admin/RentACarView.vue'
import AirlineView from './sys_admin/AirlineView.vue'
import HotelView from './sys_admin/HotelView.vue'

export default {
  name: 'SysAdminView',
  data () {
    return {
      currentView: RentACarView
    }
  },
  methods: {
    rentACar: function () {
      this.currentView = RentACarView
    },
    airline: function () {
      this.currentView = AirlineView
    },
    hotels: function () {
      this.currentView = HotelView
    }
  }
}
</script>

<style>
    nav {
        float: left;
    }
    ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 200px;
    background-color: #f1f1f1;
    }

    li a {
    display: block;
    color: #000;
    padding: 8px 16px;
    text-decoration: none;
    }

    /* Change the link color on hover */
    li a:hover {
    background-color: #555;
    color: white;
    }

</style>
