<template>
  <div>
    <br />
    <div>
      <b-link :to="{ path: 'add'}" append>Add new rent a car</b-link>
    </div>
    <b-table hover :items="rentACars"></b-table>
  </div>
</template>

<script>

export default {
  data () {
    return {
      rentACars: []
    }
  },
  mounted () {

  }
}
</script>

<style>

</style>
