package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.EMPTY;
import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.UPDATE_PLAN_HEAD;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateViewTest {

  private CommandLineTester commandLineTester;
  private PlanController planController;
  private UpdateView updateView;


  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    updateView = new UpdateView(planController);
  }

  @Test
  @DisplayName("Plan 객체가 저장 되어 있지 않은 상황")
  public void updateViewCase1Test() {
    // Given
    int validIndex = 0;
    String input = """
        %d
        """;
    commandLineTester.setInput(String.format(input, validIndex));

    // When
    updateView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = UPDATE_PLAN_HEAD + LINE
        + EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }
  
}
