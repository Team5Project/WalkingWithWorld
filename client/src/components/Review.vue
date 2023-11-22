<template>
  <section class="review" v-if="!reviewList.data || reviewList.data.length === 0">
    <h3>리뷰</h3>
    <div class="review_container">
      <i class="rv-chav left fa-solid fa-circle-chevron-left"></i>
      <i class="rv-chav right fa-solid fa-circle-chevron-right"></i>
      <div class="review_none">
        <p>아직 리뷰가 작성되지 않았습니다.</p>
        <p>첫 리뷰를 작성해 주세요!</p>
        <div @click="clickParam" class="btns btn_write_big">
          리뷰작성
        </div>
      </div>
    </div>
  </section>
  <section class="review" v-else>
    <div id="reviews" class="review_content" v-for="review in reviewList.data" :key="review.id">
      <section v-if="review.photosList && review.photosList.length > 0">
        <aside class="images">
          <figure class="viewer">
            <img :src="'http://localhost:8089/ex_images/' + review.photosList[0].imgName" alt="">
          </figure>
          <div class="img_list">
            <figcaption class="thumb" v-for="photo in review.photosList" :key="photo.id">
              <img :src="'http://localhost:8089/ex_images/' + photo.imgName" alt="">
            </figcaption>
          </div>
        </aside>
      </section>
      <section v-else>
        <aside class="images">
          <figure class="viewer">
            <img :src="'/images/noimage.png'" alt="">
          </figure>
        </aside>
      </section>
      <article class="review_main">
        <!-- 나머지 리뷰 내용 표시 -->

        <div class="review_info">
          <div class="detail_member">
            <a href="" class="profile_image"></a>
            <span class="name">{{ review.createdBy }}</span>
            <span class="date">{{ review.createdAt }}</span>
          </div>
          <th:block th:if="${session.auth!=null && session.auth.getName()== review.createdBy}">
            <div class="up_del">
              <span class="info_modi" @click="modify">수정</span>
              <span class="info_del" @click="deleteReviews(review.id)">삭제</span>
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
import router from '@/router/index.js'


const props = defineProps(['id', 'walkingPathdId']);

const clickParam = () => {
  router.push({
    path: "/" + props.walkingPathdId + "/reviews",
  })
}

const reviewList = ref([]);

const getReviewList = async () => {
  await axios.get(`http://localhost:8089/${props.walkingPathdId}/reviews`)
    .then((response) => {
      return response.data
    })
    .then((data) => {
      reviewList.value = data;
      console.log(reviewList.data);
    })

}
getReviewList();

// const setList = async() => {
// 		getList.value = await fetchList();
// 	}
// 	setList().then(()=>{
// 		console.log(getList.value.data);
// 		})


function deleteReviews(e){
  axios.delete(`http://localhost:8089/reviews/`+e,{
    headers:{
      Authorization: JSON.parse(localStorage.getItem("token")).authorization,
    }
  })
  .then(response =>{
    if(response.status == 205){
      alert("리뷰가 정상적으로 삭제되었습니다.")
      window.location.reload(true);
    }
  })
}
</script>
<style scoped>
@import "@/assets/walking_path_detail.css";
</style>