<template>
    <Header/>
    <!-- 헤더 입력 -->
    <div class="search overwidth">
        <div class="content">
            <p>오늘은 어디로 떠나볼까요?</p>
            <form method="post" action="/walking-path/search" class="search_form">
                <input type="text" name="keyword" class="search_input">
                <input type="submit" class="btns btn_search" value="GO">
            </form>
        </div>
    </div>
    <main>
        <section class="recent_path" style="opacity: 50%;">
            <div class="recent_header">
                <h3>최근 산책길</h3>
                <a href="">더보기</a>
            </div>
            <div class="recent_list">
                <article class="recent_wrapper">
                    <figure class="recent_image">
                        <img src="/images/sample/1.jpg" alt="">
                    </figure>
                    <div class="path_contents">
                        <h3 class="recent_title">청계산</h3>
                        <address>종로구 청계천로 999-11, 서울</address>
                        <p class="path_dtinfo">15Km | 50분 </p>
                        <p class="rating">★★★★★</p>
                    </div>
                </article>
                <article class="recent_wrapper">
                    <figure class="recent_image">
                        <img src="/images/sample/1.jpg" alt="">
                    </figure>
                    <div class="path_contents">
                        <h3 class="recent_title">청계산</h3>
                        <address>종로구 청계천로 999-11, 서울</address>
                        <p class="path_dtinfo">15Km | 50분 </p>
                        <p class="rating">★★★★★</p>
                    </div>
                </article>
                <article class="recent_wrapper">
                    <figure class="recent_image">
                        <img src="/images/sample/1.jpg" alt="">
                    </figure>
                    <div class="path_contents">
                        <h3 class="recent_title">청계산</h3>
                        <address>종로구 청계천로 999-11, 서울</address>
                        <p class="path_dtinfo">15Km | 50분 </p>
                        <p class="rating">★★★★★</p>
                    </div>
                </article>
                <article class="recent_wrapper">
                    <figure class="recent_image">
                        <img src="/images/sample/1.jpg" alt="">
                    </figure>
                    <div class="path_contents">
                        <h3 class="recent_title">청계산</h3>
                        <address>종로구 청계천로 999-11, 서울</address>
                        <p class="path_dtinfo">15Km | 50분 </p>
                        <p class="rating">★★★★★</p>
                    </div>
                </article>
            </div>
        </section>
    </main>
    <div class="signupreq overwidth">
        <div class="signupbox">
            <article>
                <p>아직 회원이 아니신가요?<br> 지금 가입하세요!</p>
            </article>
            <a href="" class="btns btn_signup_main">회원가입하기</a>
        </div>
    </div>
    <!-- 푸터 입력 -->
    <Footer/>
</template>

<script>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';

export default {
  components: {
    Header,
    Footer,
  },
}
</script>

<style>
@import "../assets/main.css";
</style>