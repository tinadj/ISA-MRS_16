<template>
<b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active nina">
            <div class="row padd">
              <div class="col-md-7">
                <div class="row">
                  <h1> {{item.departure.substring(11,16)}} - {{item.arrival.substring(11,16)}}</h1>
                </div>
                <div class="row">
                  <h6 class="small-font"> {{item.departureDestination.city}}  -    {{item.arrivalDestination.city}}</h6>
                </div>
              </div>

              <div class="col-md-3">
                <h3> {{item.transferNum}} stop</h3>
              </div>

               <div class="col-md-2">
                  <h3> {{this.diff}}</h3>
                </div>
            </div>

            <div class="profile-info-value">
              <span>
                  <b-button variant="outline-danger"  v-on:click="showModal">Remove</b-button>
                  <b-modal ref="confirmation" hide-footer>
                      <div class="d-block text-center">
                          <h3>Are you sure you want to remove this flight?</h3>
                      </div>
                      <b-button class="mt-3" variant="outline-primary" block v-on:click="removeFlight">Yes</b-button>
                      <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
                  </b-modal>
              </span>
            </div>
            </div>
          </div>
          </div>
        </div>
      </div>
    </b-card >
  </b-card-group>
</template>
<script>
import {AXIOS} from '../../http-common'
import { faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'FlightInfo',
    props: ["item"],
    data() {
        return {
            locationIcon: faMapMarkerAlt,
            map: false,
            diff: ''
        }
    },
    mounted() {
      let dt1 = new Date(this.item.arrival);
      console.log(this.item.arrival)
      console.log(this.item.departure)
      let dt2 = new Date(this.item.departure);
      let diff1 = this.diff_minutes(dt1, dt2);

      let hours = Math.floor(diff1 / 3600);
      diff1 %= 3600;
      let minutes = Math.floor(diff1 / 60);
      let seconds = diff1 % 60;

      this.diff = hours + "h " + minutes + "min " + seconds +"s"
    },
    methods: {
        showModal: function(id) {
            this.$refs['confirmation'].show()
        },
        hideModal: function() {
            this.$refs['confirmation'].hide()
        },
        removeVehicle: function() {

          },
         diff_minutes: function(dt2, dt1)
           {

            var diff =(dt2.getTime() - dt1.getTime()) / 1000;
            diff /= 60;
            return Math.abs(Math.round(diff));

           }
    }
}
</script>


<style scoped>
  body{margin-top:20px;}

  .align-center, .center {
      text-align: center!important;
  }
  .small-font {
    font-size: 20px;
    color: grey;
  }

  .profile-user-info {
      display: table;
      width: 98%;
      width: calc(100% - 24px);
      margin: 0 auto
  }
  .nina {
    color: black;
    padding: 20px;
  }

  .profile-info-row {
      display: table-row
  }

  .profile-info-name,
  .profile-info-value {
      display: table-cell;
      border-top: 1px dotted #D5E4F1
  }
  .padd {
    padding:10px;
  }

  .profile-info-name {
      text-align: right;
      padding: 6px 10px 6px 4px;
      font-weight: 400;
      color: #667E99;
      background-color: transparent;
      width: 150px;
      vertical-align: middle
  }

  .profile-info-value {
      padding: 6px 4px 6px 6px
  }

  .profile-info-value>span+span:before {
      display: inline;
      content: ",";
      margin-left: 1px;
      margin-right: 3px;
      color: #666;
      border-bottom: 1px solid #FFF
  }

   .profile-info-value>span, .name {
      color: #666;
  }

  .name {
    padding-left:3em
  }

  .profile-info-value>span+span.editable-container:before {
      display: none
  }

  .profile-info-row:first-child .profile-info-name,
  .profile-info-row:first-child .profile-info-value {
      border-top: none
  }

  .profile-user-info-striped {
      border: 1px solid #DCEBF7
  }

  .profile-user-info-striped .profile-info-name {
      color: #336199;
      background-color: #EDF3F4;
      border-top: 1px solid #F7FBFF
  }

  .profile-user-info-striped .profile-info-value {
      border-top: 1px dotted #DCEBF7;
      padding-left: 12px
  }

  .profile-picture {
      border: 1px solid #CCC;
      background-color: #FFF;
      padding: 4px;
      display: inline-block;
      max-width: 120%;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
      box-shadow: 1px 1px 1px rgba(0, 0, 0, .15)
  }
</style>
