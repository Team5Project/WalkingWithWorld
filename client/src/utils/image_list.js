let imgRoot = document.querySelectorAll(".images")
console.log(imgRoot);
imgRoot.forEach(function (ir) {
    let imgList = ir.querySelectorAll(".img_list img");

    imgList.forEach(function (img) {
        img.addEventListener("click", (e) => {
            ir.querySelector(".viewer img").src = img.src;
        });
    });
});