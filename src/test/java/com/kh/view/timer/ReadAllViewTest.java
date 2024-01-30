package com.kh.view.timer;

import main.com.kh.model.vo.Timer;
import main.com.kh.view.timer.ReadAllView;
import main.com.kh.view.timer.constant.Constant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static main.com.kh.view.timer.constant.Constant.*;

public class ReadAllViewTest extends MainViewTest {

  private ReadAllView readAllView;

  @Override
  public void setInput(String input) {
    super.setInput(input);
    readAllView = new ReadAllView(timerController);
  }

  public void setInput() {
    this.setInput("");
  }

  @Test
  public void readAllViewCase1Test() {
    // Case: Timer 객체가 10개 저장되어있는 상태에서 모든 Timer 객체를 조회하는 상황
    // Given
    setInput();
    addMockData(originals);

    // When
    readAllView.execute();
    String actual = out.toString();

    // Then
    StringBuilder expected = new StringBuilder(READ_ALL_HEAD + LINE);
    for (Timer timer : originals) {
      expected.append(timer.toString()).append(LINE);
    }
    assertThat(actual).isEqualTo(expected.toString());
  }

  @Test
  public void readAllViewCase2Test() {
    // Case: List가 Empty인 상태에서 모든 Timer 객체를 조회하는 상황
    // Given
    setInput();

    // When
    readAllView.execute();
    String actual = out.toString();

    // Then
    String expected = READ_ALL_HEAD + LINE +
            Constant.EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }

}
