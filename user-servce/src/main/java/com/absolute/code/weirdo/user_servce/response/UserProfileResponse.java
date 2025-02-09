package com.absolute.code.weirdo.user_servce.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserProfileResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String role;
}
