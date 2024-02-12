package com.kh.plan.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DdlHelper;
import com.kh.plan.model.vo.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTest {

  private PlanService planService;

  @BeforeEach
  public void setUp() {
    planService = new PlanService();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }

  @Test
  @DisplayName("valid Plan 객체가 주어질 때 delete 성공한다.")
  public void deleteSuccessTest() {
    // Given: valid plan 객체를 저장한다.
    Plan target = planService.create("valid title", "valid memo");

    // When: delete 메서드를 호출한다.
    boolean actual = planService.delete(target);

    // Then: actual은 true이다.
    assertThat(actual).isTrue();
  }

}
