<template>
  <div class="comments_wrapper">
    
    <div class="comments_content">
      <!--                    여기에 댓글이 들어감-->
      <article class="comments" v-for="item in getComments.data">
        <div class="comments_info">
          <div class="review_info">
            <div class="detail_member">
                <span href="" class="profile_image"></span>
                <span class="name"> {{ item.name }} </span>
                <span class="date">날짜입력란</span>
            </div>
                <div class="up_del" style="visibility:visible;">
                    <span id="update" class="info_modi">수정</span>
                    <span id="delete" class="info_del">삭제</span>
                </div>
          </div>
        </div>
            <p> {{ item.content }} </p>
        </article>
    </div>
    <form action="" method="post" id="comments_write">
      <input type="text" id="comment_content" class="comments_input" name="content" placeholder="댓글을 입력해주세요"  v-model="comment_content" required/>
      <input type="button" class="btns btn_comments" value="등록" @click="writeComments()"/>
    </form>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import axios from 'axios';

const props = defineProps(['id']);
const getComments = ref([]);

const commentsUrl = `http://localhost:8089/${props.id}/comments`

function listRead(){
  const fetchComments = async () =>{
    const response = await axios.get(commentsUrl);
    return response.data
  }
  const setComments = async () => {
    getComments.value = await fetchComments(); 
  }
  setComments().then(()=>{
    console.log(getComments.value.data);
  })
}

listRead();

const comment_content = ref('');

const token = localStorage.getItem('token');

let bearer;
if(token != null){
  bearer = token.split('"')[3]
}
console.log(bearer);

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
  listRead();
}

</script>
<style scoped>
  @import "@/assets/walking_path_detail.css";
</style>