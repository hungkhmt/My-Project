const allSideMenu = document.querySelectorAll("#sidebar .side-menu.top li a");
// Lấy tham chiếu đến các phần tử DOM
var nhanvienTab = document.getElementById("nhanvien");
var sanphamTab = document.getElementById("sanpham");
var chinhanhTab = document.getElementById("chinhanh");
var quanlyTab = document.getElementById("quanly");
var searchInput = document.getElementById("search-input");

$(function () {
  if (!isLogin()) {
    // redirect to login page
    window.location.replace("http://127.0.0.1:5501/html/login/login.html");
    return;
  }
  document.getElementById("userFullName").innerText =
    storage.getItem("FULL_NAME");
});

allSideMenu.forEach((item) => {
  const li = item.parentElement;

  item.addEventListener("click", function () {
    allSideMenu.forEach((i) => {
      i.parentElement.classList.remove("active");
    });
    li.classList.add("active");
  });
});

//TOGGER SIDEBAR
const menuBar = document.querySelector("#content nav .bx.bx-menu");
const sidebar = document.getElementById("sidebar");

menuBar.addEventListener("click", function () {
  sidebar.classList.toggle("hide");
});

const searchButton = document.querySelector(
  "#content nav form .form-input button"
);
const searchButtonIcon = document.querySelector(
  "#content nav form .form-input button .bx"
);
const searchForm = document.querySelector("#content nav form");

searchButton.addEventListener("click", function (e) {
  if (window.innerWidth < 576) {
    e.preventDefault();
    searchForm.classList.toggle("show");
    if (searchForm.classList.contains("show")) {
      searchButtonIcon.classList.replace("bx-search", "bx-x");
    } else {
      searchButtonIcon.classList.replace("bx-x", "bx-search");
    }
  }
});

if (window.innerWidth < 768) {
  sidebar.classList.add("hide");
} else if (window.innerWidth < 576) {
  searchButtonIcon.classList.replace("bx-x", "bx-search");
  searchForm.classList.remove("show");
}

window.addEventListener("resize", function () {
  if (this.innerWidth > 576) {
    searchButtonIcon.classList.replace("bx-x", "bx-search");
    searchForm.classList.remove("show");
  }
});

function viewHomePage() {
  $(".main_content").load("../../html/homepage/tt.html", function () {
    // buildSanPhamTable();
    document.getElementById("userFullName").innerText =
      storage.getItem("FULL_NAME");
  });
}

nhanvienTab.addEventListener("click", function () {
  searchInput.placeholder = "Search for Nhan vien";
  setupNhanVienSearchEvent();
  searchButton.addEventListener("click", function () {
    buildNhanVienTable();
  });
});

sanphamTab.addEventListener("click", function () {
  searchInput.placeholder = "Search for San pham";
  setupSanPhamSearchEvent();
  searchButton.addEventListener("click", function () {
    buildSanPhamTable();
  });
});

chinhanhTab.addEventListener("click", function () {
  searchInput.placeholder = "Search for Chi nhanh";
});

quanlyTab.addEventListener("click", function () {
  searchInput.placeholder = "Search for Quan ly";
  setupQuanLySearchEvent();
  searchButton.addEventListener("click", function () {
    buildQuanLyTable();
  });
});

function logout() {
  storage.removeItem("ID");
  storage.removeItem("USERNAME");
  storage.removeItem("PASSWORD");
  window.location.replace("http://127.0.0.1:5501/html/login/login.html");
}

function isLogin() {
  if (storage.getItem("ID")) {
    return true;
  }
  return false;
}
