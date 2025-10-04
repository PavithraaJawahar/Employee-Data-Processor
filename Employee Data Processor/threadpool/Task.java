package threadpool;
import model.*;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EmployeeparseException;
import model.Employee;

public class Task implements Runnable{
    private String line;
    private Writer writer;

    public Task(String line,Writer writer)
    {
        this.line=line;
        this.writer=writer;
    }

    @Override
    public void run()
    {
        try{
            Employee e=parseline(line);
            writer.add_emp(e);
        }
        catch(EmployeeparseException e)
        {
            writer.log("Parsing error: " + e.getMessage());
        }
    }

    public Employee parseline(String line) throws EmployeeparseException
    {
        Pattern p = Pattern.compile(
            "\"name\"\\s*:\\s*\"([^\"]+)\".*"+
            "\"dob\"\\s*:\\s*\"([^\"]+)\".*"+
            "\"department\"\\s*:\\s*\"([^\"]+)\".*"+
            "\"salary\"\\s*:\\s*(\\d+).*"+
            "\"experienceDays\"\\s*:\\s*(\\d+)"
        );
        Matcher m = p.matcher(line);

        if(m.find()) {
            String name = m.group(1);
            LocalDate dob = LocalDate.parse(m.group(2));
            String dept = m.group(3);
            int salary = Integer.parseInt(m.group(4));
            int expDays = Integer.parseInt(m.group(5));
            return new Employee(name, dob, dept, salary, expDays);
        } else {
            throw new EmployeeparseException("Invalid line: " + line);
        }
    }

    }


    

