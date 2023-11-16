<template>
	<div class="body">
		<div id="left" class="signup_img">
			<img src="/images/signup_left_image.png">
		</div>
		<div id="right" class="signup_form">
			<div class="form">
				<form @submit.prevent="submitForm">
					<input type="text" v-model="name" class="input" placeholder="이름" required><br>
					<input type="password" v-model="password"  class="input" placeholder="패스워드" required><br>
					<section class="email">
						<input type="email" v-model="email" class="input" placeholder="Email" required>
						<button v-on:click.self.prevent="verifyEmail" value="중복 검사" class="btn-email">중복 검사</button>
					</section>
					<section class="address">
						<input type="text" v-model="addr"  id="address" class="input" placeholder="주소">
						<button v-on:click.self.prevent="searchAddr" value="주소 찾기" class="btn-address">주소 찾기</button>
					</section>
					<input type="submit" value="확인" class="btn-signup">
					<button class="btn-ref"><a th:href="${referer}" style="text-decoration: none">뒤로가기</a></button>
				</form>
			</div>
		</div>
	</div>
</template>
<script setup>
import { ref } from 'vue';
import axios from 'axios';
import router from '@/router/index.js'
import searchAddr from '@/utils/addr.js'

const name = ref("");
const password = ref("");
const email = ref("");
const addr = ref("");


function submitForm(){
	const RequestUsersDTO = {};
	RequestUsersDTO.name = name.value;
	RequestUsersDTO.password = password.value;
	RequestUsersDTO.email = email.value;
	RequestUsersDTO.addr = addr.value;
	
	axios.post('http://localhost:8089/signup',RequestUsersDTO)
	.then((response)=>{
		if(response.status == 201){
			alert("회원가입이 완료되었습니다.")
			router.push('/login')		}
	})
	.catch((err) =>{
		alert("회원가입이 실패하였습니다. 입력을 다시 확인해주세요", err)
		router.push('/signup')
	})
};

function verifyEmail(){
	if(email.value === null || email.value ===""){
		alert("이메일을 입력해주세요.")
		return;
	}
	axios.get('http://localhost:8089/signup/email?email='+email.value)
	.then((response)=>{
		if(response.status == 200){
			alert("사용하셔도 좋은 이메일 입니다.")
		}
	})
	.catch((err)=>{
		alert(err);
	})
}
</script>
<style scoped>
@import "@/assets/signup_form.css";
</style>