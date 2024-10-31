package com.absolute.code.weirdo.user_servce.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpRequest {
    private  String fullName;
    private  String email;
    private  String role;
    private  String password;
}
