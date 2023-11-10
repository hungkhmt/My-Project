function viewSanPhamPage() {
  $(".main_content").load("../../html/sanpham/sanpham.html", function () {
    buildSanPhamTable();
  });
}

function buildSanPhamTable() {
  $("#sanpham_table tbody").empty();
  getSanPhamList();
}

// paging
var SanPhamCurrentPage = 1;
var size = 10;
var tongSanPham = 0;

var sortField = "id";
var isAsc = true;

var sanPhams = [];
function getSanPhamList() {
  var url = "http://localhost:8080/api/v1/SanPham";

  url += "?pageNumber=" + SanPhamCurrentPage + "&size=" + size;

  // sorting
  url += "&sort=" + sortField + "," + (isAsc ? "asc" : "desc");

  // search
  var search = document.getElementById("search-input").value;
  if (search) {
    url += "&search=" + search;
  }

  // filter
  var maxDonGia = document.getElementById("filter-max-dongia-select").value;
  if (maxDonGia) {
    url += "&maxDonGia=" + maxDonGia;
  }

  var minDonGia = document.getElementById("filter-min-dongia-select").value;
  if (minDonGia) {
    url += "&minDonGia=" + minDonGia;
  }

  //1. GET: lấy danh sách các SanPham
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
    success: function (sanphamArr) {
      $("#sanpham-tab_body").empty();
      sanPhams = sanphamArr.content;
      // fillSanPhamToTable();
      sanPhams.forEach(function (element) {
        $("#sanpham-tab_body").append(
          "<tr>" +
            `<td><input type='checkbox' name='check-input' value='${element.id}'></td>` +
            "<td>" +
            element.id +
            "</td>" +
            '<td style="text-align: left;">' +
            element.tenSP +
            "</td>" +
            "<td>" +
            element.donGia +
            "</td>" +
            "<td>" +
            element.soLuong +
            "</td>" +
            "<td>" +
            element.ngayNhap +
            "</td>" +
            "<td>" +
            element.ngayHetHan +
            "</td>" +
            "<td>" +
            `"<a href='#' id='btnEdit' onclick='opendUpdateSanPhamModal(${element.id})'"` +
            "return false;'>Sửa</a>" +
            `" <a href='#' id='btnDelete' onclick='openDeleteSanPhamModal(${element.id})'"` +
            "return false;'>Xóa</a>" +
            "</td>" +
            "</tr>"
        );
      });
      tongSanPham = sanphamArr.totalPages;
      if (tongSanPham >= 2) {
        fillSanPhamPaging(sanphamArr.numberOfElements, sanphamArr.totalPages);
        createSanPhamPagination(sanphamArr.totalPages);
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

function createSanPhamPagination(totalPages) {
  var totalSanPhams = totalPages;
  $(".pagination").empty();
  $(".pagination").append(
    '<li class="page-item" id="above-SanPham" onclick="firsySanPhamPage()" style = "margin-right: 15px;">First</li>' +
      '<li class="page-item" id="above-SanPham" onclick="lastSanPhamPage()" style = "margin-left = 3px;">Last</li>'
  );

  while (totalSanPhams > 0) {
    $("#above-SanPham").after(
      '<li class="page-item">' + totalSanPhams + "</li>"
    );

    totalSanPhams -= 1;
  }

  $(".page-item").click(function () {
    const elem = $(this).text();
    if (elem != "First" && elem != "Last") {
      changeSanPhamPage(Number(elem));
    }
  });
}

// paging
function fillSanPhamPaging(currentSize, totalPages) {
  // text
  document.getElementById("SanPham-page-info").innerHTML =
    currentSize +
    (currentSize > 1 ? " records " : " record ") +
    SanPhamCurrentPage +
    " of " +
    totalPages;
}

function firsySanPhamPage() {
  changeSanPhamPage(1);
}

function lastSanPhamPage() {
  changeSanPhamPage(tongSanPham);
}

function changeSanPhamPage(page) {
  SanPhamCurrentPage = page;
  buildSanPhamTable();
}

function resetSearch() {
  search = "";
}

function openSanPhamModal() {
  $("#object-info").modal("show");
}

// open create modal
function openAddSanPhamModal() {
  openSanPhamModal();
  resetAddSanPhamForm();
}

function hideSanPhamModal() {
  $("#object-info").modal("hide");
}

function resetAddSanPhamForm() {
  $("#object-body .modal input").val("");
}

function createNewSanPham() {
  let tenSP = document.getElementById("sanpham-name").value;
  let donGia = document.getElementById("sanpham-price").value;
  let soLuong = document.getElementById("sanpham-quantity").value;
  let ngayNhap = document.getElementById("sanpham-date").value;
  let ngayHetHan = document.getElementById("sanpham-exp").value;
  let SanPham = {
    tenSP: tenSP,
    donGia: donGia,
    soLuong: soLuong,
    ngayNhap: ngayNhap,
    ngayHetHan: ngayHetHan,
  };

  let isOk = true;

  let validMessage = "";
  if (tenSP.length < 2 || tenSP.length > 30) {
    validMessage += "username's length is not valid\n";
  }
  if (donGia <= 0) {
    validMessage += "first name's length is not valid\n";
  }
  if (soLuong <= 0) {
    validMessage += "last name's length is not valid\n";
  }

  if (validMessage.length != 0) {
    alert(validMessage);
    // console.log(validMessage);
    return;
  } else {
    isOk = true;
  }
  let isCheck = true;
  if (isOk == true) {
    $.ajax({
      url: "http://localhost:8080/api/v1/SanPham",
      type: "POST",
      data: JSON.stringify(SanPham), // body
      contentType: "application/json",
      async: false,
      beforeSend: function (xhr) {
        xhr.setRequestHeader(
          "Authorization",
          "Basic " +
            btoa(
              storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")
            )
        );
      },
      success: function (result) {
        isCheck = true;
      },
      // },
      // error: function(xhr){
      //     isCheck = false;
      //     console.log(xhr.responseJSON.message);
      //     const errs = xhr.responseJSON.error;
      //     let errorList = "";
      //     for (const key in errs) {
      //         if (Object.hasOwnProperty.call(errs, key)) {
      //             const element = errs[key];
      //             errorList += element  + "\n";
      //         }
      //     }
      //      alert(errorList);
      // }
    });
  }

  if (isCheck == true) {
    hideSanPhamModal();
    buildSanPhamTable();
  }
}

var SanPhamTemp;

// hiện modal update SanPham
function opendUpdateSanPhamModal(SanPhamID) {
  $("#object-info form h2").html("Update SanPham");
  openSanPhamModal();
  resetAddSanPhamForm();
  // gọi api đổ thông tin vào SanPham
  $.ajax({
    url: "http://localhost:8080/api/v1/SanPham" + "/" + SanPhamID,
    type: "GET",
    async: false,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Basic " +
          btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD"))
      );
    },
    success: function (result) {
      SanPhamTemp = result;
      //fill data
      document.getElementById("sanpham-id").value = result.id;
      document.getElementById("sanpham-name").value = result.tenSP;
      document.getElementById("sanpham-price").value = result.donGia;
      document.getElementById("sanpham-quantity").value = result.soLuong;
      document.getElementById("sanpham-date").value = result.ngayNhap;
      document.getElementById("sanpham-exp").value = result.ngayHetHan;
    },
  });
}

// update SanPham
function updateSanPham(SanPhamID) {
  var tenSP = document.getElementById("sanpham-name").value;
  var donGia = document.getElementById("sanpham-price").value;
  var soLuong = document.getElementById("sanpham-quantity").value;
  var ngayNhap = document.getElementById("sanpham-date").value;
  var ngayHetHan = document.getElementById("sanpham-exp").value;

  SanPhamTemp.tenSP = tenSP;
  SanPhamTemp.donGia = donGia;
  SanPhamTemp.soLuong = soLuong;
  SanPhamTemp.ngayNhap = ngayNhap;
  SanPhamTemp.ngayHetHan = ngayHetHan;

  console.log(SanPhamTemp);

  let isOk = true;
  // gọi api thêm mới SanPham
  $.ajax({
    url: "http://localhost:8080/api/v1/SanPham" + "/" + SanPhamID,
    type: "PUT",
    data: JSON.stringify(SanPhamTemp), // body
    contentType: "application/json",
    async: false,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Basic " +
          btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD"))
      );
    },
    success: function (result) {
      isOk = true;
    },
    // error: function(xhr){
    //     isOk = false;
    //     console.log(xhr.responseJSON.message);
    //     const errs = xhr.responseJSON.error;
    //     let errorList = "";
    //     for (const key in errs) {
    //         if (Object.hasOwnProperty.call(errs, key)) {
    //             const element = errs[key];
    //             errorList += element  + "\n";
    //         }
    //     }
    //      alert(errorList);
    // }
  });
  if (isOk == true) {
    hideSanPhamModal();
  }

  getSanPhamList();
}

function saveSanPham() {
  let sanphamID = document.getElementById("sanpham-id").value;
  console.log(sanphamID);

  if (sanphamID == undefined || sanphamID == "") {
    createNewSanPham();
  } else {
    updateSanPham(sanphamID);
  }
  // showAlertSuccess();
}

// hiện modal xóa sanpham
function openDeleteSanPhamModal(sanphamID) {
  $("#delete-sanpham-modal").modal("show");
  document.getElementById("delete-sanpham-id").value = sanphamID;
}

// ẩn modal xóa sanpham
function hideDeleteSanPhamModal() {
  $("#delete-sanpham-modal").modal("hide");
}

function deleteSanPham() {
  let id = document.getElementById("delete-sanpham-id").value;
  $.ajax({
    url: "http://localhost:8080/api/v1/SanPham" + "/" + id,
    type: "DELETE",
    async: false,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Basic " +
          btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD"))
      );
    },
    success: function (result) {},
  });
  hideDeleteSanPhamModal();
  getSanPhamList();
}

function fillSanPhamSorting() {
  var sortTypeClazz = isAsc ? "fa-sort-up" : "fa-sort-down";
  var defaultSortType = "fa-sort";

  switch (sortField) {
    case "id":
      changeIconSort("sp-sort-id", sortTypeClazz);
      changeIconSort("sp-sort-name", defaultSortType);
      changeIconSort("sp-sort-dongia", defaultSortType);
      changeIconSort("sp-sort-soluong", defaultSortType);
      changeIconSort("sp-sort-date", defaultSortType);
      changeIconSort("sp-sort-exp", defaultSortType);
      break;
    case "tenSP":
      changeIconSort("sp-sort-name", sortTypeClazz);
      changeIconSort("sp-sort-id", defaultSortType);
      changeIconSort("sp-sort-dongia", defaultSortType);
      changeIconSort("sp-sort-soluong", defaultSortType);
      changeIconSort("sp-sort-date", defaultSortType);
      changeIconSort("sp-sort-exp", defaultSortType);
      break;
    case "donGia":
      changeIconSort("sp-sort-name", defaultSortType);
      changeIconSort("sp-sort-id", defaultSortType);
      changeIconSort("sp-sort-dongia", sortTypeClazz);
      changeIconSort("sp-sort-soluong", defaultSortType);
      changeIconSort("sp-sort-date", defaultSortType);
      changeIconSort("sp-sort-exp", defaultSortType);
      break;

    case "soLuong":
      changeIconSort("sp-sort-name", defaultSortType);
      changeIconSort("sp-sort-id", defaultSortType);
      changeIconSort("sp-sort-dongia", defaultSortType);
      changeIconSort("sp-sort-soluong", sortTypeClazz);
      changeIconSort("sp-sort-date", defaultSortType);
      changeIconSort("sp-sort-exp", defaultSortType);
      break;
    case "ngayNhap":
      changeIconSort("sp-sort-name", defaultSortType);
      changeIconSort("sp-sort-id", defaultSortType);
      changeIconSort("sp-sort-dongia", defaultSortType);
      changeIconSort("sp-sort-soluong", defaultSortType);
      changeIconSort("sp-sort-date", sortTypeClazz);
      changeIconSort("sp-sort-exp", defaultSortType);
      break;

    case "ngayHetHan":
      changeIconSort("sp-sort-name", defaultSortType);
      changeIconSort("sp-sort-id", defaultSortType);
      changeIconSort("sp-sort-dongia", defaultSortType);
      changeIconSort("sp-sort-soluong", defaultSortType);
      changeIconSort("sp-sort-date", defaultSortType);
      changeIconSort("sp-sort-exp", sortTypeClazz);
      break;
    // sort by id
    default:
      changeIconSort("sp-sort-name", defaultSortType);
      changeIconSort("sp-sort-dongia", defaultSortType);
      changeIconSort("sp-sort-soluong", defaultSortType);
      changeIconSort("sp-sort-date", defaultSortType);
      changeIconSort("sp-sort-exp", defaultSortType);
      break;
  }
}

function changeIconSort(id, sortTypeClazz) {
  //chuyen ve kieu sort default
  document.getElementById(id).classList.remove("fa-sort-up", "fa-sort-down");
  //kieu sorting ban dau
  document.getElementById(id).classList.add(sortTypeClazz);
}

function changeSanPhamSort(field) {
  if (field == sortField) {
    isAsc = !isAsc;
  } else {
    sortField = field;
    isAsc = true;
  }
  buildSanPhamTable();
}

function refreshSanPham() {
  // refresh paging
  SanPhamCurrentPage = 1;
  size = 10;

  // refresh sorting
  sortField = "id";
  isAsc = true;

  // refresh filter
  $("#filter-min-dongia-select").val("").trigger("change");
  $("#filter-max-dongia-select").val("").trigger("change");

  // refresh search
  document.getElementById("search-input").value = "";

  // Get API
  buildSanPhamTable();
}

function setupSanPhamSearchEvent() {
  $("#search-input").on("keyup", function (event) {
    // enter key code = 13
    if (event.keyCode === 13) {
      buildSanPhamTable();
    }
  });
}

// filter
function filterSanPham() {
  buildSanPhamTable();
}
