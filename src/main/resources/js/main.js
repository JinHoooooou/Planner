let planList = [];

$(window).ready(function () {
  let data = requestPlanListAndNickname();
  renderMainPage(data);

  // 메인 페이지 기본적으로 렌더링 다 한 후에 이벤트 적용
  $("#createPlanButton").on("click", requestCreatePlan);
  $("#planUpdateButton").on("click", requestUpdatePlan);
  $("#signOut").on("click", requestSignOut);

  let planList = data.planList;
  $("#sortByStartDateAscButton").on("click", () => {
    sortBy(planList, "startDate", "ASC")
  });
  $("#sortByStartDateDescButton").on("click", () => {
    sortBy(planList, "startDate", "DESC")
  });
  $("#sortByEndDateAscButton").on("click", () => {
    sortBy(planList, "endDate", "ASC");
  });
  $("#sortByEndDateDescButton").on("click", () => {
    sortBy(planList, "endDate", "DESC")
  });
})

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
    $("#planList").append(
        $(`<div id="plan-${plan.planId}">`)
        .addClass("d-flex align-items-center list-group-item list-group-item-action")
        .append(
            $(`<input class="form-check-input my-auto me-3" type="checkbox">`)
            .attr(plan.complete === 'Y' ? "checked" : "unchecked", "true")
            .on("change", () => {
              requestCompletePlan(plan.planId);
            })
        ).append(
            $(`<a id="list-${plan.planId}" href="#" class="flex-grow-1 form-checked-content">`)
            .data("plan", plan)
            .attr({
              "data-bs-toggle": "offcanvas",
              "data-bs-target": "#detailPlan"
            }).on("click", getDetailList).append(
                $(`<p class="display-6 my-auto text-truncate">`).text(plan.title)
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
            $(`<i class="bi bi-trash3-fill ms-2 h2">`).on("click", () => {
              requestDeletePlan(plan.planId);
            })
        )
    )
  });
}

function requestCompletePlan(planId) {
  let formCheckInput = $(`#plan-${planId} .form-check-input`);
  let complete = $(formCheckInput).prop("checked");
  console.log(planId);
  $.ajax({
    url: `/api/plan/complete`,
    type: "POST",
    data: {
      "planId": planId,
      "complete": (complete ? "Y" : "N")
    },
    dataType: "json",
    success: function () {
      location.reload();
      // requestPlanListAndNickname();
    },
    error: function (xhr) {
      alert(xhr.responseJSON.message);
      if (xhr.status === 401) {
        window.location.href = "/user/signin.html";
      } else {
        location.reload();
      }
    }
  })
}

function requestDeletePlan(planId) {
  if (confirm("정말 삭제하시겠습니까?")) {
    $.ajax({
      url: `/api/plan/delete`,
      type: "GET",
      data: {"planId": planId},
      dataType: "json",
      success: function () {
        location.reload();
        // requestPlanListAndNickname();
      },
      error: function (xhr) {
        alert(xhr.responseJSON.message);
        if (xhr.status === 401) {
          window.location.href = "/user/signin.html";
        } else {
          location.reload();
        }
      }
    })
  }
}

function requestCreatePlan() {
  let title = $("#planTitleInput").val()
  let startDate = $("#planStartDateInput").val()
  let endDate = $("#planEndDateInput").val()
  let remindAlarmDate = $("#endAlarmDate").val();

  let formData = {
    "title": title, "startDate": startDate, "endDate": endDate, "remindAlarmDate": remindAlarmDate
  }

  $.ajax({
    url: "/api/plan/create",
    type: "post",
    data: formData,
    dataType: "json",
    success: function (response) {
      // requestPlanListAndNickname();
      location.reload();
    }, error: function (xhr) {
      alert(xhr.responseJSON.message);
    }
  })
}

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

function requestSignOut() {
  $.ajax({
    url: "/api/user/signout", success: function () {
      window.location.href = "/index.html"
    }, error: function (xhr) {
      alert(xhr.responseJSON.message);
    }
  })
}

function sortBy(list, by, order) {
  let sorted = list.sort((a, b) => {
    let timeA = new Date(a[by])
    let timeB = new Date(b[by])
    return timeA === timeB ? a["planId"] - b["planId"] : order === "ASC" ? timeA - timeB : timeB - timeA;
  })
  renderPlanList(sorted);
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





