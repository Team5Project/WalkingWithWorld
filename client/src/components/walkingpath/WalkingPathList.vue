<template>
  <main>
	<section>
			<aside class="category">
					<div class="cat_location">
							<div class="cat_location">
									<h3>지역</h3>
									<div class="cat_location_list">
											<p id="location_check_all">
													<i class="check_all_i fa-solid fa-circle-check"></i>
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
					<!-- <input class="btns btn_cat" type="button" th:data-word="${keyword}"
							th:onclick="searchWalkingPath(this.getAttribute('data-word'))" value="검색하기"> -->
					<input class="btns btn_cat" type="button" value="검색하기" @click="getFilteredList()">
			</aside>

			<article id="walking-path">
					<div id="walking-path_wrapper">
						<h3 v-if="keyword">"{{ keyword }}" 검색 결과입니다.</h3>
							<h3 th:text="|검색 결과 ${#lists.size(walkingPathList)}건|"></h3>
							<div class="path_list">

									<!--  -->
									<!-- 리스트 출력부분 -->
									<!--  -->
									<div class="path_wrapper" v-for="item in getList.data">
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
							<p class="pagenation">&lt;&lt; &lt; 1 2 3 4 5 &gt; &gt;&gt;</p>
							<button class="btns btn_path_submit" @click="modeToModify">
								산책로 등록
							</button>
							<!-- <router-link v-if="{sessionauth == null}" to="/Login" class="btns btn_path_submit">
								산책로 등록
							</router-link> -->
					</div>
			</article>
	</section>
</main>

</template>
<script setup>
  import { defineEmits, ref, onMounted, inject } from 'vue';
	import axios from 'axios';
import { useRoute } from 'vue-router';

  const emit = defineEmits(['pageMode']);
  const modeToModify = () => {
    emit('pageMode', 'modify');
  }
	
	// --------------------
	// 리스트 불러오기 - 전체
	// --------------------

	const getList = ref([]);
	const keyword = useRoute().query.keyword;
	const fetchList = async () =>{
		if(keyword == null) {
			const response = await axios.get('http://localhost:8089/walking-path');
			return response.data;
		} else {
			const response = await axios.get('http://localhost:8089/walking-path/search?keyword=' + keyword);
			return response.data;
		}
	}

	// --------------------
	// 리스트 불러오기 - 필터링
	// --------------------
	// let FilterKeyword = "";
	// let locationStr = "";
	// const urlStr = ref(`http://localhost:8089/walking-path/filter?keyword=${FilterKeyword}&filters=location%3A${locationStr}%7C${minTime}%3A5%7C${maxTime}%3A30%7C${minDistance}%3A1%7C${maxDistance}%3A10`)
	// function getFilteredList(){
	// 	const fetchListFilter = async () =>{
	// 		const response = await axios.get(urlStr)
	// 		return response.data;
	// 	}
	// 	const setListFilter = async() => {
	// 		getList.value = await fetchListFilter();
	// 	}
	// }



	const setList = async() => {
		getList.value = await fetchList();
	}
	setList().then(()=>{
		console.log(getList.value.data);
		})
	onMounted(() => {
		import('@/utils/walking_path.js')

	})	
</script>
<style scoped>
    @import "@/assets/walking_path.css";
</style>