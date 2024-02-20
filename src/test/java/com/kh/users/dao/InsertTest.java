package com.kh.users.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DdlHelper;
import com.kh.users.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsertTest {

  private UserDao userDao;

  @BeforeEach
  public void setUp() {
    userDao = new UserDao();
    DdlHelper.resetUsersSequence();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();
  }

  @Test
  public void insertTest1() {
    // Given: valid Users 객체가 주어진다.
    User validUser = buildValidUser();

    // When: UserDao.insert() 메서드를 호출한다.
    userDao.insert(validUser);

    // Then: DB Users 테이블에 컬럼이 추가된다.
    User actual = userDao.findByUserNo(1);
    assertThat(actual.getUserNo()).isEqualTo(1);
    assertThat(actual.getUserId()).isEqualTo("validUserId");
    assertThat(actual.getUserPw()).isEqualTo("password");
    assertThat(actual.getUserName()).isEqualTo("이진호");
    assertThat(actual.getNickname()).isEqualTo("고소하게");
    assertThat(actual.getEmail()).isEqualTo("jinho@kh.kr");
    assertThat(actual.getPhone()).isEqualTo("010-1234-5678");
    assertThat(actual.getSsn()).isEqualTo("010203-3123456");
    assertThat(actual.getAddress()).isEqualTo("Seoul");
  }

  private User buildValidUser() {
    return User.builder()
        .userId("validUserId")
        .userPw("password")
        .userName("이진호")
        .nickname("고소하게")
        .email("jinho@kh.kr")
        .phone("010-1234-5678")
        .ssn("010203-3123456")
        .address("Seoul")
        .build();
  }
}
