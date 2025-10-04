package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.FileprocessingException;

public class Writer {
    private Map<String,List<Employee>> data=new ConcurrentHashMap<>();
    private BufferedWriter log_writer;

    public Writer(String filename) throws FileprocessingException
    {
        try{
        this.log_writer=new BufferedWriter(new FileWriter(filename));
        }
        catch(IOException e)
        {
            throw new FileprocessingException("Unable to open file: "+filename);
        }
    }

    public synchronized void add_emp(Employee e)
    {
        data.computeIfAbsent(e.get_dept(),p->new ArrayList<>()).add(e);

    }
    public void write()
    {
        data.forEach((dept,list)->{
            list.sort((a,b)->b.get_salary()-a.get_salary());

            try(BufferedWriter writer=new BufferedWriter(new FileWriter(dept+".csv")))
            {
                writer.write("Name,DOB,Age,Department,Salary,DateofJoining");
                writer.newLine();
                for(Employee e:list)
                {
                    writer.write(String.join(",",e.row()));
                    writer.newLine();
                }
                log("Written file: " + dept+".csv");
            }
            catch(IOException e)
            {
                System.out.println("Unable to write "+dept+" file");
            }
        });  
        try {
            log_writer.close();
        } catch (IOException e) {
            System.out.println("Unable to close log writer object");
        }
    }

    public synchronized void log(String msg)
    {
        try 
        {
            log_writer.write(LocalDateTime.now()+"- msg");
            log_writer.newLine();
            log_writer.flush();
        } 
        catch (IOException e) {
          e.printStackTrace();
        }
    }

}
