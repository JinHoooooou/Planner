package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.EMPTY;
import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.UPDATE_PLAN_HEAD;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdatePlanViewTest {

  private CommandLineTester commandLineTester;
  private PlanController planController;
  private UpdatePlanView updatePlanView;


  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    updatePlanView = new UpdatePlanView(planController, Plan.create("temp", ""));
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
    updatePlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = UPDATE_PLAN_HEAD + LINE
        + EMPTY + LINE;
    assertThat(actual).isEqualTo(expected);
  }

//  @Test
//  public void updateViewCase2Test() {
//    /* Case
//      Timer 객체가 10개 저장되어 있고, index 2의 Timer 객체를 다음과 같이 수정하는 상황
//      title: Updated Title 2
//      hour: 0
//      minute : 20
//      second : 0
//     */
//    String input = """
//        2
//        Updated Title 2
//        Y
//        0
//        20
//        0
//        """;
//    setInput(input);
//    addMockData(originals);
//
//    updateView.execute();
//    String actual = out.toString();
//
//    String expected = UPDATE_HEAD + LINE
//        + INPUT_INDEX + LINE
//        + UPDATE_INPUT_TITLE + LINE
//        + UPDATE_ASK_USER_INPUT_TIME + LINE
//        + UPDATE_INPUT_HOUR + LINE
//        + UPDATE_INPUT_MINUTE + LINE
//        + UPDATE_INPUT_SECOND + LINE
//        + String.format(UPDATE_RESULT_SUCCESS_FORMAT, originals.get(2), planController.selectOne(2))
//        + LINE;
//    assertThat(actual).isEqualTo(expected);
//  }
}
