package com.absolute.code.weirdo.create_job_post_service.repository;

import com.absolute.code.weirdo.create_job_post_service.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
