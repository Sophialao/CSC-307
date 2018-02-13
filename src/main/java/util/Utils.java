package util;

import java.io.*;
import java.util.*;

public class Utils {

    public static Map<Integer, String> parseFile(String fileName) {
        TreeMap<Integer, String> file = new TreeMap<Integer, String>();

        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");

                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < split.length; i++)
                    builder.append(split[i]+" ");

                file.put(Integer.parseInt(split[0]), builder.toString());
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void writeToFile(Map<Integer, String> file, String fileName) {
        String res = "";
        Iterator<Integer> it = file.keySet().iterator();

        while (it.hasNext()) {
            int key = it.next();
            String value = file.get(key);

            res += Integer.toString(key) + " ";
            res += file.get(key);

            /*
            String[] v = value.split(" ");
            for (String s: v) {
                res += s + " ";
            }
            */
            res += "\n";
        }

        try {
            FileWriter writer = new FileWriter(new File(fileName));
            writer.write(res);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
