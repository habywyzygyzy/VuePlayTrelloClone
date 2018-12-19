<template>
  <div id="app">
    <Menu class="mb-3"></Menu>
    <router-view/>
    <p class="bg-danger text-white" style="padding: 0; margin: 0; position: absolute; right: 0; bottom: 0; left: 0; text-align: center">Asrul Harahap - 2018</p>
  </div>
</template>

<script>
import Menu from '@/components/Menu.vue'
export default {
  name: 'kanban',
  components: {
    Menu
  }
}
</script>
<style scoped>
</style>