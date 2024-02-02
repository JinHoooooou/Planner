package com.kh.controller.plan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateTest {

  private PlanController planController;

  @BeforeEach
  public void setUp() {
    planController = new PlanController();
  }

  @Test
  @DisplayName("Valid title 주어질 때 create 성공한다.")
  public void createSuccessTest1() {
    // Given: valid title
    String validTitle = "valid title 0";
    // And: 저장되어 있는 Plan객체는 없다.
    assert planController.isEmpty();

    // When: create 메서드를 호출한다.
    Plan actual = planController.create(validTitle);

    /* Then: actual
              title: "valid title 0"
              timerCount: 0
              memo: ""
              clear: false
     */
    assertThat(actual.getTitle()).isEqualTo(validTitle);
    assertThat(actual.getTimerCount()).isZero();
    assertThat(actual.getMemo()).isEmpty();
    assertThat(actual.isClear()).isFalse();
    // And: 저장된 Plan는 1개이다.
    assertThat(planController.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("Valid title, memo 주어질 때 create 성공한다.")
  public void createSuccessTest2() {
    // Given: valid title
    String validTitle = "valid title 0";
    // And: valid memo
    String validMemo = "valid memo content";
    // And: 저장되어 있는 Timer객체는 없다.
    assert planController.isEmpty();

    // When: create 메서드를 호출한다.
    Plan actual = planController.create(validTitle, validMemo);


    /* Then: actual
     *        title: "valid title 0"
     *        timerCount: 0
     *        memo: "valid memo content"
     *        clear: false
     * */
    assertThat(actual.getTitle()).isEqualTo(validTitle);
    assertThat(actual.getTimerCount()).isZero();
    assertThat(actual.getMemo()).isEqualTo(validMemo);
    assertThat(actual.isClear()).isFalse();
    // And: 저장된 Timer는 1개이다.
    assertThat(planController.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("Invalid title 주어질 때 create 실패한다.")
  public void createFail1() {
    // Given: invalid title (Empty String)
    String invalidTitle = "";
    // And: 저장되어 있는 Timer객체는 없다.
    assert planController.isEmpty();

    // When: create 메서드를 호출한다.
    Plan actual = planController.create(invalidTitle);

    // Then : actual은 null이다.
    assertThat(actual).isNull();
    // And: 저장된 Timer는 0개이다.
    assertThat(planController.isEmpty()).isTrue();
  }

  @Test
  @DisplayName("Invalid title, Valid memo 주어질 때 create에 실패한다.")
  public void createFail2() {
    // Given: invalid title (Empty String)
    String invalidTitle = "";
    // And: valid memo
    String validMemo = "valid memo content";
    assert planController.isEmpty();

    // When: create 메서드를 호출한다.
    Plan actual = planController.create(invalidTitle, validMemo);

    // Then : actual은 null이다.
    assertThat(actual).isNull();
    // And: 저장된 Timer는 0개이다.
    assertThat(planController.isEmpty()).isTrue();
  }

}
