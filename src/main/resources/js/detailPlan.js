$(window).on("load", function () {
  let planId = new URLSearchParams(window.location.search).get("planId");

  $.ajax({
    url: `/plan?planId=${planId}`,
    type: "GET",
    async: false,
    success: function (response) {
      let plan = response.plan;
      let detailPlanList = response.detailPlanList;
      printPlan(plan);
      printDetailPlanList(detailPlanList);
    },
    error: function () { },
  });
});

function printPlan(element) {
  $("#planTitle").html(element.title);
  $("#planWriter").html(element.writer);
  $("#planDate").html(`${element.startDate} ~ ${element.endDate}`);
  $("#planRemindAlarm").html(element.remindAlarmDate == null ? "없어" : element.remindAlarmDate);
  $("#planComplete").html(element.complete ? "완료" : "미완료");
}

function printDetailPlanList(list) {
  let detailPlanListDiv = $("#detailPlanList");
  $.each(list, function (index, element) {
    detailPlanListDiv.append(`<div class="accordion-item">
    <h1 class="accordion-header container"">
      <div class="detail-item collapsed row" data-bs-toggle="collapse"
        data-bs-target="#collapse${index}" aria-expanded="false" aria-controls="collapse${index}">
        <input class="col-1 my-auto mx-4 form-check-input" data-bs-toggle="collapse" type="checkbox" ${element.complete ? "checked" : ""}/>
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
  </div>
`
    );
  });
}

// `
//       <div class="list-group-item list-group-item-action list-group-item-action-secondary clickable-item" style="border:1px solid red" data-status="inactive">
//       <input class="form-check-input" type="checkbox" value checked style="font-size: 1.375em" />
//       <span class="pt-1 form-checked-content">
//         <strong class="d-inline-block text-truncate" style="width:300px">${element.contents}</strong>
//         <small class="d-block text-body-secondary">
//           <i class="bi bi-calendar-event"></i>
//           ${element.startTime} ~ ${element.endTime}
//         </small>
//       </span>
//     </div>
//       `;
