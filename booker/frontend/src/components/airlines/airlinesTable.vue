<template>
  <div>
    <br />
    <div>
      <b-link :to="{ path: 'add'}" append>Add new airline</b-link>
    </div>
    <table class="table table-sm col-sm">
        <thead>
            <th>Name</th>
            <th>Address</th>
            <th>Description</th>
        </thead>
        <tbody>
            <tr v-for="airline in airlines" :key="airline.name">
                <td>{{airline.name}}</td>
                <td>{{airline.address}}</td>
                <td>{{airline.description}}</td>
            </tr>
        </tbody>
    </table>
  </div>
</template>

<script>

export default {
  data () {
    return {
      airlines: []
    }
  },
  mounted () {

  }
}
</script>

<style>

</style>
