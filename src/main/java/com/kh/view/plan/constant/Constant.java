package com.kh.view.plan.constant;

public interface Constant {


  String LINE = "\r\n";
  String MAIN_HEAD = "======= MINI PLANNER APP =======";
  String MAIN_CREATE_PLAN = "1. Plan 생성";
  String MAIN_LIST_PLAN = "2. Plan List 보기";
  String MAIN_SELECT_PLAN = "3. Plan 선택";
  String MAIN_UPDATE_PLAN = "4. 타이머 수정";
  String MAIN_DELETE_PLAN = "5. Plan 삭제";
  String MAIN_MODIFY_TIMER = "6. 타이머 수정";
  String MAIN_EXIT = "9. 프로그램 종료";
  String EXIT_APP = "프로그램을 종료합니다.";

  String INPUT_MENU = "메뉴 번호 선택: ";
  String INPUT_INDEX = "Index를 입력하세요.";
  String EMPTY = "저장된 타이머가 없습니다.";
  String INPUT_ERROR = "잘못 입력하였습니다. 다시 입력해주세요.";

  String CREATE_PLAN_HEAD = "======= Plan 생성 =======";
  String CREATE_PLAN_INPUT_TITLE = "Title 입력: ";
  String CREATE_PLAN_RESULT_SUCCESS_FORMAT = "새 Plan을 생성하였습니다. %s";
  String CREATE_PLAN_RESULT_FAIL = "새 Plan 생성에 실패하였습니다.";

  String SELECT_PLAN_HEAD = "======= Plan 선택 =======";
  String LIST_ALL_PLAN_HEAD = "======= 모든 Plan 보기 =======";

  String UPDATE_PLAN_HEAD = "======= Plan 수정 =======";
  String UPDATE_INPUT_TITLE = "수정할 Title 입력: ";
  String UPDATE_ASK_USER_INPUT_TIME = "시간도 수정 하겠습니까? (Y/N): ";

  String UPDATE_INPUT_HOUR = "수정할 Hour 입력: ";
  String UPDATE_INPUT_MINUTE = "수정할 Minute 입력: ";
  String UPDATE_INPUT_SECOND = "수정할 Second 입력: ";
  String UPDATE_RESULT_SUCCESS_FORMAT = "타이머를 수정하였습니다.(%s -> %s)";
  String UPDATE_RESULT_FAIL = " 타이머 수정에 실패하였습니다.";

  String DELETE_HEAD = "======= 타이머 삭제 =======";
  String DELETE_SUCCESS = "타이머를 삭제했습니다.";
  String DELETE_FAIL = "타이머 삭제에 실패하였습니다.";
}
