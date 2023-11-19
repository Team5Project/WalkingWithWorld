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
                <p class="btns btn_vdelete" @click="deleteVisitorList(vo.id)">
                  삭제
                </p>
              </div>
            </div>
          </div>
        </article>
      </div>
      <div class="btn_write_wrapper">
        <button class="visitor_write btns btn_vwrite" @click="modeToModify">
          글쓰기
        </button>
      </div>
    </section>
  </main>
</template>

<script setup>
import { defineEmits, ref, computed } from "vue";
import axios from "axios";

const emit = defineEmits(["pageMode"]);
const modeToModify = () => {
  emit("pageMode", "modify");
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
  };
  const formattedDate = new Date(dateString).toLocaleDateString(
    "ko-KR",
    options
  );
  return formattedDate;
};

const props = defineProps(["id"]);
const visitorId = computed(() => props.id);
console.log(visitorId.value);

async function deleteVisitorList(visitorId) {
  const password = prompt("비밀번호를 입력하세요:");
  console.log("password:", password);
  if (password) {
    /* const VisitorId = visitorId.value; */
    const url = `http://localhost:8089/${visitorId}`;
    /* const data = {
      id: VisitorId,
      password: password,
    }; */
    await axios
      .delete(url, {
        headers: { "Content-Type": "application/json" },
        data: { password: password },
      })
      .then((response) => {
        console.log(response.data);
        setList();
      })
      .catch((error) => {
        console.error(error);
      });
  }
}
</script>

<style scoped>
@import "@/assets/visitor.css";
</style>
