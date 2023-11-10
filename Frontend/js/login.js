var username = document.querySelector("#username");
var password = document.querySelector("#password");
var form = document.querySelector("form");

function showError(input, message) {
  let parent = input.parentElement;
  let small = parent.querySelector("small");

  parent.classList.add("error");
  console.log(parent);
  small.innerText = message;
}

function showSuccecss(input) {
  let parent = input.parentElement;
  let small = parent.querySelector("small");

  parent.classList.remove("error");
  small.innerText = "";
}

function checkEmptyError(listInput) {
  let isEmptyError = false;
  listInput.forEach((input) => {
    input.value = input.value.trim();

    if (!input.value) {
      isEmptyError = true;
      showError(input, "Khong duoc de trong");
    } else {
      showSuccecss(input);
    }
  });
  return isEmptyError;
}

function checkLenghtError(input, min, max) {
  input.value = input.value.trim();

  if (input.value.length < min) {
    showError(input, `Phai nhap it nhat ${min} ky tu`);
    return true;
  }

  if (input.value.length > max) {
    showError(input, `Khong duoc qua ${max} ky tu`);
    return true;
  }

  showSuccecss(input);
  return false;
}

document.getElementById("btn-login").addEventListener("click", function (e) {
  e.preventDefault();

  let isEmptyError = checkEmptyError([username, password]);
  let isUsernameLenghtError = checkLenghtError(username, 6, 30);
  let isPasswordLenghtError = checkLenghtError(password, 6, 15);
  if (isEmptyError) {
    checkEmptyError([username, password]);
  } else if (isUsernameLenghtError || isPasswordLenghtError) {
    checkLenghtError(username, 6, 30);
    checkLenghtError(password, 6, 15);
  } else {
    //Call API Login
    $.ajax({
      url: "http://localhost:8080/api/v1/login",
      type: "GET",
      contentType: "application/json",
      dataType: "json", // datatype return
      async: false,
      beforeSend: function (xhr) {
        xhr.setRequestHeader(
          "Authorization",
          "Basic " + btoa(username.value + ":" + password.value)
        );
      },
      success: function (data, textStatus, xhr) {
        var isRememberMe = document.getElementById("rememberMe").checked;
        storage.saveRememberMe(isRememberMe);

        // save data to storage

        storage.setItem("ID", data.id);
        storage.setItem("FULL_NAME", data.fullName);
        storage.setItem("USERNAME", username.value);
        storage.setItem("PASSWORD", password.value);
        storage.setItem("ROLE", data.role);

        window.location.replace(
          "http://127.0.0.1:5501/html/homepage/homepage.html"
        );
      },
      error(jqXHR, textStatus, errorThrown) {
        if (jqXHR.status == 401) {
          showNameErrorMessage("Username or Password is not correct!");
          console.log(username);
          console.log(password);
        } else {
          console.log(jqXHR);
          console.log(textStatus);
          console.log(errorThrown);
        }
      },
    });
  }
});

function showNameErrorMessage(message) {
  document.getElementById("nameErrorMessage").style.display = "block";
  document.getElementById("nameErrorMessage").innerHTML = message;
}

function hideNameErrorMessage() {
  document.getElementById("nameErrorMessage").style.display = "none";
}
