package com.kh.dao.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.dao.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsertTest {

  private UserDao userDao;

  @BeforeEach
  public void setUp() {
    userDao = new UserDao();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();
  }

  @Test
  public void insertOneUserTest() {
    // Given: valid Users 객체가 주어진다.
    User validUser = DaoTestUtils.buildValidUsers(1).get(0);

    // When: UserDao.insert() 메서드를 호출한다.
    userDao.insert(validUser);

    // Then: DB Users 테이블에 레코드가 추가된다.
    User actual = userDao.findByUserId("validUserId" + 0);
    assertUser(actual, validUser);
  }

  @Test
  public void insertTenUserTest() {
    // Given: valid Users 객체가 10개 주어진다.
    List<User> validUserList = DaoTestUtils.buildValidUsers(10);

    // When: UserDao.insert() 메서드를 10번 호출한다.
    for (User validUser : validUserList) {
      userDao.insert(validUser);
    }

    // Then: DB User 테이블에 레코드가 10개 추가된다.
    List<User> actualList = userDao.findAll();
    for (int i = 0; i < actualList.size(); i++) {
      assertUser(actualList.get(i), validUserList.get(i));
    }
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
