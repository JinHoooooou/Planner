package com.kh.plan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.helper.DdlHelper;
import com.kh.plan.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTest {

  private PlanService planService;

  @BeforeEach
  public void setUp() {
    planService = new PlanService();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }

  @Test
  @DisplayName("valid original Plan 객체와 valid update title, valid update memo 주어질 때 update 성공한다.")
  public void updateSuccessTest1() {
    // Given: valid plan 객체를 저장한다.
    Plan original = planService.create("original title 1", "original memo 1");

    // And: valid update title, valid update memo 주어진다.
    String validUpdateTitle = "update title";
    String validUpdateMemo = "";

    // When: update 메서드를 호출한다.
    Plan actual = planService.update(original, validUpdateTitle, validUpdateMemo);

    /* Then: actual
     *        id: 1
     *        title: "update title"
     *        memo: ""
     *        clear: false
     *        timerCount: 0
     * */
    assertThat(actual.getId()).isEqualTo(1);
    assertThat(actual.getTitle()).isEqualTo(validUpdateTitle);
    assertThat(actual.getMemo()).isEmpty();
    assertThat(actual.isClear()).isFalse();
    assertThat(actual.getTimerCount()).isZero();
  }

  @Test
  @DisplayName("valid original Plan 객체와 invalid update title, valid update memo 주어질 때 update 실패한다.")
  public void updateFailTest1() {
    // Given: valid plan 객체를 저장한다.
    Plan original = planService.create("original title 1", "original memo 1");

    // And: invalid update title, valid update memo 주어진다.
    String invalidUpdateTitle = "";
    String validUpdateMemo = "";

    // When: update 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planService.update(original, invalidUpdateTitle, validUpdateMemo))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid update title");
  }

}
