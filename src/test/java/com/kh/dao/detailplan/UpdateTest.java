package com.kh.dao.detailplan;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTest {

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
  @DisplayName("update 성공1: valid 데이터가 주어질 때")
  public void updateSuccessTest1() {
    // Given: DB에 DetailPlan 레코드를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: 수정할 데이터가 주어진다.
    DetailPlan updateDetailPlan = DetailPlan.builder()
        .detailPlanId(1)
        .planId(1)
        .writer(validUserId1)
        .contents("updateContents")
        .startTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 12, 20)))
        .endTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 12, 20)))
        .remindAlarmTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 30, 30)))
        .complete(true)
        .build();

    // When: DetailPlanDao.update() 메서드를 호출한다.
    int result = detailPlanDao.update(updateDetailPlan);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 있던 레코드는 수정할 데이터로 수정된다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual.getContents()).isEqualTo(updateDetailPlan.getContents());
    assertThat(actual.getStartTime())
        .isEqualToIgnoringNanos(updateDetailPlan.getStartTime());
    assertThat(actual.getEndTime())
        .isEqualToIgnoringNanos(updateDetailPlan.getEndTime());
    assertThat(actual.getRemindAlarmTime())
        .isEqualToIgnoringNanos(updateDetailPlan.getRemindAlarmTime());
    assertThat(actual.isComplete()).isEqualTo(updateDetailPlan.isComplete());
  }

  @Test
  @DisplayName("update 성공2: Nullable 컬럼에 대한 데이터가 null일 때")
  public void updateSuccessTest2() {
    // Given: DB에 DetailPlan 레코드를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: 수정할 데이터가 주어진다.
    // Nullable 컬럼에 대한 데이터는 null로 주어진다.
    DetailPlan updateDetailPlan = DetailPlan.builder()
        .detailPlanId(1)
        .planId(1)
        .writer(validUserId1)
        .contents(null)
        .startTime(null)
        .endTime(null)
        .remindAlarmTime(null)
        .build();

    // When: DetailPlanDao.update() 메서드를 호출한다.
    int result = detailPlanDao.update(updateDetailPlan);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 있던 레코드는 수정할 데이터로 수정된다.
    // And: Nullable 데이터는 null로 수정된다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual.getContents()).isNull();
    assertThat(actual.getStartTime()).isNull();
    assertThat(actual.getEndTime()).isNull();
    assertThat(actual.getRemindAlarmTime()).isNull();
    assertThat(actual.isComplete()).isFalse();
  }

  @Test
  @DisplayName("update 실패1: invalid detailPlanId가 주어질 때")
  public void updateFailTest1() {
    // Given: DB에 DetailPlan 객체를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: invalid detailPlanId가 주어진다.
    DetailPlan updateDetailPlan = DetailPlan.builder()
        .detailPlanId(-1)
        .planId(1)
        .writer(validUserId1)
        .contents("updateContents")
        .build();

    // When: DetailPlanDao.update() 메서드를 호출한다.
    int result = detailPlanDao.update(updateDetailPlan);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 레코드는 수정되지 않는다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual.getContents()).isNotEqualTo(updateDetailPlan.getContents());
  }

  @Test
  @DisplayName("update 실패2: invalid planId가 주어질 때")
  public void updateFailTest2() {
    // Given: DB에 DetailPlan 레코드를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: invalid planId가 주어진다.
    DetailPlan updateDetailPlan = DetailPlan.builder()
        .detailPlanId(1)
        .planId(10)
        .writer(validUserId1)
        .contents("updateContents")
        .build();

    // When: DetailPlanDao.update() 메서드를 호출한다.
    int result = detailPlanDao.update(updateDetailPlan);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 레코드는 수정되지 않는다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual.getContents()).isNotEqualTo(updateDetailPlan.getContents());
  }

  @Test
  @DisplayName("update 실패3: invalid writer가 주어질 때")
  public void updateFailTest3() {
    // Given: DB에 DetailPlan 레코드를 저장한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 1);
    // And: invalid writer가 주어진다.
    DetailPlan updateDetailPlan = DetailPlan.builder()
        .detailPlanId(1)
        .planId(1)
        .writer("invalidUserId")
        .contents("updateContents")
        .build();

    // When: DetailPlanDao.update() 메서드를 호출한다.
    int result = detailPlanDao.update(updateDetailPlan);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 레코드는 수정되지 않는다.
    DetailPlan actual = detailPlanDao.findByDetailPlanId(1);
    assertThat(actual.getContents()).isNotEqualTo(updateDetailPlan.getContents());
  }
}
