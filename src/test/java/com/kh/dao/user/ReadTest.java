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
  @DisplayName("findAll 성공1: DB에 레코드가 있을 때")
  public void findAllSuccessTest1() {
    // Given: DB에 User 레코드를 저장한다.
    for (int i = 0; i < 10; i++) {
      DaoTestUtils.addUserData("validUserId" + i);
    }

    // When: UserDao.findAll() 메소드를 호출한다.
    List<User> actualList = userDao.findAll();

    // Then: actualList의 size는 10이다.
    assertThat(actualList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("findAll 실패1: DB에 레코드가 없을 때")
  public void findAllSuccessTest2() {
    // Given: DB에 저장된 User 레코드가 없다.

    // When: UserDao.findAll() 메소드를 호출한다.
    List<User> actualList = userDao.findAll();

    // Then: actualList의 size는 0이다.
    assertThat(actualList.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("findByUserId 성공: valid userId가 주어질 때")
  public void findByUserIdSuccessTest1() {
    // Given: DB에 User 레코드를 저장한다.
    DaoTestUtils.addUserData("validUserId");

    // And: valid userId가 주어진다.
    String validUserId = "validUserId";

    // When: UserDao.findByUserId() 메소드를 호출한다.
    User actual = userDao.findByUserId(validUserId);

    // Then: actual의 userId는 "validUserId"이다.
    assertThat(actual.getUserId()).isEqualTo(validUserId);
  }

  @Test
  @DisplayName("findByUserId 실패: invalid userId가 주어질 때")
  public void findByUserIdFailTest1() {
    // Given: DB에 User 레코드를 저장한다.
    DaoTestUtils.addUserData("validUserId");
    // And: invalid userId가 주어진다.
    String invalidUserId = "invalidUserId";

    // When: UserDao.findByUserId() 메소드를 호출한다.
    User actual = userDao.findByUserId(invalidUserId);

    // Then: actual은 null이다.
    assertThat(actual).isNull();
  }
}
