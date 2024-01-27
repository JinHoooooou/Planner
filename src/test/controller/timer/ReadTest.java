package test.controller.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadTest {

  private TimerController timerController;

  @BeforeEach
  public void setUp() {
    timerController = new TimerController();
  }

  @Test
  public void readOneSuccessTest1() {
    // 10개의 저장된 Timer가 있고, Valid한 index가 주어질 때 read one에 성공한다.

    // Given: 10개의 Timer가 저장되어 있다.
    setDataInList(10);
    assert timerController.size() == 10;
    // And: valid한 index가 주어진다.
    int validIndex = 0;

    // When: readOne 메서드를 호출한다.
    Timer actual = timerController.readOne(validIndex);

    // Then : actual의 title은 "title0"이다.
    assertThat(actual.getTitle()).isEqualTo("title0");
    // And: actual의 hour/minute/second값은 모두 valid하다.
    assertThat(actual.getHours()).isBetween(0, 12);
    assertThat(actual.getMinutes()).isBetween(0, 59);
    assertThat(actual.getSeconds()).isBetween(0, 59);
  }

  @Test
  public void readOneFailTest1() {
    // 저장된 Timer가 없을 때 read one에 실패한다.

    // Given: 저장되어 있는 Timer객체가 없다.
    assert timerController.isEmpty();
    // And: valid한 index가 주어진다.
    int validIndex = 0;

    // When: readOne 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> timerController.readOne(validIndex))
            .isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  public void readOneFailTest2() {
    // 10개의 저장된 Timer가 있고, Invalid한 index가 주어질 때 read one에 실패한다.

    // Given: 10개의 저장된 Timer가 있다.
    setDataInList(10);
    assert timerController.size() == 10;
    // And: invalid한 index가 주어진다.
    int invalidIndex = 10;

    // When: readOne 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> timerController.readOne(invalidIndex))
            .isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  public void readAllSuccessTest1() {
    // 10개의 저장된 Timer가 있을 때 read all에 성공한다.

    // Given: 10개의 저장된 Timer가 있다.
    setDataInList(10);
    assert timerController.size() == 10;

    // When: readAll 메서드를 호출한다.
    List<Timer> actual = timerController.readAll();

    // Then: actual의 size는 10이다.
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  public void readAllSuccessTest2() {
    // 저장된 Timer가 없을 때 read all에 성공한다.

    // Given: 저장도니 Timer가 없다.
    assert timerController.isEmpty();

    // When: readAll 메서드를 호출한다.
    List<Timer> actual = timerController.readAll();

    // Then: actual의 size는 0이다.
    assertThat(actual.size()).isEqualTo(0);
  }

  public void setDataInList(int count) {
    Random random = new Random();
    for (int i = 0; i < count; i++) {
      timerController.create("title" + i,
              random.nextInt(0, 10),
              random.nextInt(0, 60),
              random.nextInt(0, 60));
    }
  }
}
