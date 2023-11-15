<template>
    <hr class="header_hr">
    <main>
    <section class="detail_header">
        <div class="detail_title">
            <h2 th:text="${walkingPaths.title}">타이틀 미입력</h2>
            <th:block th:if="${session.auth !=null && session.auth.getName()==walkingPaths.getCreatedBy()}">
                <div class="up_del">
                    <a th:href="|/walking-path/modify/${walkingPaths.id}|"><span class="info_modi">수정</span></a>
                    <span th:onclick="|deleteWalkingPath(${walkingPaths.id})|" class="info_del">삭제</span>
                </div>
            </th:block>
        </div>
        <p>
            <span class="star_score">★★★★★</span>
            <i></i>리뷰 <b>8</b>
            <i>|</i> 댓글 <b>22</b>
            <th:block th:if="${walkingPaths.mapList}">
                <th:block th:if="${walkingPaths.getMapList().get(0).getDistance()>1000}">
                    <i>|</i> 거리 <b
                        th:text="${#numbers.formatDecimal(walkingPaths.getMapList().get(0).getDistance()/1000,1,2)}">10</b>
                    Km
                </th:block>
                <th:block th:unless="${walkingPaths.getMapList().get(0).getDistance()>1000}">
                    <i>|</i> 거리 <b th:text="${walkingPaths.getMapList().get(0).getDistance()}">10</b> m
                </th:block>
                <th:block th:if="${walkingPaths.getMapList().get(0).getTime()>60}">
                    <i>|</i> 시간 <b th:text="${walkingPaths.getMapList().get(0).getTime()/60}">3</b> 시간 <b
                        th:text="${walkingPaths.getMapList().get(0).getTime()%60}"></b> 분
                </th:block>
                <th:block th:unless="${walkingPaths.getMapList().get(0).getTime()>60}">
                    <i>|</i> 시간 <b th:text="${walkingPaths.getMapList().get(0).getTime()}"></b> 분
                </th:block>
            </th:block>
        </p>
        <address th:text="${walkingPaths.addr}">주소 미입력</address>
    </section>
    <section class="infoAndcomments">
        <div class="iac_wrapper">
            <article class="detail_info">
                <aside class="images">
                    <figure class="viewer">
                        <img th:if="${walkingPaths.photosList}"
                             th:src="@{|/ex_images/${walkingPaths.photosList[0].imgName}|}"/>
                        <img th:unless="${walkingPaths.photosList}" th:src="@{/images/noimage.png}"/>
                    </figure>

                    <div class="img_list">
                        <figcaption class="thumb" th:if="${walkingPaths.photosList}"
                                    th:each=" wp : ${walkingPaths.photosList}">
                            <img th:src="@{|/ex_images/${wp.imgName}|}">
                        </figcaption>
                    </div>
                </aside>
                <div class="review_write">
                    <p>
                        이 산책로는 어땠나요?<br>
                        리뷰를 남겨주세요!
                    </p>
                    <div>

                        <a th:href="@{|/reviews/${walkingPaths.getId()}/write|}" class="btns btn_write_big">
                            리뷰작성
                        </a>
                    </div>
                </div>
            </article>
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
        </div>
    </section>
    <section class="map">
        <h3>상세 경로</h3>
        <h4 id="exist">게시자가 상세 경로를 입력하지 않았습니다.</h4>
        <div class="map_wrap">
            <div class="readMap" id="map"></div>
        </div>
    </section>
    <section class="review">
        <h3>리뷰</h3>
        <div class="review_container">
            <i class="rv-chav left fa-solid fa-circle-chevron-left"></i>
            <i class="rv-chav right fa-solid fa-circle-chevron-right"></i>
            <div class="review_none">
                <p>아직 리뷰가 작성되지 않았습니다.</p>
                <p>첫 리뷰를 작성해 주세요!</p>
                <a th:href="@{|/reviews/${walkingPaths.getId()}/write|}" class="btns btn_write_big">
                    리뷰작성
                </a>
            </div>
        </div>
    </section>
</main>
</template>

<script>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';

export default {
  components: {
    Header,
    Footer,
  },
}
</script>
<style scoped>
    @import "../assets/walking_path_detail.css";
</style>