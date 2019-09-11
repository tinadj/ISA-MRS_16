<template>
<b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active nina">
            <div class="row padd">
              <div class="col-md-4">
                <div class="row">
                  <h1> {{item.departure.substring(11,16)}} - {{item.arrival.substring(11,16)}}</h1>
                </div>
                <div class="row">
                  <h6 class="small-font"> {{item.departureDestination.city}}  -    {{item.arrivalDestination.city}}</h6>
                </div>
                <div class="row">
                  <h4>Airline: {{item.airline.name}}</h4>
                </div>

                <div class="row">
                  Departure: {{item.departure}}
                  Arrival: {{item.arrival}}
                </div>
              </div>

              <div class="col-md-2">
                <h3> {{item.transferNum}} stop</h3>
              </div>

               <div class="col-md-2">
                  <h3> {{this.diff}}</h3>
                </div>

                <div class="col-md-4">
                 <h6>{{item.ticketPrices[0].travelClass}} CLASS: <b> {{item.ticketPrices[0].price}}e </b></h6>
                 <h6>{{item.ticketPrices[1].travelClass}} CLASS: <b>{{item.ticketPrices[1].price}}e</b></h6>
                 <h6>{{item.ticketPrices[2].travelClass}} CLASS: <b>{{item.ticketPrices[2].price}}e</b></h6>
               </div>
            </div>

            <div class="profile-info-value">
              <span>
                  <b-button class="marg" variant="outline-primary" v-on:click="reserveModal">Reserve</b-button>

              </span>
            </div>
            </div>
          </div>
          </div>
         <b-modal ref="addDiscount" hide-footer>
            <div class="d-block text-center">
                 <h3>Seats</h3>

                <div class="row">
                  <div v-for="(ticket, index) in item.tickets" class="col-md-2" >
                    <button type="button" class="btn disabled"  v-if="ticket.reserved == true" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                    <button type="button" class="btn btn-danger" v-on:click="selectTicket(ticket)" v-else-if="ticket.seat.type == 'BUSINESS'" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                    <button type="button" class="btn btn-warning" v-on:click="selectTicket(ticket)" v-else-if="ticket.seat.type == 'FIRST'" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                    <button type="button" class="btn btn-success" v-on:click="selectTicket(ticket)" v-else-if="ticket.seat.type == 'ECONOMY'" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                  </div>
                </div>

                <h3>RESERVATION INFO</h3> <br />
                Selected ticket: {{ selectedTicket.id }}
                <div class="mt-10">
                  <b-form-input v-model="checked" type=number step=1 placeholder="Enter number of checked luggage"></b-form-input>
                  <b-form-input v-model="carryOn" type=number step=1 placeholder="Enter number of carry on luggage"></b-form-input>
                    <b-form-input v-model="firstName" placeholder="Enter first name"></b-form-input>
                  <b-form-input v-model="lastName"  placeholder="Enter last name"></b-form-input>
                  <b-form-input v-model="passport" placeholder="Enter passport number"></b-form-input>

                    <b-button class="mt-3" variant="outline-primary" block v-on:click="reserve">Reserve</b-button>
                </div>

            </div>
            <b-button class="mt-2" block v-on:click="hideEditModal">Cancel</b-button>
        </b-modal>
        </div>
      </div>
    </b-card >
  </b-card-group>
</template>
<script>
import {AXIOS} from '../../http-common'
import { faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'RegisteredFlightInfo',
    props: ["item"],
    data() {
        return {
            locationIcon: faMapMarkerAlt,
            map: false,
            diff: '',
            selectedTicket:'',
            newSeat: {
              seatLetter: '',
              seatRow: '',
              type: null
            },
            options: [
             { value: 'BUSINESS', text: 'BUSINESS CLASS' },
             { value: 'FIRST', text: 'FIRST CLASS' },
             { value: 'ECONOMY', text: 'ECONOMY CLASS' }],
             success: false,
             error: false,
             checked: 0,
            carryOn: 0,
            firstName: '',
            lastName: '',
            passport: ''
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

      this.diff =  minutes + "h " + seconds +" min"
    },
    methods: {
       diff_minutes: function(dt2, dt1)
         {
          var diff =(dt2.getTime() - dt1.getTime()) / 1000;
          diff /= 60;
          return Math.abs(Math.round(diff));
         },
         reserveModal: function(id) {
            this.$refs['addDiscount'].show()
        },hideEditModal: function() {
              this.$refs['addDiscount'].hide()
          },
         reserve: function()
         {
            if(this.selectedTicket !== ""){
                const params = {
                "flightId" : this.item.id,
                "checked" : this.checked,
                "carryOn" : this.carryOn,
                "firstName" : this.firstName,
                "lastName" : this.lastName,
                "passport" : this.passport,
                "ticketId" : this.selectedTicket
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
         },
         selectTicket: function(seat){
          this.selectedTicket = seat
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
