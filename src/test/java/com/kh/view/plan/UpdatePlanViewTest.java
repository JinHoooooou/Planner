package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.SELECTED_PLAN;
import static com.kh.view.plan.constant.Constant.UPDATE_INPUT_MEMO;
import static com.kh.view.plan.constant.Constant.UPDATE_INPUT_TITLE;
import static com.kh.view.plan.constant.Constant.UPDATE_PLAN_HEAD;
import static com.kh.view.plan.constant.Constant.UPDATE_RESULT_SUCCESS_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.controller.PlanController;
import com.kh.helper.CommandLineTester;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdatePlanViewTest {

  private CommandLineTester commandLineTester;
  private PlanController planController;
  private UpdatePlanView updatePlanView;
  private Plan target;

  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    target = Plan.create("valid title", "valid memo");
  }

  @Test
  @DisplayName("수정하려는 Plan 객체가 null인 상황")
  public void updateViewCase1Test() {
    // Given
    String validUpdatedTitle = "valid update title";
    String validUpdatedMemo = "valid update memo";
    String input = """
        %s
        %s
        """;
    commandLineTester.setInput(String.format(input, validUpdatedTitle, validUpdatedMemo));
    Plan invalidPlan = null;

    // When
    updatePlanView = new UpdatePlanView(planController, invalidPlan);
    // Then
    assertThatThrownBy(() -> updatePlanView.execute()).isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("valid Plan 객체와 valid title valid memo 주어진 상황")
  public void updateViewCase2Test() {
    // Given
    String validUpdatedTitle = "valid update title";
    String validUpdatedMemo = "valid update memo";
    String input = """
        %s
        %s
        """;
    final String originalString = target.toString();
    commandLineTester.setInput(String.format(input, validUpdatedTitle, validUpdatedMemo));

    // When
    updatePlanView = new UpdatePlanView(planController, target);
    updatePlanView.execute();
    String actual = commandLineTester.getOutput();

    String expected = UPDATE_PLAN_HEAD + LINE
        + String.format(SELECTED_PLAN, originalString) + LINE + LINE
        + UPDATE_INPUT_TITLE + LINE
        + UPDATE_INPUT_MEMO + LINE
        + String.format(UPDATE_RESULT_SUCCESS_FORMAT, target) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("valid Plan 객체와 invalid title valid memo 주어진 상황")
  public void updateViewCase3Test() {
    // Given
    String invalidUpdatedTitle = "";
    String validUpdatedMemo = "valid update memo";
    String input = """
        %s
        %s
        """;
    commandLineTester.setInput(String.format(input, invalidUpdatedTitle, validUpdatedMemo));

    // When
    updatePlanView = new UpdatePlanView(planController, target);
    // Then
    assertThatThrownBy(() -> updatePlanView.execute()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("invalid title");
  }
}
