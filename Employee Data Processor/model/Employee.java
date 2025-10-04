package model;
import java.time.LocalDate;

public class Employee
{
    private String name;
    private LocalDate dob;
    private String dept;
    private int salary;
    private int exp_days;
    private LocalDate doj;
    private int age;

    public Employee(String name,LocalDate dob,String dept,int salary,int exp_days)
    {
        this.name=name;
        this.dob=dob;
        this.dept=dept;
        this.salary=salary;
        this.exp_days=exp_days;
        this.age=LocalDate.now().getYear()-dob.getYear();
        this.doj=LocalDate.now().minusDays(exp_days);
    }
    public String get_dept()
    {
        return dept;
    }
    public int get_salary()
    {
        return salary;
    }
    public String[] row()
    {
        return new String[] { 
            name,dob.toString(),
            Integer.toString(age),
            dept,Integer.toString(salary),doj.toString()
        };
    }
}