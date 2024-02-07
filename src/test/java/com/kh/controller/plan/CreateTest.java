package com.kh.controller.plan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.controller.PlanController;
import com.kh.helper.DdlHelper;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateTest {

  private PlanController planController;

  @BeforeEach
  public void setUp() {
    planController = new PlanController();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }


  @Test
  @DisplayName("Valid title 주어질 때 create 성공한다.")
  public void createSuccessTest1() {
    // Given: valid title 주어진다.
    String validTitle = "valid title 0";

    // When: create 메서드를 호출한다.
    Plan actual = planController.create(validTitle);

    /* Then: actual
              id: 1
              title: "valid title 0"
              timerCount: 0
              memo: ""
              clear: false
     */
    assertThat(actual.getId()).isEqualTo(1);
    assertThat(actual.getTitle()).isEqualTo(validTitle);
    assertThat(actual.getTimerCount()).isZero();
    assertThat(actual.getMemo()).isEmpty();
    assertThat(actual.isClear()).isFalse();
  }

  @Test
  @DisplayName("Valid title, memo 주어질 때 create 성공한다.")
  public void createSuccessTest2() {
    // Given: valid title, valid memo 주어진다.
    String validTitle = "valid title 0";
    String validMemo = "valid memo content";

    // When: create 메서드를 호출한다.
    Plan actual = planController.create(validTitle, validMemo);


    /* Then: actual
     *        id: 1
     *        title: "valid title 0"
     *        timerCount: 0
     *        memo: "valid memo content"
     *        clear: false
     * */
    assertThat(actual.getTitle()).isEqualTo(validTitle);
    assertThat(actual.getTimerCount()).isZero();
    assertThat(actual.getMemo()).isEqualTo(validMemo);
    assertThat(actual.isClear()).isFalse();
  }

  @Test
  @DisplayName("Invalid title 주어질 때 create 실패한다.")
  public void createFail1() {
    // Given: invalid title (Empty String) 주어진다.
    String invalidTitle = "";

    // When: create 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planController.create(invalidTitle))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid title");
  }

  @Test
  @DisplayName("Invalid title, Valid memo 주어질 때 create에 실패한다.")
  public void createFail2() {
    // Given: invalid title (Empty String), valid memo 주어진다.
    String invalidTitle = "";
    String validMemo = "valid memo content";

    // When: create 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planController.create(invalidTitle, validMemo))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid title");
  }

}
