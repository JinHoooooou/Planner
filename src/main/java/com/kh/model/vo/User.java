package com.kh.model.vo;

import com.kh.model.dto.user.SignUpRequestDto;
import jakarta.validation.ValidationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  private String userId;
  private String userPw;
  private String userName;
  private String nickname;
  private String email;
  private String phone;
  private LocalDate enrollDate;

  public static User from(ResultSet resultSet) throws SQLException {
    return User.builder()
        .userId(resultSet.getString("user_id"))
        .userPw(resultSet.getString("user_pw"))
        .userName(resultSet.getString("user_name"))
        .nickname(resultSet.getString("nickname"))
        .email(resultSet.getString("email"))
        .phone(resultSet.getString("phone"))
        .enrollDate(resultSet.getDate("enroll_date").toLocalDate())
        .build();
  }

  public static User from(SignUpRequestDto requestDto) {
    return User.builder()
        .userId(requestDto.getUserId())
        .userPw(requestDto.getUserPw())
        .userName(requestDto.getUserName())
        .nickname(requestDto.getNickname())
        .email(requestDto.getEmail())
        .phone(requestDto.getPhone())
        .build();
  }

  public User putRequestDto(JSONObject requestBody) {
    String userPw = requestBody.getString("userPw");
    String userPwConfirm = requestBody.getString("userPwConfirm");
    String email = requestBody.getString("email");
    String phone = requestBody.getString("phone");

    if (!userPw.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+]).{8,20}$")) {
      throw new ValidationException("비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해야합니다.");
    }
    if (!userPw.equals(userPwConfirm)) {
      throw new ValidationException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }
    if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      throw new ValidationException("유효하지 않은 이메일입니다.");
    }
    if (!phone.matches("^01[0-9][0-9]{3,4}[0-9]{4}$")) {
      throw new ValidationException("유효하지 않은 휴대전화번호입니다.");
    }

    this.setUserPw(userPw);
    this.setEmail(email);
    this.setPhone(phone);

    return this;
  }

  public JSONObject parseJson() {
    JSONObject result = new JSONObject();
    result.put("userId", this.getUserId());
    result.put("userPw", this.getUserPw());
    result.put("userName", this.getUserName());
    result.put("nickname", this.getNickname());
    result.put("email", this.getEmail());
    result.put("phone", this.getPhone());

    return result;
  }
}
