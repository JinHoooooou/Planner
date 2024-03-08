function getPlanAndDetails(planId) {
  $.ajax({
    url: `${GET_DETAIL_PLANS}?planId=${planId}`,
    type: "GET",
    async: false,
    success: renderPlanAndDetails,
    error: error
  });
}

function createNewDetail() {
  let formData = {};
  $("#detailForm").serializeArray().forEach(function (element) {
    formData[element.name] = element.value;
  })
  formData["planId"] = planId;
  formData["writer"] = writer;

  $.ajax({
    url: "/detail/create",
    type: "POST",
    contentType: "application/x-www-form-urlencoded",
    data: formData,
    success: function () {
      location.reload();
    },
    error: function () {
      alert("Error");
    }
  })
}
