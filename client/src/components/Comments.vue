<template>
  <div class="comments_wrapper">
    
    <div class="comments_content">
      <!--                    여기에 댓글이 들어감-->
      <article class="comments" v-for="item in getComments">
        <div class="comments_info">
          <div class="review_info">
            <div class="detail_member">
                <span href="" class="profile_image"></span>
                <span class="name"> {{ item.name }} </span>
                <span class="date">{{ dateFormat(item.createdAt) }}</span>
            </div>
                <div class="up_del" style="visibility:visible;">
                    <span id="update" class="info_modi">수정</span>
                    <span id="delete" class="info_del" @click="deleteComments(item.id)">삭제</span>
                </div>
          </div>
        </div>
            <p> {{ item.content }} </p>
        </article>
        <span @click="moreGetComments()">more + </span>
    </div>
    <form action="" method="post" id="comments_write">
      <input type="text" id="comment_content" class="comments_input" name="content" placeholder="댓글을 입력해주세요" v-model="comment_content" required/>
      <input type="button" class="btns btn_comments" value="등록" @click="writeComments()"/>
    </form>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import axios from 'axios';
import dayjs from 'dayjs';

const props = defineProps(['id']);
const getComments = ref([]);
const currentPage = ref(0);
const totalPages = ref('');
const commentsUrl = `http://localhost:8089/${props.id}/comments`;
const comment_content = ref('');
const token = localStorage.getItem('token');
let bearer;

if(token != null){
  bearer = token.split('"')[3]
}
console.log(bearer);


// ----------------------------------
// get
// ----------------------------------

async function CommentsRead() {
  try {
    const response = await axios.get(`${commentsUrl}?page=${currentPage.value}&size=5`);
    const { data, pageInfo } = response.data;
    getComments.value = [...getComments.value, ...data];
    totalPages.value = pageInfo.totalPages;
    currentPage.value++ ;
    if(currentPage.value === 1){
      document.querySelector('.comments_content').style.overflowY = "hidden"
    }else{
      document.querySelector('.comments_content').style.overflowY = "scroll"
    }
  } catch (error) {
    console.error('Error fetching comments:', error);
  }
}

function dateFormat(date) {
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
}

function moreGetComments(){
  CommentsRead();
}

CommentsRead();

// ----------------------------------
// post
// ----------------------------------

async function writeComments(){
  const postComments = {
    "content": comment_content.value
  }
  await axios.post(commentsUrl, postComments, {
    headers: {
      'Content-Type': 'application/json',
      'authorization': bearer,
    },
  });
  comment_content.value = '';
  CommentsRead();
}

// ----------------------------------
// delete
// ----------------------------------

async function deleteComments(commentsId){
  console.log(bearer);
  const result = window.confirm("정말 삭제하겠습니까?");
  if(result){
    await axios.delete(`http://localhost:8089/comments/${commentsId}`,{
      headers:{
        'authorization': bearer,
      }
    });
    CommentsRead();
  }else{
    return;
  }
}

</script>

<style scoped>
  @import "@/assets/walking_path_detail.css";
</style>