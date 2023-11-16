<template>
  <main>
    <div class="visitors_write_header">게시글 작성</div>
    <section>
      <div class="visitors_write_wrap">
        <aside class="visitor_logo">
          <img th:src="@{/images/logo-big.png}" />
        </aside>
        <article>
          <div class="insert_visitor">
            <form @submit.prevent="submitForm" method="post" action="/visitor">
              <p class="inline_insert">
                <label for="visitor_name_input">작성자명</label>
                <input
                  v-model="name"
                  type="text"
                  name="name"
                  placeholder="작성자명"
                  id="visitor_name_input"
                  maxlength="10"
                  required
                /><br />
              </p>
              <p class="inline_insert">
                <label for="visitor_password_input">비밀번호</label>
                <input
                  v-model="password"
                  type="password"
                  name="password"
                  placeholder="비밀번호"
                  id="visitor_password_input"
                  maxlength="10"
                  required
                /><br />
              </p>
              <p class="inline_textarea">
                <label for="visitor_content_input">내용</label>
                <textarea
                  v-model="content"
                  name="content"
                  id="visitor_content_input"
                  required
                ></textarea>
              </p>
              <div class="visitor_mod">
                <router-link to="/visitor" class="btns btn_vback"
                  >이전화면</router-link
                >
                <input type="reset" value="초기화" class="btns btn_vreset" />
                <input type="submit" value="등록" class="btns btn_vsubmit" />
              </div>
            </form>
          </div>
        </article>
      </div>
    </section>
  </main>
</template>

<script>
import axios from "axios";
export default {
  data: function () {
    return {
      name: "",
      password: "",
      content: "",
    };
  },
  methods: {
    submitForm: function () {
      console.log(this.name, this.content, this.password);
      var url = "http://localhost:8089/visitor";
      var data = {
        name: this.name,
        password: this.password,
        content: this.content,
      };
      axios
        .post(url, data)
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
@import "../assets/insert_visitors.css";
</style>
