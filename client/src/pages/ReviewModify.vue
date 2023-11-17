<template>
    <Header/>
  <hr class="header_hr">
    <div class="all">
        <div class="title">
            <h1>리뷰 등록</h1><br>
        </div>
        <div class="wrapper">
            <div class="left">
                <section v-if=walkingPath>
                    <div class="walking-path">
                        <img v-if="walkingPath.photosList.length ==0"
                        src="/images/noimage.png" alt="">
                        <img v-if="walkingPath.photosList.length>0"
                            v-bind:src="'http://localhost:8089/ex_images/'+walkingPath.photosList[0].imgName" alt="" />
                        <span>{{ walkingPath.title }}</span>
                        <section v-if="walkingPath.mapList.length>0">
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
                            <textarea name="content" v-model="content" rows="10" cols="60" wrap="soft" style="resize: none; padding: 10px"
                                required></textarea><br>
                            <span>사진(5개 제한)</span><br>
                            <section class="img-area">
                                <div id="review_image_container" class="image_container">
                                </div>
                                <div class="filebox">
                                    <label for="file">+</label>
                                    <input id="file" type="file" name="files" 
                                    accept='image/jpg,impge/png,image/jpeg,image/gif'
                        
                                    multiple />
                                </div>
                            </section>
                    </section>
                    <section class="submit">
                        <input type="submit" value="저장" class="btn-review">
                        <button class="btn-ref"><a th:href="@{/walking-path/{id}(id=${walkingPaths.getId()})}">뒤로가기</a></button>
                    </section>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <Footer/>
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
const file = ref("");

function getWalkingPath(){
    axios.get(`http://localhost:8089/walking-path/${props.id}`)
    .then((response)=>{
        return response.data;
    })
    .then((data)=>{
        walkingPath.value = data;
        console.log(walkingPath.value)
    })
}
getWalkingPath();

function readInputFile(){

}

function postReview(){
    const formData = new FormData();
    formData.append('reviewsRequestDTO', new Blob([JSON.stringify({content})],{type:"application/json"}))
    
    if(file){
        formData.append('files',file);
    } else {
        formData.append('files',new Blob(),'');
    }

    console.log(JSON.parse(localStorage.getItem("token")).authorization);

    axios.post(`http://localhost:8089/${props.id}/reviews`,formData,{
        headers:{
            authorization : JSON.parse(localStorage.getItem("token")).authorization,
            "Content-Type" : `multipart/form-data`
        },
    })
    .then(() => close(undefined))
    .catch(error => console.log(error))
}

</script>

<style scoped>
  @import "../assets/review_write_form.css";
</style>  