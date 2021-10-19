<template>
  <div id="app">
    <div id="header">
      <main-header/>
    </div>
 
    <!-- <img src="./assets/img/bg-callout.jpg">
    <router-view/> -->
 
    <!-- <div id="footer">
      <main-footer/>
    </div> -->
  </div>
</template>
 
<script>
import MainHeader from '../common/mainheader.vue'
// import MainFooter from './common/mainfooter.vue'

export default {
  name: 'app',
  components: {
    "main-header": MainHeader,
    // "main-footer": MainFooter
  }
}
</script>
 
<style>
  @import './css/style.css'
</style>
