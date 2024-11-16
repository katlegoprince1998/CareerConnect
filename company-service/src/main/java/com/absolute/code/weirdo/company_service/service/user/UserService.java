package com.absolute.code.weirdo.company_service.service.user;

import com.absolute.code.weirdo.company_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "COMPANY-SERVICE", url = "http://localhost:5001")
public interface UserService {
    @GetMapping("/api/v1/profile/user-profile")
    UserDto getUserProfile(@RequestHeader("Authorization") String jwt);
}
