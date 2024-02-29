package com.kh.dao.plan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.dao.DaoTestUtils;
import com.kh.database.DataAccessException;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InsertTest {

  private PlanDao planDao;
  private String validUserId1 = "validUserId1";

  @BeforeEach
  public void setUp() {
    planDao = new PlanDao();
    DdlHelper.resetPlanSequence();
    DdlHelper.dropPlanTable();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();
    DdlHelper.createPlanTable();

    DaoTestUtils.addUserData(validUserId1);
  }

  @Test
  @DisplayName("Valid Plan 주어질 때 insert 성공한다.")
  public void insertPlanSuccessTest1() {
    // Given: valid Plan 주어진다.
    Plan validPlan = Plan.builder()
        .title("validTitle")
        .writer("validUserId1")
        .startDate(Date.valueOf(LocalDateTime.now().toLocalDate()))
        .endDate((Date.valueOf(LocalDate.now())))
        .remindAlarmDate(Date.valueOf(LocalDate.now()))
        .complete(false)
        .createDate(Date.valueOf(LocalDate.now()))
        .build();

    // When: PlanDao.insert() 메서드를 호출한다.
    planDao.insert(validPlan);

    // Then: DB에 validPlan이 저장된다.
    Plan actual = planDao.findByUsersIdOrderByEndDate(validUserId1).get(0);
    assertThat(actual.getWriter()).isEqualTo(validPlan.getWriter());
    assertThat(actual.getTitle()).isEqualTo(validPlan.getTitle());
    assertThat(actual.getStartDate().toString()).isEqualTo(validPlan.getStartDate().toString());
    assertThat(actual.getEndDate().toString()).isEqualTo(validPlan.getEndDate().toString());
    assertThat(actual.getRemindAlarmDate().toString()).isEqualTo(
        validPlan.getRemindAlarmDate().toString());
    assertThat(actual.isComplete()).isEqualTo(validPlan.isComplete());
    assertThat(actual.getCreateDate().toString()).isEqualTo(validPlan.getCreateDate().toString());
  }

  @Test
  @DisplayName("Nullable 컬럼에 대한 데이터가 없어도 insert 성공한다")
  public void insertPlanSuccessTest2() {
    // Given: NotNull 컬럼에 대한 값만 있는 Plan 주어진다.
    Plan validPlan = Plan.builder()
        .writer(validUserId1)
        .title("validTitle")
        .build();

    // When: PlanDao.insert() 메서드를 호출한다.
    planDao.insert(validPlan);
    // Then: DB에 validPlan이 저장된다.
    Plan actual = planDao.findByUsersIdOrderByEndDate(validUserId1).get(0);
    assertThat(actual.getWriter()).isEqualTo(validPlan.getWriter());
    assertThat(actual.getTitle()).isEqualTo(validPlan.getTitle());
    // And: Nullable 컬럼들은 default 값으로 저장된다.
    assertThat(actual.getStartDate()).isNull();
    assertThat(actual.getEndDate()).isNull();
    assertThat(actual.getRemindAlarmDate()).isNull();
    assertThat(actual.isComplete()).isFalse();
    assertThat(actual.getCreateDate().toString()).isEqualTo(
        Date.valueOf(LocalDate.now()).toString());

  }

  @Test
  @DisplayName("Writer 정보가 DB에 없을 때 insert 실패한다.")
  public void insertPlanFailTest1() {
    // Given: Plan 주어진다.
    Plan invalidPlan = Plan.builder()
        .title("validTitle")
        .startDate(Date.valueOf(LocalDateTime.now().toLocalDate()))
        .endDate((Date.valueOf(LocalDate.now())))
        .remindAlarmDate(Date.valueOf(LocalDate.now()))
        .complete(false)
        .createDate(Date.valueOf(LocalDate.now()))
        .build();
    // And: Plan의 Writer는 DB에 저장 되지 않은 USER_ID이다.
    invalidPlan.setWriter("invalidUserId");

    // When: PlanDao.insert() 메서드를 호출한다.
    // Then: IllegalArgumentException 발생한다.
    assertThatThrownBy(() -> planDao.insert(invalidPlan)).isInstanceOf(
            IllegalArgumentException.class)
        .hasMessageContaining("invalid session");
  }

  @Test
  @DisplayName("NotNull 컬럼에 대한 데이터가 없으면 insert 실패한다.")
  public void insertPlanFailTest2() {
    // Given: Plan 주어진다.
    Plan invalidPlan = Plan.builder()
        .title(null)
        .writer(validUserId1)
        .startDate(Date.valueOf(LocalDateTime.now().toLocalDate()))
        .endDate((Date.valueOf(LocalDate.now())))
        .remindAlarmDate(Date.valueOf(LocalDate.now()))
        .complete(false)
        .createDate(Date.valueOf(LocalDate.now()))
        .build();

    // When: PlanDao.insert() 메서드를 호출한다.
    // Then: DataAccessException 발생한다.
    assertThatThrownBy(() -> planDao.insert(invalidPlan)).isInstanceOf(DataAccessException.class)
        .hasMessageContaining("NULL");
  }
}
