package com.ausy_technologies.employeemanagement.Repository;

import com.ausy_technologies.employeemanagement.Model.DAO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
