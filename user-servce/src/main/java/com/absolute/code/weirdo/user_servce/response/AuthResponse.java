package com.absolute.code.weirdo.user_servce.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthResponse {
    public  String jwt;
    public  String message;
    public  Boolean status;
}
