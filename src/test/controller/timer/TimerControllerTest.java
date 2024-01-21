package test.controller.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.model.vo.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimerControllerTest {

  private TimerController timerController;

  @BeforeEach
  public void setUp() {
    timerController = new TimerController();
  }

  @Test
  public void createTimerSuccessTest1() {
    // Given: valid한 title가 주어지고 시간은 주어지지 않는다.
    String title = "validTitle";
    // And: list의 size는 0이다.
    assert timerController.getTimerList().isEmpty();

    // When: createTimer메소드를 호출한다.
    boolean actual = timerController.createTimer(Timer.createDefault(title));

    // Then : actual은 true이다.
    assertThat(actual).isTrue();
    // And: list의 size는 1이다.
    List<Timer> list = timerController.getTimerList();
    assertThat(list.size()).isEqualTo(1);
    // And: 저장된 데이터의 title은 "validTitle"이다.
    assertThat(list.get(0).getTitle()).isEqualTo(title);
    // And: 저장된 데이터의 hour은 0, minute는 20, second는 0이다.
    assertThat(list.get(0).getHours()).isEqualTo(0);
    assertThat(list.get(0).getMinutes()).isEqualTo(20);
    assertThat(list.get(0).getSeconds()).isEqualTo(0);
  }

  @Test
  public void createTimerSuccessTest2() {
    // Given: valid한 title가 주어지고 시간은 5/10/20으로 주어진다.
    String title = "validTitle";
    int hour = 5;
    int minute = 10;
    int second = 20;
    // And: list의 size는 0이다.
    assert timerController.getTimerList().isEmpty();

    // When: createTimer메소드를 호출한다.
    boolean actual = timerController.createTimer(Timer.create(title, hour, minute, second));

    // Then : actual은 true이다.
    assertThat(actual).isTrue();
    // And: list의 size는 1이다.
    List<Timer> list = timerController.getTimerList();
    assertThat(list.size()).isEqualTo(1);
    // And: 저장된 데이터의 title은 "validTitle"이다.
    assertThat(list.get(0).getTitle()).isEqualTo(title);
    // And: 저장된 데이터의 hour은 5, minute는 10, second는 20.
    assertThat(list.get(0).getHours()).isEqualTo(5);
    assertThat(list.get(0).getMinutes()).isEqualTo(10);
    assertThat(list.get(0).getSeconds()).isEqualTo(20);
  }

}
