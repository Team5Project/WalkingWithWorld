<template>
  <hr class="header_hr">
    <div class="all">
        <div class="title">
            <h1>리뷰 등록</h1><br>
        </div>
        <div class="wrapper">
            <div class="left">
                <th:block th:if="${walkingPaths != null}">
                    <div class="walking-path">
                        <img th:if="${#lists.size(walkingPaths.getPhotosList())>0}"
                            th:src="@{|/ex_images/${walkingPaths.getPhotosList().get(0).getImgName()}|}" alt="" />
                        <span th:text="${walkingPaths.getTitle()}"></span>
                        <th:block th:if="${#lists.size(walkingPaths.getMapList())>0}">
                            <span th:text="${walkingPaths.getMapList().get(0).getDistance()}"></span>
                        </th:block>
                        <span th:text="${walkingPaths.getAddr()}"></span>
                    </div>
                </th:block>
            </div>

            <div class="review_form">
                <div class="form">
                  <form method="post" th:action="@{|/reviews/${walkingPaths.getId()}/write|}" enctype="multipart/form-data">
                          <section class="write">
                            <span>별점</span> <br>
                            <span value="5">★★★★★</span><br>
                            <span>본문</span> <br>
                            <textarea name="content" rows="10" cols="60" wrap="soft" style="resize: none; padding: 10px"
                                required></textarea><br>
                            <span>사진(5개 제한)</span><br>
                            <section class="img-area">
                                <div id="review_image_container" class="image_container">
                                </div>
                                <div class="filebox">
                                    <label for="file">+</label>
                                    <input id="file" type="file" name="files" onchange="setThumbnail(event)" multiple />
                                </div>
                            </section>
                    </section>
                    <section class="submit">
                        <input type="submit" value="저장" class="btn-review">
                        <button class="btn-ref"><a th:href="@{/walking-path/{id}(id=${walkingPaths.getId()})}">뒤로가기</a></button>
                    </section>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
  @import "../assets/review_write_form.css";
</style>  