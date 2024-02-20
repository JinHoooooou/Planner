package com.kh.users.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

  private int userNo;
  private String userId;
  private String userPw;
  private String userName;
  private String nickName;
  private String email;
  private String phone;
  private String ssn;
  private String address;
  private LocalDate enrollDate;

  public static Users from(ResultSet resultSet) throws SQLException {
    return Users.builder()
        .userNo(resultSet.getInt("userno"))
        .userId(resultSet.getString("userid"))
        .userPw(resultSet.getString("userpw"))
        .userName(resultSet.getString("username"))
        .nickName(resultSet.getString("nickname"))
        .email(resultSet.getString("email"))
        .phone(resultSet.getString("phone"))
        .ssn(resultSet.getString("ssn"))
        .address(resultSet.getString("address"))
        .enrollDate(resultSet.getDate("enrolldate").toLocalDate())
        .build();
  }
}
