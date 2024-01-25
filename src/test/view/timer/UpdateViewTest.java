package test.view.timer;

import main.com.kh.view.timer.UpdateView;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static test.constant.Constant.*;

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

    updateView.main();
    String actual = out.toString();

    String expected = UPDATE_TITLE + LINE +
            UPDATE_EMPTY + LINE;
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
            0
            20
            0
            """;
    setInput(input);
    setDataInList(10);

    updateView.main();
    String actual = out.toString();

    String expected = UPDATE_TITLE + LINE +
            String.format(UPDATE_INPUT_INDEX,timerController.size()-1) + LINE +
            UPDATE_INPUT_NEW_TITLE + LINE +
            UPDATE_INPUT_HOUR + LINE +
            UPDATE_INPUT_MINUTE + LINE +
            UPDATE_INPUT_SECOND + LINE +
            UPDATE_RESULT_SUCCESS + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}