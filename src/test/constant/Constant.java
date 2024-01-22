package test.constant;

public interface Constant {
  String LINE = "\r\n";
  String MAIN_TITLE = "======= MINI TIMER APP =======";
  String MAIN_CREATE = "1. Timer 생성";
  String MAIN_START = "2. Timer 시작";
  String MAIN_READ_ALL = "3. 모든 Timer 보기";
  String MAIN_UPDATE = "4. Timer 수정";
  String MAIN_DELETE = "5. Timer 삭제";
  String MAIN_EXIT = "9. 프로그램 종료";
  String MAIN_INPUT_MENU = "메뉴 번호 선택: ";

  String CREATE_TITLE = "======= TIMER 생성 =======";
  String CREATE_INPUT_TITLE = "Title 입력: ";
  String CREATE_INPUT_DEFAULT = "Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): ";
  String CREATE_INPUT_HOUR = "Hour: ";
  String CREATE_INPUT_MINUTE = "Minute: ";
  String CREATE_INPUT_SECOND = "Second: ";
  String CREATE_RESULT_SUCCESS = "새 타이머를 생성하였습니다.";
  String CREATE_INPUT_DEFAULT_ERROR = "잘못 입력하였습니다. 다시 입력해주세요.";
}
