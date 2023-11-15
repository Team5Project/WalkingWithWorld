<template>
  <div class="body">
		<div id="left" class="signup_img">
			<img src="/images/signup_left_image.png">
		</div>
		<div id="right" class="signup_form">
			<div class="form">
				<form method="POST" action="/signup">
					<hidden value="create"></hidden>
					<input type="text" name="name" class="input" placeholder="이름" required><br>
					<input type="password" name="password" class="input" placeholder="패스워드" required><br>
					<input type="email" name="email" class="input" placeholder="Email" required><br>
					<section class="address">
						<input type="text" name="addr" id="address" class="input" placeholder="주소" required>
						<button onclick="searchAddr()" value="주소 찾기" class="btn-address">주소 찾기</button>
					</section>
					<input type="submit" value="확인" class="btn-signup">
					<button class="btn-ref"><a th:href="${referer}" style="text-decoration: none">뒤로가기</a></button>
				</form>
			</div>
		</div>  
  </div>
</template>

<script>

</script>

<style scoped>
  @import "../assets/signup_form.css";
</style>