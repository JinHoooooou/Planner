package com.kh.dao.detailplan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReadTest {

  DetailPlanDao detailPlanDao;
  String validUserId1 = "validUserId1";
  String validUserId2 = "validUserId2";

  @BeforeEach
  public void setUp() {
    detailPlanDao = new DetailPlanDao();
    DdlHelper.dropSequence("PLAN");
    DdlHelper.dropSequence("DETAIL");
    DdlHelper.createSequence("PLAN");
    DdlHelper.createSequence("DETAIL");
    DdlHelper.dropTable("DETAIL_PLAN");
    DdlHelper.dropTable("PLAN");
    DdlHelper.dropTable("USERS");
    DdlHelper.createUsersTable();
    DdlHelper.createPlanTable();
    DdlHelper.createDetailPlanTable();

    DaoTestUtils.addUserData(validUserId1);
    DaoTestUtils.addUserData(validUserId2);
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 2);
    DaoTestUtils.addPlanData(validUserId2, "validTitle", 2);
  }

  @Test
  @DisplayName("findAll 성공1: DB에 데이터가 있을 때")
  public void findAllSuccessTest1() {
    // Given: planId와 writer가 다른 valid DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    DaoTestUtils.addDetailPlan(validUserId2, 3, 5);

    // When: DetailPlanDao.findAll() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findAll();

    // Then: actaulList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("findAll 성공2: DB에 데이터가 없을 때")
  public void findAllSuccessTest2() {
    // Given: DB에 저장된 DetailPlan 객체가 없다.

    // When: DetailPlanDao.findAll() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findAll();

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriter 성공1: valid writer가 주어지고 DB에 데이터가 있을 때")
  public void findByWriterSuccessTest1() {
    // Given: planId가 다른 valid DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    DaoTestUtils.addDetailPlan(validUserId1, 2, 5);

    // When: DetailPlanDao.findByWriter() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriter(validUserId1);

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(10);
    for (int i = 0; i < actualList.size(); i++) {
      DetailPlan actual = actualList.get(i);
      assertThat(actual.getWriter()).isEqualTo(validUserId1);
      assertThat(actual.getPlanId()).isIn(1, 2);
    }
  }

  @Test
  @DisplayName("findByWriter 실패1: DB에 데이터가 없을 때")
  public void findByWriterFailTest1() {
    // Given: DB에 저장된 DetailPlan 객체가 없다.
    // And: valid wrtier가 주어진다.
    String validWriter = validUserId1;

    // When: DetailPlanDao.findByWriter() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriter(validWriter);

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriter 실패2: invalid writer가 주어질 때")
  public void findByWriterFailTest2() {
    // Given: writer와 planId가 다른 valid DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    DaoTestUtils.addDetailPlan(validUserId1, 2, 5);
    DaoTestUtils.addDetailPlan(validUserId2, 3, 5);
    // And: invalid wrtier가 주어진다.
    String invalidWriter = "invalidUserId";

    // When: DetailPlanDao.findByWriter() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriter(invalidWriter);

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriterAndPlanId 성공1: valid 데이터와 DB에 해당 데이터가 있을 때")
  public void findByWriterAndPlanIdSuccessTest1() {
    // Given: planId가 다른 valid DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    DaoTestUtils.addDetailPlan(validUserId1, 2, 5);
    // And: valid 데이터가 주어진다.
    String validWriter = validUserId1;
    int validPlanId = 1;

    // When: DetailPlanDao.findByWriterAndPlanId() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriterAndPlanId(validWriter, validPlanId);

    // Then: actaulList의 size는 5이다.
    assertThat(actualList.size()).isEqualTo(5);
    for (DetailPlan actual : actualList) {
      assertThat(actual.getPlanId()).isEqualTo(validPlanId);
      assertThat(actual.getWriter()).isEqualTo(validWriter);
    }
  }

  @Test
  @DisplayName("findByWriterAndPlanId 실패1: DB에 해당 데이터가 없을 때")
  public void findByWriterAndPlanIdFailTest1() {
    // Given: DB에 저장된 DetailPlan 객체가 없다.
    // And: valid 데이터가 주어진다.
    String validWriter = validUserId1;
    int validPlanId = 1;

    // When: DetailPlanDao.findByWriterAndPlanId() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriterAndPlanId(validWriter, validPlanId);

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriterAndPlanId 실패2: invalid writer가 주어질 때")
  public void findByWriterAndPlanIdFailTest2() {
    // Given: writer와 planId가 다른 valid DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    DaoTestUtils.addDetailPlan(validUserId1, 2, 5);
    DaoTestUtils.addDetailPlan(validUserId2, 3, 5);
    // And: invalid writer가 주어진다.
    String invalidWriter = "invalidUserId";
    int validPlanId = 1;

    // When: DetailPlanDao.findByWriterAndPlanId() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriterAndPlanId(invalidWriter, validPlanId);

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByWriterAndPlanId 실패3: invalid planId가 주어질 때")
  public void findByWriterAndPlanIdFailTest3() {
    // Given: writer와 planId가 다른 valid DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    DaoTestUtils.addDetailPlan(validUserId1, 2, 5);
    DaoTestUtils.addDetailPlan(validUserId2, 3, 5);
    // And: invalid planId가 주어진다.
    String validWriter = validUserId1;
    int invalidPlanId = -1;

    // When: DetailPlanDao.findByWriterAndPlanId() 메서드를 호출한다.
    List<DetailPlan> actualList = detailPlanDao.findByWriterAndPlanId(validWriter, invalidPlanId);

    // Then: actaulList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByDetailPlanId 성공1: valid detailPlanId가 주어질 때")
  public void findByDetailPlanIdSuccessTest1() {
    // Given: DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    // And: valid detailPlanId가 주어진다.
    int validDetailPlanId = 3;

    // When: DetailPlanDao.findByDetailPlanId() 메서드를 호출한다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(validDetailPlanId);

    // Then: actual의 id는 3, writer는 validuserId1, planId는 1이다.
    assertThat(actual.getDetailPlanId()).isEqualTo(3);
    assertThat(actual.getPlanId()).isEqualTo(1);
    assertThat(actual.getWriter()).isEqualTo(validUserId1);
  }

  @Test
  @DisplayName("findByDetailPlanId 실패1: invalid detailPlanId가 주어질 때")
  public void findByDetailPlanIdFailTest1() {
    // Given: DetailPlan 객체를 DB에 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 5);
    // And: valid detailPlanId가 주어진다.
    int validDetailPlanId = 13;

    // When: DetailPlanDao.findByDetailPlanId() 메서드를 호출한다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(validDetailPlanId);

    // Then: actual은 null이다.
    assertThat(actual).isNull();
  }
}
