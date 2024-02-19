package com.kh.plan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.helper.DdlHelper;
import com.kh.plan.model.vo.Plan;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateTest {

  private PlanService planService;

  @BeforeEach
  public void setUp() {
    planService = new PlanService();
    DdlHelper.resetSequence();
    DdlHelper.dropTable();
    DdlHelper.createTable();
  }

  @Test
  @DisplayName("Valid title, startDate, endDate 주어질 때 create 성공한다.")
  public void createSuccessTest() {
    // Given: valid title, valid memo 주어진다.
    String validTitle = "valid title 0";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now();

    // When: create 메서드를 호출한다.
    planService.create(validTitle, startDate, endDate);

    Plan actual = planService.findAll().get(0);
    /* Then: actual
     *        id: 1
     *        title: "valid title 0"
     *        startDate: LocalDate.now()
     *        endDate: LocalDate.now()
     * */
    assertThat(actual.getId()).isEqualTo(1);
    assertThat(actual.getTitle()).isEqualTo(validTitle);
    assertThat(actual.getStartDate()).isEqualTo(startDate);
    assertThat(actual.getEndDate()).isEqualTo(endDate);
  }

  @Test
  @DisplayName("Invalid title, Valid memo 주어질 때 create에 실패한다.")
  public void createFailTest() {
    // Given: invalid title (Empty String), valid memo 주어진다.
    String invalidTitle = "";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now();

    // When: create 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planService.create(invalidTitle, startDate, endDate))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("invalid title");
  }

}
