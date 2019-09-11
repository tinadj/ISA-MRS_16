<template>
RESERVATIONNNNN

</template>
<script>
import {AXIOS} from '../../http-common'
import { faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'ReservationFlight',
    data() {
        return {
            item: null
        }
    },
    mounted() {
    console.log(this.$route.params.flight)
    },
    methods: {

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
