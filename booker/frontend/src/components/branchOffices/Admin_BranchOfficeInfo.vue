<template>
<b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active">
					<div class="row">
						<div class="profile-user-info">

								<div class="profile-info-row">
									<div class="profile-info-value">
										<span>{{item.name}}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-value">
										<span><font-awesome-icon :icon="locationIcon"/> {{item.address.city}}, {{item.address.state}}</span>
									</div>
								</div>

                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span>
                                            <b-link v-on:click="map = !map">Show on map...</b-link>
                                            <b-modal v-model="map" hide-footer>
                                                <yandex-map
                                                    :coords="[item.latitude, item.longitude]"
                                                    zoom="12"
                                                    style="width: 250; height: 250px;"
                                                    :behaviors="[]"
                                                    :controls="['zoomControl']"
                                                    map-type="hybrid"
                                                    >
                                                    <ymap-marker
                                                        marker-id="1"
                                                        marker-type="placemark"
                                                        :coords="[item.latitude, item.longitude]"
                                                        :marker-fill="{color: '#000000', opacity: 0.4}"
                                                        :marker-stroke="{color: '#ff0000', width: 5}"
                                                    ></ymap-marker>

                                                </yandex-map>
                                            </b-modal>
                                        </span>
									</div>
								</div>



                                <div class="profile-info-row">
									<div class="profile-info-value">
										<span>
                                            <b-button variant="outline-primary" :to="{ path: 'edit-branch-office-' + item.id} " class="mr-3">Edit</b-button>
                                            <b-button variant="outline-danger"  v-on:click="showModal">Remove</b-button>
                                            <b-modal ref="confirmation" hide-footer>
                                                <div class="d-block text-center">
                                                    <h3>Are you sure you want to remove this branch office?</h3>
                                                </div>
                                                <b-button class="mt-3" variant="outline-primary" block v-on:click="removeVehicle">Yes</b-button>
                                                <b-button class="mt-2" block v-on:click="hideModal">Cancel</b-button>
                                            </b-modal>
                                        </span>
									</div>
								</div>
							</div>
						</div><!-- /.col -->				
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
    name: 'AdminBranchOfficeInfo',
    props: ["item"],
    data() {
        return {
            locationIcon: faMapMarkerAlt,
            map: false
        } 
    },
    methods: {
        showModal: function(id) {
            this.$refs['confirmation'].show()
        },
        removeVehicle: function() {
            AXIOS.delete('branch-offices/remove/' + this.item.id)
            .then(response => this.$router.go())
            .catch(err => console.log(err))

            this.$refs['confirmation'].hide()
        },
        hideModal: function() {
            this.$refs['confirmation'].hide()
        }
       
    }
}
</script>


<style scoped>
  body{margin-top:20px;}

  .align-center, .center {
      text-align: center!important;
  }

  .profile-user-info {
      display: table;
      width: 98%;
      width: calc(100% - 24px);
      margin: 0 auto
  }

  .profile-info-row {
      display: table-row
  }

  .profile-info-name,
  .profile-info-value {
      display: table-cell;
      border-top: 1px dotted #D5E4F1
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
