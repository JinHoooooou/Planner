function redirectErrorPage(xhr, textStatus, errorThrown) {
  if (xhr.status === 401) {
    alert("Session 에러");
  } else {
    window.location.href = "../error.html"
  }
}

