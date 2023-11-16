<template>
  <section class="review" v-if="getReviewList.length == 1">
    <h3>리뷰</h3>
    <div class="review_container">
      <i class="rv-chav left fa-solid fa-circle-chevron-left"></i>
      <i class="rv-chav right fa-solid fa-circle-chevron-right"></i>
      <div class="review_none">
        <p>아직 리뷰가 작성되지 않았습니다.</p>
        <p>첫 리뷰를 작성해 주세요!</p>
        <a th:href="@{|/reviews/${walkingPaths.getId()}/write|}" class="btns btn_write_big">
          리뷰작성
        </a>
      </div>
    </div>
  </section>
  <section class="review" v-else="getReviewList.size >= 1">
    <div id="reviews" class="review_content" v-for="review in reviewList" :key="review.in">
      <!--    여서부터!-->
      <section v-if="review.photosList.length > 0">
        <aside class="images">
          <figure class="viewer">
            <img th:src="@{|/ex_images/${review.photosList[0].imgName}|}" src="/images/sample/1.jpg" alt="">
          </figure>
          <div class="img_list">
            <figcaption class="thumb" th:each="photo : ${review.photosList}">
              <img th:src="@{|/ex_images/${photo.imgName}|}" src="/images/sample/1.jpg" alt="">
            </figcaption>
          </div>
        </aside>
      </section>
      <section v-else="review.photosList.length>0">
        <aside class="images">
          <figure class="viewer">
            <img th:src="@{/images/noimage.png}" src="/images/sample/1.jpg" alt="">
          </figure>
        </aside>
      </section>
      <article class="review_main">
        <div class="review_info">
          <div class="detail_member">
            <a href="" class="profile_image"></a>
            <span class="name">{{ review.createdBy }}</span>
            <span class="date">{{ review.createdAt }}</span>
          </div>
          <th:block th:if="${session.auth!=null && session.auth.getName()== review.createdBy}">
            <div class="up_del">
              <span class="info_modi" th:onclick="|modifiedReview(${review.getId()})|">수정</span>
              <span class="info_del" th:onclick="|deleteReview(${review.getId()})|">삭제</span>
            </div>
          </th:block>
        </div>
        <hr>
        <div class="review_text">
          <p>
            {{ review.content }}
          </p>
        </div>
      </article>
    </div>
  </section>
</template>
<script setup>
import axios from 'axios';
import { ref, defineProps } from 'vue';


const p = defineProps({
  walkingPathdId: Number
})


const reviewList = ref([]);
const getReviewList = async () => {
  await axios.get('http://localhost:8089/' + 1 + '/reviews')
    .then((response) => {
      return response.data
    })
    .then((data) => {
      reviewList.value = data.data;
    })
}
getReviewList();

// const setList = async() => {
// 		getList.value = await fetchList();
// 	}
// 	setList().then(()=>{
// 		console.log(getList.value.data);
// 		})
</script>
<style scoped>
@import "@/assets/walking_path_detail.css";
</style>