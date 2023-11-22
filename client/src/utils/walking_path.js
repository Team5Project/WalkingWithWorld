//-----------------------------------------------------
//슬라이더 스크립트
//-----------------------------------------------------

// 양방향 슬라이더
const minTime = document.getElementById("minTime");
const maxTime = document.getElementById("maxTime");
const timeSlide = document.querySelectorAll(".cat_time_range>input");

for (let ts of timeSlide) {
  ts.addEventListener("change", () => {
    set();
  });
}

set();

function set() {
  let minTimeValue = minTime.value;
  let maxTimeValue = maxTime.value;
  document.getElementById("showtime").innerText =
    "최소 " +
    Math.floor(minTimeValue / 60) +
    "시간" +
    (minTimeValue % 60) +
    "분 ~ " +
    Math.floor(maxTimeValue / 60) +
    "시간" +
    (maxTimeValue % 60) +
    "분";
}

const thumbLeft = document.querySelector(".slider > .thumb.left");
const thumbRight = document.querySelector(".slider > .thumb.right");
const range = document.querySelector(".slider > .range");

const setMin = () => {
  const _this = minTime;
  const [min, max] = [parseInt(_this.min), parseInt(_this.max)];
  _this.value = Math.min(parseInt(_this.value), parseInt(maxTime.value) - 1);
  const percent = ((_this.value - min) / (max - min)) * 100;
  thumbLeft.style.left = percent + "%";
  range.style.left = percent + "%";
};
const setMax = () => {
  const _this = maxTime;
  const [min, max] = [parseInt(_this.min), parseInt(_this.max)];
  _this.value = Math.max(parseInt(_this.value), parseInt(minTime.value) + 1);
  const percent = ((_this.value - min) / (max - min)) * 100;
  thumbRight.style.right = 100 - percent + "%";
  range.style.right = 100 - percent + "%";
};

minTime.addEventListener("input", setMin);
maxTime.addEventListener("input", setMax);
// 거리 슬라이더
const minDistance = document.getElementById("minDistance");
const maxDistance = document.getElementById("maxDistance");
const distSlide = document.querySelectorAll(".cat_distance_range>input");

for (let ds of distSlide) {
  ds.addEventListener("change", () => {
    setDistance();
  });
}

setDistance();

function setDistance() {
  let minDistanceValue = minDistance.value;
  let maxDistanceValue = maxDistance.value;
  document.getElementById("show-distance").innerText =
    "최소 " +
    minDistanceValue / 1000 +
    "Km ~ " +
    maxDistanceValue / 1000 +
    "Km";
}

const distanceThumbLeft = document.querySelector(
  ".distance-slider > .thumb.left"
);
const distanceThumbRight = document.querySelector(
  ".distance-slider > .thumb.right"
);
const distanceRange = document.querySelector(".distance-slider > .range");

const setMinDistance = () => {
  const _this = minDistance;
  const [min, max] = [parseInt(_this.min), parseInt(_this.max)];
  _this.value = Math.min(
    parseInt(_this.value),
    parseInt(maxDistance.value) - 1
  );
  const percent = ((_this.value - min) / (max - min)) * 100;
  distanceThumbLeft.style.left = percent + "%";
  distanceRange.style.left = percent + "%";
};
const setMaxDistance = () => {
  const _this = maxDistance;
  const [min, max] = [parseInt(_this.min), parseInt(_this.max)];
  _this.value = Math.max(
    parseInt(_this.value),
    parseInt(minDistance.value) + 1
  );
  const percent = ((_this.value - min) / (max - min)) * 100;
  distanceThumbRight.style.right = 100 - percent + "%";
  distanceRange.style.right = 100 - percent + "%";
};

minDistance.addEventListener("input", setMinDistance);
maxDistance.addEventListener("input", setMaxDistance);

//-----------------------------------------------------
//체크박스 컨트롤 스크립트
//-----------------------------------------------------

// let checkBoxes = document.querySelectorAll('.location_check');
// let iconAll = document.querySelector(".check_all_i");
// let paraAll = document.querySelector("#location_check_all");
// let switchAll = false;

// paraAll.addEventListener("click", () => {
//     checkBoxes.forEach(box => {
//         if (switchAll == false) {
//             box.checked = false;
//             console.log(box.checked);
//             let checkIcon = document.querySelector(`.location_name[for="${box.id}"] i`);
//             checkIcon.style.color = '';
//         } else {
//             box.checked = true;
//             console.log(box.checked);
//             let checkIcon = document.querySelector(`.location_name[for="${box.id}"] i`);
//             checkIcon.style.color = '#97bf04';
//         }
//     })
//     if (switchAll == false) {
//         switchAll = true;
//         iconAll.style.color = '';
//     } else {
//         switchAll = false;
//         iconAll.style.color = '#97bf04';
//     }
// })

// checkBoxes.forEach(checkBox => {
//     checkBox.addEventListener("change", function () {
//         let checkAll = true;
//         checkBoxes.forEach(cb => {
//             if (!cb.checked) {
//                 checkAll = false;
//                 return;
//             }
//         });
//         if (checkAll == true) {
//             iconAll.style.color = '#97bf04';
//         } else {
//             iconAll.style.color = '';
//         }

//         if (checkBox.checked) {
//             let checkIcon = document.querySelector(`.location_name[for="${checkBox.id}"] i`);
//             checkIcon.style.color = '#97bf04';
//         } else {
//             let checkIcon = document.querySelector(`.location_name[for="${checkBox.id}"] i`);
//             checkIcon.style.color = '';
//         }
//     })
// })

//-----------------------------------------------------
//구 ajax 조건 리스트 스크립트
//-----------------------------------------------------

function searchWalkingPath(keyword) {
  var SearchDTO = new Object();
  var selected = document.querySelectorAll('input[name="location"]:checked');
  var selectedValue = [];
  for (let i = 0; i < selected.length; i++) {
    selectedValue.push(selected[i].value);
  }
  SearchDTO.location = selectedValue;
  SearchDTO.minTime = document.getElementById("minTime").value;
  SearchDTO.maxTime = document.getElementById("maxTime").value;
  SearchDTO.minDistance = document.getElementById("minDistance").value;
  SearchDTO.maxDistance = document.getElementById("maxDistance").value;
  fetch("/walking-path/condition/" + keyword, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({
      location: SearchDTO.location,
      minTime: SearchDTO.minTime,
      maxTime: SearchDTO.maxTime,
      minDistance: SearchDTO.minDistance,
      maxDistance: SearchDTO.maxDistance,
    }),
  })
    .then((response) => response.text())
    .then((data) => {
      document.getElementById("walking-path").innerHTML = `${data}`;
    });
}
