<template>
  <Header />
  <!-- 헤더 입력 -->
  <hr class="header_hr" />
  <main>
    <div class="visitors_list_header">자유게시판</div>

    <section>
      <div id="article">
        <article id="deleteList">
          <div class="vwrapper" v-for="vo in list" :key="vo.id">
            <div class="visit_header">
              <span>작성자</span>
              <span>내용</span>
            </div>
            <div class="visitor_body">
              <p class="vname">[[${vo.name}]]</p>
              <p class="vcontent">[[${vo.content}]]</p>
            </div>
            <div class="visitor_footer">
              <p
                class="vcreatedAt"
                th:text="${#temporals.format(vo.createdAt, 'yyyy년 MM월 dd일 HH:mm')}"
              ></p>
              <!--<p th:text="${#temporals.format(orderDto.orderDate, 'yy-mm-dd HH:mm')}"></p>-->
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
        <a
          th:href="@{/insertVisitorsForm}"
          class="visitor_write btns btn_vwrite"
          >글쓰기</a
        >
      </div>
    </section>
  </main>
  <!-- 푸터 입력 -->
  <Footer />
</template>

<script>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

export default {
  components: {
    Header,
    Footer,
  },

  methods: {
    deleteVisitorList: function () {
      var visitorsId = id;
      var VisitorsDTO = {};
      VisitorsDTO.id = id;

      const password = prompt("비밀번호를 입력하세요:");
      VisitorsDTO.password = password;
      axios
        .delete("http:/localhost:8089/{id}")
        .then((response) => {
          return response.data;
        })
        .then((data) => {
          document.getElementById("article").innerHTML = `${data}`;
        });
    },
  },
};
</script>
<style scoped>
@import "@/assets/visitor.css";
</style>
