function getDetailList() {
  let plan = $(this).data("plan");
  $.ajax({
    url: "/detail/list",
    type: "GET",
    data: { planId: plan.planId },
    dataType: "json",
    async: false,
    success: function (response) {
      renderOffcanvas(response, plan)
    },
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.")
        window.location.href = "user/login.html";
      }
    }
  });
}

function renderOffcanvas(response, plan) {
  renderPlanSection(plan);
  renderList(response.detailList);
  renderProgress(response.detailList);

  // 이벤트
  $("#detailCreateCollapse").on({
    "show.bs.collapse": renderCreateDetailForm,
    "hide.bs.collapse": () => {
      $("#detailCreateCollapseButton").text("디테일 추가");
    }
  })
  $("#detailCreateForm").off("submit").on("submit", requestCreateDetail);
}

function renderPlanSection(plan) {
  $("#planIdForDetail").val(plan.planId);
  $("#planTitle").val(plan.title);
  $("#planStartDate").val(plan.startDate);
  $("#planEndDate").val(plan.endDate);
  $("#planRemindAlarm").val(`${plan.remindAlarmDate === null ? '' : plan.remindAlarmDate}`);
  $("#detailCreateCollapse").removeClass("show")
  $("#detailCreateCollapseButton").text("디테일 추가");
  $("#createErrorMessage").empty();
}

function renderList(list) {
  $("#detailList").empty();
  $.each(list, function (index, element) {
    appendOneToList(element);
  });
}

function appendOneToList(detail) {
  let accordionItem = $(`<div class="accordion-item" id="detail-${detail.detailPlanId}">`);
  accordionItem.append(accordionHeader(detail))
  accordionItem.append(accordionBody(detail))
  $("#detailList").append(accordionItem);

  accordionItem.on("change", ".form-check-input", requestComplete);
  accordionItem.on("click", `#detailDeleteButton-${detail.detailPlanId}`, requestDeleteDetail);
  accordionItem.on("hide.bs.collapse", requestUpdateDetail);
}

function accordionHeader(detail) {
  return `
  <div class="accordion-header container h1">
    <div class="detailItem collapsed row" data-bs-toggle="collapse" data-bs-target="#collapse-${detail.detailPlanId}">
      <input class="col-1 my-auto mx-3 form-check-input" data-bs-toggle="collapse" type="checkbox" 
        ${detail.complete === 'Y' ? "checked" : ""}/>
      <p class="col-5 my-auto px-0 m-0 text-truncate form-checked-content">
        ${detail.contents ?? ""}
      </p>
      <div class="col-3 my-auto">
        <p class="h6 m-0">${detail.startDate}</p>
        <p class="h6 d-inline-block m-0">${detail.startTime}</p>
        <span class="d-inline-block m-0">~</span>
        <p class="h6 d-inline-block m-0">${detail.endTime}</p>
      </div>
      <div class="col py-3 px-0 me-3 text-end" data-bs-toggle="collapse">
        <i class='bi bi-alarm ${detail.remindAlarmTime ? '' : 'd-none'}'></i>
        <p class="d-none">${detail.remindAlarmTime ?? ''}</p>
        <i class="bi bi-trash3" id="detailDeleteButton-${detail.detailPlanId}"></i>
      </div>
    </div>
  </div>`
}

function accordionBody(detail) {
  return `
  <div id="collapse-${detail.detailPlanId}" class="accordion-collapse collapse" data-bs-parent="#detailList">
    <div class="accordion-body p-2">
      <div class="container">
        <div class="row">
          <textarea class="form-control-plaintext col ps-2 updateInput">${detail.contents ?? ""}</textarea>
          <div class="col-5">
            <div class="row justify-content-center">
              <div class="col-8">
                <input class="form-control-plaintext updateInput" type="date" value=${detail.startDate} />
              </div>
            </div>
            <div class="row justify-content-end">
              <div class="col-6 p-0">
                <input class="form-control-plaintext updateInput" type="time" value=${detail.startTime} />
              </div>
              <div class="col-6 p-0">
                <input class="form-control-plaintext updateInput" type="time" value=${detail.endTime} />
              </div>
            </div>
            <div class="row justify-content-end">
              <div class="col-9">
                <input class="form-control-plaintext updateInput" type="datetime-local" 
                  value="${detail.remindAlarmTime}"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>`
}

function requestComplete() {
  let formCheckInput = $(this);
  let complete = $(formCheckInput).prop("checked");
  let detailPlanId = $(formCheckInput).parents(".accordion-item").attr("id").split("-")[1];
  $.ajax({
    url: `/detail/${detailPlanId}`,
    data: JSON.stringify({ "complete": (complete ? "Y" : "N") }),
    contentType: "application/json",
    type: "patch",
    success: updateProgress,
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.");
        window.location.href = "user/login.html";
      } else {
        replaceWithErrorIcon(formCheckInput);
      }
    }
  })
}

function requestUpdateDetail() {
  let original = $(this).find("p");
  let update = $(this).find(".updateInput");
  if (!isUpdated(original, update)) {
    return;
  }

  let detailPlanId = $(this).attr("id").split("-")[1];
  let formCheckInput = $(this).find(".form-check-input");
  let updateData = {
    "updateContents": $(update[0]).val(),
    "updateStartDate": $(update[1]).val(),
    "updateStartTime": $(update[2]).val(),
    "updateEndTime": $(update[3]).val(),
    "updateRemindAlarmTime": $(update[4]).val(),
  };
  $.ajax({
    url: `/detail/${detailPlanId}`,
    type: "PUT",
    contentType: "application/json",
    data: JSON.stringify(updateData),
    success: function () {
      updateAccordionHeader(original, updateData);
    },
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.");
        window.location.href = "user/login.html";
      } else {
        replaceWithErrorIcon(formCheckInput);
      }
    }
  })
}

function isUpdated(original, update) {
  for (let i = 0; i < original.length; i++) {
    if ($(original[i]).text().trim() !== $(update[i]).val()) {
      return true;
    }
  }
  return false;
}

function updateAccordionHeader(original, updateData) {
  let accordionHeader = $(original[0]).parent();
  $(accordionHeader).removeClass("text-danger");
  let errorIcon = $(accordionHeader).find(".bi-exclamation-circle");
  if (errorIcon) {
    $(errorIcon).replaceWith($(`<input class="col-1 my-auto mx-3 form-check-input" data-bs-toggle="collapse" type="checkbox" 
      ${$(errorIcon).attr("checked") ?? ''}/>`));
  }

  $(original[0]).text(updateData["updateContents"]);
  $(original[1]).text(updateData["updateStartDate"]);
  $(original[2]).text(updateData["updateStartTime"]);
  $(original[3]).text(updateData["updateEndTime"]);
  $(original[4]).text(updateData["updateRemindAlarmTime"]);

  let alarmIcon = $(original[4]).parent().find(".bi-alarm");
  $(alarmIcon).toggleClass("d-none", updateData["updateRemindAlarmTime"] === '');
}

function replaceWithErrorIcon(formCheckInput) {
  let accordionHeader = $(formCheckInput).parent();
  $(accordionHeader).addClass("text-danger");
  $(formCheckInput).replaceWith(
    $(`<i class="bi bi-exclamation-circle col-1 my-auto mx-2 p-0" ${$(formCheckInput).attr("checked")
      ?? ''}  ></i>`));
}

function requestDeleteDetail() {
  let detailPlanId = $(this).attr("id").split("-")[1];
  if (confirm("정말 삭제하시겠습니까?")) {
    $.ajax({
      url: `/detail/${detailPlanId}`,
      type: "DELETE",
      success: function () {
        $(`#detail-${detailPlanId}`).remove();
        updateProgress();
      },
      error: function () {
        console.log("error")
      }
    })
  }
}

function renderProgress(list) {
  let progress = $("#planProgress");
  if (list.length === 0) {
    progress.css("width", '0%').text('0%');
  }

  let checkedCount = 0;
  $.each(list, function (index, element) {
    checkedCount += element.complete === 'Y' ? 1 : 0;
  });

  progress.css("width", (checkedCount / list.length) * 100 + '%')
    .text(Math.round((checkedCount / list.length) * 100).toFixed(1) + '%');
}

function renderCreateDetailForm() {
  let today = new Date();
  $("#detailCreateCollapseButton").text("접기");
  $("#detailContents").val("");
  $("#detailStartDate").val(
    today.getFullYear() + "-"
    + String(today.getMonth() + 1).padStart(2, '0') + "-"
    + String(today.getDate()).padStart(2, '0')
  );
  $("#detailStartTime").val(
    String(today.getHours()).padStart(2, '0') + ":" + String(today.getMinutes()).padStart(2, '0')
  );
  $("#detailEndTime").val(
    String(today.getHours() + 1).padStart(2, '0') + ":" + String(today.getMinutes()).padStart(2, '0')
  );
}

function requestCreateDetail(event) {
  event.preventDefault()
  $.ajax({
    url: "/detail",
    type: "POST",
    contentType: "application/x-www-form-urlencoded",
    data: $(this).serialize(),
    dataType: "json",
    success: function (response) {
      appendOneToList(response);
      $("#detailCreateCollapse").removeClass("show");
      $("#detailCreateCollapseButton").text("디테일 추가");
      $("#createErrorMessage").empty();
      updateProgress(event);
    },
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.");
        window.location.href = "user/login.html";
      } else if (xhr.status === 400) {
        response = xhr.responseJSON;
        $("#createErrorMessage").text(response.message);
      }
    }
  })
}

function updateProgress() {
  let checkedCount = $(".form-check-input:checked").length;
  let totalCheckBoxCount = $(".form-check-input").length;
  let progressPercent = (checkedCount / totalCheckBoxCount) * 100;
  $("#planProgress").css("width", progressPercent + '%')
    .text(Math.round(progressPercent).toFixed(1) + '%')
}
