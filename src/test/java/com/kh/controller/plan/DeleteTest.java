package com.kh.controller.plan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.controller.PlanController;
import com.kh.helper.DdlHelper;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTest {

  private PlanController planController;

  @BeforeEach
  public void setUp() {
    planController = new PlanController();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }

  @Test
  @DisplayName("valid Plan 객체가 주어질 때 delete 성공한다.")
  public void deleteSuccessTest() {
    // Given: valid plan 객체를 저장한다.
    Plan target = planController.create("valid title", "valid memo");

    // When: delete 메서드를 호출한다.
    boolean actual = planController.delete(target);

    // Then: actual은 true이다.
    assertThat(actual).isTrue();
  }

}
