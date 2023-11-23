<template>
  <header class="header_wrap">
    <router-link to="/" class="logo"/>
    <nav>
      <router-link class="header_link" to="/walking-path" @click="modeToDefault">산책로</router-link>
      <router-link class="header_link" to="/visitor">자유게시판</router-link>
    </nav>

    <div class="member" v-if="isAuth">
      <div class="header_sign"></div>
      <span class="profile_image">
        

      </span>
      <span class="name">{{  user }}님</span>
      <div class="btns btn_signup cursor" @click="logout()">
        <i class="sign_icon fa-solid fa-key" ></i>
        Logout
      </div>
    </div>
    
      <!-- <router-link class="btns btn_signup" to="/signup">
        <i class="sign_icon fa-solid fa-user-plus"></i>
        {{  user }}님
      </router-link>
      
      <div class="btns btn_signup cursor" @click="logout()">
        <i class="sign_icon fa-solid fa-key" ></i>
        Logout
      </div> -->

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
import { defineEmits, ref } from 'vue';
const user = ref(null);
const token = localStorage.getItem('token');
if(token != null){
    var base64Payload = token.split('.')[1];
    var payload = atob(base64Payload);
    var result = JSON.parse(payload.toString());
    user.value = result.email;
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

  const emit = defineEmits(['pageMode','printMode']);
  const modeToDefault = () => {
    emit('pageMode', 'default');
    emit('printMode', 'refresh');
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