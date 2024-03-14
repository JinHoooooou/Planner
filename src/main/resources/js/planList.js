$(window).on("load", function () {
  getPlanList()
  addOpenOffCanvasEventToButton();
});

function getPlanList() {
  $.ajax({
    url: `${GET_PLANS}`,
    type: "GET",
    async: false,
    success: renderPlanList,
    error: redirectErrorPage
  })
}

function renderPlanList(response) {
  let planList = response.planList;
  let planListDiv = $("#planList");

  $.each(planList, function (index, element) {
    planListDiv.append(renderOnePlan(index, element));
  });
}

function renderOnePlan(index, element) {
  return `
  <div id="plan-${element.planId}" class="row my-3 text-center justify-content-center" style="border : 1px solid red">
  <div class="col">
    <p>${element.planId}</p><br/>
    <p>${element.title}</p><br/>
    <p>${element.startDate}</p><br/>
    <p>${element.endDate}</p><br/>
    <p>${element.remindAlarmDate}</p><br/>
    <p>${element.complete}</p><br/>
  </div>
    <div class="col-2 my-3" style="border: 1px solid blue">
      <button id="detailsButton-${element.planId}" class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#detailPlan">
      버튼
      </button>
    </div>
  </div>
  `
}

function addOpenOffCanvasEventToButton() {
  $("[id^=detailsButton]").click(function () {
    let planId = $(this).attr("id").split("-")[1];
    getPlanAndDetails(planId);
  })
}
