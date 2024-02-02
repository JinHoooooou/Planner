package com.kh.controller.plan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTest {

  private PlanController planController;

  @BeforeEach
  public void setUp() {
    planController = new PlanController();
  }

  @Test
  @DisplayName("valid original Plan 객체와 valid updated Plan 주어질 때 update 성공한다.")
  public void updateSuccessTest1() {
    // Given: valid plan 객체가 주어지고
    Plan newPlan = planController.create("first title");
    // And: valid plan 객체가 주어진다.
    String validUpdatedTitle = "updated title";
    String validUpdatedMemo = "";
    Plan updatePlan = Plan.create(validUpdatedTitle, validUpdatedMemo);

    // When: update 메서드를 호출한다.
    Plan actual = planController.update(newPlan, updatePlan);

    /* Then: actual
     *        title: "updated title"
     *        memo: ""
     *        clear: false
     *        timerCount: 0
     * */
    assertThat(actual.getTitle()).isEqualTo(validUpdatedTitle);
    assertThat(actual.getMemo()).isEmpty();
    assertThat(actual.isClear()).isFalse();
    assertThat(actual.getTimerCount()).isZero();
    assertThat(actual).isEqualTo(newPlan);
  }

  @Test
  @DisplayName("valid Plan 객체와 invalid title 주어질 때 update 실패한다.")
  public void updateFailTest1() {
    // Given: valid plan 객체가 주어지고
    Plan newPlan = planController.create("original title");
    // And: invalid updatedTitle 주어진다.
    String invalidUpdatedTitle = "";
    String validUpdateMemo = "";
    Plan updatePlan = Plan.create(invalidUpdatedTitle, validUpdateMemo);

    // When: update 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planController.update(newPlan, updatePlan)).isInstanceOf(
            IllegalArgumentException.class)
        .hasMessageContaining("invalid title");
    // And: newPlan title: "original title"이다.
    assertThat(newPlan.getTitle()).isEqualTo("original title");
  }


}
