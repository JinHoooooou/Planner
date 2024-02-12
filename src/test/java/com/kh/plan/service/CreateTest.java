package com.kh.plan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.helper.DdlHelper;
import com.kh.plan.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateTest {

  private PlanService planService;

  @BeforeEach
  public void setUp() {
    planService = new PlanService();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }

  @Test
  @DisplayName("Valid title, memo 주어질 때 create 성공한다.")
  public void createSuccessTest() {
    // Given: valid title, valid memo 주어진다.
    String validTitle = "valid title 0";
    String validMemo = "valid memo content";

    // When: create 메서드를 호출한다.
    Plan actual = planService.create(validTitle, validMemo);


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
  @DisplayName("Invalid title, Valid memo 주어질 때 create에 실패한다.")
  public void createFailTest() {
    // Given: invalid title (Empty String), valid memo 주어진다.
    String invalidTitle = "";
    String validMemo = "valid memo content";

    // When: create 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planService.create(invalidTitle, validMemo))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid title");
  }

}
