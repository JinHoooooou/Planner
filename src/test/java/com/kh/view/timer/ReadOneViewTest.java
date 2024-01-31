package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.EMPTY;
import static com.kh.view.timer.constant.Constant.INPUT_ERROR;
import static com.kh.view.timer.constant.Constant.INPUT_INDEX;
import static com.kh.view.timer.constant.Constant.LINE;
import static com.kh.view.timer.constant.Constant.READ_ONE_HEAD;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

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
    int index = 0;
    String input = """
        %d
        """;
    setInput(String.format(input, index));
    addMockData(originals);

    // When
    readOneView.execute();
    String actual = out.toString();

    // Then
    String expected = READ_ONE_HEAD + LINE
        + String.format(INPUT_INDEX) + LINE
        + originals.get(index) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void readOneViewCase2Test() {
    // Case: Timer 객체가 10개 저장되어있는 상태에서 index 9의 객체를 조회하는데, 그 전에 index 입력을 한 번 잘못하는 상황
    // Given
    int invalidIndex = 10;
    int validIndex = 9;
    String input = """
        %d
        %d
        """;
    setInput(String.format(input, invalidIndex, validIndex));
    addMockData(originals);

    // When
    readOneView.execute();
    String actual = out.toString();

    // Then
    String expected = READ_ONE_HEAD + LINE
        + INPUT_INDEX + LINE
        + INPUT_ERROR + LINE
        + INPUT_INDEX + LINE
        + originals.get(validIndex) + LINE;
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
    readOneView.execute();
    String actual = out.toString();

    // Then
    String expected = READ_ONE_HEAD + LINE
        + EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}
