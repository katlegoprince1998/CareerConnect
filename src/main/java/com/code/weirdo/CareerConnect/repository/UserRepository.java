package com.code.weirdo.CareerConnect.repository;

import com.code.weirdo.CareerConnect.models.UserCC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserRepository extends JpaRepository<UserCC, Long> {
    UserCC findUserByEmail(String email) throws UsernameNotFoundException;
}
