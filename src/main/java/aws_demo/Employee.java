package aws_demo;

import jakarta.persistence.*;

@Entity
@Table(name = "emp")
public class Employee {

    public Employee() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments IDs in Postgres
    @Column(name = "id")
    private Long empId;
    @Column(name = "name")
    private String empName;
    @Column(name = "salary")
    private double salary;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }



}
