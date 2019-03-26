<template>
    <div class="container">
        <div class="row">
            <table class="table table-sm col-sm">
                <thead>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Description</th>
                </thead>
                <tbody>
                    <tr v-for="airline in airlines">
                        <td>{{airline.name}}</td>
                        <td>{{airline.address}}</td>
                        <td>{{airline.description}}</td>
                    </tr>
                </tbody>
            </table>
            <div class="col-sm">
                <button type="button" v-on:click="addWin = true">Add new Airline</button>
                <AddAirline v-if="addWin" v-on:close="addWin = false" v-on:addAirline='addAirline'></AddAirline>
            </div>
        </div>
    </div>
</template>

<script>
import AddAirline from "../../components/addAirline.vue"
import axios from "axios"

export default {
    data() {
        return {
            airlines: [],
            addWin: false
        }
    },
    components: {
        AddAirline
    },
    methods: {
        addAirline(newAirline) {
            this.airlines = [...this.airlines, newAirline]
        }
    },
    mounted() {
        
	}
}
</script>

<style>
    
</style>