<template>
<h2>지도</h2>
<h4>게시자가 상세 경로를 입력하지 않았습니다.</h4>
  <div class="map_wrap">
      <div class="readMap" id="map"></div>
  </div>
</template>
<script setup>
const src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d753200077e444dd40df1e458903dfd3"
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.541, 126.986), // 지도의 중심좌표
        level: 6 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
</script>
<style>

</style>