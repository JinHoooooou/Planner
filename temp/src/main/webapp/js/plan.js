




// function showInfo() {
//     if (document.getElementById('dropdown-menu').style.display == "none") {

//         document.getElementById('dropdown-menu').style.display = "flex";
//     } else {
//         document.getElementById('dropdown-menu').style.display = "none"
//         // document.getElementsByClassName('btn btn-secondary btn-sm show').style.display = "none";
//     }

// }



function submitPlanner() {
    const title = document.getElementById('title').value;
    let startDate = document.getElementById('startDate').value;
    let endDate = document.getElementById('endDate').value;
    let endAlarmDateBoolean = document.getElementById('endAlarmDateBoolean')
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

    if (startDate > endAlarmDate) {
     
        alert('마감 알람 날짜를 다시 설정해주세요!');
        return false;
    }

    if (endAlarmDateBoolean.checked && !endAlarmDate) {
        
        endAlarmDate = getCurrentDate();
    }
    
    const planner = { title, startDate, endDate, endAlarmDate };
    
    planners.push(planner);
   

    updatePlannerList();
    
    

    document.getElementById('title').value = '';
    
    document.getElementById('startDate').value = '';
   
    document.getElementById('endDate').value = '';
   
    document.getElementById('endAlarmDate').value = '';
    
    initializeDateInput()
    
    return true;
    
}

function togglePlannerDetails(index) {
    const plannerDetails = document.querySelectorAll('.plannerDetails');
    plannerDetails[index].classList.toggle('showDetails');
}

function deletePlanner(index) {
    planners.splice(index, 1);
    updatePlannerList();
}

function updatePlannerList() {
    const plannersList = document.getElementById('planners');
    plannersList.innerHTML = '';

    planners.forEach((planner, index) => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <div class="plannerItem">
                <div style="display : flex;">
                    <input type="radio" name="complete" value="complete" id="com_radio" onchange="deletePlanner(${index})">
                    <strong>${planner.title}</strong>
                </div>
                <div class="plannerDate"><span>${formatDate(planner.endDate)}</span></div>
                <span class="deleteButton" onclick="deletePlanner(${index})"><b>X</b></span>
            </div>`;
        listItem.addEventListener('click', () => {
            togglePlannerDetails(index);
        });
        plannersList.appendChild(listItem);

        const details = document.createElement('div');
        details.classList.add('plannerDetails');
        details.innerHTML = planner.title + "<div id='toDetailDiv'><button id='toDetailButton' onclick='moveToDetailPage()'>To Detail</button><div>";
        listItem.appendChild(details);
    });
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

window.onload = function () {
    initializeDateInput();
};

function initializeDateInput() {
    // 날짜 입력 필드 찾기
    var dateInput = document.getElementById('startDate');
    var dateInput2 = document.getElementById('endDate');
    var dateInput3 = document.getElementById('endAlarmDate');

    // 현재 날짜를 포함한 임의의 날짜로 설정 (형식을 강제로 바꾸기 위함)
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
    var day = ('0' + currentDate.getDate()).slice(-2);
    var today = year + '-' + month + '-' + day;

    // 날짜 입력 필드의 값을 현재 날짜로 설정
    dateInput.value = today;
    dateInput2.value = today;
    dateInput3.value = today;

    
}

function moveToDetailPage() {
    window.location.href = 'https://www.google.com';
}
