<template>
    <Header />
    <WalkingPathRead :id="WalkingPathId" v-if="compDetailMode === 'default'" @pageDetailMode="modeChange"/>
    <WalkingPathUpdate :id="WalkingPathId" v-if="compDetailMode === 'update'" @pageDetailMode="modeChange"/>
    <Footer />
</template>

<script setup>
import { computed, ref } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import WalkingPathRead from '@/components/walkingpath/WalkingPathRead.vue';
import WalkingPathUpdate from '@/components/walkingpath/WalkingPathUpdate.vue';

const props = defineProps(['id']);
const WalkingPathId = computed(() => props.id);

const compDetailMode = ref('default');
const modeChange = (changed) => {
	compDetailMode.value = changed;
}
</script>

<style scoped>
@import "@/assets/walking_path_detail.css";
</style>