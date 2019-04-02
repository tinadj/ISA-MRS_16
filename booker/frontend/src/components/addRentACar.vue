<template>
    <div>
        <form v-on:submit="add">
            <input type="text" v-model="name" placeholder="Name"><br>
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
            name: ''
        }
    },
    methods: {
        add(e) {
            e.preventDefault();

            const rent_a_car = {
                'name': this.name
            }

            this.$emit('addRentACar', rent_a_car)
        }
    }
}
</script>

<style>

</style>
