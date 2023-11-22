<template>
  <main>
	<section>
			<aside class="category">
					<div class="cat_location">
							<div class="cat_location">
									<h3>지역</h3>
									<div class="cat_location_list">
											<p id="location_check_all">
													<i class="check_all_i fa-solid fa-circle-check cursor"></i>
													서울 전체
											</p>
											<p>
													<input type="checkbox" name="location" value="강남구" class="location_check"
															id="radio_gangnam">
													<label for="radio_gangnam" class="location_name">
															<i class="fa-solid fa-circle-check"></i>
															강남구
													</label>
											</p>
											<p>
													<input type="checkbox" name="location" value="강동구" class="location_check"
															id="radio_gangdong">
													<label for="radio_gangdong" class="location_name">
															<i class="fa-solid fa-circle-check"></i>
															강동구
													</label>
											</p>
											<p>
													<input type="checkbox" name="location" value="관악구" class="location_check"
															id="radio_Gwanak">
													<label for="radio_Gwanak" class="location_name">
															<i class="fa-solid fa-circle-check"></i>
															관악구
													</label>
											</p>
											<p>
													<input type="checkbox" name="location" value="송파구" class="location_check"
															id="radio_songpa">
													<label for="radio_songpa" class="location_name">
															<i class="fa-solid fa-circle-check"></i>
															송파구
													</label>
											</p>
											<p>
													<input type="checkbox" name="location" value="종로구" class="location_check"
															id="radio_jongro">
													<label for="radio_jongro" class="location_name">
															<i class="fa-solid fa-circle-check"></i>
															종로구
													</label>
											</p>
									</div>
							</div>
					</div>
					<hr class="cat_hr">
					<div class="cat_time">
							<h3>소요 시간</h3>
							<div class="cat_time_range multi-range-slider">
									<p id="showtime"></p> 
                  
									<!-- 입력 받을 슬라이더 -->

									<input type="range" name="minTime" id="minTime" value="0" min="0" max="180" step="10" />
									<input type="range" name="maxTime" id="maxTime" value="180" min="0" max="180" step="10" /> 
									<!-- 양방향 슬라이더 보여주기 -->
									<div class="slider">
											<div class="track"></div>
											<div class="range"></div>
											<div class="thumb left"></div>
											<div class="thumb right"></div>
									</div>
							</div>
					</div>
					<hr class="cat_hr">
					<div class="cat_distance">
							<h3>거리</h3>
							<div class="cat_distance_range multi-range-slider">
									<p id="show-distance"></p> 
               
									<!-- 입력 받을 슬라이더 -->
									<input type="range" name="minDistance" id="minDistance" class="dist_Slider" value="0" min="0" max="20000" step="100" />
									<input type="range" name="maxDistance" id="maxDistance" class="dist_Slider" value="20000" min="0" max="20000" step="100" />
									<!-- 양방향 슬라이더 보여주기 -->
									<div class="distance-slider">
											<div class="track"></div>
											<div class="range"></div>
											<div class="thumb left"></div>
											<div class="thumb right"></div>
									</div>
							</div>
					</div>
					<hr class="cat_hr">
					<input class="btns btn_cat" type="button" value="검색하기" @click="getFilteredList">
			</aside>

			<article id="walking-path">
					<div id="walking-path_wrapper">
						<h3 class="search_title" v-if="keyword">"{{ keyword }}" 검색 결과입니다.</h3>
							<h3 th:text="|검색 결과 ${#lists.size(walkingPathList)}건|"></h3>
							<div class="path_list">

									<!--  -->
									<!-- 리스트 출력부분 -->
									<!--  -->
									<div class="path_wrapper" v-for="item in getList">
										<router-link :to="'/walking-path/'+item.id" class="path_img">
													<img v-if="item.photos == null"
															src="/images/noimage.png" alt="">
													<img v-if="item.photos != null"
															:src="'http://localhost:8089/ex_images/'+item.photos.imgName" alt="">
											</router-link>
											<div class="path_content">
													<router-link :to="'/walking-path/'+item.id" class="path_title">{{ item.title }}</router-link>
													<address>{{ item.addr }}</address>
													<p class="path_dtinfo">
														<b>{{ item.distance >= 1000 ? (item.distance/1000).toFixed(1) +'k' : item.distance }}m</b> | 
														<b>{{ item.time >= 60 ? (item.time/60).toFixed(0)+'시간'+ ' ' + (item.time%60) + '분' : item.time + '분' }}</b> 
													</p>
													<p class="rating">★★★★★</p>
											</div>
											<div class="path_auth">
													<p th:text="${walkingPath.createdBy}">조회수 : {{ item.view }}</p>
											</div>
									</div>
										<!--  -->
									<!-- 리스트 출력부분 -->
									<!--  -->
							</div>
					</div> 
					<div class="path_ctrl">
							<!-- <a th:href="@{/walking-path}" class="btns btn_wp_return">전체목록</a> -->
							<p class="pagenation">
								<i class="fa-solid fa-chevron-left"></i>
								<span v-for="i in pageNum">
									<b :class="{ 'currentPage': i+1 === pagenation.page }" class="cursor" @click="fetchList(i)">{{ i+1 }}</b>
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
  import { defineEmits, ref, onMounted, defineProps, watch, reactive } from 'vue';
	import axios from 'axios';
	import { useRoute } from 'vue-router';
	import router from '@/router/index.js'

  const emit = defineEmits(['pageMode']);
	const printMode = ref('');
	const props = defineProps(['getPrintMode']);
	const getList = ref([]);
	const pagenation = ref([]);
	let pageNum = reactive([]);
	const keyword = useRoute().query.keyword;

	const modeToModify = () => {
    emit('pageMode', 'modify');
  }

	// --------------------
	// 리스트 불러오기 - 전체
	// --------------------


	async function fetchList(i){
		let walkingPathGetUrl;

		if(keyword == null) {
			walkingPathGetUrl = `http://localhost:8089/walking-path?page=${i}&size=3`;
		} else {
			printMode.value = 'search';
			walkingPathGetUrl = `http://localhost:8089/walking-path/search?keyword=${keyword}`;
		}

		const response = await axios.get(walkingPathGetUrl);
		const { data, pageInfo, barNumber } = response.data;

		getList.value = data;
		pagenation.value = pageInfo;
		pageNum = barNumber;

	}
	
	fetchList(0);
	
	watch(() => props.getPrintMode, async () => {
		printMode.value = props.getPrintMode;
		const response = await axios.get('http://localhost:8089/walking-path');
		getList.value = response.data.data;
	});

	onMounted(() => {
		import('@/utils/walking_path.js')
	});
	// 필터 적용
	const getFilteredList = function() {
		const response = axios.get(`http://localhost:8089/walking-path/filter?page=0&size=10&keyword=${keyword}&filters=location%3A%7CminTime%3A5%7CmaxTime%3A100%7CminDistance%3A1%7CmaxDistance%3A5000`);
		getList.value = response.data;
		if(keyword == null) {
			router.push(`walking-path/filter?page=0&size=10&keyword=&filters=location%3A%7CminTime%3A5%7CmaxTime%3A100%7CminDistance%3A1%7CmaxDistance%3A5000`);
		}
		else {
			router.push(`filter?page=0&size=10&keyword=${keyword}&filters=location%3A%7CminTime%3A5%7CmaxTime%3A100%7CminDistance%3A1%7CmaxDistance%3A5000`);
		}
	}
</script>
<style scoped>
    @import "@/assets/walking_path.css";
		.currentPage {
			color: #97bf04;
			font-weight: bold;
			font-size: 1.1em;
		}
</style>