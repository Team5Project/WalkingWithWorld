<template>
  <section class="review">
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
</template>
<script setup></script>
<style scoped>
  @import "@/assets/walking_path_detail.css";
</style>