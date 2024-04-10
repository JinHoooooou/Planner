package com.kh.model.dto.user;

import com.kh.constant.Message;
import com.kh.constant.Regex;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequestDto {

  private String userId;
  private String userPw;
  private String userPwConfirm;
  private String userName;
  private String nickname;
  private String email;
  private String phone;


  public static SignUpRequestDto from(HttpServletRequest request) {
    try {
      String userId = request.getParameter("userId");
      String userPw = request.getParameter("userPw");
      String userPwConfirm = request.getParameter("userPwConfirm");
      String userName = request.getParameter("userName");
      String nickname = request.getParameter("nickname");
      String email = request.getParameter("email");
      String phone = request.getParameter("phone");

      return SignUpRequestDto.builder()
          .userId(userId)
          .userPw(userPw)
          .userPwConfirm(userPwConfirm)
          .userName(userName)
          .nickname(nickname)
          .email(email)
          .phone(phone)
          .build();
    } catch (RuntimeException e) {
      throw new RuntimeException(Message.INVALID_REQUEST);
    }
  }

  public void validate() {
    if (!this.getUserId().matches(Regex.USER_ID)) {
      throw new RuntimeException(Message.ERROR_USER_ID);
    }
    if (!this.getUserPw().matches(Regex.USER_PASSWORD)) {
      throw new RuntimeException(Message.ERROR_USER_PASSWORD);
    }
    if (!this.getUserPw().equals(this.getUserPwConfirm())) {
      throw new RuntimeException(Message.ERROR_USER_PASSWORD_CONFIRM);
    }
    if (!this.getUserName().matches(Regex.USER_NAME)) {
      throw new RuntimeException(Message.ERROR_USER_NAME);
    }
    if (!this.getNickname().matches(Regex.USER_NICKNAME)) {
      throw new RuntimeException(Message.ERROR_USER_NICKNAME);
    }
    if (!this.getEmail().matches(Regex.USER_EMAIL)) {
      throw new RuntimeException(Message.ERROR_USER_EMAIL);
    }
    if (!this.getPhone().matches(Regex.USER_PHONE)) {
      throw new RuntimeException(Message.ERROR_USER_PHONE);
    }
  }
}
