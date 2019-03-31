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
                    <tr v-for="hotel in hotels">
                        <td>{{hotel.name}}</td>
                        <td>{{hotel.address}}</td>
                        <td>{{hotel.description}}</td>
                    </tr>
                </tbody>
            </table>
            <div class="col-sm">
                <td scope="col"> <button type="button" v-on:click="addWin = true">Add new Hotel</button></td>
                <AddHotel v-if="addWin" v-on:close="addWin = false" v-on:addHotel='addHotel'></AddHotel>
            </div>
        </div>
    </div>
</template>

<script>
/*eslint-disable */
import AddHotel from "../../components/addHotel.vue"
import axios from "axios"

export default {
    data() {
        return {
            hotels: [],
            addWin: false
        }
    },
    components: {
        AddHotel
    },
    methods: {
        addHotel(newHotel) {
            this.hotels = [...this.hotels, newHotel]
        }
    },
    mounted() {
	}
}
</script>

