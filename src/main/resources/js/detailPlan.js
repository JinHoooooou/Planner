$(window).on("load", function () {
  $.ajax({
    url: "/plan?planId=1",
    type: "GET",
    async: false,
    success: function (response) {
      let plan = response.plan;
      let detailPlanList = response.detailPlanList;
      printPlan(plan);
      printDetailPlanList(detailPlanList);
    },
    error: function () {},
  });
  $(".list-group-item").click(function () {
    $(".list-group-item").attr("data-status", "inactive");
    $(this).attr("data-status", "active");
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
    detailPlanListDiv.append(`
      <div class="list-group-item list-group-item-action clickable-item" style="border:1px solid red" data-status="inactive">
      <input class="form-check-input" type="checkbox" value checked style="font-size: 1.375em" />
      <span class="pt-1 form-checked-content">
        <strong class="d-inline-block text-truncate" style="width:300px">${element.contents}</strong>
        <small class="d-block text-body-secondary">
          <i class="bi bi-calendar-event"></i>
          ${element.startTime} ~ ${element.endTime}
        </small>
      </span>
    </div>
      `);
  });
}
