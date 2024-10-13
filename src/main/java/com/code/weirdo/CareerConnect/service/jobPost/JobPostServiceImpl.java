package com.code.weirdo.CareerConnect.service.jobPost;

import com.code.weirdo.CareerConnect.dto.JobPostDto;
import com.code.weirdo.CareerConnect.dtoConvertor.JobPostConvertor;
import com.code.weirdo.CareerConnect.models.JobPost;
import com.code.weirdo.CareerConnect.repository.JobPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService{
    private final JobPostRepository repository;

    @Override
    public JobPostDto createJobPost(JobPostDto dto) throws Exception {
        if (dto == null)
            throw new Exception("Object is empty");
        JobPost jobPost = JobPostConvertor.convertToEntity(dto);
        JobPost createdPost = repository.save(jobPost);
        return JobPostConvertor.convertToDto(createdPost);
    }
}
