let planList = [];

window.onload = function () {
	
	initializeDateInput();
	planners = [];
	var now_utc = Date.now()
	var timeOff = new Date().getTimezoneOffset() * 60000;
	var today = new Date(now_utc - timeOff).toISOString().split("T")[0];

	document.getElementById("startDate").setAttribute("min", today);
	document.getElementById("endDate").setAttribute("min", today);
	document.getElementById("endAlarmDate").setAttribute("min", today);

	// html 인라인으로 선언한 함수 부분 수정하기
	document.getElementById("endAlarmDateBoolean").onchange = is_checked;
	document.getElementById("save").onclick = submitPlanner;

	// 검색 기능
	document.getElementById("searchButton").onclick = searchFunction;

	document.getElementById("search").onkeyup = processChange;
	$("#logout").on("click", requestLogOut);

	document.getElementById("switch").onchange = darkMode;
	getPlanList();
};

function getPlanList() {
	$.ajax({
		url: "/plan/list",
		type: "get",
		dataType: "json",
		success: function (response) {
			planList = response.planList;
			drawPlanList(response);
			for (let i = 0; i < planList.length; i++) {
				if (planList[i].complete === 'N') {

					// 마감 알람 설정 함수(alert)
					if (planList[i].remindAlarmDate != undefined) {
						if ((new Date(planList[i].remindAlarmDate).getTime() < new Date().getTime())) {
							if (new Date(planList[i].endDate).getMonth() > new Date().getMonth()) {
								$(`#alarmMessage-${planList[i].planId}`).removeClass("d-none")
							}
							if (new Date(planList[i].endDate).getMonth() == new Date().getMonth()) {
								if (new Date(planList[i].endDate).getDate() >= new Date().getDate()) {
									$(`#alarmMessage-${planList[i].planId}`).removeClass("d-none")
								}
							}
						}
					}

				}
			}
		},
		error: function () {
			alert("연결실패!")
			window.location.href = "/index.html";
		},
	})
}

function darkMode() {
	let body = document.body;
	body.classList.toggle("dark-mode");
	let h1 = document.getElementById("h-title")
	h1.classList.toggle("dark-mode");
	let icon_dark = document.getElementsByClassName("icon-dark")
	let ic_dark = document.getElementsByClassName("ic-dark")
	for (let i = 0; i < icon_dark.length; i++) {
		icon_dark[i].classList.toggle("dark-mode");
		ic_dark[i].classList.toggle("dark-mode");
	}
	let main = document.querySelector("main")
	main.classList.toggle("dark-mode-main");
	let input_dark = document.getElementsByClassName("input-dark")
	for (let i = 0; i < input_dark.length; i++) {
		input_dark[i].classList.toggle("dark-mode-input");

	}
	let completion = document.getElementsByClassName("completion")
	for (let i = 0; i < completion.length; i++) {
		completion[i].classList.toggle("dark-mode");
	}
	let searchButton_dark = document.getElementById("searchButton")
	searchButton_dark.classList.toggle("dark-mode");
	let dark_scheme = document.getElementsByClassName("scheme-dark")
	for (let i = 0; i < dark_scheme.length; i++) {
		dark_scheme[i].classList.toggle("dark-scheme");
	}
	let save = document.getElementById("save")
	save.classList.toggle("dark-mode-save");
	document.getElementById("plDiv").classList.toggle("dark-mode");

	let plannerItem = document.getElementsByClassName("plannerItem");
	for (let i = 0; i < plannerItem.length; i++) {
		plannerItem[i].classList.toggle("dark-mode");
	}
	let EndListDate = document.getElementsByClassName("EndListDate");
	for (let i = 0; i < EndListDate.length; i++) {
		EndListDate[i].classList.toggle("dark-mode");
	}


}

function requestLogOut() {
	$.ajax({
		url: "/user/logout",
		success: function () {
			window.location.href = "/index.html"
		},
		error: function () { alert("invalid error") }
	})
}
function debounce(func, timeout = 300) {
	let timer;
	return (...args) => {
		clearTimeout(timer);
		timer = setTimeout(() => {
			func.apply(this, args);
		}, timeout);
	};
}

const processChange = debounce(() => searchFunction());

function enterkey(e) {
	if (e.keyCode == 13) {
		searchFunction();
	}

}
// 메인페이지 & 검색 시 페이지
function drawPlanList(response) {
	let switch_dark = document.getElementById("switch");
	let plannerItem = document.getElementsByClassName("plannerItem");
	let EndListDate = document.getElementsByClassName("EndListDate");

	let count = 0;
	for (let i = 0; i < planList.length; i++) {
		if (planList[i].complete === 'N') {
			count++;
		}
	}
	function sortEndDateASC() {
		const endDateAsc = planList.sort(function (a, b) {
			if (new Date(a["endDate"]).getTime() < new Date(b["endDate"]).getTime())
				return -1;
			if (new Date(a["endDate"]).getTime() > new Date(b["endDate"]).getTime())
				return 1;
			return 0;
		});

		showTodoList();
		if (switch_dark.checked) {
			for (let i = 0; i < plannerItem.length; i++) {
				plannerItem[i].classList.add("dark-mode");
				EndListDate[i].classList.add("dark-mode");
			}
		}
		return endDateAsc;
	}

	function sortEndDateDESC(planList) {
		const endDateDesc = planList.sort(function (a, b) {
			if (new Date(a["endDate"]).getTime() < new Date(b["endDate"]).getTime())
				return 1;
			if (new Date(a["endDate"]).getTime() > new Date(b["endDate"]).getTime())
				return -1;
			return 0;
		});

		showTodoList();
		if (switch_dark.checked) {
			for (let i = 0; i < plannerItem.length; i++) {
				plannerItem[i].classList.add("dark-mode");
				EndListDate[i].classList.add("dark-mode");
			}
		}
		return endDateDesc;
	}

	function sortStartDateASC(planList) {
		const startDateAsc = planList.sort(function (a, b) {
			if (new Date(a["startDate"]).getTime() < new Date(b["startDate"]).getTime())
				return -1;
			if (new Date(a["startDate"]).getTime() > new Date(b["startDate"]).getTime())
				return 1;
			return 0;
		});
		showTodoList();
		if (switch_dark.checked) {
			for (let i = 0; i < plannerItem.length; i++) {
				plannerItem[i].classList.add("dark-mode");
				EndListDate[i].classList.add("dark-mode");
			}
		}
		return startDateAsc;
	}

	function sortStartDateDESC(planList) {
		const startDateDesc = planList.sort(function (a, b) {
			if (new Date(a["startDate"]).getTime() < new Date(b["startDate"]).getTime())
				return 1;
			if (new Date(a["startDate"]).getTime() > new Date(b["startDate"]).getTime())
				return -1;
			return 0;
		});
		showTodoList();
		if (switch_dark.checked) {
			for (let i = 0; i < plannerItem.length; i++) {
				plannerItem[i].classList.add("dark-mode");
				EndListDate[i].classList.add("dark-mode");
			}
		}
		return startDateDesc;
	}

	sortEndDateASC();

	document.getElementById("notCompleted").innerHTML = count;
	document.getElementById("completed").innerHTML = planList.length - count;
	document.getElementById("endDateASC").onclick = function () { sortEndDateASC(planList); }
	document.getElementById("endDateDESC").onclick = function () { sortEndDateDESC(planList); }
	document.getElementById("startDateASC").onclick = function () { sortStartDateASC(planList); }
	document.getElementById("startDateDESC").onclick = function () { sortStartDateDESC(planList); }
	document.getElementById("nickname").innerHTML = response.nickname;
}

function searchFunction() {
	const searchTitle = document.getElementById("search").value
	let formData = { "search": searchTitle };

	$.ajax({
		url: "/search.pl",
		type: "post",
		data: formData,
		dataType: "json",
		success: function (response) {
			planList = response.planList;
			drawPlanList(response);
		},
		error: function (error) {
			console.log(error)
		}
	})
}


function showTodoList() {
	let planTitle = null;
	let planEndDate = null;
	let childNodes = '';
	let planAlarmDate = null;

	for (let i = 0; i < planList.length; i++) {
		planTitle = planList[i].title;
		planAlarmDate = planList[i].remindAlarmDate;
		planEndDate = planList[i].endDate;
		planListDel = planList[i].planId;
		planListComp = planList[i].planId;
		planComplete = planList[i].complete;
		childNodes +=
			`<li id="plan-${planList[i].planId}">
		<div class="plannerItem">
		<div style="display: flex;">
			<input type="checkbox" name="complete" value="complete"
				class="comRadio" onchange="completePlanner(${planListComp})"
				${planComplete === 'Y' ? 'checked' : ''}
				><strong id="listTitle-${i}" ${planComplete === 'Y' ? 'style="text-decoration : line-through; text-decoration-thickness: 3px; "' : ''}>
					${planTitle}
				</strong>
		</div>
		<div class="plannerDate">
			<i id="alarmMessage-${planList[i].planId}" class="bi bi-alarm text-danger d-none"></i>
			<span id="listEndDate" class="EndListDate">${planEndDate}</span>
		</div>
		<span class="deleteButton" onclick="deletePlanner(${planListDel})"><b>X</b></span>
	</div>
</li>`;


	}
	document.getElementById("plannersEle").innerHTML = childNodes;
	for (let i = 0; i < planList.length; i++) {
		$(`#listTitle-${i}`).attr({
			"data-bs-toggle": "offcanvas",
			"data-bs-target": "#detailPlan"
		}).data("plan", planList[i])
			.on("click", getDetailList);
	}

	for (let i = 0; i < planList.length; i++) {
		let planAlarmDate = planList[i].remindAlarmDate;
		let planEndDate = planList[i].endDate;
		let planComplete = planList[i].complete;
		if (planAlarmDate != null) {

			if (new Date(planAlarmDate).setHours(0, 0, 0, 0) < new Date().getTime()) {
				if (planComplete === 'N') {
					document.getElementsByClassName("plannerItem")[i].setAttribute("style", "animation: heartBeat 1s ease-in-out infinite;");
					$(`#alarmMessage-${planList[i].planId}`).removeClass("d-none");
				}
			}
		}
		// 마감 기한이 지난 플랜에 스타일 주기

		if (new Date(planEndDate).getMonth() < new Date().getMonth()) {
			document.getElementsByClassName("plannerItem")[i].setAttribute("style", "border: 2px solid red;");
		}

		if (new Date(planEndDate).getMonth() === new Date().getMonth()) {
			if (new Date(planEndDate).getDate() < new Date().getDate()) {
				document.getElementsByClassName("plannerItem")[i].setAttribute("style", "border: 2px solid red;");
			}
		}

	}
}

function deletePlanner(planId) {
	if (confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			url: `/plan/${planId}`,
			type: "DELETE",
			success: function () {
				// location.reload();
				getPlanList();
			},
			error: function (xhr) {
				if (xhr.status === 401) {
					alert("로그인이 필요한 페이지 입니다.");
					window.location.href = "/user/signin.html";
				} else {
					alert("invalid error");
					location.reload();
				}
			}
		})
	}
}

function completePlanner(planId) {
	let formCheckInput = $(`#plan-${planId} .comRadio`);
	let complete = $(formCheckInput).prop("checked");
	$.ajax({
		url: `/plan/${planId}`,
		type: "PATCH",
		data: JSON.stringify({ "complete": (complete ? "Y" : "N") }),
		contentType: "application/json",
		success: function () {
			// locataion.reload();
			getPlanList();
		},
		error: function (xhr) {
			if (xhr.status === 401) {
				alert("로그인이 필요한 페이지 입니다.");
				window.location.href = "/user/signin.html";
			} else {
				alert("invalid error");
				location.reload();
			}
		}
	})
}

function is_checked() {
	if (document.getElementById("endAlarmDateBoolean").checked == true) {
		document.getElementById("endAlarmDate").readOnly = false;
	} else if (document.getElementById("endAlarmDateBoolean").checked == false) {
		document.getElementById("endAlarmDate").readOnly = true;
		document.getElementById("endAlarmDate").value = "";
	}
}

function submitPlanner() {
	const title = document.getElementById('title').value;
	let startDate = document.getElementById('startDate').value;
	let endDate = document.getElementById('endDate').value;
	let endAlarmDateBoolean = document
		.getElementById('endAlarmDateBoolean')
	let endAlarmDate = document.getElementById('endAlarmDate').value;

	if (!title) {
		alert('제목을 입력해주세요!');
		document.getElementById('title').focus();
		return false;
	}

	if (!startDate) {
		startDate = getCurrentDate();
	}

	if (!endDate) {
		endDate = getCurrentDate();
	}

	if (startDate > endDate) {
		alert('마감 날짜를 다시 설정해주세요!');
		return false;
	}
	if (endAlarmDateBoolean.checked) {
		if (startDate > endAlarmDate) {
			alert('마감 알람 날짜를 다시 설정해주세요!');
			return false;
		}
	}
	if (endAlarmDateBoolean.checked) {
		if (endDate < endAlarmDate) {
			alert('마감 알람 날짜를 다시 설정해주세요!');
			return false;
		}
	}
	if (endAlarmDateBoolean.checked && !endAlarmDate) {
		endAlarmDate = getCurrentDate();
	}
	let formData = { "title": title, "startDate": startDate, "endDate": endDate, "remindAlarmDate": endAlarmDate }
	$.ajax({
		url: "/plan/create",
		type: "post",
		data: formData,
		success: function () {
			alert("추가 성공!")
			// location.onload();
			getPlanList();
		},
		error: function () {
			alert("추가 실패!")
		}
	})
}

function formatDate(dateString) {
	const date = new Date(dateString);
	const month = (date.getMonth() + 1).toString().padStart(2, '0');
	const day = date.getDate().toString().padStart(2, '0');
	return `${month}월 ${day}일`;
}

function getCurrentDate() {
	const today = new Date();
	console.log(today);
	const month = (today.getMonth() + 1).toString().padStart(2, '0');
	const day = today.getDate().toString().padStart(2, '0');
	return `${today.getFullYear()}-${month}-${day}`;
	
}

function initializeDateInput() {
	// 날짜 입력 필드 찾기
	let dateInput = document.getElementById('startDate');
	let dateInput2 = document.getElementById('endDate');
	
	
	// 현재 날짜를 포함한 임의의 날짜로 설정 (형식을 강제로 바꾸기 위함)
	let currentDate = new Date();
	let year = currentDate.getFullYear();
	let month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
	let day = ('0' + currentDate.getDate()).slice(-2);
	let today = year + '-' + month + '-' + day;

	// 날짜 입력 필드의 값을 현재 날짜로 설정
	dateInput.value = today;
	dateInput2.value = today;
	
}





