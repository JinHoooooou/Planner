package com.kh.users.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DdlHelper;
import com.kh.users.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsertTest {

  private UsersDao usersDao;

  @BeforeEach
  public void setUp() {
    usersDao = new UsersDao();
    DdlHelper.resetUsersSequence();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();
  }

  @Test
  public void insertTest1() {
    // Given: valid Users 객체가 주어진다.
    Users validUsers = buildValidUsers();

    // When: UserDao.insert() 메서드를 호출한다.
    usersDao.insert(validUsers);

    // Then: DB Users 테이블에 컬럼이 추가된다.
    Users actual = usersDao.findByUserNo(1);
    assertThat(actual.getUserNo()).isEqualTo(1);
    assertThat(actual.getUserId()).isEqualTo("validUserId");
    assertThat(actual.getUserPw()).isEqualTo("password");
    assertThat(actual.getUserName()).isEqualTo("이진호");
    assertThat(actual.getNickName()).isEqualTo("고소하게");
    assertThat(actual.getEmail()).isEqualTo("jinho@kh.kr");
    assertThat(actual.getPhone()).isEqualTo("010-1234-5678");
    assertThat(actual.getSsn()).isEqualTo("010203-3123456");
    assertThat(actual.getAddress()).isEqualTo("Seoul");
  }

  private Users buildValidUsers() {
    return Users.builder()
        .userId("validUserId")
        .userPw("password")
        .userName("이진호")
        .nickName("고소하게")
        .email("jinho@kh.kr")
        .phone("010-1234-5678")
        .ssn("010203-3123456")
        .address("Seoul")
        .build();
  }
}
