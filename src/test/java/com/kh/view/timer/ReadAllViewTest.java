package com.kh.view.timer;

import static com.kh.view.timer.constant.Constant.EMPTY;
import static com.kh.view.timer.constant.Constant.LINE;
import static com.kh.view.timer.constant.Constant.READ_ALL_HEAD;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.model.vo.Timer;
import org.junit.jupiter.api.Test;

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
    String expected = READ_ALL_HEAD + LINE + EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }

}
