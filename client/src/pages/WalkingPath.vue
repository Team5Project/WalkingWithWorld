<template>
	<Header @pageMode="modeChange"/>
	<!-- 헤더 입력 -->
	<hr class="header_hr">
	<WalkingPathList v-if="compMode === 'default'" @pageMode="modeChange"/>
	<WalkingPathCreate v-if="compMode === 'modify'" @pageMode="modeChange"/>
	<!-- <WalkingPathDetail/> -->
  <!-- 푸터 입력 -->
  <Footer/>
</template>


<script setup>
import { ref } from 'vue';

import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import WalkingPathList from '@/components/walkingpath/WalkingPathList.vue';
import WalkingPathCreate from '@/components/walkingpath/WalkingPathCreate.vue';

const compMode = ref('default');
const modeChange = (changed) => {
	compMode.value = changed;
}
</script>
