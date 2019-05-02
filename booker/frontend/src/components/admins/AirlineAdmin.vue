<template>
  <b-container>
    <b-row>
      <b-col>
          <br />
          <router-view></router-view>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>

export default {
  data () {
    return {
    }
  },
  methods: {

  },
  mounted () {
    console.log(this.$route.path);
  }
}
</script>

<style>

</style>
