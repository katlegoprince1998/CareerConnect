package com.code.weirdo.CareerConnect.repository;

import com.code.weirdo.CareerConnect.models.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    @Query(
            "SELECT j FROM JobPost j WHERE (:jobTitle IS NULL or j.jobTitle = :jobTitle) " +
                    "AND (:companyName IS NULL OR j.companyName = :companyName) " +
                    "AND (:jobType IS NULL OR :jobType = j.jobType) " +
                    "AND (:salaryRange IS NULL OR j.salaryRange = :salaryRange)"
    )
    List<JobPost> filterJobs(@Param("jobTitle") String jobTitle,
                             @Param("companyName") String companyName,
                             @Param("jobType") String jobType,
                             @Param("salaryRange") String salaryRange);

}
