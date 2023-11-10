function viewNhanVienPage() {
  $(".main_content").load("../../html/nhanvien/nhanvien.html", function () {
    buildNhanVienTable();
  });
}

function buildNhanVienTable() {
  $("#sanpham_table tbody").empty();
  getNhanVienList();
}

// paging
var NhanVienCurrentPage = 1;
var size = 10;
var tongNhanVien = 0;

var sortField = "id";
var isAsc = true;

var nhanViens = [];
function getNhanVienList() {
  var url = "http://localhost:8080/api/v1/NhanVien";
  // paging
  url += "?pageNumber=" + NhanVienCurrentPage + "&size=" + size;

  // sorting
  url += "&sort=" + sortField + "," + (isAsc ? "asc" : "desc");

  // search
  var search = document.getElementById("search-input").value;
  if (search) {
    url += "&search=" + search;
  }
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
    success: function (nhanVienArr) {
      $("#sanpham-tab_body").empty();
      nhanViens = nhanVienArr.content;
      fillNhanVienToTable();
      tongNhanVien = nhanVienArr.totalPages;
      if (tongNhanVien >= 2) {
        fillNhanVienPaging(
          nhanVienArr.numberOfElements,
          nhanVienArr.totalPages
        );
        createNhanVienPagination(nhanVienArr.totalPages);
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

function fillNhanVienToTable() {
  nhanViens.forEach(function (element) {
    $("#sanpham-tab_body").append(
      "<tr>" +
        "<td>" +
        '<input id="checkbox-" type="checkbox"/>' +
        "</td>" +
        "<td>" +
        element.id +
        "</td>" +
        '<td style="text-align: left;">' +
        element.fullName +
        "</td>" +
        "<td>" +
        element.gioiTinh +
        "</td>" +
        "<td>" +
        element.namSinh +
        "</td>" +
        "<td>" +
        element.role +
        "</td>" +
        "<td>" +
        element.chiNhanhID +
        "</td>" +
        "<td>" +
        `"<a href='#' id='btnEdit' onclick='opendUpdateNhanVienModal(${element.id})'"` +
        "return false;'>Sửa</a>" +
        `" <a href='#' id='btnDelete' onclick='openDeleteNhanVienModal(${element.id})'"` +
        "return false;'>Xóa</a>" +
        "</td>" +
        "</tr>"
    );
  });
}

function createNhanVienPagination(totalPages) {
  $(".pagination").empty();
  $(".pagination").append(
    '<li class="page-item" id="above-SanPham" onclick="firstNhanVienPage()" style = "margin-right: 15px;">First</li>' +
      '<li class="page-item" id="above-SanPham" onclick="lastNhanVienPage()" style = "margin-left = 3px;">Last</li>'
  );

  var totalNhanViens = totalPages;

  while (totalNhanViens > 0) {
    $("#above-SanPham").after(
      '<li class="page-item">' + totalNhanViens + "</li>"
    );

    totalNhanViens -= 1;
  }

  $(".page-item").click(function () {
    const elem = $(this).text();
    if (elem != "First" && elem != "Last") {
      changeNhanVienPage(Number(elem));
    }
  });
}

// paging
function fillNhanVienPaging(currentSize, totalPages) {
  // text
  document.getElementById("SanPham-page-info").innerHTML =
    currentSize +
    (currentSize > 1 ? " records " : " record ") +
    NhanVienCurrentPage +
    " of " +
    totalPages;
}

function firstNhanVienPage() {
  changeNhanVienPage(1);
}

function lastNhanVienPage() {
  changeNhanVienPage(tongNhanVien);
}

function changeNhanVienPage(page) {
  NhanVienCurrentPage = page;
  buildNhanVienTable();
}

function openNhanVienModal() {
  $("#object-info").modal("show");
}

// open create modal
function openAddNhanVienModal() {
  openNhanVienModal();
  resetAddNhanVienForm();
}

function hideNhanVienModal() {
  $("#object-info").modal("hide");
}

function resetAddNhanVienForm() {
  $("#nhanvien-body .modal input").val("");
  $("#nhanvien-body .modal select").val("");
}

function createNewNhanVien() {
  let firstName = document.getElementById("NhanVien-firstname").value;
  let lastName = document.getElementById("NhanVien-lastname").value;
  let namSinh = document.getElementById("NhanVien-namsinh").value;
  let gioiTinh = document.getElementById("modal-select-gioitinh").value;
  let chiNhanhID = document.getElementById("modal-select-chinhanh").value;
  let role = document.getElementById("modal-select-role").value;
  let NhanVien = {
    firstName: firstName,
    lastName: lastName,
    namSinh: namSinh,
    gioiTinh: gioiTinh,
    chiNhanhID: chiNhanhID,
    role: role,
  };

  let isOk = true;
  let isCheck = true;
  if (isOk == true) {
    $.ajax({
      url: "http://localhost:8080/api/v1/NhanVien",
      type: "POST",
      data: JSON.stringify(NhanVien), // body
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
      error: function (xhr) {
        isCheck = false;
        console.log(xhr.responseJSON.message);
        const errs = xhr.responseJSON.error;
        let errorList = "";
        for (const key in errs) {
          if (Object.hasOwnProperty.call(errs, key)) {
            const element = errs[key];
            errorList += element + "\n";
          }
        }
        alert(errorList);
      },
    });
  }

  if (isCheck == true) {
    hideNhanVienModal();
    buildNhanVienTable();
  }
}

var NhanVienTemp;

// hiện modal update NhanVien
function opendUpdateNhanVienModal(NhanVienID) {
  $("#object-info form h2").html("Update Nhan Vien");
  openNhanVienModal();
  resetAddNhanVienForm();
  // gọi api đổ thông tin vào NhanVien
  $.ajax({
    url: "http://localhost:8080/api/v1/NhanVien" + "/" + NhanVienID,
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
      NhanVienTemp = result;
      //fill data
      let fullName = result.fullName;
      let nameParts = fullName.split(" ");
      let lastName = nameParts[nameParts.length - 1];
      let firstName = nameParts.slice(0, -1).join(" ");
      document.getElementById("NhanVien-id").value = result.id;
      document.getElementById("NhanVien-firstname").value = firstName;
      document.getElementById("NhanVien-lastname").value = lastName;
      document.getElementById("NhanVien-namsinh").value = result.namSinh;
      document.getElementById("modal-select-gioitinh").value = result.gioiTinh;
      document.getElementById("modal-select-chinhanh").value =
        result.chiNhanhID;
      document.getElementById("modal-select-role").value = result.role;
    },
  });
}

// update NhanVien
function updateNhanVien(NhanVienID) {
  var firstName = document.getElementById("NhanVien-firstname").value;
  var lastName = document.getElementById("NhanVien-lastname").value;
  var namSinh = document.getElementById("NhanVien-namsinh").value;
  var gioiTinh = document.getElementById("modal-select-gioitinh").value;
  var chiNhanhID = document.getElementById("modal-select-chinhanh").value;
  var role = document.getElementById("modal-select-role").value;

  NhanVienTemp.firstName = firstName;
  NhanVienTemp.lastName = lastName;
  NhanVienTemp.namSinh = namSinh;
  NhanVienTemp.gioiTinh = gioiTinh;
  NhanVienTemp.chiNhanhID = chiNhanhID;
  NhanVienTemp.role = role;

  let isOk = true;
  // gọi api thêm mới NhanVien
  $.ajax({
    url: "http://localhost:8080/api/v1/NhanVien" + "/" + NhanVienID,
    type: "PUT",
    data: JSON.stringify(NhanVienTemp), // body
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
    error: function (xhr) {
      isOk = false;
      console.log(xhr.responseJSON.message);
      const errs = xhr.responseJSON.error;
      let errorList = "";
      for (const key in errs) {
        if (Object.hasOwnProperty.call(errs, key)) {
          const element = errs[key];
          errorList += element + "\n";
        }
      }
      alert(errorList);
    },
  });
  if (isOk == true) {
    hideNhanVienModal();
  }

  buildNhanVienTable();
}

function saveNhanVien() {
  let NhanVienID = document.getElementById("NhanVien-id").value;
  console.log(NhanVienID);

  if (NhanVienID == undefined || NhanVienID == "") {
    createNewNhanVien();
  } else {
    updateNhanVien(NhanVienID);
  }
  // showAlertSuccess();
}

// hiện modal xóa NhanVien
function openDeleteNhanVienModal(NhanVienID) {
  $("#delete-sanpham-modal").modal("show");
  document.getElementById("delete-sanpham-id").value = NhanVienID;
}

// ẩn modal xóa NhanVien
function hideDeleteNhanVienModal() {
  $("#delete-sanpham-modal").modal("hide");
}

function deleteNhanVien() {
  let id = document.getElementById("delete-sanpham-id").value;
  $.ajax({
    url: "http://localhost:8080/api/v1/NhanVien" + "/" + id,
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
  hideDeleteNhanVienModal();
  buildNhanVienTable();
}

function changeNhanVienSort(field) {
  if (field == sortField) {
    isAsc = !isAsc;
  } else {
    sortField = field;
    isAsc = true;
  }
  buildNhanVienTable();
}

function refreshNhanVien() {
  // refresh paging
  NhanVienCurrentPage = 1;
  size = 10;

  // refresh sorting
  sortField = "id";
  isAsc = true;

  // refresh search
  document.getElementById("search-input").value = "";

  // Get API
  buildNhanVienTable();
}

function setupNhanVienSearchEvent() {
  $("#search-input").on("keyup", function (event) {
    // enter key code = 13
    if (event.keyCode === 13) {
      buildNhanVienTable();
    }
  });
}
