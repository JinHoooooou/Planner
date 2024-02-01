package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.EMPTY;
import static com.kh.view.plan.constant.Constant.INPUT_ERROR;
import static com.kh.view.plan.constant.Constant.INPUT_INDEX;
import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.SELECT_PLAN_HEAD;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SelectPlanViewTest {

  private SelectPlanView selectPlanView;
  private CommandLineTester commandLineTester;
  private PlanController planController;

  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    selectPlanView = new SelectPlanView(planController);
  }

  @AfterEach
  public void tearDown() {
    commandLineTester.clear();
  }

  @Test
  @DisplayName("valid index 입력한 상황")
  public void readOneViewCase1Test() {
    // Given
    int validIndex = 0;
    String input = """
        %d
        """;
    commandLineTester.setInput(String.format(input, validIndex));
    addMockData(10);

    // When
    selectPlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = SELECT_PLAN_HEAD + LINE
        + String.format(INPUT_INDEX) + LINE
        + planController.select(validIndex) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("invalid index 입력한 상황")
  public void readOneViewCase2Test() {
    // Given
    int invalidIndex = -1;
    int validIndex = 1;
    String input = """
        %d
        %d
        """;
    commandLineTester.setInput(String.format(input, invalidIndex, validIndex));
    addMockData(10);

    // When
    selectPlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = SELECT_PLAN_HEAD + LINE
        + String.format(INPUT_INDEX) + LINE
        + INPUT_ERROR + LINE
        + String.format(INPUT_INDEX) + LINE
        + planController.select(validIndex) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("Plan 객체가 저장 되어 있지 않은 상황")
  public void readOneViewCase3Test() {
    // Given
    String input = """
        0
        """;
    commandLineTester.setInput(input);

    // When
    selectPlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = SELECT_PLAN_HEAD + LINE
        + EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  public void addMockData(int count) {
    for (int i = 0; i < count; i++) {
      planController.create("valid title " + i);
    }
  }
}
