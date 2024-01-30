package com.kh.controller.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTest {

  private TimerController timerController;

  @BeforeEach
  public void setUp() {
    timerController = new TimerController();
  }

  @Test
  public void createSuccessTest1() {
    // Valid한 title이 주어지고 Hour/Minute/Second는 주어지지 않을 때 create에 성공한다.

    // Given: valid한 title이 주어지고 시간은 주어지지 않는다.
    String validTitle = "valid title 0";
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(validTitle);

    // Then : actual은 true이다.
    assertThat(actual).isTrue();
    // And: 저장된 Timer는 1개이다.
    assertThat(timerController.size()).isEqualTo(1);
    // And: 저장된 데이터의 title은 "valid title 0"이다.
    Timer actualTimer = timerController.readOne(0);
    assertThat(actualTimer.getTitle()).isEqualTo(validTitle);
    // And: 저장된 데이터의 hour은 0, minute는 20, second는 0이다.
    assertThat(actualTimer.getHours()).isEqualTo(0);
    assertThat(actualTimer.getMinutes()).isEqualTo(20);
    assertThat(actualTimer.getSeconds()).isEqualTo(0);
  }

  @Test
  public void createSuccessTest2() {
    // Valid한 title이 주어지고 Valid한 Hour/Minute/Second는 주어졌을 때 create에 성공한다.

    // Given: valid한 title이 주어지고
    String validTitle = "valid title 0";
    // And: valid한 hour/minute/second가 주어진다.
    int validHour = 1;
    int validMinute = 2;
    int validSecond = 3;
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(validTitle, validHour, validMinute, validSecond);

    // Then : actual은 true이다.
    assertThat(actual).isTrue();
    // And: 저장된 Timer는 1개이다.
    assertThat(timerController.size()).isEqualTo(1);
    // And: 저장된 데이터의 title은 "valid title 0"이다.
    Timer actualTimer = timerController.readOne(0);
    assertThat(actualTimer.getTitle()).isEqualTo(validTitle);
    // And: 저장된 데이터의 hour은 1, minute는 2, second는 3이다.
    assertThat(actualTimer.getHours()).isEqualTo(validHour);
    assertThat(actualTimer.getMinutes()).isEqualTo(validMinute);
    assertThat(actualTimer.getSeconds()).isEqualTo(validSecond);
  }

  @Test
  public void createFail1() {
    // Invalid한 title이 주어지고 시간은 주어지지 않을 때 create에 실패한다.

    // Given: invalid한 title이 주어진다. (Empty String)
    String invalidTitle = "";
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(invalidTitle);

    // Then : actual은 false이다.
    assertThat(actual).isFalse();
    // And: 저장된 Timer는 0개이다.
    assertThat(timerController.isEmpty()).isTrue();
  }

  @Test
  public void createFail2() {
    // Invalid한 title이 주어지고 Valid한 Hour/Minute/Second는 주어졌을 때 create에 실패한다.

    // Given: invalid한 title이 주어지고 (Empty String)
    String invalidTitle = "";
    // And: valid한 hour/minute/second가 주어진다.
    int validHour = 11;
    int validMinute = 59;
    int validSecond = 59;
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(invalidTitle, validHour, validMinute, validSecond);

    // Then : actual은 false이다.
    assertThat(actual).isFalse();
    // And: 저장된 Timer는 0개이다.
    assertThat(timerController.isEmpty()).isTrue();
  }

  @Test
  public void createFail3() {
    // Valid한 title, minute, second가 주어지고 Invalid한 hour가 주어졌을 때 create에 실패한다.

    // Given: valid한 title, minute, second가 주어지고
    String validTitle = "valid title 3";
    int validMinute = 59;
    int validSecond = 59;
    // And: invalid한 hour가 주어진다.
    int invalidHour = 12;
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(validTitle, invalidHour, validMinute, validSecond);

    // Then : actual은 false이다.
    assertThat(actual).isFalse();
    // And: 저장된 Timer는 0개이다.
    assertThat(timerController.isEmpty()).isTrue();
  }

  @Test
  public void createFail4() {
    // Valid한 title, hour, second가 주어지고 Invalid한 minute이 주어졌을 때 create에 실패한다.

    // Given: valid한 title, hour, second가 주어지고
    String validTitle = "valid title 4";
    int validHour = 11;
    int validSecond = 59;
    // And: invalid한 minute이 주어진다.
    int invalidMinute = 60;
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(validTitle, validHour, invalidMinute, validSecond);

    // Then : actual은 false이다.
    assertThat(actual).isFalse();
    // And: 저장된 Timer는 0개이다.
    assertThat(timerController.isEmpty()).isTrue();
  }

  @Test
  public void createFail5() {
    // Valid한 title, hour, minute이 주어지고 Invalid한 second가 주어졌을 때 create에 실패한다.

    // Given: valid한 title, hour, minute이 주어지고
    String validTitle = "valid title 4";
    int validHour = 11;
    int validMinute = 59;
    // And: invalid한 second가 주어진다.
    int invalidSecond = 60;
    // And: 저장되어 있는 Timer객체는 없다.
    assert timerController.isEmpty();

    // When: create 메서드를 호출한다.
    boolean actual = timerController.create(validTitle, validHour, validMinute, invalidSecond);

    // Then : actual은 false이다.
    assertThat(actual).isFalse();
    // And: 저장된 Timer는 0개이다.
    assertThat(timerController.isEmpty()).isTrue();
  }

}
