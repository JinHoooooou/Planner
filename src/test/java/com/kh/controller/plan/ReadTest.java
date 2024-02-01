package com.kh.controller.plan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReadTest {

  private PlanController planController;

  @BeforeEach
  public void setUp() {
    planController = new PlanController();
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있고 Valid index 주어질 때 select 성공한다.")
  public void selectSuccessTest1() {
    // Given: 10개의 Plan 객체가 저장되어 있다.
    addMockData(10);
    assert planController.size() == 10;
    // And: valid index 주어진다.
    int validIndex = 0;

    // When: select 메서드를 호출한다.
    Plan actual = planController.select(validIndex);

    /* Then: actual
     *        title: "title 0"
     *        memo: ""
     *        clear: false
     *        timerCount: 0
     * */
    assertThat(actual.getTitle()).isEqualTo("title 0");
    assertThat(actual.getMemo()).isEmpty();
    assertThat(actual.getClear()).isFalse();
    assertThat(actual.getTimerCount()).isZero();
  }

  @Test
  @DisplayName("저장된 Plan 객체가 없을 때 select 실패한다.")
  public void selectFailTest1() {
    // Given: 저장되어 있는 Plan 객체가 없다.
    assert planController.isEmpty();
    // And: valid index 주어진다.
    int validIndex = 0;

    // When: select 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> planController.select(validIndex))
        .isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있고 Invalid index 주어질 때 select 실패한다.")
  public void selectFailTest2() {
    // Given: 10개의 저장된 Plan 객체가 있다.
    addMockData(10);
    assert planController.size() == 10;
    // And: invalid한 index가 주어진다.
    int invalidIndex = 10;

    // When: readOne 메서드를 호출한다.
    // Then: IndexOutOfBoundException이 발생한다.
    assertThatThrownBy(() -> planController.select(invalidIndex))
        .isInstanceOf(IndexOutOfBoundsException.class);
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있을 때 listAl 성공한다")
  public void readAllSuccessTest1() {
    // Given: 10개의 저장된 Plan 객체가 있다.
    addMockData(10);
    assert planController.size() == 10;

    // When: readAll 메서드를 호출한다.
    List<Plan> actual = planController.listAll();

    // Then: actual의 size는 10이다.
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("저장된 Plan 객체가 없을 때 listAll 성공한다.")
  public void readAllSuccessTest2() {
    // Given: 저장되어 있는 Plan 객체가 없다.
    assert planController.isEmpty();

    // When: readAll 메서드를 호출한다.
    List<Plan> actual = planController.listAll();

    // Then: actual의 size는 0이다.
    assertThat(actual.size()).isEqualTo(0);
  }

  public void addMockData(int count) {
    for (int i = 0; i < count; i++) {
      planController.create("title " + i);
    }
  }
}
