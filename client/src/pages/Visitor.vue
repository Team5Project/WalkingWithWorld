<template>
  <Header />
  <hr class="header_hr" />
  <VisitorList
    v-if="compMode === 'list'"
    @pageMode="modeChange"
    @targetId="getTargetId"
  />
  <VisitorCreate v-if="compMode === 'create'" @pageMode="modeChange" />
  <VisitorUpdate
    v-if="compMode === 'update'"
    @pageMode="modeChange"
    :data="visitorList"
    :id="target"
  />
  <Footer />
</template>

<script setup>
import { ref, defineProps } from "vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import VisitorList from "@/components/visitor/VisitorList.vue";
import VisitorCreate from "@/components/visitor/VisitorCreate.vue";
import VisitorUpdate from "@/components/visitor/VisitorUpdate.vue";
import axios from "axios";

const visitorList = ref([""]);
const visitorId = defineProps(["id"]);
const compMode = ref("list");
let target;

const modeChange = (changed) => {
  compMode.value = changed;
};
const getTargetId = (t) => {
  target = t;
  console.log(target);
};

const getVisitorList = async () => {
  let response = await axios.get("http://localhost:8089/list");
  const data = response.data;
  visitorList.value = data;
};

getVisitorList();
</script>
<style scoped>
@import "@/assets/visitor.css";
</style>
