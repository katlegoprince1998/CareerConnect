package com.absolute.code.weirdo.user_servce.globals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GRepository<T, D> extends JpaRepository<T, D> {
}
