<template>
    <div>
        <form v-on:submit="add">
            <input type="text" v-model="name" placeholder="Name"><br>
            <input type="text" v-model="address" placeholder="Address"><br>
            <input type="text" v-model="description" placeholder="Description"><br>
            <input type="submit" value="Add">
            <button v-on:click="$emit('close')">Close</button>
        </form>
    </div>
</template>

<script>
/*eslint-disable */
export default {
    name: "AddRentACar",
    data() {
        return {
            name: '',
            address: '',
            description: ''
        }
    },
    methods: {
        add(e) {
            e.preventDefault();

            const rent_a_car = {
                'name': this.name,
                'address': this.address,
                'description': this.description
            }

            this.$emit('addRentACar', rent_a_car)
        }
    }
}
</script>

<style>

</style>
