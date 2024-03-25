function getDetailList() {
  let plan = $(this).data("plan");
  $.ajax({
    url: "/detail/list",
    type: "GET",
    data: { planId: plan.planId },
    dataType: "json",
    async: false,
    success: function (response) { renderOffcanvas(response, plan) },
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.")
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
    "hide.bs.collapse": () => { $("#detailCreateCollapseButton").text("디테일 추가"); }
  })
  $("#detailCreateForm").on("submit", requestCreateDetail)
}

function renderPlanSection(plan) {
  $("#planIdForDetail").val(plan.planId);
  $("#planTitle").val(plan.title);
  $("#planStartDate").val(plan.startDate);
  $("#planEndDate").val(plan.endDate);
  $("#planRemindAlarm").val(`${plan.remindAlarmDate === null ? '' : plan.remindAlarmDate}`);
  $("#detailCreateCollapse").removeClass("show")
  $("#detailCreateCollapseButton").text("디테일 추가");
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

  // 이벤트 추가
  $(`#detail-${detail.detailPlanId}`).on("change", ".form-check-input", updateProgress);
  $(`#detail-${detail.detailPlanId}`).on("click", ".bi-trash3", requestDeleteDetail)
}

function accordionHeader(detail) {
  return `
  <div class="accordion-header container h1">
    <div class="detailItem collapsed row" data-bs-toggle="collapse" data-bs-target="#collapse-${detail.detailPlanId}">
      <input class="col-1 my-auto mx-3 form-check-input" data-bs-toggle="collapse" type="checkbox" 
        ${detail.complete === 'Y' ? "checked" : ""}/>
      <div class="col-5 py-3 px-0 text-truncate form-checked-content">
      ${detail.contents ?? ""}
      </div>
      <div class="col py-3">
        <p class="h6">${detail.startDate}</p>
        <p class="h6">${detail.startTime}~${detail.endTime}</p>
      </div>
      <div class="col py-3 px-0 me-3 text-end" data-bs-toggle="collapse">
        ${detail.remindAlarmTime ? "<i class='bi bi-alarm'></i>" : ""}
        <i class="bi bi-trash3" id="$detailDeleteButton-${detail.detailPlanId}"></i>
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
          <textarea class="form-control-plaintext col ps-2 updateContents">${detail.contents ?? ""}</textarea>
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
      updateProgress(event);
    },
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인이 필요한 페이지 입니다.");
        window.location.href = "user/login.html";
      } else if (xhr.status == 400) {
        response = xhr.responseJSON;
        $("#createErrorMessage").text(response.message);
      }
    }
  })
}

function requestDeleteDetail(event) {
  event.preventDefault();
  let detailPlanId = $(this).attr("id").split("-")[1];
  console.log(detailPlanId);
  if (confirm("정말 삭제하시겠습니까?")) {
    $.ajax({
      url: `/detail/${detailPlanId}`,
      type: "DELETE",
      success: function () {
        $("#detailList").find(`#detail-${detailPlanId}`).remove();
        updateProgress(event);
      },
      error: function () {
        console.log("error")
      }
    })
  }
}

function updateProgress(event) {
  event.preventDefault();
  let checkedCount = $(".form-check-input:checked").length;
  let totalCheckBoxCount = $(".form-check-input").length;
  let progressPercent = (checkedCount / totalCheckBoxCount) * 100;
  $("#planProgress").css("width", progressPercent + '%')
    .text(Math.round(progressPercent).toFixed(1) + '%')
}