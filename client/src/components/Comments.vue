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
                <span class="date">{{ item.createdAt }}</span>
            </div>
                <div class="up_del" style="visibility:visible;">
                    <span id="update" class="info_modi" @click="changeUpdateMode(item.id, item.content)">수정</span>
                    <span id="delete" class="info_del" @click="deleteComments(item.id)">삭제</span>
                </div>
          </div>
        </div>
            <p> {{ item.content }} </p>
        </article>
        <div @click="CommentsRead"> 5개 더 보기 </div>
    </div>
    <form id="comments_write" @submit.prevent="writeComments">
      <input type="text" class="comments_input" name="content" placeholder="댓글을 입력해주세요" v-model="comment_write" required/>
      <input type="button" class="btns btn_comments" value="등록" @click="writeComments"/>
    </form>
    <form id="comments_update" @submit.prevent="updateComments" style="display:none;">
      <input type="text" class="comments_input" name="content" v-model="comment_update" required/>
      <input type="button" class="btns btn_comments" value="수정" @click="updateComments"/>
    </form>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import axios from 'axios';

const props = defineProps(['id']);
const getComments = ref([]);
const currentPage = ref(0);
const totalPages = ref('');
const commentsUrl = `http://localhost:8089/${props.id}/comments`;
const comment_write = ref('');
const comment_update = ref('');
const updateId = ref('');
const updateContent = ref('');
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
  } catch (error) {
    console.error('Error fetching comments:', error);
  }
}

async function reGetComments(){
  currentPage.value = 0;
  try {
    const response = await axios.get(`${commentsUrl}?page=${currentPage.value}&size=5`);
    const { data, pageInfo } = response.data;
    getComments.value = data;
    totalPages.value = pageInfo.totalPages;
  } catch (error) {
    console.error('Error fetching comments:', error);
  }
}

CommentsRead();

// ----------------------------------
// post
// ----------------------------------

async function writeComments(){
  const postComments = {
    "content": comment_write.value
  }
  await axios.post(commentsUrl, postComments, {
    headers: {
      'Content-Type': 'application/json',
      'authorization': bearer,
    },
  });
  comment_write.value = '';
  reGetComments();
}

// ----------------------------------
// update
// ----------------------------------

async function changeUpdateMode(id, content){
  updateId.value = id;
  updateContent.value = content;
  comment_update.value = content;
  document.querySelector('#comments_write').style.display = 'none';
  document.querySelector('#comments_update').style.display = 'flex';
}

async function updateComments(){
  const putComments = {"content": comment_update.value}
  await axios.put(`http://localhost:8089/comments/${updateId.value}`,putComments,{
    headers:{
      'content-type': 'application/json',
      'authorization': bearer,
    }
  });
  document.querySelector('#comments_write').style.display = 'flex';
  document.querySelector('#comments_update').style.display = 'none';
  reGetComments();
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
    reGetComments();
  }else{
    return;
  }
}

</script>

<style scoped>
  @import "@/assets/walking_path_detail.css";
</style>