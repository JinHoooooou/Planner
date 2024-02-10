package com.kh.view.plan;

import static com.kh.plan.view.constant.Constant.DELETE_PLAN_CANCEL;
import static com.kh.plan.view.constant.Constant.DELETE_PLAN_HEAD;
import static com.kh.plan.view.constant.Constant.DELETE_PLAN_SUCCESS;
import static com.kh.plan.view.constant.Constant.DELETE_PLAN_WARNING_MESSAGE;
import static com.kh.plan.view.constant.Constant.LINE;
import static com.kh.plan.view.constant.Constant.SELECTED_PLAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.helper.CommandLineTester;
import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import com.kh.plan.view.DeletePlanView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeletePlanViewTest {

  private CommandLineTester commandLineTester;
  private PlanController planController;
  private DeletePlanView deletePlanView;
  private Plan target;

  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    target = Plan.create("valid title", "valid memo");
  }

  @Test
  @DisplayName("valid Plan 객체를 삭제하는 상황")
  public void deleteViewCase1Test() {
    // Given
    String input = """
        Y
        """;
    commandLineTester.setInput(input);

    // When
    deletePlanView = new DeletePlanView(planController, target);
    deletePlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = DELETE_PLAN_HEAD + LINE
        + String.format(SELECTED_PLAN + LINE, target) + LINE
        + DELETE_PLAN_WARNING_MESSAGE + LINE
        + DELETE_PLAN_SUCCESS + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("invalid Plan 객체를 삭제하는 상황")
  public void deleteViewCase2Test() {
    // Given
    Plan invalidPlan = null;
    String input = """
        Y
        """;
    commandLineTester.setInput(input);

    // When
    deletePlanView = new DeletePlanView(planController, invalidPlan);
    // Then
    assertThatThrownBy(() -> deletePlanView.execute()).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("null object");
  }

  @Test
  @DisplayName("valid Plan 객체를 삭제 취소하는 상황")
  public void deleteViewCase3Test() {
    // Given
    String input = """
        N
        """;
    commandLineTester.setInput(input);

    // When
    deletePlanView = new DeletePlanView(planController, target);
    deletePlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = DELETE_PLAN_HEAD + LINE
        + String.format(SELECTED_PLAN + LINE, target) + LINE
        + DELETE_PLAN_WARNING_MESSAGE + LINE
        + DELETE_PLAN_CANCEL + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("valid Plan 객체인데 input이 Y/N 아닌상황")
  public void deleteViewCase4Test() {
    // Given
    String input = """
        S
        """;
    commandLineTester.setInput(input);

    // When
    deletePlanView = new DeletePlanView(planController, target);
    // Then
    assertThatThrownBy(() -> deletePlanView.execute()).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid input");
  }
}
