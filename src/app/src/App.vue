<template>
  <div>
    <navBar></navBar>
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
  </div>
</template>

<script>
import List from './components/List'
import NavBar from './components/NavBar'
import Submit from './components/Submit'

export default {
  components: {
    List,
    NavBar,
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
