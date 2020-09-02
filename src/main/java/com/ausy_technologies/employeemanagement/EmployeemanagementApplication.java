package com.ausy_technologies.employeemanagement;

import com.ausy_technologies.employeemanagement.Error.ErrorResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeemanagementApplication {

    public static void main(String[] args) {

        ErrorResponse.startLogger();
        SpringApplication.run(EmployeemanagementApplication.class, args);
    }

}
