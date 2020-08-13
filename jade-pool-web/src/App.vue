<template>
  <div id="app">
    <!--
      <div id="nav">
        <router-link to="/">Home</router-link> |
        <router-link to="/about">About</router-link>
      </div>
    -->
    <Header></Header>
    <router-view/>
  </div>
</template>

<script>
import Header from './components/Header.vue';

export default {
  name: "App",
  components: {
    Header: Header
  },
  methods: {

  },
  data() {
    return {

    }
  },
}
</script>
<style>
  body {
    -webkit-user-select:none;

    -moz-user-select:none;

    -ms-user-select:none;

    user-select:none;
  }
</style>
<style scoped>
    #app {
      font-family: Avenir, Helvetica, Arial, sans-serif;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
      text-align: center;
      color: #2c3e50;
    }
    #nav {
      padding: 30px;
    }

    #nav a {
      font-weight: bold;
      color: #2c3e50;
    }

    #nav a.router-link-exact-active {
      color: #42b983;
    }
</style>
