package com.kh.plan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.helper.DdlHelper;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReadTest {

  private PlanDao planDao;

  @BeforeEach
  public void setUp() {
    planDao = new PlanDao();
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
    Plan actual = planDao.findById(validPlanId);

    /* Then: actual
     *        title: "valid title 1"
     *        memo: "valid memo 1"
     *        clear: false
     *        timerCount: 0
     * */
    assertThat(actual.getTitle()).isEqualTo("valid title 1");
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있고 Invalid PlanId 주어질 때 select 실패한다.")
  public void selectFailTest2() {
    // Given: invalid Plan id 주어진다.
    insertSampleData(10);
    int invalidPlanId = -12;

    // When: select 메서드를 호출한다.
    // Then: IllegalArgumentException이 발생한다.
    assertThatThrownBy(() -> planDao.findById(invalidPlanId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid plan id");
  }

  @Test
  @DisplayName("저장된 Plan 객체가 있을 때 listAll 성공한다")
  public void readAllSuccessTest1() {
    // Given: 5개의 Plan 객체를 저장한다.
    insertSampleData(5);

    // When: listAll 메서드를 호출한다.
    List<Plan> actual = planDao.findAll();

    // Then: actual의 size는 10이다.
    assertThat(actual.size()).isNotZero();
  }

  @Test
  @DisplayName("저장된 Plan 객체가 없을 때 listAll 성공한다")
  public void readAllSuccessTest2() {
    // Given: 저장된 Plan 객체가 없다.

    // When: listAll 메서드를 호출한다.
    List<Plan> actual = planDao.findAll();

    // Then: actual의 size는 0이다.
    assertThat(actual.size()).isZero();
  }

  private void insertSampleData(int count) {
    for (int i = 0; i < count; i++) {
      planDao.create("valid title " + (i + 1), LocalDate.now(), LocalDate.now());
    }
  }

}
