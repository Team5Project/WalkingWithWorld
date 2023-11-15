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

									<input type="range" name="minTime" id="minTime" value="0" min="0" max="180" step="10"
											onchange="set()" />
									<input type="range" name="maxTime" id="maxTime" value="180" min="0" max="180" step="10"
											onchange="set()" /> 
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
									<input type="range" name="minDistance" id="minDistance" value="0" min="0" max="20000" step="100"
											onchange="setDistance()" />
									<input type="range" name="maxDistance" id="maxDistance" value="20000" min="0" max="20000"
											step="100" onchange="setDistance()" />
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
					<input class="btns btn_cat" input type="button" th:data-word="${keyword}"
							th:onclick="searchWalkingPath(this.getAttribute('data-word'))" value="검색하기">
			</aside>
			<!--  -->
			<!-- 리스트 출력부분 -->
			<!--  -->
			<article id="walking-path">
					<div id="walking-path_wrapper">
							<h3 th:if="${keyword}" th:text="|'${keyword}' 검색 결과입니다.|"></h3>
							<h3 th:text="|검색 결과 ${#lists.size(walkingPathList)}건|"></h3>
							<div class="path_list" th:each="walkingPath:${walkingPathList}">
									<div class="path_wrapper" v-for="item in getList.data">
											<a class="path_img" th:href="@{|/walking-path/${walkingPath.getId()}|}">
													<img th:if="${#lists.size(walkingPath.getPhotosList()) > 0}"
															th:src="@{|/ex_images/${walkingPath.getPhotosList().get(0).getImgName()}|}" alt="">
													<img th:unless="${#lists.size(walkingPath.getPhotosList()) > 0}"
															th:src="@{/images/noimage.png}" alt="">
											</a>
											<div class="path_content">
													<a class="path_title">{{ item.title }}</a>
													<address>{{ item.addr }}</address>
													<p class="path_dtinfo">
														<b>{{ item.distance }}</b>Km |
															
													</p>
													<p class="rating">★★★★★</p>
											</div>
											<div class="path_auth">
													<p th:text="${walkingPath.createdBy}">작성자</p>
													<p th:text="${walkingPath.createdAt}">날짜</p>
											</div>
									</div>
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
  import { defineEmits, ref } from 'vue';
	import axios from 'axios';

  const emit = defineEmits(['pageMode']);
  const modeToModify = () => {
    emit('pageMode', 'modify');
  }

	const getList = ref([]);
	const fetchList = async () =>{
		const response = await axios.get('http://localhost:8089/walking-path')
		return response.data;
	}
	const setList = async() => {
		getList.value = await fetchList();
	}
	setList().then(()=>{
		console.log(getList.value)
		})
</script>
<style scoped>
    @import "@/assets/walking_path.css";
</style>