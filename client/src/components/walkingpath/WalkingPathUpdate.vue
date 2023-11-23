<template>  
    <div id="write-form">
        <h1>산책로 수정</h1>
        <form @submit.prevent="updateWalkingPaths">
            <div id="input-items">
                <div id="left-form">
                    <label>산책로 이름<br><input type="text" class="text-box" name="title" v-model="title" :placeholder="walkingPath.data.title"></label><br>
                    <label>주소
                        <input type="button" class="button" @click.self.prevent="searchAddr" value="주소 찾기"><br>
                        <input type="text" name="addr" class="text-box" id="address" v-model="addr" :placeholder="walkingPath.data.addr"><br>
                    </label><br>
                    <span>사진(5개 제한)</span><br>
                    <section class="img-area">
                        <input type="file" @change="uploadFile" ref="imgUpload" multiple />
                        <div id="img_preview"></div>
                        <!-- <div class="filebox">
                            <label for="file" class="fileImg">+</label>
                            <v-file-input id="file" type="file" name="files" multiple />
                        </div>
                        <div id="image_container" class="image_container"></div> -->
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
                            <div @click.prevent="mapClickEvent" class="readMap" id="map"></div>
                        </div>
                    </label>
                </div>
                <div id="clear"></div>
            </div>
            <div id="buttons">
                <input type="submit" class="submit-button" value="확인">
                <span class="cursor" @click="$goBack">&nbsp; 뒤로가기 &nbsp;</span>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import axios from 'axios';
import router from '@/router/index.js'
import { useRoute } from 'vue-router';

const map = ref(null);
const coordsX = ref(null);
const coordsY = ref(null);
const totalDistance = ref(null);
const totalTime = ref(null);
onMounted(()=> {
    loadMap();
    drawLine();
    setFile();
});
const loadMap = function() {
    const mapContainer = document.getElementById('map'); // 지도를 표시할 div
    const mapOption = {
        center: new window.kakao.maps.LatLng(37.541, 126.986), // 지도의 중심좌표
        level: 6 // 지도의 확대 레벨
    };
    map.value = new window.kakao.maps.Map(mapContainer, mapOption); // 지도 생성
    kakao.maps.event.addListener(map.value, 'click', clickMap);
    kakao.maps.event.addListener(map.value, 'rightclick', rightClickMap);
};
// 산책로 등록
let drawingFlag = false; // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
let clickLine; // 마우스로 클릭한 좌표로 그려질 선 객체입니다
let distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
let dots = {}; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.

let clickLa = [];
let clickMa = [];
const clickMap = function(mouseEvent) {
    let clickPosition = mouseEvent.latLng;

    clickLa.push(clickPosition.La);
    clickMa.push(clickPosition.Ma);

    // post 해야 할 정보
    coordsX.value = clickLa;
    coordsY.value = clickMa;

    if (!drawingFlag) {

        // 상태를 true로, 선이 그리고있는 상태로 변경합니다
        drawingFlag = true;

        // 지도 위에 선이 표시되고 있다면 지도에서 제거합니다
        deleteClickLine();

        // 지도 위에 커스텀오버레이가 표시되고 있다면 지도에서 제거합니다
        deleteDistnce();

        // 지도 위에 선을 그리기 위해 클릭한 지점과 해당 지점의 거리정보가 표시되고 있다면 지도에서 제거합니다
        deleteCircleDot();

        // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
        clickLine = new kakao.maps.Polyline({
            map: map.value, // 선을 표시할 지도입니다 
            path: [clickPosition], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
            strokeWeight: 3, // 선의 두께입니다 
            strokeColor: '#db4040', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid' // 선의 스타일입니다
        });

        // 클릭한 지점에 대한 정보를 지도에 표시합니다
        displayCircleDot(clickPosition, 0);

    } else { // 선이 그려지고 있는 상태이면

        // 그려지고 있는 선의 좌표 배열을 얻어옵니다
        let path = clickLine.getPath();

        // 좌표 배열에 클릭한 위치를 추가합니다
        path.push(clickPosition);

        //console.log(clickPosition);
        // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
        clickLine.setPath(path);

        let distance = Math.round(clickLine.getLength());
        displayCircleDot(clickPosition, distance);
    }
}
// 지도에 마우스 오른쪽 클릭 이벤트를 등록합니다
// 선을 그리고있는 상태에서 마우스 오른쪽 클릭 이벤트가 발생하면 선 그리기를 종료합니다
const rightClickMap = function (mouseEvent) {

    // 지도 오른쪽 클릭 이벤트가 발생했는데 선을 그리고있는 상태이면
    if (drawingFlag) {

        // 마우스 클릭으로 그린 선의 좌표 배열을 얻어옵니다
        var path = clickLine.getPath();

        // 선을 구성하는 좌표의 개수가 2개 이상이면
        if (path.length > 1) {

            // 마지막 클릭 지점에 대한 거리 정보 커스텀 오버레이를 지웁니다
            if (dots[dots.length - 1].distance) {
                dots[dots.length - 1].distance.setMap(null);
                dots[dots.length - 1].distance = null;
            }

            var distance = Math.round(clickLine.getLength()), // 선의 총 거리를 계산합니다
            content = getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다

            document.getElementById("distance").value = distance;
            document.getElementById("time").value = distance / 67 | 0;
            // 그려진 선의 거리정보를 지도에 표시합니다
            showDistance(content, path[path.length - 1]);
        } else {
            // 선을 구성하는 좌표의 개수가 1개 이하이면 
            // 지도에 표시되고 있는 선과 정보들을 지도에서 제거합니다.
            deleteClickLine();
            deleteCircleDot();
            deleteDistnce();
        }

        // 상태를 false로, 그리지 않고 있는 상태로 변경합니다
        drawingFlag = false;
    }
};
// 클릭으로 그려진 선을 지도에서 제거하는 함수입니다
const deleteClickLine = function() {
    if (clickLine) {
        clickLine.setMap(null);
        clickLine = null;
    }
}
// 마우스 드래그로 그려지고 있는 선의 총거리 정보를 표시하기
// 마우스 오른쪽 클릭으로 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 생성하고 지도에 표시하는 함수입니다
const showDistance = function(content, position) {

    if (distanceOverlay) { // 커스텀오버레이가 생성된 상태이면

        // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
        distanceOverlay.setPosition(position);
        distanceOverlay.setContent(content);
    } else { // 커스텀 오버레이가 생성되지 않은 상태이면

        // 커스텀 오버레이를 생성하고 지도에 표시합니다
        distanceOverlay = new kakao.maps.CustomOverlay({
            map: map.value, // 커스텀오버레이를 표시할 지도입니다
            content: content,  // 커스텀오버레이에 표시할 내용입니다
            position: position, // 커스텀오버레이를 표시할 위치입니다.
            xAnchor: 0,
            yAnchor: 0,
            zIndex: 3
        });
    }
}
// 그려지고 있는 선의 총거리 정보와 
// 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 삭제하는 함수입니다
const deleteDistnce = function() {
    if (distanceOverlay) {
        distanceOverlay.setMap(null);
        distanceOverlay = null;
    }
}
// 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여 
// 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
const displayCircleDot = function(position, distance) {

    // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
    var circleOverlay = new kakao.maps.CustomOverlay({
        content: '<span class="dot"></span>',
        position: position,
        zIndex: 1
    });

    // 지도에 표시합니다
    circleOverlay.setMap(map.value);

    if (distance > 0) {
        // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
        var distanceOverlay = new kakao.maps.CustomOverlay({
            content: '<div class="dotOverlay">거리 <span class="number">' + distance + '</span>m</div>',
            position: position,
            yAnchor: 1,
            zIndex: 2
        });

        // 지도에 표시합니다
        distanceOverlay.setMap(map.value);
        // post할 정보
        totalDistance.value = distance;
        totalTime.value = distance / 67 | 0;
    }

    // 배열에 추가합니다
    dots.push({ circle: circleOverlay, distance: distanceOverlay });
}
// 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 지도에서 모두 제거하는 함수입니다
const deleteCircleDot = function() {
    var i;

    for (i = 0; i < dots.length; i++) {
        if (dots[i].circle) {
            dots[i].circle.setMap(null);
        }

        if (dots[i].distance) {
            dots[i].distance.setMap(null);
        }
    }

    dots = [];
}
// 마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여 
// 그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
// HTML Content를 만들어 리턴하는 함수입니다
const getTimeHTML = function(distance) {

    // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
    var walkTime = distance / 67 | 0;
    var walkHour = '', walkMin = '';

    // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
    if (walkTime > 60) {
        walkHour = '<span class="number">' + Math.floor(walkTime / 60) + '</span>시간 '
    }
    walkMin = '<span class="number">' + walkTime % 60 + '</span>분'

    // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
    var content = '<ul class="dotOverlay distanceInfo">';
    content += '    <li>';
    content += '        <span class="label">총거리</span><span class="number">' + distance + '</span>m';
    content += '    </li>';
    content += '    <li>';
    content += '        <span class="label">도보</span>' + walkHour + walkMin;
    content += '    </li>';
    content += '</ul>'

    clickLa = [];
    clickMa = [];

    return content;
}
// 선 그리기
const drawLine = function () {
    // 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
    var linePath = [];
    mapAry.forEach(function (map) {
        linePath.push(new kakao.maps.LatLng(map.coordinateY, map.coordinateX));
    });

    // 지도에 표시할 선을 생성합니다
    clickLine = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께 입니다
        strokeColor: 'red', // 선의 색깔입니다
        strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid' // 선의 스타일입니다
    });

    // 지도에 선을 표시합니다
    clickLine.setMap(map.value);
}
// walking-path 수정
const files = ref([]);
const title = ref(null);
const addr = ref(null);
const result = ref([]);
const id =  useRoute().params.id;
const walkingPath = defineProps(['data']);
const mapAry = walkingPath.data.coordinateList;
const updateWalkingPaths = function() {
    let mapInstance = {distance:totalDistance.value, time:totalTime.value, coordinateX:coordsX.value, coordinateY:coordsY.value};
    result.value = mapInstance;
    console.log(result.value);

    const bearer = localStorage.getItem('token').split('"')[3];
    const data = {title:title.value, addr:addr.value, requestMapDTO:result.value};
    const formData = new FormData();
    formData.append(
        'requestDTO',
        new Blob([JSON.stringify(data)], {type:'application/json'})
    );
    for(let i = 0; i < files.value.length; i++) {
        formData.append('files', files.value[i]);
    }
    
    axios.put('http://localhost:8089/walking-path/' + id, formData, { // id 넣기
        headers: {
            'authorization' : bearer,
            'Content-Type' : 'multipart/form-data'
        },
    })
    .then(response => {
        if(response.status  == 205){
            alert("산책로 수정이 완료되었습니다.");
            router.go(0);
        }
    });
}
const uploadFile = function(e) {
    const preview = document.getElementById('img_preview')
    preview.innerHTML = '';
    var file = e.target.files;
    var fileArr = Array.from(file);
    files.value = [];
    fileArr.forEach(function(f) {
        if(!f.type.match("image/.*")){
            alert("이미지 파일만 업로드 가능합니다.");
        } else{
            files.value.push(f);
            var reader = new FileReader();
            reader.onload = function(e){
                const img = document.createElement('img');
                img.style.width = "100px";
                img.src = e.target.result;
                preview.appendChild(img);
            };
        reader.readAsDataURL(f);
        }
    });
    console.log(files.value);
}
const setFile = function() {
    const preview = document.getElementById('img_preview')
    preview.innerHTML = '';
    var file = walkingPath.data.photosList;
    file.forEach(function(f) {
        const img = document.createElement('img');
        img.style.width = "100px";
        img.src = 'http://localhost:8089/ex_images/' + f.imgName;
        preview.appendChild(img);
    });
}
function searchAddr() {
  new daum.Postcode({
      oncomplete: function (data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
         // var addr = ''; // 주소 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              addr.value = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              addr.value = data.jibunAddress;
          }

          // 주소 정보를 해당 필드에 넣는다.
          document.getElementById("address").value = addr;
					
      }
  }).open();
}
</script>
<style scoped>
  @import "@/assets/walking_path_write.css";
</style>