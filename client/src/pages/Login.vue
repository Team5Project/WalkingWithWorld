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
        <form method="post" action="/login">
          <input type="hidden" name="redirectURL" th:value="${redirectURL}">
          <input type="text" name="email" class="input" placeholder="이메일을 입력해주세요."> <br>
          <input type="password" name="password" class="input" placeholder="비밀번호를 입력해주세요."> <br>
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

<style scoped>
  @import "@/assets/login_form.css";
</style>