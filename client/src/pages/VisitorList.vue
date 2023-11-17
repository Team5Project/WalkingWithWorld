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
                <p
                  class="btns btn_vdelete"
                  @click="deleteVisitorList(visitorId)"
                >
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

const deleteVisitorList = (visitorId) => {
  const password = prompt("비밀번호를 입력하세요:");

  if (password) {
    // 삭제 API 엔드포인트가 "http://localhost:8089/delete"로 가정됩니다.
    const VisitorId = visitorId.value;
    const url = `http://localhost:8089/${visitorId}`;
    const data = {
      id: VisitorId,
      password: password,
    };
    axios
      .delete(url, data, {})
      .then((response) => {
        // 성공적인 삭제 처리 (예: 목록에서 삭제된 항목 제거)
        console.log(response.data);
        // 삭제 후 목록을 새로고침하려면 setList()를 호출할 수 있습니다.
        setList();
      })
      .catch((error) => {
        // 에러 처리 (예: 비밀번호가 잘못된 경우, 서버 오류)
        console.error(error);
      });
  }
};
</script>

<style scoped>
@import "@/assets/visitor.css";
</style>
