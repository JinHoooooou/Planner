package main.com.kh.view.timer.constant;

public interface Constant {
  int DEFAULT_HOUR = 0;
  int DEFAULT_MINUTE = 20;
  int DEFAULT_SECOND = 0;

  String LINE = "\r\n";
  String MAIN_HEAD = "======= MINI TIMER APP =======";
  String MAIN_CREATE = "1. 타이머 생성";
  String MAIN_READ_ONE = "2. 타이머 보기";
  String MAIN_READ_ALL = "3. 모든 타이머 보기";
  String MAIN_UPDATE = "4. 타이머 수정";
  String MAIN_DELETE = "5. 타이머 삭제";
  String MAIN_EXIT = "9. 프로그램 종료";
  String MAIN_INPUT_MENU = "메뉴 번호 선택: ";
  String EXIT_APP = "프로그램을 종료합니다.";
  String INPUT_INDEX = "타이머 Index를 입력하세요.";

  String EMPTY = "저장된 타이머가 없습니다.";
  String INPUT_ERROR = "잘못 입력하였습니다. 다시 입력해주세요.";
  String CREATE_HEAD = "======= 타이머 생성 =======";
  String CREATE_INPUT_TITLE = "Title 입력: ";
  String CREATE_ASK_USER_INPUT_TIME = "시간도 입력하시겠습니까? 기본값은 20분입니다. (Y/N): ";
  String CREATE_INPUT_HOUR = "Hour 입력: ";
  String CREATE_INPUT_MINUTE = "Minute 입력: ";
  String CREATE_INPUT_SECOND = "Second 입력: ";
  String CREATE_RESULT_SUCCESS_FORMAT = "새 타이머를 생성하였습니다. %s";
  String CREATE_RESULT_FAIL = "새 타이머 생성에 실패하였습니다.";

  String READ_ONE_HEAD = "======= 타이머 보기 =======";
  String READ_ALL_HEAD = "======= 모든 타이머 보기 =======";

  String UPDATE_HEAD = "======= 타이머 수정 =======";
  String UPDATE_INPUT_TITLE = "수정할 Title 입력: ";
  String UPDATE_ASK_USER_INPUT_TIME = "시간도 수정 하겠습니까? (Y/N): ";

  String UPDATE_INPUT_HOUR = "수정할 Hour 입력: ";
  String UPDATE_INPUT_MINUTE = "수정할 Minute 입력: ";
  String UPDATE_INPUT_SECOND = "수정할 Second 입력: ";
  String UPDATE_RESULT_SUCCESS_FORMAT = "타이머를 수정하였습니다.(%s -> %s)";
  String UPDATE_RESULT_FAIL = " 타이머 수정에 실패하였습니다.";

}
