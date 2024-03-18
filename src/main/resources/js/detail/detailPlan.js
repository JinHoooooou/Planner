$(window).on("load", function () {
  getPlanAndDetails(planId)
  renderDetailCreateForm()

  $("#detailForm").submit(function (event) {
    event.preventDefault();
    createNewDetail();
  })

  $(".form-check-input").change(function () {
    updateProgress();
  })
});

function updateProgress() {
  let checkedCount = $(".form-check-input:checked").length;
  let totalCheckBoxCount = $(".form-check-input").length;
  let progressPercent = (checkedCount / totalCheckBoxCount) * 100;
  $("#planProgress").css("width", progressPercent + '%')
  .text(Math.round(progressPercent).toFixed(1) + '%')
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
