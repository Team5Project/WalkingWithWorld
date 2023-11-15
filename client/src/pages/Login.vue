<template>
  <div class="body">
    <div id="left" class="login_img">
      <img src="/images/login_left_image.jpg">
    </div>
    <div id="right" class="login_form">
      <div class="logo-img">
        <img th:src=@{/images/login_logo.png}>
      </div>
      <div class="form">
        <form @submit.prevent="login()">
          <!-- <form method="post" action="http://localhost:8089/login"> -->
          <input type="hidden" name="redirectURL" th:value="${redirectURL}">
          <input type="text" v-model=email class="input" placeholder="이메일을 입력해주세요."> <br>
          <input type="password" v-model=password class="input" placeholder="비밀번호를 입력해주세요."> <br>
          <input type="submit" value="LOGIN" class="btns btn-login">
        </form>
      </div>
      <section class="find">
        <a href="" class="find-a">아이디 찾기</a>
        <a href="" class="find-a">비밀번호 찾기</a>
      </section>
      <section class="forward-signup">
        아직 회원이 아니신가요?
        <a href="/signup">지금 가입하세요.</a>
      </section>
    </div>
  </div>
</template>
<script setup>
import router from '@/router/index.js'
import { ref } from 'vue';
import axios from 'axios';
const email =ref("");
const password =ref("");



axios.defaults.withCredentials = true;
function login() {
  const LoginDto={};
  LoginDto.email = this.email;
  LoginDto.password = this.password;
  axios.post('http://localhost:8089/login',{
    email: LoginDto.email,
    password : LoginDto.password
  })
  .then((response)=> {
    localStorage.setItem("token",JSON.stringify(response.headers));
    //TODO 네비게이트 설정
    router.push("/");
  })
  .catch((error) => {
    console.error("에러",error)
    window.alert("로그인 정보가 일치하지 않습니다! 계정정보를 확인해주세요!!")
  });
};
</script>

<style scoped>
@import "@/assets/login_form.css";
</style>