<template>
    <div class="row">
      <div class="col-md-9">
        <div class="panel panel-default" v-for="status in statusList">
          <div class="panel-heading">{{status.name}}</div>
          <div class="panel-body">
            <dl class="dl-horizontal">
              <dt>Completed yesterday</dt>
              <dd>{{status.yesterday}}</dd>
              <dt>Working on today</dt>
              <dd>{{status.today}}</dd>
              <dt>Impediments</dt>
              <dd>{{status.impediments}}</dd>
            </dl>
          </div>
        </div>
      </div>
    </div>
</template>
<style scoped>

</style>
<script>
  import store from '../store'

  export default {

    data () {
      return {
        statusList: []
      }
    },
    created () {
      store.on('statusList-updated', this.update)

      store.loadAll().then((list) => {
        this.statusList = list
      })
    },
    destroyed () {
      store.removeListener('statusList-updated', this.update)
    },
    methods: {
      update: function () {
        this.statusList = store.getStatusList()
      }
    }
  }
</script>
