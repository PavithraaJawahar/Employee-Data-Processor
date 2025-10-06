package main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadFactory;

import model.*;
import threadpool.*;
import exceptions.FileprocessingException;

public class Processor {
    public static void main(String[] args) {
        String filename="resources/employees.txt";
        int num_threads=20;
        String logfilename="resources/debug.log";

        try{
            Writer writer=new Writer(logfilename);
            Factory factory=new Factory("myfactory");
            Custompool pool=new Custompool(factory, num_threads);

            try(BufferedReader br=new BufferedReader(new FileReader(filename)))
            {
                String line;
                while((line=br.readLine())!=null)
                {
                    pool.execute(new Task(line,writer));

                }
            }
            catch(FileNotFoundException e)
            {
            writer.log("Cannot find input file");
            }
            catch(IOException e)
            {
            writer.log("Cannot read input file");
            }
            try {
            Thread.sleep(5000);  
            } catch (InterruptedException e) {}



            pool.shutdown();
            writer.write();

        }
        catch(FileprocessingException e)
        {
            System.err.println("Unable to process file by writer");
        }
        
    }
    
}
