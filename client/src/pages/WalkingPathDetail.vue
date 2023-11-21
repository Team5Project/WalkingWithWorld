<template>
    <Header />
    <WalkingPathRead :id="WalkingPathId" v-if="compDetailMode === 'default'" @pageDetailMode="modeChange"/>
    <WalkingPathUpdate :id="WalkingPathId" v-if="compDetailMode === 'update'" @pageDetailMode="modeChange"/>
    <Footer />
</template>

<script setup>
import { computed, ref, onMounted } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import WalkingPathRead from '@/components/walkingpath/WalkingPathRead.vue';
import WalkingPathUpdate from '@/components/walkingpath/WalkingPathUpdate.vue';

const props = defineProps(['id']);
const WalkingPathId = computed(() => props.id);

const compDetailMode = ref('default');
const modeChange = (changed) => {
	compDetailMode.value = changed;
}

onMounted(()=> {
    if(window.kakao && window.kakao.maps) {
        loadMap();
    } else {
        loadScript();
    }
});
const removeWalkingPath = function() {
    if(confirm("삭제하시겠습니까?")) {
        axios.delete('http://localhost:8089/walking-path/' + getDetail.value.id, {
            headers: {
                'authorization' : bearer,
            },
        })
        .then(response => {
            if(response.status  == 205){
                alert("게시글이 삭제되었습니다.");
                router.push('/walking-path');
            }
        });
        console.log("왜일까");
    } else {
        alert("취소되었습니다.");
    }
};
const loadScript = function() {
    const script = document.createElement("script");
    script.src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d753200077e444dd40df1e458903dfd3&autoload=false";
    script.onload = () => window.kakao.maps.load(loadMap);
    document.head.appendChild(script);
};
const loadMap = function() {
    const mapContainer = document.getElementById('map'); // 지도를 표시할 div
    const mapOption = {
        center: new window.kakao.maps.LatLng(37.541, 126.986), // 지도의 중심좌표
        level: 6 // 지도의 확대 레벨
    };
    map.value = new window.kakao.maps.Map(mapContainer, mapOption); // 지도 생성
};
const drawLine = function () {
    // 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
    var linePath = [];
    mapAry.value.forEach(function (map) {
        linePath.push(new kakao.maps.LatLng(map.coordinateY, map.coordinateX));
    });

    // 지도에 표시할 선을 생성합니다
    var polyline = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께 입니다
        strokeColor: 'red', // 선의 색깔입니다
        strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid' // 선의 스타일입니다
    });

    // 지도에 선을 표시합니다
    polyline.setMap(map.value);
}

// ----------------------------------
// get
// ----------------------------------

const clickParam = () =>{
    router.push({
        path : "/"+WalkingPathId.value+"/reviews",
    })
}

const fetchDetail = async () => {
    const response = await axios.get(`http://localhost:8089/walking-path/${WalkingPathId.value}`);
    return response.data;
}
const setDetail = async () => {
    getDetail.value = await fetchDetail();
}

setDetail().then(() => {
    import('@/utils/image_list.js')
    console.log(getDetail.value);
    mapAry.value = getDetail.value.coordinateList;
    console.log(mapAry.value.length);
    if(mapAry.value.length > 0) {
        drawLine();
    }
})

</script>

<style scoped>
@import "@/assets/walking_path_detail.css";
</style>