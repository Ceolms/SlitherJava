package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Properties;

/**
 * all final variables are located in the same file for easy editing
 * this class can read the requested variable
 * @author theo
 */
public class Params { 

    public static Properties prop = new Properties();

    public static int read(String param) {
        
        FileInputStream in;
        
        try
        {
            File f = new File("./src/paramètres.properties");
            if(f.exists() && !f.isDirectory())
            {
                
                in = new FileInputStream("./src/paramètres.properties");
                prop.load(in);
                in.close();
                String s = prop.getProperty(param);
                s.replaceAll("\\s+", "");
                return Integer.parseInt(s);
            }
            f = new File("./paramètres.properties");
            if(f.exists() && !f.isDirectory())
            {
                System.out.println("fichier dans /./");
                in = new FileInputStream("./paramètres.properties");
                prop.load(in);
                in.close();
                String s = prop.getProperty(param);
                s.replaceAll("\\s+", "");
                return Integer.parseInt(s);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static ArrayList<String> readAll()
    {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try
        {
            File f = new File("./src/scores.properties");
            if(f.exists() && !f.isDirectory())
            {
                br = new BufferedReader(new FileReader("./src/scores.properties"));
                String line;
                while ((line = br.readLine()) != null)
                {
                    lines.add(line);
                }
            }
            f = new File("./scores.properties");
            if(f.exists() && !f.isDirectory())
            {
                br = new BufferedReader(new FileReader("./scores.properties"));
                String line;
                while ((line = br.readLine()) != null)
                {
                    lines.add(line);
                }
            }
            
            
        }
        finally{try {
                br.close();
            } catch (Exception e) {
            }
            
            return lines;
        }
    }

    public static void write(String scoreLine)
    {
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter("./src/scores.properties", true));
            bw.write(scoreLine);
            bw.newLine();
            bw.flush();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (bw != null) try
            {
               bw.close();
            } catch (Exception e){}
        }
    }
}
