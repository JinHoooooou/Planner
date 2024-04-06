let planList = [];

$(window).ready(function () {
  let data = requestPlanListAndNickname();
  renderMainPage(data);

  // 메인 페이지 기본적으로 렌더링 다 한 후에 이벤트 적용
  $("#createPlanButton").on("click", requestCreatePlan);
  $("#signOut").on("click", requestSignOut);
  $("#planUpdateButton").on("click", requestUpdatePlan);

})

function requestUpdatePlan() {
  let updatePlanTitle = $("#planTitle").val();
  let updatePlanStartDate = $("#planStartDate").val();
  let updatePlanEndDate = $("#planEndDate").val();
  let updatePlanRemindAlarmDate = $("#planRemindAlarm").val();

  let planUpdateForm = {
    "title": updatePlanTitle,
    "startDate": updatePlanStartDate,
    "endDate": updatePlanEndDate,
    "remindAlarmDate": updatePlanRemindAlarmDate
  }

  $.ajax({
    url: `/plan/` + $("#planIdForDetail").val(),
    type: "PUT",
    data: JSON.stringify(planUpdateForm),
    dataType: "json",
    success: function () {
      location.reload();
    },
    error: function (xhr) {
      $("#errorMessage").text(xhr.responseJSON.message)
      .fadeIn().fadeOut(5000);
    }
  })
}

function requestPlanListAndNickname() {
  let result = {};
  $.ajax({
    url: "/api/plan/list",
    type: "get",
    dataType: "json",
    async: false,
    success: function (response) {
      result = response.data
      planList = response.data.planList;
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message)
      window.location.href = "/index.html";
    },
  })
  return result;
}

function renderMainPage(data) {
  $("#nickname").text(data.nickname);
  renderCompletePlanCount(data.planList);
  renderCreatePlanForm();
  renderPlanList(data.planList);
}

function renderCompletePlanCount(planList) {
  let completeCount = 0;
  $.each(planList, function (index, plan) {
    completeCount += plan.complete === 'Y' ? 1 : 0;
  })
  $("#completedPlanCount").text(completeCount);
  $("#notCompletedPlanCount").text(planList.length - completeCount);
}

function renderCreatePlanForm() {
  let today = new Date();
  let todayString = today.getFullYear() + "-"
      + String(today.getMonth() + 1).padStart(2, '0') + "-"
      + String(today.getDate()).padStart(2, '0')
  $("#searchPlanInput").val("");
  $("#planTitleInput").val("");
  $("#planStartDateInput").val(todayString);
  $("#planEndDateInput").val(todayString);
}

function renderPlanList(planList) {
  $("#planList").empty();
  $.each(planList, function (index, plan) {
    let alarmDate = new Date(plan.remindAlarmDate).setHours(0, 0, 0, 0)
    $("#planList").append(
        $(`<div id="plan-${plan.planId}">`)
        .addClass("d-flex align-items-center list-group-item list-group-item-action")
        .append(
            $(`<input class="form-check-input my-auto me-3" type="checkbox">`)
        ).append(
            $(`<a id="list-${plan.planId}" href="#" class="flex-grow-1">`)
            .data("plan", plan)
            .attr({
              "data-bs-toggle": "offcanvas",
              "data-bs-target": "#detailPlan"
            }).on("click", getDetailList).append(
                $(`<div class="display-6">`).text(plan.title)
            )
        ).append(
            $(`<i class="bi bi-bell-fill mx-2">`)
        ).append(
            $(`<i class="bi bi-diagram-3-fill mx-2">`))
        .append(
            $(`<div>`).text(plan.startDate)
        ).append(
            $(`<div>`).text("~")
        )
        .append(
            $(`<div>`).text(plan.endDate)
        ).append(
            $(`<i class="bi bi-trash3-fill mx-2">`)
        )

        // $("<div>").addClass("").append(
        //     $("<div>").css("display", "flex").append(
        //         $("<input>").prop({
        //           "type": "checkbox",
        //           "name": "complete",
        //           "class": "comRadio"
        //         }).attr($(plan.complete === 'Y') ? "checked" : "unchecked",
        //             "true")
        //         .on("change", () => completePlanner(plan.planId))
        //     ).append(
        //         $("<strong>").prop("id", `listTitle-${plan.planId}`)
        //         .attr({
        //               "data-bs-toggle": "offcanvas",
        //               "data-bs-target": "#detailPlan"
        //             }
        //         ).data("plan", plan)
        //         .text(plan.title)
        //         .on("click", getDetailList)
        //     )
        // ).append(
        //     $("<div>").addClass("plannerDate").append(
        //         $("<i>").prop("id", `alarmMessage-${plan.planId}`)
        //         .addClass("bi bi-alarm text-danger d-none")
        //     ).append(
        //         $("<span>").prop("id", "listEndDate")
        //         .addClass("EndListDate")
        //         .text(plan.endDate)
        //     )
        // ).css("animation",
        //     alarmDate < new Date().getTime() && plan.complete === 'N'
        //         ? "heartBeat 1s ease-in-out infinite" : "")
    )
  });
}

function darkMode() {
  let body = document.body;
  body.classList.toggle("dark-mode");
  let h1 = document.getElementById("h-title")
  h1.classList.toggle("dark-mode");
  let icon_dark = document.getElementsByClassName("icon-dark")
  let ic_dark = document.getElementsByClassName("ic-dark")
  for (let i = 0; i < icon_dark.length; i++) {
    icon_dark[i].classList.toggle("dark-mode");
    ic_dark[i].classList.toggle("dark-mode");
  }
  let main = document.querySelector("main")
  main.classList.toggle("dark-mode-main");
  let input_dark = document.getElementsByClassName("input-dark")
  for (let i = 0; i < input_dark.length; i++) {
    input_dark[i].classList.toggle("dark-mode-input");

  }
  let completion = document.getElementsByClassName("completion")
  for (let i = 0; i < completion.length; i++) {
    completion[i].classList.toggle("dark-mode");
  }
  let searchButton_dark = document.getElementById("searchButton")
  searchButton_dark.classList.toggle("dark-mode");
  let dark_scheme = document.getElementsByClassName("scheme-dark")
  for (let i = 0; i < dark_scheme.length; i++) {
    dark_scheme[i].classList.toggle("dark-scheme");
  }
  let save = document.getElementById("save")
  save.classList.toggle("dark-mode-save");
  document.getElementById("plDiv").classList.toggle("dark-mode");

  let plannerItem = document.getElementsByClassName("plannerItem");
  for (let i = 0; i < plannerItem.length; i++) {
    plannerItem[i].classList.toggle("dark-mode");
  }
  let EndListDate = document.getElementsByClassName("EndListDate");
  for (let i = 0; i < EndListDate.length; i++) {
    EndListDate[i].classList.toggle("dark-mode");
  }

}

function requestSignOut() {
  $.ajax({
    url: "/api/user/signout",
    success: function () {
      window.location.href = "/index.html"
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message);
    }
  })
}

function debounce(func, timeout = 300) {
  let timer;
  return (...args) => {
    clearTimeout(timer);
    timer = setTimeout(() => {
      func.apply(this, args);
    }, timeout);
  };
}

// 메인페이지 & 검색 시 페이지
function drawPlanList(response) {
  let switch_dark = document.getElementById("switch");
  let plannerItem = document.getElementsByClassName("plannerItem");
  let EndListDate = document.getElementsByClassName("EndListDate");

  let count = 0;
  for (let i = 0; i < planList.length; i++) {
    if (planList[i].complete === 'N') {
      count++;
    }
  }

  function sortEndDateASC() {
    const endDateAsc = planList.sort(function (a, b) {
      if (new Date(a["endDate"]).getTime() < new Date(b["endDate"]).getTime()) {
        return -1;
      }
      if (new Date(a["endDate"]).getTime() > new Date(b["endDate"]).getTime()) {
        return 1;
      }
      return 0;
    });

    showTodoList();
    if (switch_dark.checked) {
      for (let i = 0; i < plannerItem.length; i++) {
        plannerItem[i].classList.add("dark-mode");
        EndListDate[i].classList.add("dark-mode");
      }
    }
    return endDateAsc;
  }

  function sortEndDateDESC(planList) {
    const endDateDesc = planList.sort(function (a, b) {
      if (new Date(a["endDate"]).getTime() < new Date(b["endDate"]).getTime()) {
        return 1;
      }
      if (new Date(a["endDate"]).getTime() > new Date(b["endDate"]).getTime()) {
        return -1;
      }
      return 0;
    });

    showTodoList();
    if (switch_dark.checked) {
      for (let i = 0; i < plannerItem.length; i++) {
        plannerItem[i].classList.add("dark-mode");
        EndListDate[i].classList.add("dark-mode");
      }
    }
    return endDateDesc;
  }

  function sortStartDateASC(planList) {
    const startDateAsc = planList.sort(function (a, b) {
      if (new Date(a["startDate"]).getTime() < new Date(
          b["startDate"]).getTime()) {
        return -1;
      }
      if (new Date(a["startDate"]).getTime() > new Date(
          b["startDate"]).getTime()) {
        return 1;
      }
      return 0;
    });
    showTodoList();
    if (switch_dark.checked) {
      for (let i = 0; i < plannerItem.length; i++) {
        plannerItem[i].classList.add("dark-mode");
        EndListDate[i].classList.add("dark-mode");
      }
    }
    return startDateAsc;
  }

  function sortStartDateDESC(planList) {
    const startDateDesc = planList.sort(function (a, b) {
      if (new Date(a["startDate"]).getTime() < new Date(
          b["startDate"]).getTime()) {
        return 1;
      }
      if (new Date(a["startDate"]).getTime() > new Date(
          b["startDate"]).getTime()) {
        return -1;
      }
      return 0;
    });
    showTodoList();
    if (switch_dark.checked) {
      for (let i = 0; i < plannerItem.length; i++) {
        plannerItem[i].classList.add("dark-mode");
        EndListDate[i].classList.add("dark-mode");
      }
    }
    return startDateDesc;
  }

  sortEndDateASC();

  document.getElementById("notCompleted").innerHTML = count;
  document.getElementById("completed").innerHTML = planList.length - count;
  document.getElementById("endDateASC").onclick = function () {
    sortEndDateASC(planList);
  }
  document.getElementById("endDateDESC").onclick = function () {
    sortEndDateDESC(planList);
  }
  document.getElementById("startDateASC").onclick = function () {
    sortStartDateASC(planList);
  }
  document.getElementById("startDateDESC").onclick = function () {
    sortStartDateDESC(planList);
  }
  document.getElementById("nickname").innerHTML = response.data.nickname;
}

function showTodoList() {

  for (let i = 0; i < planList.length; i++) {
    let planAlarmDate = planList[i].remindAlarmDate;
    let planEndDate = planList[i].endDate;
    let planComplete = planList[i].complete;
    if (planAlarmDate != null) {

      if (new Date(planAlarmDate).setHours(0, 0, 0, 0) < new Date().getTime()) {
        if (planComplete === 'N') {
          document.getElementsByClassName("plannerItem")[i].setAttribute(
              "style",
              "animation: heartBeat 1s ease-in-out infinite;");
          $(`#alarmMessage-${planList[i].planId}`).removeClass("d-none");
        }
      }
    }
    // 마감 기한이 지난 플랜에 스타일 주기

    if (new Date(planEndDate).getMonth() < new Date().getMonth()) {
      document.getElementsByClassName("plannerItem")[i].setAttribute("style",
          "border: 2px solid red;");
    }

    if (new Date(planEndDate).getMonth() === new Date().getMonth()) {
      if (new Date(planEndDate).getDate() < new Date().getDate()) {
        document.getElementsByClassName("plannerItem")[i].setAttribute("style",
            "border: 2px solid red;");
      }
    }

  }
}

function deletePlanner(planId) {
  if (confirm("정말 삭제하시겠습니까?")) {
    $.ajax({
      url: `/plan/${planId}`,
      type: "DELETE",
      success: function () {
        // location.reload();
        requestPlanListAndNickname();
      },
      error: function (xhr) {
        if (xhr.status === 401) {
          alert("로그인이 필요한 페이지 입니다.");
          window.location.href = "/user/signin.html";
        } else {
          alert("invalid error");
          location.reload();
        }
      }
    })
  }
}

function completePlanner(planId) {
  let formCheckInput = $(`#plan-${planId} .comRadio`);
  let complete = $(formCheckInput).prop("checked");
  $.ajax({
    url: `/plan/${planId}`,
    type: "PATCH",
    data: JSON.stringify({"complete": (complete ? "Y" : "N")}),
    contentType: "application/json",
    success: function () {
      // locataion.reload();
      requestPlanListAndNickname();
    },
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.");
        window.location.href = "/user/signin.html";
      } else {
        alert("invalid error");
        location.reload();
      }
    }
  })
}

function is_checked() {
  if (document.getElementById("endAlarmDateBoolean").checked == true) {
    document.getElementById("endAlarmDate").readOnly = false;
  } else if (document.getElementById("endAlarmDateBoolean").checked == false) {
    document.getElementById("endAlarmDate").readOnly = true;
    document.getElementById("endAlarmDate").value = "";
  }
}

function requestCreatePlan() {
  let title = $("#planTitleInput").val()
  let startDate = $("#planStartDateInput").val()
  let endDate = $("#planEndDateInput").val()
  let remindAlarmDate = $("#endAlarmDate").val();

  let formData = {
    "title": title,
    "startDate": startDate,
    "endDate": endDate,
    "remindAlarmDate": remindAlarmDate
  }

  $.ajax({
    url: "/api/plan/create",
    type: "post",
    data: formData,
    dataType: "json",
    success: function (response) {
      requestPlanListAndNickname();
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message);
    }
  })
}





