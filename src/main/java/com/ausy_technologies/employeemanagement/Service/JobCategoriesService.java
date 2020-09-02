package com.ausy_technologies.employeemanagement.Service;

import com.ausy_technologies.employeemanagement.Error.ErrorResponse;
import com.ausy_technologies.employeemanagement.Model.DAO.JobCategories;
import com.ausy_technologies.employeemanagement.Repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoriesService {

    @Autowired
    private JobCategoriesRepository jobCategoriesRepository;

    public JobCategories saveJobCategory(JobCategories jobCategory){

        if(jobCategory.getJobName() != null && !jobCategory.getJobName().trim().isEmpty()) {
            return this.jobCategoriesRepository.save(jobCategory);
        }
        else {
            throw new ErrorResponse("Job name is null",204);
        }
    }


    public List<JobCategories> findAllJobCategories(){
        List<JobCategories> jobCategoryList =  jobCategoriesRepository.findAll();
        return jobCategoryList;
    }


    public JobCategories findJobById(int id){
        if(!this.jobCategoriesRepository.findById(id).isPresent()) {
            throw new ErrorResponse("Job not found!",404);
        }

        return this.jobCategoriesRepository.findById(id).get();
    }


    public void deleteJobCategory(int id){
        JobCategories jobCategory = null;
        try {
            jobCategory = jobCategoriesRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new ErrorResponse(e.getMessage(),404);
        }
        jobCategoriesRepository.delete(jobCategory);
    }

}
