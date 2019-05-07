<template>
<b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;" align="center">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active">
					<div class="row">
						
                        <div class="col-xs-12 col-sm-3 center">
							<span class="profile-picture">
								<img class="img-thumbnail" alt="Picture"  src="../../assets/no-image.jpg">
							</span>

						</div><!-- /.col -->

						<div class="col-xs-12 col-sm-9">

                            <h4 align="left">
                                <span class="name">{{item.name}}</span><br>
                                <span class="name"><star-rating v-model="this.rating" :inline="true" :star-size="22" :show-rating="false" :read-only="true" :round-start-rating="false"></star-rating><br></span>
							</h4>

							<div class="profile-user-info">
                                <div class="profile-info-row">
                                    <div class="profile-info-name"><font-awesome-icon :icon="locationIcon"/> Location</div>
								    <div class="profile-info-value">
										<span>{{item.address.city}}, {{item.address.state}}</span> 
									</div>
								</div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"><font-awesome-icon :icon="descriptionIcon"/> Description</div>
									<div class="profile-info-value">
										<span>{{item.description}}</span>
									</div>
								</div>
							</div>
						</div><!-- /.col -->
					</div><!-- /.row -->				
          </div>
        </div>
      </div>
    </b-card >
  </b-card-group>
</template>

<script>
import { faMapMarkerAlt, faInfoCircle, faAlignLeft, faBriefcase, faCar } from '@fortawesome/free-solid-svg-icons'

export default {
    name: 'AdminRACInfo',
    props: ["item"],
    data() {
        return {
            rating: 0,
            descriptionIcon: faAlignLeft,
            locationIcon: faMapMarkerAlt
        } 
    },
    mounted() {
        // Racunannje prosecne ocene Rent a Car servisa
        if (this.item.rating.length > 0) {
            for (var i = 0; i < this.item.rating.length; i++) {
                this.rating += this.item.rating.rate[i]
            }
            this.rating = this.rating / this.item.rating.length
        } else {
            this.rating = 0
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
