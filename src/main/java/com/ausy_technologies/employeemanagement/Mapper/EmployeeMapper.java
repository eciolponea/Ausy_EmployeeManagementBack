package com.ausy_technologies.employeemanagement.Mapper;

import com.ausy_technologies.employeemanagement.Error.ErrorResponse;
import com.ausy_technologies.employeemanagement.Model.DAO.Employee;
import com.ausy_technologies.employeemanagement.Model.DTO.EmployeeDTO;
import com.ausy_technologies.employeemanagement.Repository.DepartmentRepository;
import com.ausy_technologies.employeemanagement.Repository.EmployeeRepository;
import com.ausy_technologies.employeemanagement.Repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobCategoriesRepository jobCategoryRepository;

    public EmployeeDTO convertEmployeeToDto(Employee employee){

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setFirstname(employee.getFirstName());
        employeeDTO.setLastname(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setTelephone(employee.getTelephone());
        employeeDTO.setId(employee.getId());
        employeeDTO.setActive(employee.isActive());
        employeeDTO.setEndDate(employee.getEndDate());
        employeeDTO.setStartDate(employee.getStartDate());
        employeeDTO.setManager(employee.isManager());

        try {
                employeeDTO.setDepartment(employee.getDepartment().getDepartmentName());
            } catch (ErrorResponse e) {
                ErrorResponse.LogError(e);
                employeeDTO.setDepartment(null);
            }

        try {
                employeeDTO.setJobCategory(employee.getJobCategories().getJobName());
            } catch (ErrorResponse e) {
                ErrorResponse.LogError(e);
                employeeDTO.setJobCategory(null);
            }

        return employeeDTO;
        }

}
