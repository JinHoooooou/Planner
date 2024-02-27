package com.kh.users.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateTest {

  private UserDao userDao;

  @BeforeEach
  public void setUp() {
    userDao = new UserDao();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();

    DaoTestUtils.addUserData(userDao);
  }

  @Test
  public void updateUserInfoSuccessTest1() {
    // Given: DB에 저장된 valid User의 Id가 주어진다.
    String validUserId = "validUserId";
    // And: 수정할 데이터들이 주어진다.
    String updateNickname = "updateNickname";
    String updateEmail = "update@kh.kr";
    String updatePhone = "0104567890";

    // When: UserDao.update() 메서드를 호출한다.
    userDao.updateUserInfo(validUserId,
        User.builder()
            .nickname(updateNickname)
            .email(updateEmail)
            .phone(updatePhone)
            .build());

    // Then: DB에 저장된 User 객체의 정보가 수정된다.
    User actual = userDao.findByUserId("validUserId");
    assertThat(actual.getUserId()).isEqualTo(validUserId);
    assertThat(actual.getNickname()).isEqualTo(updateNickname);
    assertThat(actual.getEmail()).isEqualTo(updateEmail);
    assertThat(actual.getPhone()).isEqualTo(updatePhone);
  }

  @Test
  public void updatePasswordTest() {
    // Given: DB에 저장된 valid User의 Id가 주어진다.
    String validUserId = "validUserId";
    // And: 수정할 password가 주어진다.
    String updatePassword1 = "updatePassword";
    String updatePassword2 = "updatePassword";

    // When: UserDao.updatePassword() 메서드를 호출한다.
    userDao.updatePassword(validUserId, updatePassword1, updatePassword2);

    // Then: DB에 저장된 User 객체의 정보가 수정된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual.getUserId()).isEqualTo(validUserId);
    assertThat(actual.getUserPw()).isEqualTo(updatePassword1);
  }

}
