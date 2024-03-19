function getDetailList() {
  let plan = $(this).data("plan");
  $("#planIdForDetail").val(plan.planId);
  $("#planTitle").text(plan.title);
  $("#planDate").html(`<i class="bi bi-calendar-week"></i> | ${plan.startDate} ~ ${plan.endDate}`);
  $("#planRemindAlarm").html(
    `${plan.remindAlarmDate === null ? ''
      : '<i class="bi bi-alarm"></i> | ' + plan.remindAlarmDate}`);
  if (plan.complete === 'Y') {
    $("#planComplete").prop("checked", true);
  }

  $.ajax({
    url: `/detail/list?planId=${plan.planId}`,
    type: "GET",
    dataType: "json",
    async: false,
    success: renderDetails,
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인 부터 해")
      }
    }
  });
}

function renderDetails(response) {
  let detailList = response.detailList;
  renderList(detailList);
  renderProgress(detailList);

  // 이벤트
  $(".form-check-input:not(#planComplete)").on("change", updateProgress);
  $("#planComplete").on("change", toggleComplete)
}

function toggleComplete(event) {
  let toggle = $(this).prop("checked");
  $(".form-check-input").prop("checked", toggle ? true : false)
  updateProgress();
}

function renderList(list) {
  let detailPlanListDiv = $("#detailList");
  detailPlanListDiv.empty();
  $.each(list, function (index, element) {
    detailPlanListDiv.append(renderOneDetail(element));
  });
}
function renderOneDetail(element) {
  return `<div class="accordion-item">
    <div class="accordion-header container h1">
      <div class="detail-item collapsed row" data-bs-toggle="collapse" data-bs-target="#collapse${element.detailPlanId}">
        <input class="col-1 my-auto mx-4 form-check-input" data-bs-toggle="collapse" type="checkbox" 
          ${element.complete === 'Y' ? "checked" : ""}/>
        <div class="col-6 p-4 text-truncate form-checked-content">
          ${element.contents}
        </div>
        <div class="col p-4 text-end" >
          <i class="bi bi-trash3"></i>
        </div>
      </div>
    </h1>
    <div id="collapse${element.detailPlanId}" class="accordion-collapse collapse" data-bs-parent="#detailPlanList">
      <div class="accordion-body">
        ${element.detailPlanId} <br>
        ${element.contents} <br>
        ${element.startTime} ~ ${element.endTime} <br>
        ${element.remindAlarmTime}
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

function updateProgress() {
  let checkedCount = $(".form-check-input:checked:not(#planComplete)").length;
  let totalCheckBoxCount = $(".form-check-input:not(#planComplete)").length;
  let progressPercent = (checkedCount / totalCheckBoxCount) * 100;
  $("#planProgress").css("width", progressPercent + '%')
    .text(Math.round(progressPercent).toFixed(1) + '%')
}