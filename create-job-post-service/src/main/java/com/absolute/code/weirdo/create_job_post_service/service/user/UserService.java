package com.absolute.code.weirdo.create_job_post_service.service.user;

import com.absolute.code.weirdo.create_job_post_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:5001")
public interface UserService {
    @GetMapping("/api/v1/profile/user-profile")
    UserDto getUserProfile(@RequestHeader("Authorization") String jwt);
}
