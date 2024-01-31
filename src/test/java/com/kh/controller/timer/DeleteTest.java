package com.kh.controller.timer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.controller.TimerController;
import com.kh.model.vo.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteTest {

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
  public void deleteSuccessTest1() {
    // 10개의 저장된 Timer가 있고 Valid한 index가 주어질 때 delete에 성공한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: valid한 index와 title이 주어진다.
    int validIndex = 0;

    // When: delete 메서드를 호출한다.
    boolean actual = timerController.delete(validIndex);

    // Then: actual은 true이다.
    assertThat(actual).isTrue();
    // And: 0번째에 저장 된 Timer 객체는 originals의 1번째 저장된 Timer 객체와 같다.
    assertEquals(timerController.readOne(validIndex), originals.get(validIndex + 1));
  }

  @Test
  public void deleteFailTest1() {
    // 10개의 저장된 Timer가 있고 Invalid한 index가 주어질 때 delete에 실패한다.

    // Given: 10개의 Timer가 저장되어 있다.
    addMockData(originals);
    assert timerController.size() == 10;
    // And: invalid index 주어진다.
    int invalidIndex = -123;

    // When: delete 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> timerController.delete(invalidIndex))
        .isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  public void deleteFailTest2() {
    //저장된 Timer가 없을 때 delete에 실패한다.

    // Given: 저장된 Timer가 없다.
    assert timerController.isEmpty();
    // And: valid index 주어진다.
    int validIndex = 0;

    // When: delete 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> timerController.delete(validIndex))
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

  private void assertEquals(Timer actual, Timer expected) {
    assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
    assertThat(actual.getHours()).isEqualTo(expected.getHours());
    assertThat(actual.getMinutes()).isEqualTo(expected.getMinutes());
    assertThat(actual.getSeconds()).isEqualTo(expected.getSeconds());
  }
}
