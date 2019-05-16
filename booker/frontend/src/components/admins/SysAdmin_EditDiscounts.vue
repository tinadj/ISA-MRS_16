<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 30rem;" class="mb-2 mx-auto">
            <table class="table table-borderless">
                <thead>
                    <tr>
                    <th scope="col" style="width:30%">User type</th>
                    <th scope="col" style="width:35%">Min Points</th>
                    <th scope="col" style="width:35%">Discount (%)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    <td>
                        <img id="regular-user" alt="Regular" src="../../assets/regular-user.png" >
                        <b-tooltip target="regular-user" title="Regular user" placement="left"></b-tooltip>
                    </td>
                    <td>0</td>
                    <td><b-input v-model="regular.discount" placeholder="Discount"></b-input></td>
                    </tr>
                    <tr>
                    <td>
                        <img id="bronze-user" alt="Regular" src="../../assets/bronze-user.png" >
                        <b-tooltip target="bronze-user" title="Bronze user" placement="left"></b-tooltip>
                    </td>
                    <td><b-input v-model="bronze.minPts" placeholder="Min Points"></b-input></td>
                    <td><b-input v-model="bronze.discount" placeholder="Discount"></b-input></td>
                    </tr>
                    <tr>
                    <td>
                        <img id="silver-user" alt="Regular" src="../../assets/silver-user.png" >
                        <b-tooltip target="silver-user" title="Silver user" placement="left"></b-tooltip>
                    </td>
                    <td><b-input v-model="silver.minPts" placeholder="Min Points"></b-input></td>
                    <td><b-input v-model="silver.discount" placeholder="Discount"></b-input></td>
                    </tr>
                    <tr>
                    <td>
                        <img id="gold-user" alt="Regular" src="../../assets/gold-user.png" >
                        <b-tooltip target="gold-user" title="Gold user" placement="left"></b-tooltip>
                    </td>
                    <td><b-input v-model="gold.minPts" placeholder="Min Points"></b-input></td>
                    <td><b-input v-model="gold.discount" placeholder="Discount"></b-input></td>
                    </tr>
                </tbody>
            </table>
            <b-button variant="outline-primary" class="mr-3" v-on:click="edit">Save</b-button>
            <b-button :to="{ path: '/discounts'}">Cancel</b-button><br><br>
            <b-alert variant="danger" v-model="error" dismissible>{{errorMessage}}</b-alert>
        </b-card>
    </b-card-group>
</template>

<script>
import { AXIOS } from '../../http-common';
import { faBullseye } from '@fortawesome/free-solid-svg-icons';

export default {
    name: 'IndexPage',
    data() {
        return {
            regular: '',
            bronze: '',
            silver: '',
            gold: '',
            error: false,
            errorMessage: '' 
        }
    },
    methods: {
        edit(e) {
            e.preventDefault()
            this.error = false

            if (this.valid() == true) {
                const discounts = {
                    'regularDiscount': this.regular.discount,
                    'bronzeMinPts': this.bronze.minPts,
                    'bronzeDiscount': this.bronze.discount,
                    'silverMinPts': this.silver.minPts,
                    'silverDiscount': this.silver.discount,
                    'goldMinPts': this.gold.minPts,
                    'goldDiscount': this.gold.discount
                }

                AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
                AXIOS.put('/discounts/update', discounts)
                .then(response => this.$router.push("/discounts"))
                .catch(err => {
                    this.errorMessage = "Something went wrong!"
                    this.error = true
                })
            } else {
                this.error = true
            }
        },
        valid() {
            if (this.regular.discount === "" || 
            this.bronze.minPts === "" || this.bronze.discount.length === "" ||
            this.silver.minPts === "" || this.silver.discount === "" || 
            this.gold.minPts === "" || this.gold.discount === "") {
                this.errorMessage = "Some fields are empty!"
                return false
            }
            if (this.bronze.minPts < this.regular.minPts || this.silver.minPts < this.bronze.minPts ||
            this.gold.minPts < this.silver.minPts ) {
                this.errorMessage = "Points aren't valid!"
                return false
            }
            return true
        }
    },
    mounted() {
        AXIOS.defaults.headers.common['Authorization'] = "Bearer " + localStorage.getItem('token');
        AXIOS.get('/discounts/get')
        .then(response => { 
            for (let i in response.data) {
                if (response.data[i].userType == "REGULAR") {
                    this.regular = response.data[i]
                } else if (response.data[i].userType == "BRONZE") {
                    this.bronze = response.data[i]
                } else if (response.data[i].userType == "SILVER") {
                    this.silver = response.data[i]
                } else if (response.data[i].userType == "GOLD") {
                    this.gold = response.data[i]
                }
            }
        })
    }
}
</script>

<style scoped>

</style>
