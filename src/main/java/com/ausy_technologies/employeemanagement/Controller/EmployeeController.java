package com.ausy_technologies.employeemanagement.Controller;

import com.ausy_technologies.employeemanagement.Error.ErrorResponse;
import com.ausy_technologies.employeemanagement.Mapper.EmployeeMapper;
import com.ausy_technologies.employeemanagement.Model.DAO.Department;
import com.ausy_technologies.employeemanagement.Model.DAO.Employee;
import com.ausy_technologies.employeemanagement.Model.DAO.JobCategories;
import com.ausy_technologies.employeemanagement.Model.DTO.EmployeeDTO;
import com.ausy_technologies.employeemanagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping("addEmployee/{departmentId}/{jobcategoryId}")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee , @PathVariable int departmentId, @PathVariable int jobcategoryId)
    {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","addEmployeeDepJob");
        Employee employeeAdd = null;
        try {
            employeeAdd = employeeService.addEmployee(employee,departmentId, jobcategoryId);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(employeeAdd);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(employeeAdd);

    }


    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getAllEmployees");

        List<Employee> employeeList;

        employeeList = employeeService.findAllEmployees();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);

    }


    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesDTO() {

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve", "getAllEmployees");

        try {
            employeeList = employeeService.findAllEmployees();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeDTOList = employeeList
                .stream()
                .map(e -> employeeMapper.convertEmployeeToDto(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }


    @GetMapping("/getEmployeesByDepartment/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDep(@PathVariable int departmentId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByDep");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeListDTO;
        try {
            employeeList = employeeService.findEmployeeByDepartment(departmentId);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }


        employeeListDTO = employeeList
                .stream()
                .map(e -> employeeMapper.convertEmployeeToDto(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeListDTO);
    }


    @GetMapping("/getEmployeesByJob/{jobId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByJob(@PathVariable int jobId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByJob");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeListDTO;

        try {
            employeeList = employeeService.findEmployeeByJob(jobId);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }

        employeeListDTO = employeeList
                .stream()
                .map(e -> employeeMapper.convertEmployeeToDto(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeListDTO);
    }


    @GetMapping("/getEmployeesByDepartmentAndJob/{departmentId}/{jobId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDepandJob(@PathVariable int departmentId,@PathVariable int jobId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByDepartmentAndJobId");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeListDTO;

        try {
            employeeList = employeeService.findEmployeesByDepAndJob(jobId,departmentId);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }

        employeeListDTO = employeeList
                .stream()
                .map(e -> employeeMapper.convertEmployeeToDto(e))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeListDTO);
    }


    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","deleteEmployee");
        try {
            employeeService.deleteEmployee(id);
        }
        catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Employee Not found");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Employee Deleted");
    }


    @PutMapping("/updateEmployee/{id}/{depId}/{jobId}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,@PathVariable int id, @PathVariable int depId, @PathVariable int jobId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","updateEmployee");
        Employee employeeUpdated;

        try {
            employeeUpdated = employeeService.updateEmployee(employee,id, depId, jobId);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }

        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(employeeUpdated);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDTO(@PathVariable int id){
        EmployeeDTO employeeDTO ;
        Employee employee;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeeDTO");
        try {
            employee = employeeService.findEmployeeById(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTO = employeeMapper.convertEmployeeToDto(employee);
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeDTO);
    }

}
