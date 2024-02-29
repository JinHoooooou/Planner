package com.kh.dao.plan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.dao.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReadTest {

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
  @DisplayName("valid writer 정보가 주어지고 DB에 저장된 Plan 객체가 있을 때 select 성공한다.")
  public void findAllSuccessTest1() {
    // Given: Writer 정보가 주어진다.
    String validWriter = validUserId1;
    // And: 10개의 Plan 데이터를 DB에 저장한다.
    DaoTestUtils.addPlanData(planDao, validUserId1, 10);

    // When: PlanDao.findByUsersId() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUsersId(validWriter);

    // Then: list size: 10
    assertThat(actualList.size()).isEqualTo(10);
    for (int i = 0; i < actualList.size(); i++) {
      Plan actual = actualList.get(i);
      assertThat(actual.getTitle()).isEqualTo("validTitle" + i);
      assertThat(actual.getWriter()).isEqualTo(validUserId1);
    }
  }

  @Test
  @DisplayName("valid Writer 정보가 주어지고 DB에 저장된 Plan 객체가 없어도 select 성공한다.")
  public void findAllSuccessTest2() {
    // Given: Writer 정보가 주어진다.
    String validWriter = validUserId1;
    // And: DB에 저장된 Plan 객체는 없다.

    // When: PlanDao.findByUsersId() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUsersId(validWriter);

    // Then: list size: 0
    assertThat(actualList.size()).isEqualTo(0);
  }
}
