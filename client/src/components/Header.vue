<template>
  <header class="header_wrap">
    <router-link to="/"><h1 class="logo"></h1></router-link>
    <nav>
      <router-link class="header_link" to="/walking-path">산책로</router-link>
      <router-link class="header_link" to="/visitor">자유게시판</router-link>
    </nav>
  
    <!-- <div class="member">
      <span class="profile_image">
        

      </span>
      <span class="name">산책마니아1</span>
      <a href="" class="btns btn_logout">logout</a>
    </div> -->
    <div class="header_sign">
        <router-link class="btns btn_signin" to="/login">
          <i class="sign_icon fa-solid fa-key"></i>
          Login
        </router-link>
        <router-link class="btns btn_signup" to="/signUp">
          <i class="sign_icon fa-solid fa-user-plus"></i>
          Sign up for free
        </router-link>
    </div>
  </header>
</template>

<script>
  export default {
    name: 'Header',
  };
</script>