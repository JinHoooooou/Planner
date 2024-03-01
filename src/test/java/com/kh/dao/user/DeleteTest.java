package com.kh.dao.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.kh.dao.DaoTestUtils;
import com.kh.helper.DdlHelper;
import com.kh.model.dao.UserDao;
import com.kh.model.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeleteTest {

  private UserDao userDao;

  @BeforeEach
  public void setUp() {
    userDao = new UserDao();
    DdlHelper.dropUsersTable();
    DdlHelper.createUsersTable();
  }

  /*
   *  TODO: Delete는 세션과 delete 할 userId가 같거나 관리자 계정이어야한다.
   *   근데 얘는 Dao쪽에서 검증할 것이 아니라 Controller(Servlet에서 검증해야함)
   * */
  @Test
  @DisplayName("delete 성공: valid 데이터가 주어지고 DB에 해당 데이터가 있을 때")
  public void deleteSuccessTest() {
    // Given: valid 데이터가 주어진다.
    String validUserId = "validUserId";
    // And: DB에 User 객체를 저장한다.
    DaoTestUtils.addUserData(validUserId);

    // When: UserDao.deleteByUserId() 메서드를 호출한다.
    int result = userDao.deleteByUserId(validUserId);

    // Then: result는 1이다.
    assertThat(result).isEqualTo(1);
    // And: DB에서 해당 데이터가 삭제된다.
    User actual = userDao.findByUserId(validUserId);
    assertThat(actual).isNull();
  }

  @Test
  @DisplayName("delete 실패: DB에 해당 데이터가 없을 때")
  public void deleteFailTest() {
    // Given: invalid 데이터가 주어진다.
    String invalidUserId = "invalidUserId";
    // And: DB에 User 객체를 저장한다.
    DaoTestUtils.addUserData("validUserId");

    // When: UserDao.deleteByUserId() 메서드를 호출한다.
    int result = userDao.deleteByUserId(invalidUserId);

    // Then: result는 0이다.
    assertThat(result).isEqualTo(0);
    // And: DB에서 "validUserId"에 대한 데이터는 삭제되지 않는다.
    User actual = userDao.findByUserId("validUserId");
    assertThat(actual).isNotNull();
  }

}
