package test.view.timer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateViewTest extends MainViewTest {
  @Test
  public void createViewCase1Test() {
    String input = """
            1
            title
            N
            9
            """;
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    mainView.main();
    String result = out.toString();

    String expected = """
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r
            ======= TIMER 생성 =======\r
            Title 입력: \r
            Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): \r
            새 타이머를 생성하였습니다.\r
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r                   
            """;

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void createViewCase2Test() {
    String input = """
            1
            title
            Y
            0
            20
            0
            9
            """;
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    mainView.main();
    String result = out.toString();

    String expected = """
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r
            ======= TIMER 생성 =======\r
            Title 입력: \r
            Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): \r
            Hour: \r
            Minute: \r
            Second: \r
            새 타이머를 생성하였습니다.\r
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r                   
            """;

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void createViewCase3Test() {
    String input = """
            1
            title
            INVALID_INPUT
            N
            9
            """;
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    mainView.main();
    String result = out.toString();

    String expected = """
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r
            ======= TIMER 생성 =======\r
            Title 입력: \r
            Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): \r
            잘못 입력하였습니다. 다시 입력해주세요.\r
            Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): \r
            새 타이머를 생성하였습니다.\r
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r                   
            """;

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void createViewCase4Test() {
    String input = """
            1
            title
            INVALID_INPUT
            Y
            0
            13
            0
            9
            """;
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    mainView.main();
    String result = out.toString();

    String expected = """
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r
            ======= TIMER 생성 =======\r
            Title 입력: \r
            Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): \r
            잘못 입력하였습니다. 다시 입력해주세요.\r
            Timer를 입력하시겠습니까? 기본값은 20분입니다. (Y/N): \r
            Hour: \r
            Minute: \r
            Second: \r
            새 타이머를 생성하였습니다.\r
            ======= MINI TIMER APP =======\r
            1. Timer 생성\r
            2. Timer 시작\r
            3. Timer 보기\r
            4. Timer 수정\r
            5. Timer 삭제\r
            9. 프로그램 종료\r
            메뉴 번호 선택: \r                   
            """;

    assertThat(result).isEqualTo(expected);
  }


}
