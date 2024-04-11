<template>
  <div id="app">
    <Navbar />
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/NavBarView.vue';

export default {
  name: 'App',
  components: {
    Navbar
  }
};
</script>