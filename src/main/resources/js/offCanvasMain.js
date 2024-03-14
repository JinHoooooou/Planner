function getPlanAndDetails(planId) {
  $.ajax({
    url: `${GET_DETAILS}?planId=${planId}`,
    type: "GET",
    dataType: "json",
    async: false,
    success: renderOffCanvas,
    error: redirectErrorPage
  });
}

function renderOffCanvas(response) {
  renderPlan(response.plan);
  renderDetails(response.detailPlanList);
  renderProgress(response.detailPlanList);
  $(".form-check-input").change(function () {
    updateProgress();
  })

  $("#detailCreateFormButton").click(function () {
    renderDetailCreateForm(response.plan.planId);
  })

  $("#detailForm").submit(function (event) {
    event.preventDefault();
    createNewDetail();
  })
}

function renderPlan(element) {
  $("#planIdForDetail").html(element.planId);
  $("#planTitle").html(element.title);
  $("#planWriter").html(element.writer);
  $("#planDate").html(`${element.startDate} ~ ${element.endDate}`);
  $("#planRemindAlarm").html(`${element.remindAlarmTime}`);
  $("#planComplete").html(element.complete ? "완료" : "미완료");
  $("#detailCreateForm").removeClass("show");
}

function renderDetails(list) {
  let detailPlanListDiv = $("#detailPlanList");
  detailPlanListDiv.empty();
  $.each(list, function (index, element) {
    detailPlanListDiv.append(renderOneDetail(element));
  });
}

function renderOneDetail(element) {
  return `<div class="accordion-item">
    <h1 class="accordion-header container"">
      <div class="detail-item collapsed row" data-bs-toggle="collapse" data-bs-target="#collapse${element.detailPlanId}">
        <input class="col-1 my-auto mx-4 form-check-input" data-bs-toggle="collapse" type="checkbox" 
          ${element.complete ? "checked" : ""}/>
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
  if (list.length === 0) {
    $("#planProgress").css("width", '0%').text('0%');
  }

  let checkedCount = 0;
  $.each(list, function (index, element) {
    checkedCount += element.complete ? 1 : 0;
  });

  $("#planProgress").css("width", (checkedCount / list.length) * 100 + '%')
  .text(Math.round((checkedCount / list.length) * 100).toFixed(1) + '%');
}

function updateProgress() {
  let checkedCount = $(".form-check-input:checked").length;
  let totalCheckBoxCount = $(".form-check-input").length;
  let progressPercent = (checkedCount / totalCheckBoxCount) * 100;
  $("#planProgress").css("width", progressPercent + '%')
  .text(Math.round(progressPercent).toFixed(1) + '%')
}

