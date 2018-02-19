package utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author theo
 */
public class Params { // all final variables are located in the same file for easy editing

    public static Properties prop = new Properties();

    public static int read(String param) {
        try {
            FileInputStream in = new FileInputStream("./src/params.properties");
            prop.load(in);
            in.close();
            String s = prop.getProperty(param);
            s.replaceAll("\\s+", "");
            return Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
