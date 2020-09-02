package com.ausy_technologies.employeemanagement.Repository;

import com.ausy_technologies.employeemanagement.Model.DAO.JobCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoriesRepository extends JpaRepository<JobCategories, Integer> {
}
