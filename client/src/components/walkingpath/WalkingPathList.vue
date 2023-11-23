<template>
  <main>
    <section>
      <aside class="category">
        <div class="cat_location">
          <div class="cat_location">
            <h3>지역</h3>
            <div class="cat_location_list">
              <p>
                <input
                  type="checkbox"
                  value="서울전체"
                  class="location_check"
                  id="radio_seoul"
                  v-model="selectAll"
                  @change="handleSelectAllChange"
                />
                <label
                  for="radio_seoul"
                  :class="{ checked: selectAll }"
                  class="location_name"
                >
                  <i class="fa-solid fa-circle-check"></i>
                  서울 전체
                </label>
              </p>
              <p v-for="location in locations" :key="location.value">
                <input
                  type="checkbox"
                  :value="location.value"
                  class="location_check"
                  :id="'check_' + location.label"
                  v-model="selectedLocations"
                />
                <label
                  :for="'check_' + location.label"
                  :class="{
                    checked: selectedLocations.includes(location.value),
                  }"
                  class="location_name"
                >
                  <i class="fa-solid fa-circle-check"></i>
                  {{ location.value }}
                </label>
              </p>
            </div>
          </div>
        </div>
        <hr class="cat_hr" />
        <div class="cat_time">
          <h3>소요 시간</h3>
          <div class="cat_time_range multi-range-slider">
            <p id="showtime"></p>

            <!-- 입력 받을 슬라이더 -->

            <input
              type="range"
              name="minTime"
              id="minTime"
              value="0"
              min="0"
              max="180"
              step="10"
							@change="set"
            />
            <input
              type="range"
              name="maxTime"
              id="maxTime"
              value="180"
              min="0"
              max="180"
              step="10"
							@change="set"
            />
            <!-- 양방향 슬라이더 보여주기 -->
            <div class="slider">
              <div class="track"></div>
              <div class="range"></div>
              <div class="thumb left"></div>
              <div class="thumb right"></div>
            </div>
          </div>
        </div>
        <hr class="cat_hr" />
        <div class="cat_distance">
          <h3>거리</h3>
          <div class="cat_distance_range multi-range-slider">
            <p id="show-distance"></p>

            <!-- 입력 받을 슬라이더 -->
            <input
              type="range"
              name="minDistance"
              id="minDistance"
              class="dist_Slider"
              value="0"
              min="0"
              max="9999"
              step="100"
							@change="setDistance"
            />
            <input
              type="range"
              name="maxDistance"
              id="maxDistance"
              class="dist_Slider"
              value="9999"
              min="0"
              max="9999"
              step="100"
							@change="setDistance"
            />
            <!-- 양방향 슬라이더 보여주기 -->
            <div class="distance-slider">
              <div class="track"></div>
              <div class="range"></div>
              <div class="thumb left"></div>
              <div class="thumb right"></div>
            </div>
          </div>
        </div>
        <hr class="cat_hr" />
        <input
          class="btns btn_cat"
          type="button"
          value="검색하기"
          @click="getFilteredList"
        />
      </aside>

      <article id="walking-path">
        <div id="walking-path_wrapper">
          <h3 class="search_title" v-if="keyword">
            "{{ keyword }}" 검색 결과입니다.
          </h3>
          <h3 th:text="|검색 결과 ${#lists.size(walkingPathList)}건|"></h3>
          <div class="path_list">
            <!--  -->
            <!-- 리스트 출력부분 -->
            <!--  -->
            <div class="path_wrapper" v-for="item in getList">
              <router-link :to="'/walking-path/' + item.id" class="path_img">
                <img
                  v-if="item.photos == null"
                  src="/images/noimage.png"
                  alt=""
                />
                <img
                  v-if="item.photos != null"
                  :src="
                    'http://localhost:8089/ex_images/' + item.photos.imgName
                  "
                  alt=""
                />
              </router-link>
              <div class="path_content">
                <router-link
                  :to="'/walking-path/' + item.id"
                  class="path_title"
                  >{{ item.title }}</router-link
                >
                <address>{{ item.addr }}</address>
                <p class="path_dtinfo">
                  <b
                    >{{
                      item.distance >= 1000
                        ? (item.distance / 1000).toFixed(1) + "k"
                        : item.distance
                    }}m</b
                  >
                  |
                  <b>{{
                    item.time >= 60
                      ? (item.time / 60).toFixed(0) +
                        "시간" +
                        " " +
                        (item.time % 60) +
                        "분"
                      : item.time + "분"
                  }}</b>
                </p>
                <p class="rating">★★★★★</p>
              </div>
              <div class="path_auth">
                <p th:text="${walkingPath.createdBy}">
                  조회수 : {{ item.view }}
                </p>
              </div>
            </div>
            <!--  -->
            <!-- 리스트 출력부분 -->
            <!--  -->
          </div>
        </div>
        <div class="path_ctrl">
          <p class="pagenation">
            <i class="fa-solid fa-chevron-left"></i>
            <span v-for="i in pageNum">
              <b
                :class="{ currentPage: i + 1 === pagenation.page }"
                class="cursor"
                @click="fetchList(i)"
                >{{ i + 1 }}</b>
            </span>
            <i class="fa-solid fa-chevron-right"></i>
          </p>
          <div class="btns btn_path_submit" @click="modeToModify">
            산책로 등록
          </div>
          <!-- <router-link v-if="{sessionauth == null}" to="/Login" class="btns btn_path_submit">
								산책로 등록
							</router-link> -->
        </div>
      </article>
    </section>
  </main>
</template>
<script setup>
import { defineEmits, ref, onMounted, defineProps, watch, reactive } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import router from "@/router/index.js";
import { setupWalkingPath } from "@/utils/walking_path.js";

const emit = defineEmits(["pageMode"]);
const printMode = ref("");
const props = defineProps(["getPrintMode"]);
const getList = ref([]);
const pagenation = ref([]);
let pageNum = reactive([]);
const keyword = useRoute().query.keyword != null ? useRoute().query.keyword : "";

const modeToModify = () => {
  emit("pageMode", "modify");
};

// --------------------
// 컨트롤 스크립트
// --------------------

const locations = ref([
  { value: "강남구", label: "gangnam" },
  { value: "강동구", label: "gangdong" },
  { value: "관악구", label: "Gwanak" },
  { value: "송파구", label: "songpa" },
  { value: "종로구", label: "jongro" },
]);

const selectedLocations = ref([]);
const selectAll = ref(false);

const handleSelectAllChange = () => {
  if (selectAll.value) {
    selectedLocations.value = locations.value.map((location) => location.value);
  } else {
    selectedLocations.value = [];
  }
};

watch(selectedLocations, () => {
  selectAll.value = selectedLocations.value.length === locations.value.length;
});

// --------------------
// 슬라이더 스크립트
// --------------------

const minTimeRef = ref(null);
const maxTimeRef = ref(null);
const minDistanceRef = ref(null);
const maxDistanceRef = ref(null);

onMounted(() => {
  // ref를 사용하여 DOM 요소를 참조합니다.
  minTimeRef.value = document.getElementById("minTime");
  maxTimeRef.value = document.getElementById("maxTime");
  minDistanceRef.value = document.getElementById("minDistance");
  maxDistanceRef.value = document.getElementById("maxDistance");
});
let minTimeValue=0;
let maxTimeValue=180;
let minDistanceValue=0;
let maxDistanceValue=9999;

const set = () => {
  minTimeValue = minTimeRef.value.value;
  maxTimeValue = maxTimeRef.value.value;
};

const setDistance = () => {
  minDistanceValue = minDistanceRef.value.value;
  maxDistanceValue = maxDistanceRef.value.value;
};

// --------------------
// 리스트 불러오기 - 전체
// --------------------

onMounted(() => {
  fetchList(0);
});

async function fetchList(i) {
  let walkingPathGetUrl;

  if (keyword == null) {
    walkingPathGetUrl = `http://localhost:8089/walking-path?page=${i}&size=3`;
  } else {
    printMode.value = "search";
    walkingPathGetUrl = `http://localhost:8089/walking-path/search?keyword=${keyword}&page=${i}&size=3`;
  }

  const response = await axios.get(walkingPathGetUrl);
  const { data, pageInfo, barNumber } = response.data;

  getList.value = data;
  pagenation.value = pageInfo;
  pageNum = barNumber;
}

// --------------------
// 모드 변화 감지
// --------------------

watch(
  () => props.getPrintMode,
  async () => {
    printMode.value = props.getPrintMode;
    fetchList(0);
  }
);

// --------------------
// 필터 리스트
// --------------------

function getFilteredList() {
	var locations = "";
	selectedLocations.value.forEach((word) => locations += word + "%2C");
  axios.get(
    `http://localhost:8089/walking-path/filter?page=0&size=10&keyword=${keyword}&filters=location%3A${locations}%7CminTime%3A${minTimeValue}%7CmaxTime%3A${maxTimeValue}%7CminDistance%3A${minDistanceValue}%7CmaxDistance%3A${maxDistanceValue}`
  )
	.then((response) => {
		console.log(response.data);
		const { data, pageInfo, barNumber } = response.data;
		getList.value = data;
		pagenation.value = pageInfo;
		pageNum = barNumber;
	})
  .catch((err) => console.error(err));
	router.push(
		`/walking-path/filter?page=0&size=10&keyword=${keyword}&filters=location%3A${locations}%7CminTime%3A${minTimeValue}%7CmaxTime%3A${maxTimeValue}%7CminDistance%3A${minDistanceValue}%7CmaxDistance%3A${maxDistanceValue}`
	);
}

onMounted(() => {
  setupWalkingPath();

});

</script>
<style scoped>
@import "@/assets/walking_path.css";
.currentPage {
  color: #97bf04;
  font-weight: bold;
  font-size: 1.1em;
}

.checked i {
  color: #97bf04; /* 체크된 경우의 스타일 */
}
</style>
