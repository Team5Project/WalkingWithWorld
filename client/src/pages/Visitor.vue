<template>
  <Header />
  <hr class="header_hr" />
  <VisitorList v-if="compMode === 'list'" @pageMode="modeChange" />
  <VisitorModify v-if="compMode === 'modify'" @pageMode="modeChange" />
  <Footer />
</template>

<script setup>
import { ref } from "vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import VisitorList from "@/pages/VisitorList.vue";
import VisitorModify from "@/pages/VisitorModify.vue";

const compMode = ref("list");
const modeChange = (changed) => {
  compMode.value = changed;
};
</script>
<style scoped>
@import "@/assets/visitor.css";
</style>
