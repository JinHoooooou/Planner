package com.kh.dao.plan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTest {

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
  @DisplayName("deleteByPlanIdAndWriter 성공1: valid 데이터와 DB에 해당 레코드가 있을 때")
  public void deleteByPlanIdAndWriterSuccessTest1() {
    // Given: DB에 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: valid 데이터가 주어진다.
    int validPlanId = 1;
    String validWriter = validUserId1;

    // When: PlanDao.deleteByPlanIdAndWriter() 메서드를 호출한다.
    int result = planDao.deleteByPlanIdAndWriter(validPlanId, validWriter);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 해당 레코드가 삭제된다.
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual).isNull();
  }

  @Test
  @DisplayName("deleteByPlanIdAndWriter 실패1: invalid planId가 주어질 때")
  public void deleteByPlanIdAndWriterFailTest1() {
    // Given: DB에 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: invalid planId가 주어진다.
    int invalidPlanId = 13;
    String validWriter = validUserId1;

    // When: PlanDao.deleteByPlanIdAndWriter()를 호출한다.
    int result = planDao.deleteByPlanIdAndWriter(invalidPlanId, validWriter);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 해당 레코드가 삭제되지 않는다.
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual).isNotNull();
  }

  @Test
  @DisplayName("deleteByPlanIdAndWriter 실패2: invalid writer가 주어질 때")
  public void deleteByPlanIdAndWriterFailTest2() {
    // Given: DB에 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
    // And: invalid writer가 주어진다.
    int validPlanId = 1;
    String invalidWriter = "invalidUserId";

    // When: PlanDao.deleteByPlanIdAndWriter()를 호출한다.
    int result = planDao.deleteByPlanIdAndWriter(validPlanId, invalidWriter);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 해당 레코드가 삭제되지 않는다.
    Plan actual = planDao.findByPlanId(1);
    assertThat(actual).isNotNull();
  }

}
