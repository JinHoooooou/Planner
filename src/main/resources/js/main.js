let planList = [];

window.onload = function () {
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
					if(planList[i].remindAlarmDate != undefined) {
						if((new Date(planList[i].remindAlarmDate).getTime() < new Date().getTime()) ) {
							if(new Date(planList[i].endDate).getMonth() > new Date().getMonth()) {
								alert(planList[i].title + "마감 알람입니다!");
							}
							if(new Date(planList[i].endDate).getMonth() == new Date().getMonth()) {
								if(new Date(planList[i].endDate).getDate() >= new Date().getDate()) {
									alert(planList[i].title + "마감 알람입니다!");
								}
							}
							
							
						}
					}
					
				}
			}
		},
		error: function () {
			alert("연결실패!")
		},
	})
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
	

	
};

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
		return startDateDesc;
	}

	sortEndDateASC();

	document.getElementById("notCompleted").innerHTML = count;
	document.getElementById("completed").innerHTML = planList.length - count;
	document.getElementById("endDateASC").onclick = function () { sortEndDateASC(planList); }
	document.getElementById("endDateDESC").onclick = function () { sortEndDateDESC(planList); }
	document.getElementById("startDateASC").onclick = function () { sortStartDateASC(planList); }
	document.getElementById("startDateDESC").onclick = function () { sortStartDateDESC(planList); }
	document.getElementById("userIdInfo").innerHTML = response.nickname;
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
			`<li>
		<div class="plannerItem">
		<div style="display: flex;">
			<input type="checkbox" name="complete" value="complete"
				class="comRadio" onchange="completePlanner(${planListComp})"
				${planComplete === 'Y' ? 'checked' : ''}
				><strong id="listTitle-${planId}" ${planComplete === 'Y' ? 'style="text-decoration : line-through; text-decoration-thickness: 3px; "' : ''}>
					${planTitle}
				</strong>
		</div>
		<div class="plannerDate">
			<span id="listEndDate">${planEndDate}</span>
		</div>
		<span class="deleteButton" onclick="deletePlanner(${planListDel})"><b>X</b></span>
	</div>
</li>`;

		
	}
	document.getElementById("plannersEle").innerHTML = childNodes;
	for(let i=0; i<planList.length; i++) {
		let planAlarmDate = planList[i].remindAlarmDate;
		let planEndDate = planList[i].endDate;
		let planComplete = planList[i].complete;
		if(planAlarmDate != null) {
			// 마감 알람 울린 플랜에 애니메이션 효과 주기
			if(new Date(planAlarmDate).getTime() < new Date().getTime()) {
				if(planComplete === 'N') {
					document.getElementsByClassName("plannerItem")[i].setAttribute("style", "animation: heartBeat 1s ease-in-out infinite;" );
				}
			}
		}
		// 마감 기한이 지난 플랜에 스타일 주기
		if(new Date(planEndDate).getDate() < new Date().getDate()) {
			document.getElementsByClassName("plannerItem")[i].setAttribute("style", "border: 1px solid red;");
		}

	}

	
}

function deletePlanner(index) {
	let formData = { "planId": index };
	$.ajax({
		url: "/delete.pl",
		type: "get",
		data: formData,
		success: function () {
			alert("삭제 성공!")
			location.reload();
		},
		error: function (error) {
			alert("삭제 실패!")
			console.log(error)
		}
	})
}

function completePlanner(index) {
	let formData = { "planId": index };
	$.ajax({
		url: "/complete.pl",
		type: "get",
		data: formData,
		success: function () {
			alert("완료 성공!")
			location.reload();
		},
		error: function (error) {
			alert("완료 실패!")
			console.log(error)
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
			location.reload();
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
	const month = (today.getMonth() + 1).toString().padStart(2, '0');
	const day = today.getDate().toString().padStart(2, '0');
	return `${today.getFullYear()}-${month}-${day}`;
}

function initializeDateInput() {
	// 날짜 입력 필드 찾기
	var dateInput = document.getElementById('startDate');
	var dateInput2 = document.getElementById('endDate');

	// 현재 날짜를 포함한 임의의 날짜로 설정 (형식을 강제로 바꾸기 위함)
	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
	var day = ('0' + currentDate.getDate()).slice(-2);
	var today = year + '-' + month + '-' + day;

	// 날짜 입력 필드의 값을 현재 날짜로 설정
	dateInput.value = today;
	dateInput2.value = today;
}

// // 다크 모드 적용
// document.addEventListener("DOMContentLoaded", function() {
//     const darkModeToggle = document.getElementById("switch");
//     const body = document.body;

//     // 초기화: 저장된 테마 모드가 있으면 적용, 없으면 시스템 설정에 따라 초기화
//     if (localStorage.getItem("darkMode") === "enabled") {
//         enableDarkMode();
//     } else if (localStorage.getItem("darkMode") === "disabled") {
//         disableDarkMode();
//     } else if (window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)").matches) {
//         enableDarkMode();
//     }

//     // 다크 모드 전환 버튼 클릭 시 토글
//     darkModeToggle.addEventListener("click", () => {
		
//         if (body.classList.contains("dark-mode")) {
//             disableDarkMode();
//         } else {
//             enableDarkMode();
//         }
//     });

//     // 다크 모드 활성화
//     function enableDarkMode() {
//         body.classList.add("dark-mode");
//         localStorage.setItem("darkMode", "enabled");
//     }

//     // 다크 모드 비활성화
//     function disableDarkMode() {
//         body.classList.remove("dark-mode");
//         localStorage.setItem("darkMode", "disabled");
//     }
// });



