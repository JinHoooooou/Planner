package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.CREATE_ASK_USER_INPUT_TIME;
import static com.kh.view.timer.constant.Constant.CREATE_HEAD;
import static com.kh.view.timer.constant.Constant.CREATE_INPUT_HOUR;
import static com.kh.view.timer.constant.Constant.CREATE_INPUT_MINUTE;
import static com.kh.view.timer.constant.Constant.CREATE_INPUT_SECOND;
import static com.kh.view.timer.constant.Constant.CREATE_INPUT_TITLE;
import static com.kh.view.timer.constant.Constant.CREATE_RESULT_SUCCESS_FORMAT;
import static com.kh.view.timer.constant.Constant.INPUT_ERROR;
import static com.kh.view.timer.constant.Constant.LINE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class CreateViewTest extends MainViewTest {

  private CreateView createView;

  @Override
  public void setInput(String input) {
    super.setInput(input);
    createView = new CreateView(timerController, new Scanner(System.in));
  }

  @Test
  public void createViewCase1Test() {
    // Case: Timer title은 입력하고
    // And: hour/minute/second는 입력하지 않는 상황
    // Given
    String title = "title";
    String input = """
        %s
        N
        9
        """;
    setInput(String.format(input, title));

    // When
    createView.execute();
    String actual = out.toString();

    // Then
    String expected = CREATE_HEAD + LINE
        + CREATE_INPUT_TITLE + LINE
        + CREATE_ASK_USER_INPUT_TIME + LINE
        + String.format(CREATE_RESULT_SUCCESS_FORMAT, timerController.readOne(0)) + LINE;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void createViewCase2Test() {
    // Case: Timer title을 입력하고
    // And: hour/minute/second를 모두 입력하는 상황
    // Given
    String title = "title";
    int hour = 0;
    int minute = 20;
    int second = 0;
    String input = """
        %s
        Y
        %d
        %d
        %d
        9
        """;
    setInput(String.format(input, title, hour, minute, second));

    // When
    createView.execute();
    String actual = out.toString();

    //Then
    String expected = CREATE_HEAD + LINE
        + CREATE_INPUT_TITLE + LINE
        + CREATE_ASK_USER_INPUT_TIME + LINE
        + CREATE_INPUT_HOUR + LINE
        + CREATE_INPUT_MINUTE + LINE
        + CREATE_INPUT_SECOND + LINE
        + String.format(CREATE_RESULT_SUCCESS_FORMAT, timerController.readOne(0)) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void createViewCase3Test() {
    // Case: Timer title을 입력하고
    // And: hour/minute/second는 입력하지 않는데, (Y/N)입력을 한번 잘못한 상황
    // Given
    String title = "title";
    String input = """
        %s
        INVALID_INPUT
        N
        9
        """;
    setInput(String.format(input, title));

    // When
    createView.execute();
    String actual = out.toString();

    // Then
    String expected = CREATE_HEAD + LINE
        + CREATE_INPUT_TITLE + LINE
        + CREATE_ASK_USER_INPUT_TIME + LINE
        + INPUT_ERROR + LINE
        + CREATE_ASK_USER_INPUT_TIME + LINE
        + String.format(CREATE_RESULT_SUCCESS_FORMAT, timerController.readOne(0)) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void createViewCase4Test() {
    // Case: Timer의 title을 입력하고
    // And: hour/minute/second도 입력하는데 (Y/N)입력을 한번 잘못한 상황
    // Given
    String title = "title";
    int hour = 0;
    int minute = 59;
    int second = 59;
    String input = """
        %s
        INVALID_INPUT
        Y
        %d
        %d
        %d
        9
        """;
    setInput(String.format(input, title, hour, minute, second));

    // When
    createView.execute();
    String actual = out.toString();

    // Then
    String expected = CREATE_HEAD + LINE
        + CREATE_INPUT_TITLE + LINE
        + CREATE_ASK_USER_INPUT_TIME + LINE
        + INPUT_ERROR + LINE
        + CREATE_ASK_USER_INPUT_TIME + LINE
        + CREATE_INPUT_HOUR + LINE
        + CREATE_INPUT_MINUTE + LINE
        + CREATE_INPUT_SECOND + LINE
        + String.format(CREATE_RESULT_SUCCESS_FORMAT, timerController.readOne(0)) + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}
