<template>
  <div class="comments_wrapper">
    
    <div class="comments_content">
      <!--                    여기에 댓글이 들어감-->
    </div>
    <form action="" method="post" id="comments_write" onsubmit="return false;">
      <input type="text" id="comment_content" class="comments_input" name="content"
              placeholder="댓글을 입력해주세요" required/>
      <input type="button" class="btns btn_comments" th:if="${session.auth !=null}"
              th:onclick="|writeComment(${walkingPaths.getId()})|" value="등록"/>
      <input type="button" class="btns btn_comments" th:if="${session.auth == null}"
              th:onclick="|location.href='@{/login?redirectURL=/walking-path/{id}(id=${walkingPaths.getId()})}'|"
              value="등록">
    </form>
  </div>
</template>

<script setup>

</script>
<style scoped>
  @import "@/assets/walking_path_detail.css";
</style>