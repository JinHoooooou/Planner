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
  @DisplayName("findAll 성공1: DB에 데이터가 있을 때")
  public void findAllSuccessTest1() {
    // Given: writer가 다른 valid Plan 객체를 DB에 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId2, "validTitle", 5);

    // When: PlanDao.findAll() 메서드를 호출한다.
    List<Plan> actualList = planDao.findAll();

    // Then: actualList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("findAll 성공2: DB에 데이터가 없을 때")
  public void findAllSuccessTest2() {
    // Given: DB에 저장된 Plan 객체가 없다.

    // When: PlanDao.findAll() 메서드를 호출한다.
    List<Plan> actualList = planDao.findAll();

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByUserId 성공1: valid writer와 DB에 해당 데이터가 있을 때")
  public void findByUserIdSuccessTest1() {
    // Given: writer가 다른 valid Plan 객체를 DB에 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId2, "validTitle", 5);
    // And: valid writer 정보가 주어진다.
    String validWriter = validUserId1;

    // When: PlanDao.findByUsersId() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUsersId(validWriter);

    // Then: actualList의 size는 5이다.
    assertThat(actualList.size()).isEqualTo(5);
    for (int i = 0; i < actualList.size(); i++) {
      Plan actual = actualList.get(i);
      assertThat(actual.getTitle()).isEqualTo("validTitle" + (i + 1));
      assertThat(actual.getWriter()).isEqualTo(validUserId1);
    }
  }

  @Test
  @DisplayName("findByUserId 성공2: valid writer와 DB에 해당 데이터가 없을 때")
  public void findByUserIdSuccessTest2() {
    // Given: DB에 저장된 Plan 객체는 없다.
    // And: valid writer 정보가 주어진다.
    String validWriter = validUserId1;

    // When: PlanDao.findByUsersId() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUsersId(validWriter);

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByUserId 성공3: invalid writer일 때")
  public void findByUserIdSuccessTest3() {
    // Given: invalid Writer 정보가 주어진다.
    String invalidWriter = "invalidUserId";

    // When: PlanDao.findByUsersId() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUsersId(invalidWriter);

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByUserIdOrderByEndDate 성공: valid writer일 때")
  public void findByUserIdOrderByEndDateSuccessTest() {
    // Given: DB에 EndDate가 다른 Plan 객체를 저장한다.
    DaoTestUtils.addPlanDataDifferentEndDate(validUserId1, "validTitle", 10);
    // And: valid Writer 정보가 주어진다.
    String validWriter = validUserId1;

    // When: PlanDao.findByUsersIdOrderByEndDate() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUsersIdOrderByEndDate(validWriter);

    // Then: actualList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
    // And: acutalList는 endDate 기준으로 오름차순 정렬되어 있다.
    List<Plan> expected = new ArrayList<>(actualList);
    expected.sort(Comparator.comparing(Plan::getEndDate));
    assertThat(actualList).isEqualTo(expected);
  }


  @Test
  @DisplayName("findByUserIdAndTitleContaining 성공1: valid 데이터가 주어지고 DB에 해당 데이터가 있을 때")
  public void findByUserIdAndTitleContainingSuccessTest1() {
    // Given: title이 다른 valid Plan 객체를 DB에 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId1, "content", 5);
    // And: valid 데이터가 주어진다.
    // {writer:validUserId1, titleKeyword:Title}
    String writer = validUserId1;
    String titleKeyword = "Title";

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUserIdAndTitleContaining(writer, titleKeyword);

    // Then: actualList의 size는 5이다.
    assertThat(actualList.size()).isEqualTo(5);
  }

  @Test
  @DisplayName("findByUserIdAndTitleContaining 성공2: valid 데이터가 주어지고 DB에 해당 데이터가 없을 때")
  public void findByUserIdAndTitleContainingSuccessTest2() {
    // Given: DB에 Plan 객체 데이터가 없다.
    // And: valid 데이터가 주어진다.
    // {writer:validUserId1, titleKeyword:Title}
    String writer = validUserId1;
    String titleKeyword = "Title";

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUserIdAndTitleContaining(writer, titleKeyword);

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByUserIdAndTitleContaining 성공3: invalid 데이터가 주어졌을 때")
  public void findByUserIdAndTitleContainingSuccessTest3() {
    // Given: title이 다른 valid Plan 객체를 DB에 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    DaoTestUtils.addPlanData(validUserId1, "content", 5);
    // And: invalid 데이터가 주어진다.
    // {writer: invalidUserId}
    String writer = "invalidUserId";
    String titleKeyword = "Title";

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    List<Plan> actualList = planDao.findByUserIdAndTitleContaining(writer, titleKeyword);

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByPlanId 성공: valid planId가 주어지고 DB에 해당 데이터가 있을 때")
  public void findByPlanIdSuccessTest() {
    // Given: valid Plan 객체를 DB에 저장한다.
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 5);
    // And: valid planId가 주어진다.
    int validPlanId = 1;

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    Plan actual = planDao.findByPlanId(validPlanId);

    // Then: actual의 writer는 validUserId1이고, title은 validTitle1이다.
    assertThat(actual.getPlanId()).isEqualTo(1);
    assertThat(actual.getWriter()).isEqualTo(validUserId1);
    assertThat(actual.getTitle()).isEqualTo("validTitle1");
  }

  @Test
  @DisplayName("findByPlanId 실패: invalid planId가 주어질 때")
  public void findByPlanIdFailTest() {
    // And: invalid planId가 주어진다.
    int validPlanId = -2;

    // When: PlanDao.findByUserIdAndTitleContaining() 메서드를 호출한다.
    Plan actual = planDao.findByPlanId(validPlanId);

    // Then: actual은 null이다.
    assertThat(actual).isNull();
  }
}
