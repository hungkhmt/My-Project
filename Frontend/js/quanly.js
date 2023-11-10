function viewQuanLyPage() {
  $(".main_content").load("../../html/quanly/quanly.html", function () {
    buildQuanLyTable();
  });
}

function buildQuanLyTable() {
  $("#sanpham_table tbody").empty();
  getQuanLyList();
}

// paging
var QuanLyCurrentPage = 1;
var size = 10;
var tongQuanLy = 0;

var sortField = "id";
var isAsc = true;

var QuanLys = [];
function getQuanLyList() {
  var url = "http://localhost:8080/api/v1/QuanLy";

  url += "?pageNumber=" + QuanLyCurrentPage + "&size=" + size;

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
    success: function (QuanLyArr) {
      $("#sanpham-tab_body").empty();
      QuanLys = QuanLyArr.content;
      fillQuanLyToTable();
      tongQuanLy = QuanLyArr.totalPages;
      if (tongQuanLy >= 2) {
        fillQuanLyPaging(QuanLyArr.numberOfElements, QuanLyArr.totalPages);
        createQuanLyPagination(QuanLyArr.totalPages);
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

function fillQuanLyToTable() {
  QuanLys.forEach(function (element) {
    $("#sanpham-tab_body").append(
      "<tr>" +
        "<td>" +
        '<input id="checkbox-" type="checkbox"/>' +
        "</td>" +
        "<td>" +
        element.id +
        "</td>" +
        '<td style="text-align: left;">' +
        element.username +
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
        element.chinhanhId +
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

function createQuanLyPagination(totalPages) {
  $(".pagination").empty();
  $(".pagination").append(
    '<li class="page-item" id="above-SanPham" onclick="firstQuanLyPage()" style = "margin-right: 15px;">First</li>' +
      '<li class="page-item" id="above-SanPham" onclick="lastQuanLyPage()" style = "margin-left = 3px;">Last</li>'
  );

  var totalQuanLys = totalPages;

  while (totalQuanLys > 0) {
    $("#above-SanPham").after(
      '<li class="page-item">' + totalQuanLys + "</li>"
    );

    totalQuanLys -= 1;
  }

  $(".page-item").click(function () {
    const elem = $(this).text();
    if (elem != "First" && elem != "Last") {
      changeQuanLyPage(Number(elem));
    }
  });
}

// paging
function fillQuanLyPaging(currentSize, totalPages) {
  // text
  document.getElementById("SanPham-page-info").innerHTML =
    currentSize +
    (currentSize > 1 ? " records " : " record ") +
    QuanLyCurrentPage +
    " of " +
    totalPages;
}

function firstQuanLyPage() {
  changeQuanLyPage(1);
}

function lastQuanLyPage() {
  changeQuanLyPage(tongQuanLy);
}

function changeQuanLyPage(page) {
  QuanLyCurrentPage = page;
  buildQuanLyTable();
}

function openQuanLyModal() {
  $("#object-info").modal("show");
}

// open create modal
function openAddQuanLyModal() {
  openQuanLyModal();
  resetAddQuanLyForm();
}

function hideQuanLyModal() {
  $("#object-info").modal("hide");
}

function resetAddQuanLyForm() {
  $("#object-body > input").val("");
}

function changeQuanLySort(field) {
  if (field == sortField) {
    isAsc = !isAsc;
  } else {
    sortField = field;
    isAsc = true;
  }
  buildQuanLyTable();
}

function refreshQuanLy() {
  // refresh paging
  QuanLyCurrentPage = 1;
  size = 10;

  // refresh sorting
  sortField = "id";
  isAsc = true;

  // refresh search
  document.getElementById("search-input").value = "";

  // Get API
  buildQuanLyTable();
}

function setupQuanLySearchEvent() {
  $("#search-input").on("keyup", function (event) {
    // enter key code = 13
    if (event.keyCode === 13) {
      buildQuanLyTable();
    }
  });
}

function createNewQuanLy() {
  let firstName = document.getElementById("QuanLy-firstname").value;
  let lastName = document.getElementById("QuanLy-lastname").value;
  let namSinh = document.getElementById("QuanLy-namsinh").value;
  let username = document.getElementById("QuanLy-username").value;
  let password = document.getElementById("QuanLy-password").value;
  let gioiTinh = document.getElementById("modal-select-gioitinh").value;
  let chiNhanhID = document.getElementById("modal-select-chinhanh").value;
  let role = document.getElementById("modal-select-role").value;
  let QuanLy = {
    firstName: firstName,
    lastName: lastName,
    namSinh: namSinh,
    gioiTinh: gioiTinh,
    chinhanhID: chiNhanhID,
    username: username,
    password: password,
    role: role,
  };

  let isOk = true;
  let isCheck = true;
  if (isOk == true) {
    $.ajax({
      url: "http://localhost:8080/api/v1/QuanLy",
      type: "POST",
      data: JSON.stringify(QuanLy), // body
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
    hideQuanLyModal();
    buildQuanLyTable();
  }
}

var QuanLyTemp;

// hiện modal update QuanLy
function opendUpdateQuanLyModal(QuanLyID) {
  $("#object-info form h2").html("Update Nhan Vien");
  openQuanLyModal();
  resetAddQuanLyForm();
  // gọi api đổ thông tin vào QuanLy
  $.ajax({
    url: "http://localhost:8080/api/v1/QuanLy" + "/" + QuanLyID,
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
      QuanLyTemp = result;
      //fill data
      let fullName = result.fullName;
      let nameParts = fullName.split(" ");
      let lastName = nameParts[nameParts.length - 1];
      let firstName = nameParts.slice(0, -1).join(" ");
      document.getElementById("QuanLy-id").value = result.id;
      document.getElementById("QuanLy-firstname").value = firstName;
      document.getElementById("QuanLy-lastname").value = lastName;
      document.getElementById("QuanLy-namsinh").value = result.namSinh;
      document.getElementById("QuanLy-username").value = result.username;
      document.getElementById("QuanLy-password").value = result.password;
      document.getElementById("modal-select-gioitinh").value = result.gioiTinh;
      document.getElementById("modal-select-chinhanh").value =
        result.chinhanhId;
      document.getElementById("modal-select-role").value = result.role;
    },
  });
}

// update QuanLy
function updateQuanLy(QuanLyID) {
  var firstName = document.getElementById("QuanLy-firstname").value;
  var lastName = document.getElementById("QuanLy-lastname").value;
  var namSinh = document.getElementById("QuanLy-namsinh").value;
  var username = document.getElementById("QuanLy-username").value;
  var password = document.getElementById("QuanLy-password").value;
  var gioiTinh = document.getElementById("modal-select-gioitinh").value;
  var chiNhanhID = document.getElementById("modal-select-chinhanh").value;
  var role = document.getElementById("modal-select-role").value;

  QuanLyTemp.firstName = firstName;
  QuanLyTemp.lastName = lastName;
  QuanLyTemp.namSinh = namSinh;
  QuanLyTemp.gioiTinh = gioiTinh;
  QuanLyTemp.chinhanhId = chiNhanhID;
  QuanLyTemp.username = username;
  QuanLyTemp.password = password;
  QuanLyTemp.role = role;

  let isOk = true;
  // gọi api thêm mới QuanLy
  $.ajax({
    url: "http://localhost:8080/api/v1/QuanLy" + "/" + QuanLyID,
    type: "PUT",
    data: JSON.stringify(QuanLyTemp), // body
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
    hideQuanLyModal();
  }

  buildQuanLyTable();
}

function saveQuanLy() {
  let QuanLyID = document.getElementById("QuanLy-id").value;
  console.log(QuanLyID);

  if (QuanLyID == undefined || QuanLyID == "") {
    createNewQuanLy();
  } else {
    updateQuanLy(QuanLyID);
  }
  // showAlertSuccess();
}
