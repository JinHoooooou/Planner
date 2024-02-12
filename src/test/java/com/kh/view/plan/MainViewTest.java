package com.kh.view.plan;

import static com.kh.plan.view.constant.Constant.INPUT_MENU;
import static com.kh.plan.view.constant.Constant.LINE;
import static com.kh.plan.view.constant.Constant.MAIN_CREATE_PLAN;
import static com.kh.plan.view.constant.Constant.MAIN_EXIT;
import static com.kh.plan.view.constant.Constant.MAIN_HEAD;
import static com.kh.plan.view.constant.Constant.MAIN_LIST_PLAN;
import static com.kh.plan.view.constant.Constant.MAIN_MODIFY_TIMER;
import static com.kh.plan.view.constant.Constant.MAIN_SELECT_PLAN;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.CommandLineTester;
import com.kh.plan.controller.PlanController;
import com.kh.plan.model.vo.Plan;
import com.kh.plan.view.MainView;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainViewTest {

  protected PlanController planController;
  protected List<Plan> originals;
  private CommandLineTester commandLineTester;

  private MainView mainView;

  @BeforeEach
  public void setUp() {
    planController = new PlanController();
    commandLineTester = new CommandLineTester();
    setOriginals(10);
    mainView = new MainView();
  }

  private void setOriginals(int count) {
    originals = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      String title = "planner title" + i;
      originals.add(Plan.create(title, ""));
    }
  }

  @AfterEach
  public void tearDown() {
    commandLineTester.clear();
  }

  @Test
  @DisplayName("Main View에서 9 입력으로 프로그램 종료")
  public void mainViewCase1Test() {
    // Given
    String input = """
        9
        """;
    commandLineTester.setInput(input);

    // When
    mainView.execute();
    String actual = commandLineTester.getOutput();

    // Then
    String expected = mainView();
    assertThat(actual).isEqualTo(expected);
  }

  public String mainView() {
    return MAIN_HEAD + LINE
        + MAIN_CREATE_PLAN + LINE
        + MAIN_LIST_PLAN + LINE
        + MAIN_SELECT_PLAN + LINE
        + MAIN_MODIFY_TIMER + LINE
        + MAIN_EXIT + LINE
        + INPUT_MENU + LINE;
  }

  public void addMockData(List<Plan> originals) {
    for (Plan target : originals) {
      planController.create(target.getTitle());
    }
  }
}
