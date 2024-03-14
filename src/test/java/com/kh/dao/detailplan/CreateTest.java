package com.kh.dao.detailplan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.database.DataAccessException;
import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.DetailPlanDao;
import com.kh.model.vo.DetailPlan;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateTest {

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
    DaoTestUtils.addPlanData(validUserId1, "validTitle", 1);
  }

  @Test
  @DisplayName("save 성공1: valid 데이터가 주어질 때")
  public void saveSuccessTest1() {
    // Given: valid 데이터가 주어진다.
    DetailPlan validDetailPlan = DetailPlan.builder()
        .planId(1)
        .writer(validUserId1)
        .contents("validContents")
        .startTime(LocalDateTime.now())
        .endTime(LocalDateTime.now())
        .remindAlarmTime(LocalDateTime.now())
        .complete(false)
        .build();

    // When: DetailPlanDao.save() 메서드를 호출한다.
    DetailPlan actual = detailPlanDao.save(validDetailPlan);

    // Then: DB의 Detail_Plan 테이블에 해당 레코드가 추가된다.
    assertThat(actual.getDetailPlanId()).isEqualTo(1);
    assertThat(actual.getPlanId()).isEqualTo(validDetailPlan.getPlanId());
    assertThat(actual.getContents()).isEqualTo(validDetailPlan.getContents());
    assertThat(actual.getStartTime()).isEqualToIgnoringNanos(validDetailPlan.getStartTime());
    assertThat(actual.getEndTime()).isEqualToIgnoringNanos(validDetailPlan.getEndTime());
    assertThat(actual.getRemindAlarmTime())
        .isEqualToIgnoringNanos(validDetailPlan.getRemindAlarmTime());
    assertThat(actual.isComplete()).isEqualTo(validDetailPlan.isComplete());
  }

  @Test
  @DisplayName("save 성공2: id check 관련")
  public void saveSuccessTest3() {
    // Given: 10개의 DetailPlan을 추가한다.
    DaoTestUtils.addDetailPlan(validUserId1, 1, 10);
    // Given: valid 데이터가 주어진다.
    DetailPlan validDetailPlan = DetailPlan.builder()
        .planId(1)
        .writer(validUserId1)
        .contents("validContents")
        .startTime(LocalDateTime.now())
        .endTime(LocalDateTime.now())
        .remindAlarmTime(LocalDateTime.now())
        .complete(false)
        .build();

    // When: DetailPlanDao.save() 메서드를 호출한다.
    DetailPlan actual = detailPlanDao.save(validDetailPlan);

    // Then: DB의 Detail_Plan 테이블에 해당 레코드가 추가된다.
    assertThat(actual.getDetailPlanId()).isEqualTo(11);
    assertThat(actual.getPlanId()).isEqualTo(validDetailPlan.getPlanId());
    assertThat(actual.getContents()).isEqualTo(validDetailPlan.getContents());
    assertThat(actual.getStartTime()).isEqualToIgnoringNanos(validDetailPlan.getStartTime());
    assertThat(actual.getEndTime()).isEqualToIgnoringNanos(validDetailPlan.getEndTime());
    assertThat(actual.getRemindAlarmTime())
        .isEqualToIgnoringNanos(validDetailPlan.getRemindAlarmTime());
    assertThat(actual.isComplete()).isEqualTo(validDetailPlan.isComplete());
  }

  @Test
  @DisplayName("save 성공2: Nullable 컬럼에 대한 데이터가 null일 때")
  public void saveSuccessTest2() {
    // Given: valid 데이터가 주어진다.
    // Nullable 컬럼에 대한 데이터는 null로 주어진다.
    DetailPlan validDetailPlan = DetailPlan.builder()
        .planId(1)
        .writer(validUserId1)
        .contents(null)
        .startTime(null)
        .endTime(null)
        .remindAlarmTime(null)
        .build();

    // When: DetailPlanDao.save() 메서드를 호출한다.
    DetailPlan actual = detailPlanDao.save(validDetailPlan);

    // Then: DB의 Detail_Plan 테이블에 해당 데이터가 추가된다.
    assertThat(actual.getDetailPlanId()).isEqualTo(1);
    // And: Nullable 컬럼은 null로 저장된다.
    assertThat(actual.getContents()).isNull();
    assertThat(actual.getStartTime()).isNull();
    assertThat(actual.getEndTime()).isNull();
    assertThat(actual.getRemindAlarmTime()).isNull();
  }

  @Test
  @DisplayName("save 실패1: invalid writer가 주어질 때")
  public void saveFailTest1() {
    // Given: invalid writer가 주어진다.
    DetailPlan invalidDetailPlan = DetailPlan.builder()
        .planId(1)
        .writer("invalidUserId")
        .build();

    // When: DetailPlanDao.save() 메서드를 호출한다.
    // Then: DataAccessException이 발생한다.
    assertThatThrownBy(() -> detailPlanDao.save(invalidDetailPlan))
        .isInstanceOf(DataAccessException.class)
        .hasMessageContaining("부모 키");
  }

  @Test
  @DisplayName("save 실패2: invalid planId가 주어질 때")
  public void saveFailTest2() {
    // Given: invalid planId가 주어진다.
    DetailPlan invalidDetailPlan = DetailPlan.builder()
        .planId(-23)
        .writer(validUserId1)
        .build();

    // When: DetailPlanDao.save() 메서드를 호출한다.
    // Then: DataAccessException이 발생한다.
    assertThatThrownBy(() -> detailPlanDao.save(invalidDetailPlan))
        .isInstanceOf(DataAccessException.class)
        .hasMessageContaining("부모 키");
  }
}
