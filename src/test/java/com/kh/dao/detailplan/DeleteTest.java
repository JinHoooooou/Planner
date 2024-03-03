package com.kh.dao.detailplan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTest {

  DetailPlanDao detailPlanDao;
  String validUserId1 = "validUserId1";

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
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 2);
  }

  @Test
  @DisplayName("deleteByDetailPlanIdAndPlanIdAndWriter 성공1: valid 데이터가 주어지고 DB에 해당 데이터가 있을 때")
  public void deleteByDetailPlanIdAndPlanIdAndWriterSuccessTest1() {
    // Given: DB에 DetailPlan 객체를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: valid 데이터가 주어진다.
    int validDetailPlanId = 1;
    String validWriter = validUserId1;
    int validPlanId = 1;

    // When: DetailPlanDao.deleteByDetailPlanIdAndWrtier() 메서드를 호출한다.
    int result = detailPlanDao.deleteByDetailPlanIdAndWriterAndPlanId(validDetailPlanId,
        validPlanId,
        validWriter);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 해당 데이터가 삭제된다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual).isNull();
  }

  @Test
  @DisplayName("deleteByDetailPlanIdAndPlanIdAndWriter 실패1: invalid detailPlanId가 주어질 때")
  public void deleteByDetailPlanIdAndPlanIdAndWriterFailTest1() {
    // Given: DB에 DetailPlan 객체를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: invalid detailPlanId가 주어진다.
    int invalidDetailPlanId = 6;
    String validWriter = validUserId1;
    int validPlanId = 1;

    // When: DetailPlanDao.deleteByDetailPlanIdAndPlanIdAndWriter() 메서드를 호출한다.
    int result = detailPlanDao.deleteByDetailPlanIdAndWriterAndPlanId(invalidDetailPlanId,
        validPlanId, validWriter);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 해당 데이터가 삭제되지 않는다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual).isNotNull();
  }

  @Test
  @DisplayName("deleteByDetailPlanIdAndPlanIdAndWriter 실패2: invalid planId가 주어질 때")
  public void deleteByDetailPlanIdAndPlanIdAndWriterFailTest2() {
    // Given: DB에 DetailPlan 객체를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: invalid planId가 주어진다.
    int validDetailPlanId = 1;
    String validWriter = validUserId1;
    int invalidPlanId = -12;

    // When: DetailPlanDao.deleteByDetailPlanIdAndPlanIdAndWriter() 메서드를 호출한다.
    int result = detailPlanDao.deleteByDetailPlanIdAndWriterAndPlanId(validDetailPlanId,
        invalidPlanId, validWriter);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 해당 데이터가 삭제되지 않는다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual).isNotNull();
  }

  @Test
  @DisplayName("deleteByDetailPlanIdAndPlanIdAndWriter 실패3: invalid writer가 주어질 때")
  public void deleteByDetailPlanIdAndPlanIdAndWriterFailTest3() {
    // Given: DB에 DetailPlan 객체를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: invalid writer가 주어진다.
    int validDetailPlanId = 1;
    String invalidWriter = "invalidUserId";
    int validPlanId = 1;

    // When: DetailPlanDao.deleteByDetailPlanIdAndPlanIdAndWriter() 메서드를 호출한다.
    int result = detailPlanDao.deleteByDetailPlanIdAndWriterAndPlanId(validDetailPlanId,
        validPlanId, invalidWriter);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 해당 데이터가 삭제되지 않는다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual).isNotNull();
  }

}
