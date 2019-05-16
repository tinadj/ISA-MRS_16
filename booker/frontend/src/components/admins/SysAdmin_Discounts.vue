<template>
    <b-card-group>
        <b-card border-variant="light" style="max-width: 30rem;" class="mb-2 mx-auto">
            <table class="table table-borderless">
                <thead>
                    <tr>
                    <th scope="col">User type</th>
                    <th scope="col">Points</th>
                    <th scope="col">Discount (%)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    <td>
                        <img id="regular-user" alt="Regular" src="../../assets/regular-user.png" >
                        <b-tooltip target="regular-user" title="Regular user" placement="left"></b-tooltip>
                    </td>
                    <td>{{regular.minPts}} - {{bronze.minPts - 1}}</td>
                    <td>{{regular.discount}}</td>
                    </tr>
                    <tr>
                    <td>
                        <img id="bronze-user" alt="Regular" src="../../assets/bronze-user.png" >
                        <b-tooltip target="bronze-user" title="Bronze user" placement="left"></b-tooltip>
                    </td>
                    <td>{{bronze.minPts}} - {{silver.minPts - 1}}</td>
                    <td>{{bronze.discount}}</td>
                    </tr>
                    <tr>
                    <td>
                        <img id="silver-user" alt="Regular" src="../../assets/silver-user.png" >
                        <b-tooltip target="silver-user" title="Silver user" placement="left"></b-tooltip>
                    </td>
                    <td>{{silver.minPts}} - {{gold.minPts - 1}}</td>
                    <td>{{silver.discount}}</td>
                    </tr>
                    <tr>
                    <td>
                        <img id="gold-user" alt="Regular" src="../../assets/gold-user.png" >
                        <b-tooltip target="gold-user" title="Gold user" placement="left"></b-tooltip>
                    </td>
                    <td>{{gold.minPts}} >= </td>
                    <td>{{gold.discount}}</td>
                    </tr>
                </tbody>
            </table>
            <b-button variant="outline-primary" :to="{ path: 'edit'}" append>Edit</b-button>
        </b-card>
    </b-card-group>
</template>

<script>
import { AXIOS } from '../../http-common';

export default {
    name: 'IndexPage',
    data() {
        return {
            regular: '',
            bronze: '',
            silver: '',
            gold: ''
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
