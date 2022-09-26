<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
ul,
li {
  list-style: none;
}
html,
body,
#app {
  height: 100%;
  width: 100%;
  background-color: #f3f3f3;
}
</style>
