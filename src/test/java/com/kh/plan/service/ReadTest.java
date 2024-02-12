package com.kh.plan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.helper.DdlHelper;
import com.kh.plan.model.vo.Plan;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReadTest {

  private PlanService planService;

  @BeforeEach
  public void setUp() {
    planService = new PlanService();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있고 Valid plan ID 주어질 때 select 성공한다.")
  public void selectSuccessTest1() {
    // Given: valid plan id 주어진다.
    insertSampleData(10);
    int validPlanId = 1;

    // When: select 메서드를 호출한다.
    Plan actual = planService.findById(validPlanId);

    /* Then: actual
     *        title: "valid title 1"
     *        memo: "valid memo 1"
     *        clear: false
     *        timerCount: 0
     * */
    assertThat(actual.getTitle()).isEqualTo("valid title 1");
    assertThat(actual.getMemo()).isEqualTo("valid memo 1");
    assertThat(actual.isClear()).isFalse();
    assertThat(actual.getTimerCount()).isZero();
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있고 Invalid PlanId 주어질 때 select 실패한다.")
  public void selectFailTest2() {
    // Given: invalid Plan id 주어진다.
    insertSampleData(10);
    int invalidPlanId = -12;

    // When: select 메서드를 호출한다.
    // Then: IllegalArgumentException이 발생한다.
    assertThatThrownBy(() -> planService.findById(invalidPlanId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid plan id");
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있을 때 listAll 성공한다")
  public void readAllSuccessTest1() {
    // Given: 5개의 Plan 객체를 저장한다.
    insertSampleData(5);

    // When: listAll 메서드를 호출한다.
    List<Plan> actual = planService.findAll();

    // Then: actual의 size는 10이다.
    assertThat(actual.size()).isNotZero();
  }

  @Test
  @DisplayName("저장된 Plan 객체가 없을 때 listAll 성공한다")
  public void readAllSuccessTest2() {
    // Given: 저장된 Plan 객체가 없다.

    // When: listAll 메서드를 호출한다.
    List<Plan> actual = planService.findAll();

    // Then: actual의 size는 0이다.
    assertThat(actual.size()).isZero();
  }

  private void insertSampleData(int count) {
    for (int i = 0; i < count; i++) {
      planService.create("valid title " + (i + 1), "valid memo " + (i + 1));
    }
  }

}
