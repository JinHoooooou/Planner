package test.view.timer;

import main.com.kh.model.vo.Timer;
import main.com.kh.view.timer.ReadOneView;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static test.constant.Constant.*;

public class ReadOneViewTest extends MainViewTest {

  private ReadOneView readOneView;

  @Override
  public void setInput(String input) {
    super.setInput(input);
    readOneView = new ReadOneView(timerController, new Scanner(System.in));
  }

  @Test
  public void readOneViewCase1Test() {
    // Case: Timer 객체가 10개 저장되어있는 상태에서 index 0의 객체를 조회하는 상황
    // Given
    String input = """
            0
            """;
    setInput(input);
    List<Timer> list = setDataInList(10);

    // When
    readOneView.main();
    String actual = out.toString();

    // Then
    String expected = READ_ONE_TITLE + LINE +
            String.format(READ_ONE_INPUT_INDEX, timerController.getTimerList().size() - 1) + LINE +
            list.get(0) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void readOneViewCase2Test() {
    // Case: Timer 객체가 10개 저장되어있는 상태에서 index 9의 객체를 조회하는데, 그 전에 index 입력을 두 번 잘못하는 상황
    // Given
    String input = """
            11
            10
            9
            """;
    setInput(input);
    List<Timer> list = setDataInList(10);

    // When
    readOneView.main();
    String actual = out.toString();

    // Then
    String expected = READ_ONE_TITLE + LINE +
            String.format(READ_ONE_INPUT_INDEX, timerController.getTimerList().size() - 1) + LINE +
            READ_ONE_INPUT_INDEX_ERROR + LINE +
            String.format(READ_ONE_INPUT_INDEX, timerController.getTimerList().size() - 1) + LINE +
            READ_ONE_INPUT_INDEX_ERROR + LINE +
            String.format(READ_ONE_INPUT_INDEX, timerController.getTimerList().size() - 1) + LINE +
            list.get(9) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void readOneViewCase3Test() {
    // Case: List가 Empty인 상태에서 index 0을 조회하는 상황
    // Given
    String input = """
            0
            """;
    setInput(input);

    // When
    readOneView.main();
    String actual = out.toString();

    // Then
    String expected = READ_ONE_TITLE + LINE +
            READ_LIST_EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}
