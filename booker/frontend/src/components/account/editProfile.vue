<template>
  <b-card-group deck>
    <b-card header-tag="header"  border-variant="light" style="max-width: 60rem;" align="center">
      <div id="user-profile-2" class="user-profile">
			<div class="tab-content no-border padding-24">
				<div id="home" class="tab-pane in active">
					<div class="row">
						
            <div class="col-xs-12 col-sm-3 center">
							<span class="profile-picture">
                
								<img class="img-thumbnail" alt="Profile Picture" v-bind:src="imagePreview" v-show="showPreview">
                <img class="img-thumbnail" alt="Profile Picture"src="../../assets/no-image.jpg" v-show="!showPreview">
							</span>

              <br><br>

              <div class="upload-btn-wrapper">
                <b-button :to="{ path: 'edit-profile' }" variant="outline-dark" v-on:click="pictureUpload">Change profile picture</b-button>
                <input
                  type="file"
                  id="pictureUpload"
                  ref="file"
                  accept="image/*"
                  hidden
                  v-on:change="handleFileUpload"
                ></input>
              </div>
						</div><!-- /.col -->

						<div class="col-xs-12 col-sm-9 center-block">

							<div class="profile-user-info">
								<div class="profile-info-row">
									<div class="profile-info-name"> Name </div>

									<div class="profile-info-value">
										<span><b-form-input v-model="firstName" placeholder="First Name"></b-form-input></span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> Last name </div>

									<div class="profile-info-value">
										<span><b-form-input v-model="lastName" placeholder="Last Name"></b-form-input></span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> City </div>

									<div class="profile-info-value">
										<span><b-form-input v-model="city" placeholder="City"></b-form-input></span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> Phone number </div>

									<div class="profile-info-value">
										<span><b-form-input v-model="phone" placeholder="Phone Number" type="number"></b-form-input></span>
									</div>
								</div>

                <div class="profile-info-row">
									<div class="profile-info-name"></div>

									<div class="profile-info-value">
										<span><b-button variant="primary" :to="{ path: 'change-pass'}">Change password</b-button></span>
									</div>
								</div>
                <br>
                
                <div class="profile-info-row">
                  <div class="profile-info-name"></div>
									<div class="profile-info-value">
                    <b-button variant="outline-primary" v-on:click="edit" class="mr-1">Save</b-button>
                    <b-button v-on:click="onCancel">Cancel</b-button>
                    </div>
							  </div>

                <div class="profile-info-row">
                  <div class="profile-info-name"></div>
									<div class="profile-info-value">
                    <b-alert variant="success" v-model="success" dismissible>Profile picture successfully changed!</b-alert>
                    <b-alert variant="danger" v-model="error" dismissible>{{this.errorMessage}}</b-alert>
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
import {AXIOS} from '../../http-common'

export default {
  name: 'editProfile',
  data () {
    return {
      id: this.$route.params.id,
      username: '',
      firstName: '',
      lastName: '',
      city: '',
      phone: '',
      error: false,
      errorMessage: '',
      success: false,
      file: '',
      imagePreview: '',
      showPreview: false
    }
  },
  mounted(){
	let api = '/users/' + this.id;

    AXIOS.get(api)
    .then(response => { 
      this.username = response.data.username
      this.firstName = response.data.name
      this.lastName = response.data.lastname
      this.city = response.data.city
      this.phone = response.data.phoneNum
    })
  }
  ,
  methods: {
    edit (e) {
        e.preventDefault()
        this.success = false
        this.error = false

        if (this.validateInputs() == true) {
          const user = {
            'username': this.username,
            'name': this.firstName,
            'lastname': this.lastName,
            'city': this.city,
            'phoneNum': this.phone
          }
          AXIOS.put('/users/update', user)
          .then(response => {
            this.$router.push("profile-page")
          })
          .catch(err => {
            this.error = true
          })

        } else {
          this.errorMessage = "Some inputs are empty"
          this.error = true
        }
    },
    validateInputs() {
      if (this.firstName.length == 0 || this.lastName.length == 0 || this.city.length == 0 || this.phone.length == 0) {
        return false 
      } else {
        return true
      }
    },
    onCancel (e) {
        e.preventDefault()
        this.$router.push("profile-page")
    },
    pictureUpload() {
      document.getElementById("pictureUpload").click()
    },

    /*
    Handles a change on the file upload
    */
    handleFileUpload(){
      /*
        Set the local file variable to what the user has selected.
      */
      console.log(this.$refs)
      this.file = this.$refs.file.files[0];

      /*
        Initialize a File Reader object
      */
      let reader  = new FileReader();

      /*
        Add an event listener to the reader that when the file
        has been loaded, we flag the show preview as true and set the
        image to be what was read from the reader.
      */
      reader.addEventListener("load", function () {
        this.showPreview = true;
        this.imagePreview = reader.result;
      }.bind(this), false);

      /*
        Check to see if the file is not empty.
      */
      if( this.file ){
        /*
          Ensure the file is an image file.
        */
        if ( /\.(jpe?g|png|gif)$/i.test( this.file.name ) ) {
          /*
            Fire the readAsDataURL method which will read the file in and
            upon completion fire a 'load' event which we will listen to and
            display the image in the preview.
          */
          reader.readAsDataURL( this.file );
        }
      }
      this.submitPicture()
    },
    submitPicture() {
    this.success = false
    this.error = false
    /*
    Initialize the form data
    */
    let formData = new FormData();

    /*
    Add the form data we need to submit
    */
    formData.append('file', this.file);

    /*
    Make the request to the POST /single-file URL
    */
    AXIOS.put( '/users/update-profile-pic', formData,
    {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).
    then(response => {
      this.success = true;
    })
    .catch(err => {
      this.errorMessage = "Changing profile picture failed."
      this.error = true   
    })
  }
  }
}
</script>

<style>

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

.upload-btn-wrapper {
  position: relative;
  overflow: hidden;
  display: inline-block;
}
</style>
