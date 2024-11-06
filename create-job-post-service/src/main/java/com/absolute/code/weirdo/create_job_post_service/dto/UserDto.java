package com.absolute.code.weirdo.create_job_post_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {
    private Long userId;
    private String fullName;
    private String email;
    private String role;
}
