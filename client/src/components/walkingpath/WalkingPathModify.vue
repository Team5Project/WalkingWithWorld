<template>  
    <div id="write-form">
        <h1>산책로 등록</h1>
        <form method="post" action="/walking-path" enctype="multipart/form-data">
            <div id="input-items">
                <div id="left-form">
                    <label>산책로 이름<br><input type="text" class="text-box" name="title" required></label><br>
                    <label>주소
                        <input type="button" class="button" onclick="searchAddr()" value="주소 찾기"><br>
                        <input type="text" name="addr" class="text-box" id="address" required><br>
                    </label><br>
                    <span>사진(5개 제한)</span><br>
                    <section class="img-area">
                        <div class="filebox">
                            <label for="file" class="fileImg">+</label>
                            <input id="file" type="file" name="files" onchange="setThumbnail(event)" multiple />
                        </div>
                        <div id="image_container" class="image_container"></div>
                    </section><br>
                    <label>
                        <input type="hidden" name="course" id="course">
                        <input type="hidden" name="time" id="time">
                        <input type="hidden" name="distance" id="distance">
                    </label>
                </div>
                <div id="line"></div>
                <div id="right-form">
                    <label>경로 그리기
                        <div class="map_wrap">
                            <div class="readMap" id="map"></div>
                        </div>
                    </label>
                </div>
                <div id="clear"></div>
            </div>
            <div id="buttons">
                <input type="submit" class="submit-button" value="확인">
                <a th:href="${referer}">&nbsp; 뒤로가기 &nbsp;</a>
            </div>
        </form>
    </div>
</template>

<script>
export default {
    name:'WalkingPathModify'
}
</script>
<style scoped>
  @import "@/assets/walking_path_write.css";
</style>