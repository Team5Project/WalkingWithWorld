<template>
    <Header />
    <hr class="header_hr">
    <div class="all">
        <div class="title">
            <h1>리뷰 등록</h1><br>
        </div>
        <div class="wrapper">
            <div class="left">
                <section v-if=walkingPath>
                    <div class="walking-path">
                        <img v-if="walkingPath.photosList == 0" src="/images/noimage.png" alt="">
                        <img v-if="walkingPath.photosList > 0"
                            :src="'http://localhost:8089/ex_images/' + walkingPath.photosList[0].imgName" alt="" />
                        <span>{{ walkingPath.title }}</span>
                        <section v-if="walkingPath.mapList > 0">
                            <span>{{ walkingPath.mapList[0].distance }}</span>
                        </section>
                        <span>{{ walkingPath.addr }}</span>
                    </div>
                </section>
            </div>

            <div class="review_form">
                <div class="form">
                    <form @submit.prevent="postReview">
                        <section class="write">
                            <span>별점</span> <br>
                            <span value="5">★★★★★</span><br>
                            <span>본문</span> <br>
                            <textarea name="content" v-model="content" rows="10" cols="60" wrap="soft"
                                style="resize: none; padding: 10px" required></textarea><br>
                            <span>사진(5개 제한)</span><br>
                            <section class="img-area">
                                <div id="review_image_container" class="image_container">
                                    
                                </div>
                                <div class="filebox">
                                    <label for="file">+</label>
                                    <input id="file" type="file" @change="readInputFile" multiple />
                                </div>
                            </section>
                        </section>
                        <section class="submit">
                            <input type="submit" value="저장" class="btn-review">
                            <span class="cursor" @click="$goBack">뒤로가기</span>
                        </section>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <Footer />
</template>
<script setup>
import { ref } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import axios from 'axios';
import router from '@/router/index.js'

const walkingPath = ref([]);
const props = defineProps(['id']);
const content = ref("");
const files = ref([]);

function getWalkingPath() {
    axios.get(`http://localhost:8089/walking-path/${props.id}`)
        .then((response) => {
            return response.data;
        })
        .then((data) => {
            walkingPath.value = data;
            console.log(walkingPath.value)
        })
}
getWalkingPath();

const readInputFile = (e) => {// 미리보기 기능구현

    const review = document.getElementById('review_image_container')
    review.innerHTML = '';

    var file = e.target.files;
    
    //e.target.files;
    
    var fileArr = Array.from(file);
    files.value = fileArr;
    console.log(fileArr)

    fileArr.forEach(function(f){
    	if(!f.type.match("image/.*")){
        	alert("이미지 확장자만 업로드 가능합니다.");
            return;
        };
        var reader = new FileReader();
        reader.onload = function(e){
            const img = document.createElement('img');
            img.style.width = "100px";
            img.src = e.target.result;
            review.appendChild(img);
        };
        reader.readAsDataURL(f);
    })
}

function postReview() {
    const formData = new FormData();
    const reviewsRequestDTO = {
        content: content.value
    }

    const json = JSON.stringify(reviewsRequestDTO);
    const blob = new Blob([json], { type: 'application/json' });

    formData.append('reviewsRequestDTO', blob);

    if (files.value && files.value.length > 0) {
        files.value.forEach((file) => {
            console.log("여기"+file);
            formData.append('files', file);
        });
    } else {
        console.log("없")
        formData.append('files', new Blob(), '');
    }

    // formData의 내용을 로그로 출력
    for (let pair of formData.entries()) {
        console.log(pair[0] + ', ' + pair[1]);
    }


    axios.post(`http://localhost:8089/${props.id}/reviews`, formData, {
        headers: {
            // Authorization : JSON.parse(localStorage.getItem("token")).authorization,
            Authorization: JSON.parse(localStorage.getItem("token")).authorization,
            'Content-Type': 'multipart/form-data',  // 수정된 부분
        },
    }).then(response => {
        if (response.status == 201) {
            router.push('/walking-path/' + props.id)
        }
    })
        // .then(() => close(undefined))
        .catch(error => console.log(error))
}

</script>

<style scoped>
@import "../assets/review_write_form.css";
</style>  