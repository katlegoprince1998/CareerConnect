package com.absolute.code.weirdo.user_servce.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SignInRequest {
    public String email;
    public String password;
}
