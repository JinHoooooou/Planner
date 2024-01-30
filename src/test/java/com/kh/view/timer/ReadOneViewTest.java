package com.kh.view.timer;

import main.com.kh.view.timer.ReadOneView;
import main.com.kh.view.timer.constant.Constant;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static main.com.kh.view.timer.constant.Constant.*;

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
    String expected = READ_ONE_HEAD + LINE +
            String.format(Constant.INPUT_INDEX) + LINE +
            originals.get(index) + LINE;
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
    String expected = READ_ONE_HEAD + LINE +
            Constant.INPUT_INDEX + LINE +
            Constant.INPUT_ERROR + LINE +
            Constant.INPUT_INDEX + LINE +
            originals.get(validIndex) + LINE;
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
    String expected = READ_ONE_HEAD + LINE +
            Constant.EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}
