package com.jugal.config.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jugal.model.Department;
import com.jugal.model.Employee;
import com.jugal.repository.DepartmentRepository;
import com.jugal.repository.EmployeeRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner init(EmployeeRepository er, DepartmentRepository dr){
        return args -> {
            // create 3 departments
            Department d1 = Department.builder().name("Engineering").build();
            Department d2 = Department.builder().name("Sales").build();
            Department d3 = Department.builder().name("HR").build();
            dr.save(d1); dr.save(d2); dr.save(d3);

            // CEO
            Employee ceo = Employee.builder().name("Hari Nale").email("hari.nale@wissen.com").title("CEO").build();
            er.save(ceo);

            // dept heads
            Employee h1 = Employee.builder().name("Jugal Khandelwal").email("jugal.khandelwal@wissen.com").title("Head - Engineering").department(d1).manager(ceo).build();
            Employee h2 = Employee.builder().name("Ram Shinde").email("ram.shinde@example.com").title("Head - Sales").department(d2).manager(ceo).build();
            Employee h3 = Employee.builder().name("Raman Singh").email("raman.singh@example.com").title("Head - HR").department(d3).manager(ceo).build();

            er.save(h1); er.save(h2); er.save(h3);

            // set heads in dept
            d1.setHead(h1); d2.setHead(h2); d3.setHead(h3);
            dr.save(d1); dr.save(d2); dr.save(d3);
        };
    }
}