function viewChiNhanhPage() {
  $(".main_content").load("../../html/chinhanh/chinhanh.html", function () {
    buildChiNhanhTable();
  });
}

function buildChiNhanhTable() {
  $("#sanpham_table tbody").empty();
  getChiNhanhList();
}

// paging
var ChiNhanhCurrentPage = 1;
var size = 10;
var tongChiNhanh = 0;

var ChiNhanhs = [];
function getChiNhanhList() {
  var url = "http://localhost:8080/api/v1/ChiNhanh";

  url += "?pageNumber=" + ChiNhanhCurrentPage + "&size=" + size;

  //1. GET: lấy danh sách các account
  $.ajax({
    url: url,
    type: "GET",
    contentType: "application/json",
    dataType: "json", // datatype return
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Basic " +
          btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD"))
      );
    },
    success: function (ChiNhanhArr) {
      $("#sanpham-tab_body").empty();
      ChiNhanhs = ChiNhanhArr.content;
      fillChiNhanhToTable();
      tongChiNhanh = ChiNhanhArr.totalPages;
      if (tongChiNhanh >= 2) {
        fillChiNhanhPaging(
          ChiNhanhArr.numberOfElements,
          ChiNhanhArr.totalPages
        );
        createChiNhanhPagination(ChiNhanhArr.totalPages);
      }
    },
    error(jqXHR, textStatus, errorThrown) {
      if (jqXHR.status == 403) {
        window.location.href =
          "http://127.0.0.1:5501/html/homepage/forbidden.html";
      } else {
        console.log(jqXHR);
        console.log(textStatus);
        console.log(errorThrown);
      }
    },
  });
}

function fillChiNhanhToTable() {
  ChiNhanhs.forEach(function (element) {
    $("#sanpham-tab_body").append(
      "<tr>" +
        "<td>" +
        '<input id="checkbox-" type="checkbox"/>' +
        "</td>" +
        "<td>" +
        element.id +
        "</td>" +
        "<td>" +
        element.diaChi +
        "</td>" +
        "<td>" +
        element.soLuongNhanVien +
        "</td>" +
        "<td>" +
        element.soLuongQuanLy +
        "</td>" +
        "<td>" +
        "<a href='#' id='btnEdit' onclick='editAccount(" +
        element.id +
        "); return false;'>Sửa</a>" +
        " <a href='#' id='btnDelete' onclick='deleteAccount(" +
        element.id +
        "); return false;'>Xóa</a>" +
        "</td>" +
        "</tr>"
    );
  });
}

function createChiNhanhPagination(totalPages) {
  var totalChiNhanhs = totalPages;
  $(".pagination").empty();
  $(".pagination").append(
    '<li class="page-item" id="above-SanPham" onclick="firsyChiNhanhPage()" style = "margin-right: 15px;">First</li>' +
      '<li class="page-item" id="above-SanPham" onclick="lastChiNhanhPage()" style = "margin-left = 3px;">Last</li>'
  );

  while (totalChiNhanhs > 0) {
    $("#above-SanPham").after(
      '<li class="page-item">' + totalChiNhanhs + "</li>"
    );

    totalChiNhanhs -= 1;
  }

  $(".page-item").click(function () {
    const elem = $(this).text();
    if (elem != "First" && elem != "Last") {
      changeChiNhanhPage(Number(elem));
    }
  });
}

// paging
function fillChiNhanhPaging(currentSize, totalPages) {
  // text
  document.getElementById("SanPham-page-info").innerHTML =
    currentSize +
    (currentSize > 1 ? " records " : " record ") +
    ChiNhanhCurrentPage +
    " of " +
    totalPages;
}

function firsyChiNhanhPage() {
  changeChiNhanhPage(1);
}

function lastChiNhanhPage() {
  changeChiNhanhPage(tongChiNhanh);
}

function changeChiNhanhPage(page) {
  ChiNhanhCurrentPage = page;
  buildChiNhanhTable();
}
