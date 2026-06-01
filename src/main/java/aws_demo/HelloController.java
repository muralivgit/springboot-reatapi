package aws_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {


    @Autowired
    EmployeeRepositoty employeeRepositoty;

    @GetMapping("/employees")
    public List<Employee> sayHello() {
        return employeeRepositoty.findAll();
    }
}
