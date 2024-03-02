package com.kh.dao.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReadTest {

  /*
   * TODO: 모든 유저 목록은 관리자일 때 가능하도록
   *
   *  */
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
  @DisplayName("findAll 성공: DB에 데이터가 있을 때")
  public void findAllSuccessTest1() {
    // Given: 10개의 User 객체를 DB에 저장한다.
    for (int i = 0; i < 10; i++) {
      DaoTestUtils.addUserData("validUserId" + i);
    }

    // When: UserDao.findAll() 메소드를 호출한다.
    List<User> actualList = userDao.findAll();

    // Then: actualList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
    for (int i = 0; i < 10; i++) {
      assertThat(actualList.get(i).getUserId()).isEqualTo("validUserId" + i);
      assertThat(actualList.get(i).getNickname()).isEqualTo("validUserId" + i);
    }
  }

  @Test
  @DisplayName("findAll 성공: DB에 데이터가 없을 때")
  public void findAllSuccessTest2() {
    // Given: DB에 저장된 User 객체가 없다.

    // When: UserDao.findAll() 메소드를 호출한다.
    List<User> actualList = userDao.findAll();

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByUserId 성공: DB에 해당 데이터가 있을 때")
  public void findByUserIdSuccessTest1() {
    // Given: 10개의 User 객체를 DB에 저장한다.
    for (int i = 0; i < 10; i++) {
      DaoTestUtils.addUserData("validUserId" + i);
    }
    // And: valid userId가 주어진다.
    String validUserId = "validUserId0";

    // When: UserDao.findByUserId() 메소드를 호출한다.
    User actual = userDao.findByUserId(validUserId);

    // Then: actual의 userId는 "validUserId0"이다.
    assertThat(actual.getUserId()).isEqualTo(validUserId);
  }

  @Test
  @DisplayName("findByUserId 실패: DB에 해당 데이터가 없을 때")
  public void findByUserIdFailTest1() {
    // Given: 10개의 User 객체를 DB에 저장한다.
    for (int i = 0; i < 10; i++) {
      DaoTestUtils.addUserData("validUserId" + i);
    }
    // And: invalid userId가 주어진다.
    String invalidUserId = "invalidUserId";

    // When: UserDao.findByUserId() 메소드를 호출한다.
    User actual = userDao.findByUserId(invalidUserId);

    // Then: actual은 null이다.
    assertThat(actual).isNull();
  }
}
