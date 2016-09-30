<template>
  <nav class="navbar navbar-default">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Status</a>
      </div>

      <div class="collapse navbar-collapse" id="navbar-collapse">
        <ul class="nav navbar-nav">

        </ul>
      </div>
    </div>
  </nav>

  <div class="container">
    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <h1>Your Status</h1>
      </div>
    </div>
    <submit></submit>

    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <h1>Team Status</h1>
      </div>
    </div>
    <list></list>
  </div>
</template>

<script>
import List from './components/List'
import Submit from './components/Submit'

export default {
  components: {
    List,
    Submit
  }
}
</script>

<style lang="scss">
  $icon-font-path: "../node_modules/bootstrap-sass/assets/fonts/bootstrap/";

  @import '../node_modules/bootswatch/paper/_variables.scss';
  @import '../node_modules/bootstrap-sass/assets/stylesheets/_bootstrap.scss';
  @import '../node_modules/bootswatch/paper/_bootswatch.scss';
</style>
