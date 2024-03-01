package com.kh.dao.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.dao.DaoTestUtils;
import com.kh.database.DataAccessException;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UpdateTest {

  private UserDao userDao;

  @BeforeEach
  public void setUp() {
    userDao = new UserDao();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();
  }

  @Test
  @DisplayName("updateUserInfo 성공1: valid 데이터가 주어지고 DB에 해당 데이터가 있을 때")
  public void updateUserInfoSuccessTest1() {
    // Given: DB에 User 객체를 저장한다.
    String validUserId = "validUserId";
    DaoTestUtils.addUserData(validUserId);
    // And: update할 데이터를 가진 User 객체가 주어진다.
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
    // And: DB에 저장된 User 객체의 정보가 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
    assertThat(actual.getNickname()).isEqualTo(updateUser.getNickname());
    assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    assertThat(actual.getPhone()).isEqualTo(updateUser.getPhone());
  }

  @Test
  @DisplayName("updateUserInfo 성공: Nullable인 컬럼의 값이 null일 때")
  public void updateUserInfoSuccessTest2() {
    // Given: DB에 User 객체를 저장한다.
    String validUserId = "validUserId";
    DaoTestUtils.addUserData(validUserId);
    // And: update할 데이터를 가진 User 객체가 주어진다.
    // phone은 null이다.
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
    // Then: DB에 저장된 User 객체의 정보가 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
    assertThat(actual.getNickname()).isEqualTo(updateUser.getNickname());
    assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    assertThat(actual.getPhone()).isEqualTo(updateUser.getPhone());
  }

  @Test
  @DisplayName("updateUserInfo 실패: DB에 해당 데이터가 없을 때")
  public void updateUserInfoFailTest1() {
    // Given: DB에 User 객체를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid User 객체가 주어진다.
    // userId가 DB에 없다.
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
    // And: DB에 저장된 userId가 "validUserId"인 User 객체의 정보가 수정되지 않는다.
    User actual = userDao.findByUserId("validUserId");
    assertThat(actual.getUserId()).isNotEqualTo(invalidUser.getUserId());
    assertThat(actual.getNickname()).isNotEqualTo(invalidUser.getNickname());
    assertThat(actual.getEmail()).isNotEqualTo(invalidUser.getEmail());
    assertThat(actual.getPhone()).isNotEqualTo(invalidUser.getPhone());
  }

  @Test
  @DisplayName("updateUserInfo 실패: NotNull인 컬림이 null일 때")
  public void updateUserInfoFailTest2() {
    // Given: DB에 User 객체를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid User 객체가 주어진다.
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
  @DisplayName("updatePassword 성공1: valid 데이터가 주어지고 DB에 해당 데이터가 있을 때")
  public void updatePasswordSuccessTest1() {
    // Given: DB에 User 객체를 저장한다.
    String validUserId = "validUserId";
    DaoTestUtils.addUserData(validUserId);
    // And: update password 데이터를 가진 User 객체가 주어진다.
    User updateUser = User.builder()
        .userId(validUserId)
        .userPw("updatePassword")
        .userPwConfirm("updatePassword")
        .build();

    // When: UserDao.updatePassword() 메서드를 호출한다.
    int result = userDao.updatePassword(updateUser);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에 저장된 User 객체의 정보가 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(updateUser.getUserId());
    assertThat(actual.getUserPw()).isEqualTo(updateUser.getUserPw());
  }

  @Test
  @DisplayName("updatePassword 실패: DB에 해당 데이터가 없을 때")
  public void updatePasswordFailTest1() {
    // Given: DB에 User 객체를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid User 객체가 주어진다.
    // userId가 DB에 없다.
    User invalidUser = User.builder()
        .userId("invalidUserId")
        .userPw("updatePassword")
        .userPwConfirm("updatePassword")
        .build();

    // When: UserDao.updatePassword() 메서드를 호출한다.
    int result = userDao.updatePassword(invalidUser);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에 저장된 userId가 "validUserId"인 User 객체의 정보가 수정되지 않는다.
    User actual = userDao.findByUserId("validUserId");
    assertThat(actual.getUserId()).isNotEqualTo(invalidUser.getUserId());
    assertThat(actual.getUserPw()).isNotEqualTo(invalidUser.getUserPw());
  }

  @Test
  @DisplayName("updatePassword 실패: userPw와 userPwConfirm이 다를 때")
  public void updatePasswordFailTest2() {
    // Given: DB에 User 객체를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid User 객체가 주어진다.
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
