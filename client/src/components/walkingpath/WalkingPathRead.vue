<template>
  <hr class="header_hr">
  <main>
      <section class="detail_header">
      <div class="detail_title">
          <h2>{{ getDetail.title }}</h2>
          <div class="up_del">
              <span class="info_modi cursor" @click="modeToModify">수정</span>
              <span class="info_del cursor" @click="removeWalkingPath">삭제</span>
          </div>
      </div>
      <p>
          <span class="star_score">★★★★★</span>
          <!-- <i></i>리뷰 <b>8</b>
          <i>|</i> 댓글 <b>22</b> -->
          <span class="detail_info_from_map" v-if="getDetail.mapList && getDetail.mapList.length > 0">
              <i>|</i> 거리 <b>{{getDetail.mapList[0].distance >= 1000 ? 
              (getDetail.mapList[0].distance/1000).toFixed(1) +' k' : 
              getDetail.mapList[0].distance }}m</b>     
              <i>|</i> 시간 <b>{{ getDetail.mapList[0].time >= 60 ? 
              (getDetail.mapList[0].time/60).toFixed(0)+' 시간'+ ' ' + (getDetail.mapList[0].time%60) + ' 분' : 
              getDetail.mapList[0].time + ' 분' }}</b>
          </span>
      </p>
      <address>{{getDetail.addr}}</address>
  </section>
      <section class="infoAndcomments">
          <div class="iac_wrapper">
              <article class="detail_info">
                  <aside class="images">
                      <figure class="viewer">
                      <img v-if="getDetail.photosList && getDetail.photosList.length > 0" :src="'http://localhost:8089/ex_images/'+getDetail.photosList[0].imgName"/>
                      <img v-if="getDetail.photosList && getDetail.photosList.length === 0" src="/images/noimage.png"/>
                  </figure>
                  <div class="img_list">
                      <figcaption class="thumb" v-if="getDetail.photosList && getDetail.photosList.length > 0" v-for="photo in getDetail.photosList">
                          <img :src="'http://localhost:8089/ex_images/'+photo.imgName">
                      </figcaption>
                  </div>
                  </aside>
                  <div class="review_write">
                      <p>
                          이 산책로는 어땠나요?<br>
                          리뷰를 남겨주세요!
                      </p>
                      <div>

                          <!-- <router-link :to="'/'+WalkingPathId + '/reviews'" class="btns btn_write_big"> -->
                              <div @click="clickParam" class="btns btn_write_big">
                                  리뷰작성
                              </div>
                          <!-- </router-link> -->
                      </div>
                  </div>
              </article>
              <!-- 코멘트 컴포넌트 -->
              <Comments :id="WalkingPathId"/>
              <!-- 코멘트 컴포넌트 -->
          </div>
      </section>
      <section class="map">
          <h3>상세 경로</h3>
          <h4 v-if="getDetail.map == null">게시자가 상세 경로를 입력하지 않았습니다.</h4>
          <div class="map_wrap">
              <div class="readMap" id="map"></div>
          </div>
      </section>
      <!-- 리뷰 컴포넌트 -->
      <Review :walkingPathdId="WalkingPathId"/>
      <!-- 리뷰 컴포넌트 -->
  </main>
</template>

<script setup>
import { ref, computed, onMounted, defineProps, defineEmits } from 'vue';
import axios from 'axios';
import Comments from '@/components/Comments.vue';
import Review from '@/components/Review.vue';
import router from '@/router/index.js'

const props = defineProps(['id']);
const WalkingPathId = computed(() => props.id);

const getDetail = ref([]);
const map = ref(null);
const mapAry = ref();
const token = localStorage.getItem('token');
let bearer;

const emit = defineEmits(['pageDetailMode']);
  const modeToModify = () => {
    emit('pageDetailMode', 'update');
  }

if(token != null){
bearer = token.split('"')[3]
}
console.log(bearer);

onMounted(()=> {
  if(window.kakao && window.kakao.maps) {
      loadMap();
  } else {
      loadScript();
  }
});
const removeWalkingPath = function() {
    if(confirm("삭제하시겠습니까?")) {
        axios.delete('http://localhost:8089/walking-path/' + getDetail.value.id, {
            headers: {
                'authorization' : bearer,
            },
        })
        .then(response => {
            if(response.status  == 205){
                alert("게시글이 삭제되었습니다.");
                router.push('/walking-path');
            }
        });
        console.log("왜일까");
    } else {
        alert("취소되었습니다.");
    }
};
const loadScript = function() {
  const script = document.createElement("script");
  script.src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d753200077e444dd40df1e458903dfd3&autoload=false";
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
const drawLine = function () {
  // 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
  var linePath = [];
  mapAry.value.forEach(function (map) {
      linePath.push(new kakao.maps.LatLng(map.coordinateY, map.coordinateX));
  });

  // 지도에 표시할 선을 생성합니다
  var polyline = new kakao.maps.Polyline({
      path: linePath, // 선을 구성하는 좌표배열 입니다
      strokeWeight: 5, // 선의 두께 입니다
      strokeColor: 'red', // 선의 색깔입니다
      strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
      strokeStyle: 'solid' // 선의 스타일입니다
  });

  // 지도에 선을 표시합니다
  polyline.setMap(map.value);
}

// ----------------------------------
// get
// ----------------------------------

const clickParam = () =>{
  router.push({
      path : "/"+WalkingPathId.value+"/reviews",
  })
}

const fetchDetail = async () => {
  const response = await axios.get(`http://localhost:8089/walking-path/${WalkingPathId.value}`);
  return response.data;
}
const setDetail = async () => {
  getDetail.value = await fetchDetail();
}

setDetail().then(() => {
  import('@/utils/image_list.js')
  console.log(getDetail.value);
  mapAry.value = getDetail.value.mapList;
  if(mapAry.value.length > 0) {
      drawLine();
  }
})

</script>

<style scoped>
@import "@/assets/walking_path_detail.css";
</style>