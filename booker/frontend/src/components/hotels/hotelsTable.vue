<template>
  <div>
    <br />
    <div>
      <b-link :to="{ path: 'add'}" append>Add new hotel</b-link>
    </div>
    <table class="table table-sm col-sm">
        <thead>
            <th>Name</th>
            <th>Address</th>
            <th>Description</th>
        </thead>
        <tbody>
            <tr v-for="hotel in hotels" :key="hotel.name">
                <td>{{hotel.name}}</td>
                <td>{{hotel.address}}</td>
                <td>{{hotel.description}}</td>
            </tr>
        </tbody>
    </table>
  </div>
</template>

<script>

export default {
  data () {
    return {
      hotels: []
    }
  },
  mounted () {

  }
}
</script>

<style>

</style>
