package com.kh.view.plan;

import static com.kh.view.plan.constant.Constant.EXIT_APP;
import static com.kh.view.plan.constant.Constant.LINE;
import static com.kh.view.plan.constant.Constant.MAIN_CREATE_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_DELETE_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_EXIT;
import static com.kh.view.plan.constant.Constant.MAIN_HEAD;
import static com.kh.view.plan.constant.Constant.MAIN_INPUT_MENU;
import static com.kh.view.plan.constant.Constant.MAIN_LIST_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_MODIFY_TIMER;
import static com.kh.view.plan.constant.Constant.MAIN_SELECT_PLAN;
import static com.kh.view.plan.constant.Constant.MAIN_UPDATE_PLAN;
import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import com.kh.model.vo.Plan;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainViewTest {

  protected ByteArrayInputStream in;
  protected ByteArrayOutputStream out;
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
  public void mainViewTest() {
    // Case: Main View 첫 화면만 출력하고 프로그램 종료하는 상황
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

  public void setInput(String input) {
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }

  public String mainView() {
    return MAIN_HEAD + LINE
        + MAIN_CREATE_PLAN + LINE
        + MAIN_LIST_PLAN + LINE
        + MAIN_SELECT_PLAN + LINE
        + MAIN_UPDATE_PLAN + LINE
        + MAIN_DELETE_PLAN + LINE
        + MAIN_MODIFY_TIMER + LINE
        + MAIN_EXIT + LINE + MAIN_INPUT_MENU + LINE
        + EXIT_APP + LINE;
  }

  public void addMockData(List<Plan> originals) {
    for (Plan target : originals) {
      planController.create(target.getTitle());
    }
  }
}
