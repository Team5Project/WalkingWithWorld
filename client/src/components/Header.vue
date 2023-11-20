<template>
  <header class="header_wrap">
    <router-link to="/">
      <h1 class="logo"></h1>
    </router-link>
    <nav>
      <router-link class="header_link" to="/walking-path" @click="modeToDefault">산책로</router-link>
      <router-link class="header_link" to="/visitor">자유게시판</router-link>
    </nav>

    <!-- <div class="member">
      <span class="profile_image">
        

      </span>
      <span class="name">산책마니아1</span>
      <a href="" class="btns btn_logout">logout</a>
    </div> -->
    <div class="header_sign" v-if="isAuth">
      <router-link class="btns btn_signup" to="/signup"> <!--마이페이지 구현 하면 마이페이지로-->
        <i class="sign_icon fa-solid fa-user-plus"></i>
        유저네임 바인딩
      </router-link>
      
      <button class="btns btn_signup" @click="logout()">
        <i class="sign_icon fa-solid fa-key" ></i>
        Logout
      </button>
        
      
    </div>
    <div class="header_sign" v-else="isAuth">
      <router-link class="btns btn_signin" to="/login">
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

<script setup>
import { defineEmits} from 'vue';

import axios from 'axios';
import router from '@/router/index.js'

const emit = defineEmits(['pageMode']);
  const modeToDefault = () => {
    console.log('modeToDefault is called');

    emit('pageMode', 'default');
  }



 const auth = () =>{
  return localStorage.getItem("token")
 }

 const isAuth = auth();

 function logout(){
  localStorage.removeItem("token");
  console.log(localStorage.getItem("token"))
  location.reload();
 }


  // methods: {
  //   getUserInfo() {
  //     const config = {
  //       headers: {
  //         Authorizaiton: JSON.parse(localStorage.getItem('token')).authorization,
  //       },
  //     };
  //     axios.get('http://localhost:8089/usersinfo', config)
  //       .then((response) => {
  //         return response.data
  //       })
  //       .then((data) => {
  //         console.log(data);
  //       });
  //   }
  // },
  // mounted() {
  //    this.getUserInfo();
//}

</script>