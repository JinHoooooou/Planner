package com.kh.dao.plan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.PlanDao;
import com.kh.model.vo.Plan;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * TODO: findAll은 관리자만 가능해야한다.
 * */
public class ReadTest {

  private PlanDao planDao;
  private String validUserId1 = "validUserId1";
  private String validUserId2 = "validUserId2";

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
    DaoTestUtils.addUserData(validUserId2);
  }

  @Test
  @DisplayName("findAll 성공1: DB에 레코드가 있을 때")
  public void findAllSuccessTest1() {
    // Given: DB에 writer가 다른 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId2, "validTitle", 5);

    // When: PlanDao.findAll() 메서드를 호출한다.
    List<Plan> actualList = planDao.findAll();

    // Then: actualList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("findAll 실패1: DB에 레코드가 없을 때")
  public void findAllFailTest1() {
    // Given: DB에 저장된 Plan 레코드가 없다.

    // When: PlanDao.findAll() 메서드를 호출한다.
    List<Plan> actualList = planDao.findAll();

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriter 성공1: valid writer가 주어질 때")
  public void findByWriterSuccessTest1() {
    // Given: DB에 writer가 다른 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId2, "validTitle", 5);
    // And: valid writer가 주어진다.
    String validWriter = validUserId1;

    // When: PlanDao.findByWriter() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByWriter(validWriter);

    // Then: actualList의 size는 5이다.
    assertThat(actualList.size()).isEqualTo(5);
    for (Plan actual : actualList) {
      assertThat(actual.getWriter()).isEqualTo(validUserId1);
    }
  }


  @Test
  @DisplayName("findByWriter 실패1: invalid writer가 주어질 때")
  public void findByWriterFailTest1() {
    // Given: DB에 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    // Given: invalid writer가 주어진다.
    String invalidWriter = "invalidUserId";

    // When: PlanDao.findByWriter() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByWriter(invalidWriter);

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriterOrderByEndDate 성공1: valid writer가 주어질 때")
  public void findByWriterOrderByEndDateSuccessTest() {
    // Given: DB에 endDate가 다른 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanDataDifferentEndDate(validUserId1, "validTitle", 10);
    // And: valid writer가 주어진다.
    String validWriter = validUserId1;

    // When: PlanDao.findByWriterOrderByEndDate() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByWriterOrderByEndDate(validWriter);

    // Then: actualList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
    // And: acutalList는 endDate 기준으로 오름차순 정렬되어 있다.
    List<Plan> expected = new ArrayList<>(actualList);
    expected.sort(Comparator.comparing(Plan::getEndDate));
    assertThat(actualList).isEqualTo(expected);
  }


  @Test
  @DisplayName("findByWriterAndTitleContaining 성공1: valid writer가 주어질 때")
  public void findByWriterAndTitleContainingSuccessTest1() {
    // Given: DB에 title이 다른 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId1, "content", 5);
    // And: valid writer와 title이 주어진다.
    String validWriter = validUserId1;
    String titleKeyword = "Title";

    // When: PlanDao.findByWriterAndTitleContaining() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByWriterAndTitleContaining(validWriter, titleKeyword);

    // Then: actualList의 size는 5이다.
    assertThat(actualList.size()).isEqualTo(5);
  }

  @Test
  @DisplayName("findByWriterAndTitleContaining 실패1: invalid writer가 주어질 때")
  public void findByWriterAndTitleContainingFailTest1() {
    // Given: DB에 title이 다른 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId1, "content", 5);
    // And: invalid writer가 주어진다.
    String invalidWriter = "invalidUserId";
    String titleKeyword = "Title";

    // When: PlanDao.findByWriterAndTitleContaining() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByWriterAndTitleContaining(invalidWriter, titleKeyword);

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByPlanId 성공1: valid planId가 주어질 때")
  public void findByPlanIdSuccessTest() {
    // Given: DB에 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    // And: valid planId가 주어진다.
    int validPlanId = 1;

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    Plan actual = planDao.findByPlanId(validPlanId);

    // Then: actual의 id는 1, writer는 validUserId1, title은 validTitle1이다.
    assertThat(actual.getPlanId()).isEqualTo(1);
    assertThat(actual.getWriter()).isEqualTo(validUserId1);
    assertThat(actual.getTitle()).isEqualTo("validTitle1");
  }

  @Test
  @DisplayName("findByPlanId 실패1: invalid planId가 주어질 때")
  public void findByPlanIdFailTest() {
    // Given: DB에 Plan 레코드를 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    // And: invalid planId가 주어진다.
    int validPlanId = -2;

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    Plan actual = planDao.findByPlanId(validPlanId);

    // Then: actual은 null이다.
    assertThat(actual).isNull();
  }
}
