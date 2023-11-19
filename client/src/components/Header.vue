<template>
  <header class="header_wrap">
    <router-link to="/"><h1 class="logo"></h1></router-link>
    <nav>
      <router-link class="header_link" to="/walking-path">산책로</router-link>
      <router-link class="header_link" to="/visitor">자유게시판</router-link>
    </nav>
    <div class="member" v-if="auth">
      <span class="profile_image"></span>
      <router-link class="name" to="/signup"> <!--마이페이지 구현 하면 마이페이지로-->
          유저네임 바인딩
        </router-link>
        <router-link class="btns btn_logout" to="/logout">
          logout
        </router-link>
    </div>
    <div class="header_sign" v-else="auth">
        <router-link  class="btns btn_signin" to="/login">
          <i class="sign_icon fa-solid fa-key"></i>
          Login
        </router-link>
        <router-link class="btns btn_signup" to="/signup">
          <i class="sign_icon fa-solid fa-user-plus"></i>
          Sign up for free
        </router-link>
    </div>
  </header>
</template>

<script>

  export default {
    name: 'Header',
    computed : {
      auth(){
        return localStorage.getItem("token")
      }
    }
  }

</script>