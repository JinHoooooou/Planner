package com.kh.dao.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kh.database.DataAccessException;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateTest {

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
  @DisplayName("save 성공1: valid 데이터가 주어질 때")
  public void saveSuccessTest1() {
    // Given: valid 데이터가 주어진다.
    User validUser = User.builder()
        .userId("validUserId")
        .userPw("password")
        .userPwConfirm("password")
        .userName("validUserName")
        .nickname("nickname")
        .email("valid" + "@kh.kr")
        .phone("010-1234-5678")
        .build();

    // When: UserDao.save() 메서드를 호출한다.
    userDao.save(validUser);

    // Then: DB의 Users 테이블에 해당 레코드가 추가된다.
    User actual = userDao.findByUserId("validUserId");
    assertUser(actual, validUser);
  }

  @Test
  @DisplayName("save 성공2: Nullable 컬럼 데이터가 주어지지 않을 때")
  public void saveSuccessTest2() {
    // Given: valid 데이터가 주어진다.
    // Nullable 컬럼에 대한 데이터는 null로 주어진다.
    User validUser = User.builder()
        .userId("validUserId")
        .userPw("password")
        .userPwConfirm("password")
        .userName("validName")
        .nickname("validNickname")
        .email("valid@kh.kr")
        .phone(null)
        .build();

    // When: UserDao.save() 메서드를 호출한다.
    int result = userDao.save(validUser);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB의 Users 테이블에 해당 레코드가 추가된다.
    User actual = userDao.findByUserId("validUserId");
    assertUser(actual, validUser);
  }

  @Test
  @DisplayName("save 실패1: userPw와 userPwConfirm이 다를 때")
  public void saveFailTest1() {
    // Given: invalid 데이터가 주어진다.
    // userPw와 userPwConfirm이 다르다.
    User invalidUser = User.builder()
        .userId("validUserId")
        .userPw("password1")
        .userPwConfirm("password2")
        .userName("validName")
        .nickname("validNickname")
        .email("valid@kh.kr")
        .phone("010-1234-5678")
        .build();

    // When: UserDao.save() 메서드를 호출한다.
    // Then: IllegalArgumentException이 발생한다.
    assertThatThrownBy(() -> userDao.save(invalidUser)).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Not Equal Password with Password Confirm");
  }

  @Test
  @DisplayName("save 실패2: NotNull인 컬럼의 값이 주어지지 않을 때")
  public void saveFailTest2() {
    // Given: invalid 데이터가 주어진다.
    // NotNull 컬럼인 userId가 주어지지 않는다.
    User invalidUser = User.builder()
        .userPw("password")
        .userPwConfirm("password")
        .userName("validName")
        .nickname("validNickname")
        .email("valid@kh.kr")
        .phone("010-1234-5678")
        .build();

    // When: UserDao.save() 메서드를 호출한다.
    // Then: DataAccessException 발생한다.
    assertThatThrownBy(() -> userDao.save(invalidUser)).isInstanceOf(DataAccessException.class)
        .hasMessageContaining("NULL");
  }

  @Test
  @DisplayName("save 실패3: userId가 이미 있을 때")
  public void createFailTest3() {
    // Given: DB에 userId가 "validUserId"인 User 레코드를 저장한다.
    User validUser = User.builder()
        .userId("validUserId")
        .userPw("password")
        .userPwConfirm("password")
        .userName("validName0")
        .nickname("validNickname0")
        .email("valid0@kh.kr")
        .phone("010-1235-5678")
        .build();
    userDao.save(validUser);
    // And: 같은 userId를 가진 데이터가 주어진다.
    User invalidUser = User.builder()
        .userId("validUserId")
        .userPw("password")
        .userPwConfirm("password")
        .userName("validName1")
        .nickname("validNickname1")
        .email("valid1@kh.kr")
        .phone("010-1236-5678")
        .build();

    // When: UserDao.save() 메서드를 호출한다.
    // Then: DataAccessException 발생한다.
    assertThatThrownBy(() -> userDao.save(invalidUser)).isInstanceOf(DataAccessException.class)
        .hasMessageContaining("무결성");
  }

  @Test
  @DisplayName("save 실패: nickname이 이미 있을 때")
  public void createFailTest4() {
    // Given: DB에 nickname이 "validNickname"인 레코드를 저장한다.
    User validUser = User.builder()
        .userId("validUserId0")
        .userPw("password")
        .userPwConfirm("password")
        .userName("validName0")
        .nickname("validNickname")
        .email("valid0@kh.kr")
        .phone("010-1235-5678")
        .build();
    userDao.save(validUser);
    // And: 같은 nickname을 가진 데이터가 주어진다.
    User invalidUser = User.builder()
        .userId("validUserId1")
        .userPw("password")
        .userPwConfirm("password")
        .userName("validName1")
        .nickname("validNickname")
        .email("valid1@kh.kr")
        .phone("010-1236-5678")
        .build();

    // When: UserDao.save() 메서드를 호출한다.
    // Then: DataAccessException 발생한다.
    assertThatThrownBy(() -> userDao.save(invalidUser)).isInstanceOf(DataAccessException.class)
        .hasMessageContaining("무결성");
  }

  private void assertUser(User actual, User expected) {
    assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
    assertThat(actual.getUserPw()).isEqualTo(expected.getUserPw());
    assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
    assertThat(actual.getNickname()).isEqualTo(expected.getNickname());
    assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    assertThat(actual.getPhone()).isEqualTo(expected.getPhone());
  }
}
