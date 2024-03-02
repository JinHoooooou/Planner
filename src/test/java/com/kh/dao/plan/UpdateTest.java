package com.kh.dao.plan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.database.DataAccessException;
import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTest {

  private PlanDao planDao;
  private String validUserId1 = "validUserId1";

  @BeforeAll
  public static void setUpAll() {
    DdlHelper.dropTable("DETAIL_PLAN");
  }

  @BeforeEach
  public void setUp() {
    planDao = new PlanDao();
    DdlHelper.dropSequence("PLAN");
    DdlHelper.createSequence("PLAN");
    DdlHelper.dropTable("PLAN");
    DdlHelper.dropTable("USERS");
    DdlHelper.createUsersTable();
    DdlHelper.createPlanTable();

    DaoTestUtils.addUserData(validUserId1);
  }

  @Test
  @DisplayName("update 성공1: valid 데이터가 주어지고 DB에 해당 데이터가 있을 때")
  public void updateSuccessTest1() {
    // Given: DB에 Plan 객체를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: 수정할 데이터가 주어진다.
    Plan updatePlan = Plan.builder()
        .planId(1)
        .title("updateTitle")
        .writer(validUserId1)
        .startDate(Date.valueOf(LocalDate.parse("2024-03-03")))
        .endDate(Date.valueOf(LocalDate.parse("2024-03-19")))
        .remindAlarmDate(Date.valueOf(LocalDate.parse("2024-03-18")))
        .complete(true)
        .build();

    // When: PlanDao.update() 메서드를 호출한다.
    int result = planDao.update(updatePlan);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 있던 데이터는 수정할 데이터로 수정된다.
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual.getTitle()).isEqualTo(updatePlan.getTitle());
    assertThat(actual.getStartDate().toLocalDate())
        .isEqualTo(updatePlan.getStartDate().toLocalDate());
    assertThat(actual.getEndDate().toLocalDate())
        .isEqualTo(updatePlan.getEndDate().toLocalDate());
    assertThat(actual.getRemindAlarmDate().toLocalDate())
        .isEqualTo(updatePlan.getRemindAlarmDate().toLocalDate());
    assertThat(actual.isComplete()).isEqualTo(updatePlan.isComplete());
  }

  @Test
  @DisplayName("update 성공2: Nullable 컬럼에 대한 데이터가 null일 때")
  public void updateSuccessTest2() {
    // Given: DB에 Plan 객체를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: 수정할 데이터가 주어진다.
    // NotNull 컬럼에 대한 데이터만 주어진다.
    Plan updatePlan = Plan.builder()
        .planId(1)
        .title("updateTitle")
        .writer(validUserId1)
        .build();

    // When: PlanDao.update() 메서드를 호출한다.
    int result = planDao.update(updatePlan);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 있던 데이터는 수정할 데이터로 수정된다.
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual.getTitle()).isEqualTo(updatePlan.getTitle());
    // And: Nullable 데이터는 null이나 기본값으로 수정된다.
    assertThat(actual.getStartDate()).isNull();
    assertThat(actual.getEndDate()).isNull();
    assertThat(actual.getRemindAlarmDate()).isNull();
    assertThat(actual.isComplete()).isFalse();
  }

  @Test
  @DisplayName("update 실패1: invalid planId가 주어질 때")
  public void updateFailTest1() {
    // Given: DB에 Plan 객체를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: invalid PlanId와 수정할 데이터가 주어진다.
    Plan updatePlan = Plan.builder()
        .planId(-1)
        .title("updateTitle")
        .writer(validUserId1)
        .build();

    // When: PlanDao.update() 메서드를 호출한다.
    int result = planDao.update(updatePlan);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 데이터는 수정되지 않는다..
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual.getTitle()).isNotEqualTo(updatePlan.getTitle());
  }

  @Test
  @DisplayName("update 실패2: invalid writer가 주어질 때")
  public void updateFailTest2() {
    // Given: DB에 Plan 객체를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: invalid writer와 수정할 데이터가 주어진다.
    Plan updatePlan = Plan.builder()
        .planId(1)
        .title("updateTitle")
        .writer("invalidUserId")
        .build();

    // When: PlanDao.update() 메서드를 호출한다.
    int result = planDao.update(updatePlan);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 데이터는 수정되지 않는다..
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual.getTitle()).isNotEqualTo(updatePlan.getTitle());
  }

  @Test
  @DisplayName("update 실패3: NotNull에 컬럼에 대한 데이터가 null일 때")
  public void updateFailTest3() {
    // Given: DB에 Plan 객체를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: invalid 데이터가 주어진다.
    // title이 null이다.
    Plan updatePlan = Plan.builder()
        .planId(1)
        .title(null)
        .writer(validUserId1)
        .build();

    // When: PlanDao.update() 메서드를 호출한다.
    // Then: DataAccessException이 발생한다.
    assertThatThrownBy(() -> planDao.update(updatePlan)).isInstanceOf(DataAccessException.class)
        .hasMessageContaining("NULL");
  }

}
