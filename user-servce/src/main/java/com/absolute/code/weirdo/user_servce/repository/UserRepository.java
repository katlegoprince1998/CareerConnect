package com.absolute.code.weirdo.user_servce.repository;

import com.absolute.code.weirdo.user_servce.globals.GRepository;
import com.absolute.code.weirdo.user_servce.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GRepository<User, Long> {
    User findByEmail(String email);
}
