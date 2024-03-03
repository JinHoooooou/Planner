package com.kh.dao.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.database.DataAccessException;
import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTest {

  private UserDao userDao;

  @BeforeAll
  public static void setUpAll() {
    DdlHelper.dropTable("DETAIL_PLAN");
    DdlHelper.dropTable("PLAN");
  }

  @BeforeEach
  public void setUp() {
    userDao = new UserDao();
    DdlHelper.dropTable("USERS");
    DdlHelper.createUsersTable();
  }

  @Test
  @DisplayName("updateUserInfo 성공1: valid 데이터가 주어질 때")
  public void updateUserInfoSuccessTest1() {
    // Given: DB에 User 레코드를 저장한다.
    String validUserId = "validUserId";
    DaoTestUtils.addUserData(validUserId);
    // And: 수정할 데이터가 주어진다.
    User updateUser = User.builder()
        .userId(validUserId)
        .nickname("updateNickname")
        .email("update@kh.kr")
        .phone("010-9876-5432")
        .build();

    // When: UserDao.updateUserInfo() 메서드를 호출한다.
    int result = userDao.updateUserInfo(updateUser);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 있던 레코드는 수정할 데이터로 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
    assertThat(actual.getNickname()).isEqualTo(updateUser.getNickname());
    assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    assertThat(actual.getPhone()).isEqualTo(updateUser.getPhone());
  }

  @Test
  @DisplayName("updateUserInfo 성공2: Nullable 컬럼에 대한 데이터가 null일 때")
  public void updateUserInfoSuccessTest2() {
    // Given: DB에 User 레코드를 저장한다.
    String validUserId = "validUserId";
    DaoTestUtils.addUserData(validUserId);
    // And: 수정할 데이터가 주어진다.
    // Nullable 컬럼에 대한 데이터는 null로 주어진다.
    User updateUser = User.builder()
        .userId(validUserId)
        .nickname("updateNickname")
        .email("update@kh.kr")
        .phone(null)
        .build();

    // When: UserDao.updateUserInfo() 메서드를 호출한다.
    int result = userDao.updateUserInfo(updateUser);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // Then: DB에 있던 레코드는 수정할 데이터로 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
    assertThat(actual.getNickname()).isEqualTo(updateUser.getNickname());
    assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    // And: Nullable 컬럼은 null로 수정된다.
    assertThat(actual.getPhone()).isNull();
  }

  @Test
  @DisplayName("updateUserInfo 실패1: invalid userId가 주어질 때")
  public void updateUserInfoFailTest1() {
    // Given: DB에 User 레코드를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid userId가 주어진다.
    User invalidUser = User.builder()
        .userId("invalidUserId")
        .nickname("updateNickname")
        .email("update@kh.kr")
        .phone("010-9876-5432")
        .build();

    // When: UserDao.updateUserInfo() 메서드를 호출한다.
    int result = userDao.updateUserInfo(invalidUser);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 해당 레코드는 수정되지 않는다.
    User actual = userDao.findByUserId("validUserId");
    assertThat(actual.getUserId()).isNotEqualTo(invalidUser.getUserId());
    assertThat(actual.getNickname()).isNotEqualTo(invalidUser.getNickname());
    assertThat(actual.getEmail()).isNotEqualTo(invalidUser.getEmail());
    assertThat(actual.getPhone()).isNotEqualTo(invalidUser.getPhone());
  }

  @Test
  @DisplayName("updateUserInfo 실패2: NotNull 컬림에 대한 데이터가 null일 때")
  public void updateUserInfoFailTest2() {
    // Given: DB에 User 레코드를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid 데이터가 주어진다.
    // nickname이 null이다.
    User invalidUser = User.builder()
        .userId("validUserId")
        .nickname(null)
        .email("update@kh.kr")
        .phone("010-9876-5432")
        .build();

    // When: UserDao.updateUserInfo() 메서드를 호출한다.
    // Then: DataAccessException이 발생한다.
    assertThatThrownBy(() -> userDao.updateUserInfo(invalidUser))
        .isInstanceOf(DataAccessException.class)
        .hasMessageContaining("NULL");
  }

  @Test
  @DisplayName("updatePassword 성공1: valid 데이터가 주어질 때")
  public void updatePasswordSuccessTest1() {
    // Given: DB에 User 레코드를 저장한다.
    String validUserId = "validUserId";
    DaoTestUtils.addUserData(validUserId);
    // And: valid 데이터가 주어진다.
    User updateUser = User.builder()
        .userId(validUserId)
        .userPw("updatePassword")
        .userPwConfirm("updatePassword")
        .build();

    // When: UserDao.updatePassword() 메서드를 호출한다.
    int result = userDao.updatePassword(updateUser);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 있던 해당 레코드는 수정할 데이터로 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
    assertThat(actual.getUserPw()).isEqualTo(updateUser.getUserPw());
  }

  @Test
  @DisplayName("updatePassword 실패1: invalid userId가 주어질 때")
  public void updatePasswordFailTest1() {
    // Given: DB에 User 레코드를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid userId가 주어진다.
    User invalidUser = User.builder()
        .userId("invalidUserId")
        .userPw("updatePassword")
        .userPwConfirm("updatePassword")
        .build();

    // When: UserDao.updatePassword() 메서드를 호출한다.
    int result = userDao.updatePassword(invalidUser);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 있던 해당 레코드는 수정되지 않는다.
    User actual = userDao.findByUserId("validUserId");
    assertThat(actual.getUserId()).isNotEqualTo(invalidUser.getUserId());
    assertThat(actual.getUserPw()).isNotEqualTo(invalidUser.getUserPw());
  }

  @Test
  @DisplayName("updatePassword 실패2: invalid 데이터가 주어질 때")
  public void updatePasswordFailTest2() {
    // Given: DB에 User 레코드를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid 데이터가 주어진다.
    // userPw와 userPwConfirm이 다르다.
    User invalidUser = User.builder()
        .userId("validUserId")
        .userPw("updatePassword")
        .userPwConfirm("updatePassword1")
        .build();

    // When: UserDao.updatePassword() 메서드를 호출한다.
    // Then: IllegalArgumentExcetpion이 발생한다.
    assertThatThrownBy(() -> userDao.updatePassword(invalidUser))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Not Equal Password with Password Confirm");
  }
}
