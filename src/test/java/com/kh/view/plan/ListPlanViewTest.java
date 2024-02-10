package com.kh.view.plan;

import static com.kh.plan.view.constant.Constant.LINE;
import static com.kh.plan.view.constant.Constant.LIST_ALL_PLAN_HEAD;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.CommandLineTester;
import com.kh.plan.controller.PlanController;
import com.kh.plan.view.ListPlanView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ListPlanViewTest {

  private ListPlanView listPlanView;
  private CommandLineTester commandLineTester;
  private PlanController planController;

  @BeforeEach
  public void setUp() {
    commandLineTester = new CommandLineTester();
    planController = new PlanController();
    listPlanView = new ListPlanView(planController);
  }

  @Test
  @DisplayName("Plan 객체가 저장 되어 있는 상황")
  public void readAllViewCase1Test() {
    // Given
    addMockData(15);

    // When
    listPlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    StringBuilder expected = new StringBuilder(LIST_ALL_PLAN_HEAD + LINE);
    for (int i = 0; i < 15; i++) {
      expected.append(String.format("Index %d: %s", i, planController.select(i))).append(LINE);
    }
    assertThat(actual).isEqualTo(expected.toString());
  }

  @Test
  @DisplayName("Plan 객체가 저장 되어있지 않은 상황")
  public void readAllViewCase2Test() {
    // Given

    // When
    listPlanView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = LIST_ALL_PLAN_HEAD + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  public void addMockData(int count) {
    for (int i = 0; i < count; i++) {
      planController.create("valid title " + i);
    }
  }
}
