package com.kh.view.timer;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static com.kh.view.timer.constant.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;

class UpdateViewTest extends MainViewTest {


  private UpdateView updateView;

  @Override
  public void setInput(String input) {
    super.setInput(input);
    updateView = new UpdateView(timerController, new Scanner(System.in));
  }

  @Test
  public void updateViewCase1Test() {
    // Case: 저장된 Timer가 없을 때, index 를 입력으로 주었을 때
    String input = """
            0
            """;
    setInput(input);

    updateView.execute();
    String actual = out.toString();

    String expected = UPDATE_HEAD + LINE +
            EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void updateViewCase2Test() {
    /* Case
      Timer 객체가 10개 저장되어 있고, index 2의 Timer 객체를 다음과 같이 수정하는 상황
      title: Updated Title 2
      hour: 0
      minute : 20
      second : 0
     */
    String input = """
            2
            Updated Title 2
            Y
            0
            20
            0
            """;
    setInput(input);
    addMockData(originals);

    updateView.execute();
    String actual = out.toString();

    String expected = UPDATE_HEAD + LINE +
            INPUT_INDEX + LINE +
            UPDATE_INPUT_TITLE + LINE +
            UPDATE_ASK_USER_INPUT_TIME + LINE +
            UPDATE_INPUT_HOUR + LINE +
            UPDATE_INPUT_MINUTE + LINE +
            UPDATE_INPUT_SECOND + LINE +
            String.format(UPDATE_RESULT_SUCCESS_FORMAT, originals.get(2), timerController.readOne(2)) + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}