package com.ausy_technologies.employeemanagement.Controller;

import com.ausy_technologies.employeemanagement.Error.ErrorResponse;
import com.ausy_technologies.employeemanagement.Model.DAO.JobCategories;
import com.ausy_technologies.employeemanagement.Service.JobCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobcategories")
public class JobCategoriesController {

    @Autowired
    private JobCategoriesService jobCategoriesService;


    @PostMapping("/addJobCategory")
    public ResponseEntity<JobCategories> addJobCategory(@RequestBody JobCategories jobCategory){
        JobCategories jobCategoryAdded ;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","addJobCategory");
        try{
            jobCategoryAdded =jobCategoriesService.saveJobCategory(jobCategory);
        }catch (ErrorResponse  e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(jobCategory);
    }


    @GetMapping("/getAllJobCategories")
    public ResponseEntity<List<JobCategories>> getAllJobCategories(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getAllJobCategories");
        List<JobCategories> jobCategoryList;

        try{
            jobCategoryList = jobCategoriesService.findAllJobCategories();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategoryList);
    }


    @GetMapping("/getJobCategoryById/{id}")
    public ResponseEntity<JobCategories> getJobById(@PathVariable int id){
       JobCategories jobCategories = null;

        try{
            jobCategories = jobCategoriesService.findJobById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","findJobById");

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategories);
    }


    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<String> deleteJobCategory(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","deleteJobCategory");
        try{
            jobCategoriesService.deleteJobCategory(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Job not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Job deleted!");
    }

}
