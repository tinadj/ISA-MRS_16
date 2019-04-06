<template>
  <div>
    <br />
    <div>
      <b-link :to="{ path: 'add'}" append>Add new airline</b-link>
    </div>
    <b-table hover :items="airlines"></b-table>

  </div>
</template>

<script>

export default {
  data () {
    return {
      airlines: [
          { name: 'Airline1', city: 'Adress1', state: 'State1', description: 'Descripton 1', }
        ]
    }
  },
  mounted () {

  }
}
</script>

<style>

</style>
