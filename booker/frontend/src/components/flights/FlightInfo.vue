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
                  <b-button class="marg" variant="outline-primary"  v-on:click="showAddModal">Add seat</b-button>

                  <b-button class="marg" variant="outline-primary"  v-on:click="showEditModal">Edit seats</b-button>
                  <b-button variant="outline-danger"  v-on:click="showModal">Remove</b-button>
                  <b-modal ref="confirmation" hide-footer>
                      <div class="d-block text-center">
                          <h3>Are you sure you want to remove this flight?</h3>
                      </div>
                      <b-button class="mt-3" variant="outline-primary" block v-on:click="removeFlight">Yes</b-button>
                      <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
                  </b-modal>

                  <b-modal ref="addSeat" hide-footer>
                      <div class="d-block text-center">
                          <b-form-input v-model="newSeat.seatRow" ></b-form-input>
                          <b-form-input v-model="newSeat.seatLetter" ></b-form-input>
                          <b-form-select v-model="newSeat.type" :options="options"></b-form-select>

                          <b-alert variant="success" v-model="success">Successfully added!</b-alert>
                          <b-alert variant="danger" v-model="error" dismissible>Something went wrong!</b-alert>

                      </div>
                      <b-button class="mt-3" variant="outline-primary" block v-on:click="addSeat">Add seat</b-button>
                      <b-button class="mt-2" block v-on:click="hideAddModal">Cancel</b-button>
                  </b-modal>

                  <b-modal ref="editSeats" hide-footer>
                      <div class="d-block text-center">
                          <h3>Seats</h3>

                          <div class="row">
                            <div v-for="(ticket, index) in item.tickets" class="col-md-2" >
                              <button type="button" class="btn disabled"  v-if="ticket.reserved == true" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                              <button type="button" class="btn btn-danger" v-on:click="select(ticket.seat)" v-else-if="ticket.seat.type == 'BUSINESS'" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                              <button type="button" class="btn btn-warning" v-on:click="select(ticket.seat)" v-else-if="ticket.seat.type == 'FIRST'" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                              <button type="button" class="btn btn-success" v-on:click="select(ticket.seat)" v-else-if="ticket.seat.type == 'ECONOMY'" >{{ ticket.seat.seatRow }}{{ ticket.seat.seatLetter }}</button>
                            </div>
                          </div>

                          <div class="mt-10" v-if="selectedSeat != ''">
                            <h3> SELECTED SEAT {{ selectedSeat.id }}</h3>
                            <b-form-input v-model="selectedSeat.seatRow" ></b-form-input>
                            <b-form-input v-model="selectedSeat.seatLetter" ></b-form-input>
                            <b-button class="mt-3" variant="outline-primary" block v-on:click="saveSeat">Save</b-button>
                            <b-button class="mt-3" variant="outline-danger" block v-on:click="removeSeat">Remove seat</b-button>

                          </div>

                      </div>
                      <b-button class="mt-2" block v-on:click="hideEditModal">Cancel</b-button>
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
            diff: '',
            selectedSeat: '',
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
             error: false
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
        showModal: function(id) {
            this.$refs['confirmation'].show()
        },
        showAddModal: function(id) {
            this.$refs['addSeat'].show()
        },
        showEditModal: function(id) {
            this.$refs['editSeats'].show()
        },
        select: function(seat){
          this.selectedSeat = seat
        },
        hideModal: function() {
            this.$refs['confirmation'].hide()
        },
        hideAddModal: function() {
            this.$refs['addSeat'].hide()
        },
        hideEditModal: function() {
            this.$refs['editSeats'].hide()
            this.selectedSeat = ''
        },addSeat : function(){

          if((this.newSeat.seatRow <0 && this.newSeat.seatRow == '') && this.newSeat.seatLetter == '' && this.newSeat.type != null){
            this.success = false;
            this.error = true
          }
          else {
            const destination = {
              'seatRow': this.newSeat.seatRow,
              'seatLetter': this.newSeat.seatLetter,
              'type': this.newSeat.type,
              'id': this.$route.params.id
            }

            AXIOS.post('/flights/add-seat', destination)
            .then(response => {
              this.success = true;
              this.error = false;
              this.$router.go();
            })
            .catch(err => {
              this.success = false;
              this.error = true
            })
           }

        },
        removeSeat: function() {
          let api = '/flights/removeSeat/' +  this.selectedSeat.id + "/" +this.$route.params.id;
              AXIOS.delete(api)
              .then(response => {
              })
              .catch(err => console.log(err))
              this.$refs['editSeats'].hide()

        },
        removeFlight: function() {
          let api = '/flights/remove/' +  this.item.id + "/" +this.$route.params.id;
              AXIOS.delete(api)
              .then(response => {
                this.$router.go();

              })
              .catch(err => console.log(err))
              this.$refs['confirmation'].hide()
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
