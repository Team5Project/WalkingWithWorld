<template>
      <main>
        <div class="visitors_write_header">
            게시글 작성
        </div>
        <section>
            <div class="visitors_write_wrap">
                <aside class="visitor_logo">
                    <img th:src="@{/images/logo-big.png}">
                </aside>
                <article>
                    <div class="insert_visitor">
                        <form method="post" action="/insertVisitors">
                            <p class="inline_insert">
                                <label for="visitor_name_input">작성자명</label>
                                <input type="text" name="name" placeholder="작성자명" id="visitor_name_input" maxlength="10" required><br>
                            </p>
                            <p class="inline_insert">
                                <label for="visitor_password_input">비밀번호</label>
                                <input type="password" name="password" placeholder="비밀번호" id="visitor_password_input"
                                    maxlength="10" required><br>
                            </p>
                            <p class="inline_textarea">
                                <label for="visitor_content_input">내용</label>
                                <textarea name="content" id="visitor_content_input" required></textarea>
                            </p>
                            <div class="visitor_mod">
                                <a th:href="@{/visitorslist}" class="btns btn_vback">이전화면</a>
                                <input type="reset" value="초기화" class="btns btn_vreset">
                                <input type="submit" value="등록" class="btns btn_vsubmit">
                            </div>
                        </form>
                    </div>
                </article>
            </div>
        </section>
    </main>
</template>

<style scoped>
  @import "../assets/insert_visitors.css";
</style>