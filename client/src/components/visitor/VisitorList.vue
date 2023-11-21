<template>
  <main>
    <div class="visitors_list_header">자유게시판</div>

    <section>
      <div id="article">
        <article id="deleteList">
          <div class="vwrapper" v-for="vo in list">
            <div class="visit_header">
              <span>작성자</span>
              <span>내용</span>
            </div>
            <div class="visitor_body">
              <p class="vname">{{ vo.name }}</p>
              <p class="vcontent">{{ vo.content }}</p>
            </div>
            <div class="visitor_footer">
              <p class="vcreatedAt">
                {{ formatDate(vo.createdAt) }}
              </p>
              <div class="visitor_mod">
                <router-link
                  class="btns btn_vdelete"
                  :to="'/visitor/' + vo.id"
                  @click="modeToUpdate(vo.id)"
                >
                  수정
                </router-link>
                <p class="btns btn_vdelete" @click="deleteVisitorList(vo.id)">
                  삭제
                </p>
              </div>
            </div>
          </div>
        </article>
      </div>
      <div class="btn_write_wrapper">
        <button class="visitor_write btns btn_vwrite" @click="modeToCreate()">
          글쓰기
        </button>
      </div>
    </section>
  </main>
</template>

<script setup>
import { defineProps, defineEmits, ref, computed } from "vue";
import axios from "axios";

const props = defineProps(["id"]);
let visitorId;
const emit = defineEmits(["pageMode", "targetId"]);

const modeToCreate = () => {
  emit("pageMode", "create");
};

const modeToUpdate = (id) => {
  emit("pageMode", "update");
  emit("targetId", id);
};
const list = ref([]);
const fetchList = async () => {
  const response = await axios.get("http://localhost:8089/list");
  return response.data;
};
const setList = async () => {
  list.value = await fetchList();
};
setList().then(() => {
  console.log(list.value);
});
const formatDate = (dateString) => {
  const options = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  };
  const formattedDate = new Date(dateString).toLocaleDateString(
    "ko-KR",
    options
  );
  return formattedDate;
};

async function deleteVisitorList(visitorId) {
  const password = prompt("비밀번호를 입력하세요:");
  console.log("password:", password);
  if (password) {
    /* const VisitorId = visitorId.value; */
    const url = `http://localhost:8089/${visitorId}`;

    await axios
      .delete(url, {
        headers: { "Content-Type": "application/json" },
        data: { password: password },
      })
      .then((response) => {
        console.log(response.data);
        alert("게시글이 삭제되었습니다.");
        setList();
      })
      .catch((error) => {
        console.error(error);
        alert("비밀번호가 일치하지 않습니다.");
      });
  }
}
</script>

<style scoped>
@import "@/assets/visitor.css";
</style>
