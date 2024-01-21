package test.view.timer;

import main.com.kh.view.timer.MainView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class MainViewTest {

  protected ByteArrayInputStream in;
  protected ByteArrayOutputStream out;
  protected MainView mainView;


  @BeforeEach
  public void setUp() {
    mainView = new MainView();
    out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(System.in);
  }

  @Test
  public void mainViewTest() {
    String input = """
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
            """;

    assertThat(result).isEqualTo(expected);
  }
}