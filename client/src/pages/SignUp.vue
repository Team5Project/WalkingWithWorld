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
					<input type="email" v-model="email" class="input" placeholder="Email" required><br>
					<section class="address">
						<input type="text" v-model="addr"  id="address" class="input" placeholder="주소">
						<button v-on:click="searchAddr" value="주소 찾기" class="btn-address">주소 찾기</button>
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

const name = ref("");
const password = ref("");
const email = ref("");
const addr = ref("");

function searchAddr() {
	new daum.Postcode({
		oncomplete: function (data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 주소 정보를 해당 필드에 넣는다.
			document.getElementById("address").value = addr;
		}
	}).open();
}

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
	
}
</script>
<style scoped>
@import "@/assets/signup_form.css";
</style>