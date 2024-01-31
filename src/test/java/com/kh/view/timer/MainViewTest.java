package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.EXIT_APP;
import static com.kh.view.timer.constant.Constant.LINE;
import static com.kh.view.timer.constant.Constant.MAIN_CREATE;
import static com.kh.view.timer.constant.Constant.MAIN_DELETE;
import static com.kh.view.timer.constant.Constant.MAIN_EXIT;
import static com.kh.view.timer.constant.Constant.MAIN_HEAD;
import static com.kh.view.timer.constant.Constant.MAIN_INPUT_MENU;
import static com.kh.view.timer.constant.Constant.MAIN_READ_ALL;
import static com.kh.view.timer.constant.Constant.MAIN_READ_ONE;
import static com.kh.view.timer.constant.Constant.MAIN_UPDATE;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.TimerController;
import com.kh.model.vo.Timer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainViewTest {

  protected ByteArrayInputStream in;
  protected ByteArrayOutputStream out;
  protected TimerController timerController;
  protected List<Timer> originals;

  private MainView mainView;

  @BeforeEach
  public void setUp() {
    timerController = new TimerController();
    setOriginals(10);
    out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    mainView = new MainView();
  }

  private void setOriginals(int count) {
    originals = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < count; i++) {
      String title = "title" + i;
      int hour = random.nextInt(0, 12);
      int minute = random.nextInt(0, 60);
      int second = random.nextInt(0, 60);

      originals.add(Timer.create(title, hour, minute, second));
    }
  }

  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(System.in);
  }

  @Test
  public void mainViewTest() {
    // Case: Main View 첫 화면만 출력하고 프로그램 종료하는 상황
    // Given
    String input = """
        9
        """;
    setInput(input);

    // When
    mainView.execute();
    String actual = out.toString();

    // Then
    String expected = mainView();
    assertThat(actual).isEqualTo(expected);
  }

  public void setInput(String input) {
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }

  public String mainView() {
    return MAIN_HEAD + LINE + MAIN_CREATE + LINE + MAIN_READ_ONE + LINE + MAIN_READ_ALL + LINE
        + MAIN_UPDATE + LINE + MAIN_DELETE + LINE + MAIN_EXIT + LINE + MAIN_INPUT_MENU + LINE
        + EXIT_APP + LINE;
  }

  public void addMockData(List<Timer> originals) {
    for (Timer target : originals) {
      timerController.create(target.getTitle(), target.getHours(), target.getMinutes(),
          target.getSeconds());
    }
  }
}
