<template>
  <Header />
  <!-- 헤더 입력 -->
  <hr class="header_hr" />
  <main>
    <div class="visitors_list_header">자유게시판</div>

    <section>
      <div id="article">
        <article id="deleteList">
          <div class="vwrapper" v-for="vo in list">
            <div class="visit_header">
              <span>작성자</span>
              <span>내용</span>
            </div>
            <div class="visitor_body">
              <p class="vname">{{ vo.name }}</p>
              <p class="vcontent">{{ vo.content }}</p>
            </div>
            <div class="visitor_footer">
              <p class="vcreatedAt">
                {{ formatDate(vo.createdAt) }}
              </p>
              <div class="visitor_mod">
                <p
                  class="btns btn_vdelete"
                  @click="deleteVisitorList(vo.getId())"
                >
                  삭제
                </p>
              </div>
            </div>
          </div>
        </article>
      </div>
      <div class="btn_write_wrapper">
        <router-link to="/visitor-modify" class="visitor_write btns btn_vwrite"
          >글쓰기</router-link
        >
      </div>
    </section>
  </main>
  <!-- 푸터 입력 -->
  <Footer />
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { ref } from "vue";
import axios from "axios";

const list = ref([]);
const fetchList = async () => {
  const response = await axios.get("http://localhost:8089/list");
  return response.data;
};
const setList = async () => {
  list.value = await fetchList();
};
setList().then(() => {
  console.log(list.value);
});
const formatDate = (dateString) => {
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  };
  const formattedDate = new Date(dateString).toLocaleDateString(
    "ko-KR",
    options
  );
  return formattedDate;
};
</script>
<style scoped>
@import "@/assets/visitor.css";
</style>
