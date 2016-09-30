<template>
    <div class="row">
      <div class="col-md-6">
        <form v-on:submit.prevent="onSubmit">
          <div class="form-group">
            <label for="yourName">Your name</label>
            <input class="form-control" type="text" v-model="yourName" id="yourName"/>
          </div>
          <div class="form-group">
            <label for="yesterday">Completed yesterday</label>
            <input class="form-control" type="text" v-model="yesterday" id="yesterday"/>
          </div>
          <div class="form-group">
            <label>Working on today</label>
            <input class="form-control" type="text" v-model="today" id="today"/>
          </div>
          <div class="form-group">
            <label>Impediments</label>
            <input class="form-control" type="text" v-model="impediments" id="impediments"/>
          </div>

          <button class="btn btn-success" type="submit" id="submit">Submit</button>
      </form>
      </div>
    </div>
</template>
<style>

</style>
<script>
  import store from '../store'

  export default {

    data () {
      return {
        yourName: null,
        yesterday: null,
        today: null,
        impediments: null
      }
    },
    methods: {
      onSubmit: function (event) {
        let status = { name: this.yourName, yesterday: this.yesterday, today: this.today, impediments: this.impediments }

        store.newStatus(status)
      }
    }

  }
</script>
