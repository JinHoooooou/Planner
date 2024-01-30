package com.kh.controller.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UpdateTest {

  private TimerController timerController;
  private List<Timer> originals;

  @BeforeEach
  public void setUp() {
    timerController = new TimerController();
    setOriginals(10);
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

  @Test
  public void updateSuccessTest1() {
    // 10개의 저장된 Timer가 있고 Valid한 index, title이 주어지고, hour/minute/second는 주어지지 않을 때 update에 성공한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index와 title이 주어진다.
    int validIndex = 0;
    String validTitleToUpdate = "valid updated title 0";

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex, validTitleToUpdate);

    // Then: actual은 true이다.
    assertThat(actual).isTrue();
    // And: 0번째에 저장 된 Timer의 title은 "valid updated title 0"이다.
    Timer actualTimer = timerController.readOne(validIndex);
    assertThat(actualTimer.getTitle()).isEqualTo(validTitleToUpdate);
    // And:  hour/minute/second는 0/20/0이다.
    assertThat(actualTimer.getHours()).isEqualTo(0);
    assertThat(actualTimer.getMinutes()).isEqualTo(20);
    assertThat(actualTimer.getSeconds()).isEqualTo(0);
  }

  @Test
  public void updateSuccessTest2() {
    // 10개의 저장된 Timer가 있고 Valid한 index, title, hour/minute/second가 주어질 때 update에 성공한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index, title, hour/minute/second가 주어진다.
    int validIndex = 9;
    String validTitleToUpdate = "valid updated title 123";
    int validHourToUpdate = 11;
    int validMinuteToUpdate = 59;
    int validSecondToUpdate = 59;

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex,
            validTitleToUpdate,
            validHourToUpdate,
            validMinuteToUpdate,
            validSecondToUpdate);

    // Then: actual은 true이다.
    assertThat(actual).isTrue();
    // And: 9번째에 저장 된 Timer의 title은 "valid updated title 0"이다.
    Timer actualTimer = timerController.readOne(validIndex);
    assertThat(actualTimer.getTitle()).isEqualTo(validTitleToUpdate);
    // And: hour/minute/second는 11/59/59이다.
    assertThat(actualTimer.getHours()).isEqualTo(validHourToUpdate);
    assertThat(actualTimer.getMinutes()).isEqualTo(validMinuteToUpdate);
    assertThat(actualTimer.getSeconds()).isEqualTo(validSecondToUpdate);
  }

  @Test
  public void updateFailTest1() {
    // 10개의 저장된 Timer가 있고 Valid한 index가 주어지고 Invalid한 title이 주어질 때 update에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index가 주어지고
    int validIndex = 3;
    // And: invalid한 title이 주어진다. (Empty String)
    String invalidTitleToUpdate = "";

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex, invalidTitleToUpdate);

    // Then: actual은 false이다.
    assertThat(actual).isFalse();
    // And: 3번째에 저장 된 Timer는 수정되지 않는다..
    assertNotUpdated(timerController.readOne(validIndex), originals.get(validIndex));
  }

  @Test
  public void updateFailTest2() {
    // 10개의 저장된 Timer가 있고 Valid한 index, hour, minute, second가 주어지고 Invalid한 title이 주어질 때 update에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index, hour, minute, second가 주어지고
    int validIndex = 9;
    int validHourToUpdate = 11;
    int validMinuteToUpdate = 59;
    int validSecondToUpdate = 59;
    // And: invalid한 title이 주어진다. (Empty String)
    String invalidTitleToUpdate = "";

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex,
            invalidTitleToUpdate,
            validHourToUpdate,
            validMinuteToUpdate,
            validSecondToUpdate);

    // Then: actual은 false이다.
    assertThat(actual).isFalse();
    // And: 9번째에 저장 된 Timer는 수정되지 않는다..
    assertNotUpdated(timerController.readOne(validIndex), originals.get(validIndex));
  }

  @Test
  public void updateFailTest3() {
    // 10개의 저장된 Timer가 있고 Valid한 index, title, minute, second가 주어지고 Invalid한 hour가 주어질 때 update에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index, title, minute, second가 주어지고
    int validIndex = 4;
    String validTitleToUpdate = "valid updated title 231";
    int validMinuteToUpdate = 59;
    int validSecondToUpdate = 59;
    // And: invalid한 hour가 주어진다. (Empty String)
    int invalidHourToUpdate = -1;

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex,
            validTitleToUpdate,
            invalidHourToUpdate,
            validMinuteToUpdate,
            validSecondToUpdate);

    // Then: actual은 false이다.
    assertThat(actual).isFalse();
    // And: 4번째에 저장 된 Timer는 수정되지 않는다..
    assertNotUpdated(timerController.readOne(validIndex), originals.get(validIndex));
  }

  @Test
  public void updateFailTest4() {
    // 10개의 저장된 Timer가 있고 Valid한 index, title, hour, second 주어지고 Invalid한 minute이 주어질 때 update에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index, title, hour, second가 주어지고
    int validIndex = 4;
    String validTitleToUpdate = "valid updated title 231";
    int validHourToUpdate = 11;
    int validSecondToUpdate = 59;
    // And: invalid한 minute이 주어진다. (Empty String)
    int invalidMinuteToUpdate = -1;

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex,
            validTitleToUpdate,
            validHourToUpdate,
            invalidMinuteToUpdate,
            validSecondToUpdate);

    // Then: actual은 false이다.
    assertThat(actual).isFalse();
    // And: 4번째에 저장 된 Timer는 수정되지 않는다..
    assertNotUpdated(timerController.readOne(validIndex), originals.get(validIndex));
  }

  @Test
  public void updateFailTest5() {
    // 10개의 저장된 Timer가 있고 Valid한 index, title, hour, minute 주어지고 Invalid한 second 주어질 때 update에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index, title, hour, minute가 주어지고
    int validIndex = 6;
    String validTitleToUpdate = "valid updated title 666";
    int validHourToUpdate = 11;
    int validMinuteToUpdate = 59;
    // And: invalid한 minute이 주어진다. (Empty String)
    int invalidSecondToUpdate = -1;

    // When: update 메서드를 호출한다.
    boolean actual = timerController.update(validIndex,
            validTitleToUpdate,
            validHourToUpdate,
            validMinuteToUpdate,
            invalidSecondToUpdate);

    // Then: actual은 false이다.
    assertThat(actual).isFalse();
    // And: 6번째에 저장 된 Timer는 수정되지 않는다..
    assertNotUpdated(timerController.readOne(validIndex), originals.get(validIndex));
  }

  @Test
  public void updateFailTest6() {
    // 저장된 Timer가 없을 때 update에 실패한다.

    // Given: 저장된 Timer가 없다.
    assert timerController.isEmpty();
    // And: valid index, title 주어진다.
    int validIndex = 0;
    String validTitleToUpdate = "valid updated title 666";

    // When: update 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> timerController.update(validIndex, validTitleToUpdate))
            .isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  public void updateFailTest7() {
    // 10개의 저장된 Timer가 있고 Invalid한 index가 주어질 때 update에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: invalid index, valid title 주어진다.
    int invalidIndex = -2;
    String validTitleToUpdate = "valid updated title";

    // When: update 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> timerController.update(invalidIndex, validTitleToUpdate))
            .isInstanceOf(IndexOutOfBoundsException.class);
  }

  private void addMockData(List<Timer> originals) {
    for (Timer target : originals) {
      timerController.create(target.getTitle(),
              target.getHours(),
              target.getMinutes(),
              target.getSeconds());
    }
  }

  private void assertNotUpdated(Timer actual, Timer original) {
    assertThat(actual.getTitle()).isEqualTo(original.getTitle());
    assertThat(actual.getHours()).isEqualTo(original.getHours());
    assertThat(actual.getMinutes()).isEqualTo(original.getMinutes());
    assertThat(actual.getSeconds()).isEqualTo(original.getSeconds());
  }
}
