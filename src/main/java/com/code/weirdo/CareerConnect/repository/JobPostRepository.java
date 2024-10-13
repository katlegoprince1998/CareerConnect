package com.code.weirdo.CareerConnect.repository;

import com.code.weirdo.CareerConnect.models.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
