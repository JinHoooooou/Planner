package com.kh.model.dto.user;

import com.kh.constant.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInRequestDto {

  private String userId;
  private String userPw;


  public static SignInRequestDto from(HttpServletRequest request) {
    try {
      String userId = request.getParameter("userId");
      String userPw = request.getParameter("userPw");

      return SignInRequestDto.builder()
          .userId(userId)
          .userPw(userPw)
          .build();
    } catch (RuntimeException e) {
      throw new RuntimeException(Message.INVALID_REQUEST);
    }
  }
}
