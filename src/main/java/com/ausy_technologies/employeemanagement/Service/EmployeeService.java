package com.ausy_technologies.employeemanagement.Service;

import com.ausy_technologies.employeemanagement.Error.ErrorResponse;
import com.ausy_technologies.employeemanagement.Model.DAO.Department;
import com.ausy_technologies.employeemanagement.Model.DAO.Employee;
import com.ausy_technologies.employeemanagement.Model.DAO.JobCategories;
import com.ausy_technologies.employeemanagement.Repository.DepartmentRepository;
import com.ausy_technologies.employeemanagement.Repository.EmployeeRepository;
import com.ausy_technologies.employeemanagement.Repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobCategoriesRepository jobCategoriesRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Employee addEmployee(Employee employee, int departmentId, int jobcategoryId){
        Department department =null;
        JobCategories jobCategory = null;

        try {
            department = departmentRepository.findById(departmentId).get();

        } catch (RuntimeException e) {
            throw new ErrorResponse(e, "No department with this id" ,404);
        }

        try {
            jobCategory = jobCategoriesRepository.findById(jobcategoryId).get();
        } catch (RuntimeException e) {
            throw new ErrorResponse(e, "No job with this id" ,404);
        }
        employee.setDepartment(department);
        employee.setJobCategories(jobCategory);

        return employeeRepository.save(employee);
    }


    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }


    public Employee findEmployeeById(int id){
        Employee employee;

        try {
            employee = employeeRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new ErrorResponse(e,"No employee with this id",404);
        }

        return employee;
    }


    public List<Employee> findEmployeeByDepartment(int departmentId){
        List<Employee> employeeList = findAllEmployees();
        List<Employee> employeeListByDep;
        employeeListByDep =  employeeList
                                .stream()
                                .filter(e -> e.getDepartment() != null)
                                .filter(e -> e.getDepartment().getId() == departmentId)
                                 .collect(Collectors.toList());

        return  employeeListByDep;
    }


    public List<Employee> findEmployeeByJob(int jobId){
        List<Employee> employeeList = findAllEmployees();
        List<Employee> employeeListByJob;
        employeeListByJob = employeeList
                            .stream()
                            .filter(e -> e.getJobCategories() != null)
                            .filter(e -> e.getJobCategories().getId() == jobId)
                            .collect(Collectors.toList());

        return employeeListByJob;
    }


    public List<Employee> findEmployeesByDepAndJob(int depId, int jobId){
        List<Employee> employeeList = findAllEmployees();
        List<Employee> employeeList1;
        employeeList1 = employeeList
                .stream()
                .filter(e -> e.getDepartment() != null)
                .filter(e -> e.getDepartment().getId() == depId)
                .filter(e -> e.getJobCategories() != null)
                .filter(e -> e.getJobCategories().getId() == jobId)
                .collect(Collectors.toList());

        return employeeList1;
    }


    public void deleteEmployee(int id){
        Employee employee = null;

        try {
            employee = employeeRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new ErrorResponse(e,"No employee with this id", 404);
        }

        employeeRepository.delete(employee);
    }


    public Employee updateEmployee(Employee employee, int id,int depId, int jobId){
        Employee updatedEmployee = findEmployeeById(id);

        Department department;
        JobCategories jobCategory;

        if(!this.departmentRepository.findById(depId).isPresent()) {
            throw new ErrorResponse("Department not found!",400);
        } else {
             department = departmentRepository.findById(depId).get();
        }

        if(!this.jobCategoriesRepository.findById(jobId).isPresent()) {
            throw new ErrorResponse("Job not found!",400);
        } else {
             jobCategory = jobCategoriesRepository.findById(jobId).get();
        }

        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());

        updatedEmployee.setDepartment(department);
        updatedEmployee.setJobCategories(jobCategory);

        updatedEmployee.setActive(employee.isActive());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setBirthday(employee.getBirthday());
        updatedEmployee.setCp(employee.getCp());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setStartDate(employee.getStartDate());
        updatedEmployee.setEndDate(employee.getEndDate());
        updatedEmployee.setHasDrivingLicense(employee.isHasDrivingLicense());
        updatedEmployee.setManager(employee.isManager());
        updatedEmployee.setNoChildren(employee.getNoChildren());
        updatedEmployee.setSalary(employee.getSalary());
        updatedEmployee.setSocialSecurityNumber(employee.getSocialSecurityNumber());
        updatedEmployee.setStudies(employee.getStudies());
        updatedEmployee.setTelephone(employee.getTelephone());

        employeeRepository.save(updatedEmployee);
        return updatedEmployee;
    }

}
