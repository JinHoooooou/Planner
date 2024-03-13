function error() {
  alert("Error!!")
}

function renderPlanAndDetails(response) {
  renderPlan(response.plan);
  renderDetails(response.detailPlanList);
}

function renderPlan(element) {
  writer = element.writer;
  $("#planTitle").html(element.title);
  $("#planWriter").html(element.writer);
  $("#planDate").html(`${element.startDate} ~ ${element.endDate}`);
  $("#planRemindAlarm").html(`${element.remindAlarmTime}`);
  $("#planComplete").html(element.complete ? "완료" : "미완료");
}

function renderDetails(list) {
  let detailPlanListDiv = $("#detailPlanList");
  let checkedCount = 0;
  $.each(list, function (index, element) {
    detailPlanListDiv.append(renderOneDetail(index, element));
    checkedCount = element.complete ? checkedCount + 1 : checkedCount;
  });
  $("#planProgress").css("width", (checkedCount / list.length) * 100 + '%')
  .text(Math.round((checkedCount / list.length) * 100).toFixed(1) + '%');
}

function renderOneDetail(index, element) {
  return `<div class="accordion-item">
    <h1 class="accordion-header container"">
      <div class="detail-item collapsed row" data-bs-toggle="collapse" data-bs-target="#collapse${index}">
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
    <div id="collapse${index}" class="accordion-collapse collapse" data-bs-parent="#detailPlanList">
      <div class="accordion-body">
        ${element.contents} <br>
        ${element.startTime} ~ ${element.endTime} <br>
        ${element.remindAlarmTime}
      </div>
    </div>
  </div>`
}

function renderDetailCreateForm() {
  let today = new Date();
  $("#startDate").val(parseToDate(today));
  $("#startTime").val(parseToTime(today));
  $("#endTime").val(parseToAfterOneHour(today))
  $("#remindAlarmTime").val(parseToDate(today) + 'T' + parseToTime(today));
}

function parseToDate(date) {
  return date.getFullYear()
      + '-'
      + String(date.getMonth() + 1).padStart(2, '0')
      + '-'
      + String(date.getDate()).padStart(2, '0')
}

function parseToTime(date) {
  return `${date.getHours()}:${date.getMinutes()}`
}

function parseToAfterOneHour(date) {
  date.setHours(date.getHours() + 1);
  return `${date.getHours()}:${date.getMinutes()}`
}
