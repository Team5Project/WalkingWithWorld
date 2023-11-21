<template>
  <main>
    <div class="visitors_write_header">게시글 수정</div>
    <section>
      <div class="visitors_write_wrap">
        <aside class="visitor_logo">
          <img th:src="@{/images/logo-big.png}" />
        </aside>
        <article>
          <div class="insert_visitor">
            <form method="post" action="/visitor">
              <p class="inline_insert">
                <label for="visitor_name_input">작성자명</label>
                <input
                  v-model="vname"
                  type="text"
                  name="name"
                  placeholder="작성자명"
                  id="visitor_name_input"
                  maxlength="10"
                  required
                /><br />
              </p>
              <p class="inline_insert">
                <label for="visitor_password_input">비밀번호</label>
                <input
                  v-model="vpassword"
                  type="password"
                  name="password"
                  placeholder="비밀번호"
                  id="visitor_password_input"
                  maxlength="10"
                  required
                /><br />
              </p>
              <p class="inline_textarea">
                <label for="visitor_content_input">내용</label>
                <textarea
                  v-model="vcontent"
                  name="content"
                  id="visitor_content_input"
                  required
                ></textarea>
              </p>
              <div class="visitor_mod">
                <button class="btns btn_vback" @click="modeToList">
                  이전화면
                </button>

                <input type="reset" value="초기화" class="btns btn_vreset" />
                <input
                  type="button"
                  value="등록"
                  class="btns btn_vsubmit"
                  @click="submitForm()"
                />
                <input
                  type="button"
                  value="수정"
                  class="btns btn_vsubmit"
                  @click="submitModifyForm(visitorId)"
                />
              </div>
            </form>
          </div>
        </article>
      </div>
    </section>
  </main>
</template>

<script setup>
import { defineProps, defineEmits, ref, onMounted } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
const emit = defineEmits(["pageMode"]);
const vname = ref("");
const vpassword = ref("");
const vcontent = ref("");
const props = defineProps(["data", "id"]);
const visitorId = props.id;
const modeToList = () => {
  emit("pageMode", "list");
};

for (let prop of props.data) {
  if (prop.id == visitorId) {
    console.log(prop);
    vname.value = prop.name;
    vcontent.value = prop.content;
  }
}

/* const visitorId = computed(() => props.id); */

// async function submitModifyForm(visitorId) {
//   const url = `http://localhost:8089/visitor/${visitorId.value}`;
//   const data = {
//     name: vname.value,
//     password: vpassword.value,
//     content: vcontent.value,
//   };

//   await axios
//     .put(url, data, {
//       headers: { "Content-Type": "application/json" },
//     })
//     .then(function (response) {
//       console.log(response);
//       if (response.status == 200) {
//         alert("게시글이 수정되었습니다.");
//         modeToList();
//       }
//     })
//     .catch(function (error) {
//       console.log("error: ", error);
//     });
// }
</script>

<style scoped>
@import "@/assets/insert_visitors.css";
</style>
