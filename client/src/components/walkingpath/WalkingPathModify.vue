<template>  
    <div id="write-form">
        <h1>산책로 등록</h1>
        <form method="post" action="/walking-path" enctype="multipart/form-data">
            <div id="input-items">
                <div id="left-form">
                    <label>산책로 이름<br><input type="text" class="text-box" name="title" required></label><br>
                    <label>주소
                        <input type="button" class="button" onclick="searchAddr()" value="주소 찾기"><br>
                        <input type="text" name="addr" class="text-box" id="address" required><br>
                    </label><br>
                    <span>사진(5개 제한)</span><br>
                    <section class="img-area">
                        <div class="filebox">
                            <label for="file" class="fileImg">+</label>
                            <input id="file" type="file" name="files" onchange="setThumbnail(event)" multiple />
                        </div>
                        <div id="image_container" class="image_container"></div>
                    </section><br>
                    <label>
                        <input type="hidden" name="course" id="course">
                        <input type="hidden" name="time" id="time">
                        <input type="hidden" name="distance" id="distance">
                    </label>
                </div>
                <div id="line"></div>
                <div id="right-form">
                    <label>경로 그리기
                        <div class="map_wrap">
                            <div @click="mapClickEvent" class="readMap" id="map"></div>
                        </div>
                    </label>
                </div>
                <div id="clear"></div>
            </div>
            <div id="buttons">
                <input type="submit" class="submit-button" value="확인">
                <a th:href="${referer}">&nbsp; 뒤로가기 &nbsp;</a>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
const map = ref(null);
onMounted(()=> {
    if(window.kakao && window.kakao.maps) {
        loadMap();
    } else {
        loadScript();
    }
});
const loadScript = function() {
    const script = document.createElement("script");
    script.src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d753200077e444dd40df1e458903dfd3&libraries=drawing&autoload=false";
    script.onload = () => window.kakao.maps.load(loadMap);
    document.head.appendChild(script);
};
const loadMap = function() {
    const mapContainer = document.getElementById('map'); // 지도를 표시할 div
    const mapOption = {
        center: new window.kakao.maps.LatLng(37.541, 126.986), // 지도의 중심좌표
        level: 6 // 지도의 확대 레벨
    };
    map.value = new window.kakao.maps.Map(mapContainer, mapOption); // 지도 생성
};
// 산책로 등록
let drawingFlag = false; // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
let moveLine; // 선이 그려지고 있을때 마우스 움직임에 따라 그려질 선 객체 입니다
let clickLine // 마우스로 클릭한 좌표로 그려질 선 객체입니다
let distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
let dots = {}; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.

let clickLa = [];
let clickMa = [];
let fullDistance;
let resultLat = [];

const mapClickEvent = function (mouseEvent) {
    // 마우스로 클릭한 위치입니다 
    // alert(mouseEvent.latLng instanceof kakao.maps.LatLng); // true
    let clickPosition = mouseEvent.LatLng;
console.log(clickPosition);
    clickLa.push(clickPosition.La);
    clickMa.push(clickPosition.Ma);
};
</script>
<style scoped>
  @import "@/assets/walking_path_write.css";
</style>