package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.CREATE_PLAN_HEAD;
import static com.kh.view.plan.constant.Constant.CREATE_PLAN_INPUT_TITLE;
import static com.kh.view.plan.constant.Constant.CREATE_PLAN_RESULT_FAIL;
import static com.kh.view.plan.constant.Constant.CREATE_PLAN_RESULT_SUCCESS_FORMAT;
import static com.kh.view.plan.constant.Constant.LINE;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateViewTest {

  private CreateView createView;
  private PlanController planController;
  private CommandLineTester commandLineTester;

  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    createView = new CreateView(planController);
  }

  @AfterEach
  public void tearDown() {
    commandLineTester.clear();
  }

  @Test
  @DisplayName("valid title 입력한 상황")
  public void createViewCase1Test() {
    // Given
    String validTitle = "valid title";
    String input = """
        %s
        N
        9
        """;
    commandLineTester.setInput(String.format(input, validTitle));

    // When
    createView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = CREATE_PLAN_HEAD + LINE
        + CREATE_PLAN_INPUT_TITLE + LINE
        + String.format(CREATE_PLAN_RESULT_SUCCESS_FORMAT, planController.select(0)) + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("invalid title 입력한 상황")
  public void createViewCase2Test() {
    // Given
    String invalidTitle = "";
    String input = """
        %s
        Y
        9
        """;
    commandLineTester.setInput(String.format(input, invalidTitle));

    // When
    createView.execute();
    String actual = commandLineTester.getOutput();

    //Then
    String expected = CREATE_PLAN_HEAD + LINE
        + CREATE_PLAN_INPUT_TITLE + LINE
        + CREATE_PLAN_RESULT_FAIL + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}
