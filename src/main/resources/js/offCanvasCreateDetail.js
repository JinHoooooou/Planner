function createNewDetail() {
  let planId = $("#planIdForDetail").val();
  let formData = $("#detailForm").serialize();
  formData += `&planId=${planId}`
  console.log(formData)
  $.ajax({
    url: POST_CREATE_DETAIL,
    type: "POST",
    contentType: "application/x-www-form-urlencoded",
    data: formData,
    dataType: "json",
    success: function (response) {
      let detailPlanListDiv = $("#detailPlanList");
      detailPlanListDiv.append(renderOneDetail(response))
      $("#detailCreateForm").removeClass("show");
    },
    error: function () {
      alert("Error");
    }
  })
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
