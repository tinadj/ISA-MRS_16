<template>
<b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active nina">

				<div class="col-md-7">
                    <div class="row">
                      <h1> {{flight.departure.substring(11,16)}} - {{flight.arrival.substring(11,16)}}</h1>
                    </div>
                    <div class="row">
                      <h6 class="small-font"> {{flight.departureDestination.city}}  -    {{flight.arrivalDestination.city}}</h6>
                    </div>
              </div>

               <div class="col-md-3">
                  <h1> - {{item.discount}}%</h1> </br >PRICE:  {{item.price*(100 - item.discount)/100}}
                </div>
            </div>


            <div class="profile-info-value">
              <span>
                  <b-button class="marg" variant="outline-primary" v-on:click="reserve">Reserve</b-button>
              </span>
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
    name: 'RegisteredTicketInfo',
    props: ["item"],
    data() {
        return {
            locationIcon: faMapMarkerAlt,
            map: false,
            flight: '',
             success: false,
             error: false
        }
    },
    mounted() {
    console.log(this.item)
      let api = '/flights/tickets/' + this.item.id;
	console.log(api)
      AXIOS.get(api)
      .then(response => {
        this.flight = response.data
        console.log(response.data)
      })
    },
    methods: {
       diff_minutes: function(dt2, dt1)
         {
          var diff =(dt2.getTime() - dt1.getTime()) / 1000;
          diff /= 60;
          return Math.abs(Math.round(diff));
         },

         reserve: function()
         {
            const params = {
                "flightId" : this.flight.id,
                "checked" : 1,
                "carryOn" : 1,
                "firstName" : 'Nikolina',
                "lastName" : 'Petrovic',
                "passport" : '0303997',
                "ticketId" : this.item.id
                }

                AXIOS.post('flight-reservations/reserve-flight', params)
                .then(response => {
                    if (response.status == 200) {
                        this.success = true
                    } else if (response.status = 403) {
                        this.errorMessage = "Ticket is reserved in this period!"
                        this.error = true
                    } else {
                        this.errorMessage = "Something went wrong!"
                        this.error = true
                    }
                })
                .catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.error = true
                })

         }
    }
}
</script>


<style scoped>
  body{margin-top:20px;}

  .align-center, .center {
      text-align: center!important;
  }
  .marg {
    margin-right: 5px;
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
