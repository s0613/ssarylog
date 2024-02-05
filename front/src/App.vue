<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>

        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/write">About</RouterLink>
  </header>

  <RouterView />
</template>

<style>
</style>
