<template>
  <div class="comments_wrapper">
    
    <div class="comments_content">
      <!--                    여기에 댓글이 들어감-->
      <article class="comments" th:each=" comment : ${commentList}">
        <div class="comments_info">
          <div class="review_info">
            <div class="detail_member">
                <span href="" class="profile_image"></span>
                <span class="name" th:text="${comment.getName()}">이름 입력란</span>
                <span class="date" th:text="${#temporals.format(comment.created_at,'yyyy-MM-dd')}">날짜입력란</span>
            </div>
            <th:block th:if="${session.auth!=null && session.auth.getName() == comment.getName()}">
                <div class="up_del" style="visibility:visible;">
                    <span id="update" class="info_modi" th:onclick="|UpdateAct(${comment.id})|">수정</span>
                    <span id="delete" class="info_del" th:onclick="|deleteComment(${comment.id})|" th:value="${comment.walkingPathsId}">삭제</span>
                </div>
            </th:block>
          </div>
        </div>
            <p th:text="${comment.getContent()}">조용조용히 아이와 걸으며 산책하기 좋습니다.</p>
        </article>
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