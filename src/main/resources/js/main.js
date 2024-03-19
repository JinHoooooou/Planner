$(window).ready(function () {
  $.ajax({
    url: "/plan/list",
    type: "GET",
    async: false,
    dataType: "json",
    success: renderMainPage,
    error: function (xhr) {
      if (xhr.status === 401) {
        alert("로그인부터해")
        window.location.href = "user/login.html";
      }
    }
  })
})
function renderMyInfo(nickname) {
  $("#nickname").text(nickname);
}

function renderCompletePlan(planList) {
  let total = planList.length;
  let completeCount = 0;
  for (let i = 0; i < planList.length; i++) {
    completeCount += planList[i].complete ? 1 : 0;
  }
  $("#completedPlan").text(completeCount);
  $("#notCompletedPlan").text(Number(total - completeCount));
}

function renderCreateForm() {
  let today = new Date();
  let todayDate = today.getFullYear() + '-'
    + String(today.getMonth() + 1).padStart(2, '0') + '-'
    + String(today.getDate()).padStart(2, '0')
  $("#startDate").val(todayDate);
  $("#endDate").val(todayDate)
  $("#remindAlarmDate").val(todayDate);
}

function renderMainPage(response) {
    let planList = $("#planList");
    renderMyInfo(response.writer);
    renderCompletePlan(response.planList);
    renderCreateForm();

    planList.empty();
    $.each(response.planList, function (index, plan) {
        planList.append(`
            <li>
                <div class="plannerItem">
                    <div style="display: flex;">
                        <input type="checkbox" name="complete" class="com_radio"
                            ${plan.complete ? "checked" : ""} 
                            onchange="completePlanner(${plan.planId})" />
                        <strong> ${plan.title} </strong>
                    </div>
                    <div class="plannerDate">
                        <span>
                        ${plan.endDate}
                        </span>
                    </div>
                    <span class="deleteButton" onclick="deletePlanner(${plan.planId})"><b>X</b></span>
                </div>
            </li>
            `)
    })
}

function completePlanner(planId) {
  /*
  ajax로 update api 호출해서 complete 수정
      성공시 반영
          title에 취소줄 반영, class="plan-complete" 이용
          완료할 작업 수 / 완료한 작업수 반영, id=notCompletedPlan / id=completedPlan 이용
      실패시 오류메세지
  */

}

function deletePlanner(planId) {
  /*
  ㄹㅇ 삭제할건지 물어보고
  ajax로 delete api 호출해서 Plan 삭제
      성공시 반영
          단순히 main.html 페이지 reload
      실패시 오류메세지
  */
}

function moveToDetailPage() {
  window.location.href = 'https://www.google.com';
}
