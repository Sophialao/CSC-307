package util;

import model.DbWritable;

import java.io.*;
import java.util.*;

public class Utils {

    public static Map<String, DbWritable> parseFile(String fileName, Class<? extends DbWritable> c) {
        TreeMap<String, DbWritable> file = new TreeMap<String, DbWritable>();

        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(fileName));


            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                DbWritable o = c.getConstructor().newInstance();
                o.readFields(split);


                file.put(split[0], o);
            }
            reader.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String[] readLine(String fileName, String id) {
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if (split[0].equals(id)) {
                    return split;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removeLine(String fileName, String id) {
        BufferedWriter bw = null;
        BufferedReader br = null;
        String line;
        File input = new File(fileName);
        File temp = new File("mock_db/temp.txt");

        try {
            br = new BufferedReader(new FileReader(input));
            bw = new BufferedWriter(new FileWriter(temp));

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split[0].equals(id)) continue;
                String res = "";
                for (String s: split) {
                        if (s.equals(split[split.length-1])) {
                            res += s;
                        } else {
                            res += s + ",";
                        }

                }
                res += "\n";
                bw.write(res);
                bw.flush();
            }
            bw.flush();
            bw.close();
            br.close();

            temp.renameTo(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e)  {
            e.printStackTrace();
        }
    }

    public static void appendLine(String fileName, String line) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(fileName, true));
            bw.write(line);
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e)  {
            e.printStackTrace();
        }
    }

}
