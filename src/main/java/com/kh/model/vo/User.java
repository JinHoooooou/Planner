package com.kh.model.vo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  // DB에 사용될 필드

  @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,16}$"
      , message = "아이디: 영문자로 시작해야 하며 8~16자의 영문자, 숫자, _를 사용해야합니다.")
  private String userId;
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+]).{8,20}$"
      , message = "비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해야합니다.")
  private String userPw;
  @Pattern(regexp = "^[가-힣]{2,20}$"
      , message = "이름: 2~20자의 한글을 사용해야합니다."
  )
  private String userName;
  @Pattern(regexp = "^[가-힣a-zA-Z0-9]{3,20}$"
      , message = "닉네임: 3~20자의 한글, 영문, 숫자를 사용해야합니다.")
  private String nickname;
  @Email(message = "이메일: 유효하지 않은 이메일입니다.")
  private String email;
  @Pattern(regexp = "^01[0-9][0-9]{3,4}[0-9]{4}$"
      , message = "휴대전화번호: 유효하지 않은 휴대전화번호입니다.")
  private String phone;
  private LocalDate enrollDate;

  private String userPwConfirm;

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

  public static User dto(HttpServletRequest req) {
    User user = User.builder()
        .userId(req.getParameter("userId"))
        .userPw(req.getParameter("userPw"))
        .userPwConfirm(req.getParameter("userPwConfirm"))
        .userName(req.getParameter("userName"))
        .nickname(req.getParameter("nickname"))
        .email(req.getParameter("email"))
        .phone(req.getParameter("phone"))
        .build();

    if (user.equalsPassword()) {
      return user;
    }
    throw new ValidationException("비밀번호: 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
  }

  public void validate() {
    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();) {
      Validator validator = validatorFactory.getValidator();
      Set<ConstraintViolation<User>> violations = validator.validate(this);

      if (!violations.isEmpty()) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<User> violation : violations) {
          errorMessage.append(violation.getMessage()).append("\n");
        }
        throw new ValidationException(errorMessage.toString());
      }
    }
  }

  public boolean equalsPassword() {
    return this.getUserPw().equals(this.getUserPwConfirm());
  }
}
