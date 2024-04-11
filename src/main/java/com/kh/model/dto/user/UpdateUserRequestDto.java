package com.kh.model.dto.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserRequestDto {

  private String userPw;
  private String userPwConfirm;
  private String email;
  private String phone;


  public static UpdateUserRequestDto from(HttpServletRequest request) {
    return UpdateUserRequestDto.builder()
        .userPw(request.getParameter("userPw"))
        .userPwConfirm(request.getParameter("userPwConfirm"))
        .email(request.getParameter("email"))
        .phone(request.getParameter("phone"))
        .build();
  }
}
