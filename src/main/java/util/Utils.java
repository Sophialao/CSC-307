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
                String[] split = line.split(" ");
                DbWritable o = c.getConstructor().newInstance();
                o.readFields(split);

<<<<<<< HEAD
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < split.length; i++)
                    builder.append(split[i]+" ");

                //System.out.println(builder.toString());
                file.put(Integer.parseInt(split[0]), builder.toString());
=======
                file.put(split[0], o);
>>>>>>> aa8ac3d3409698662f48a37d6ccee55b2099fc61
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

    public static String[] readLine(String fileName, String id) {
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                if (split[0] == id) {
                    return split;
                }
            }
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
        File temp = new File("temp.txt");

        try {
            br = new BufferedReader(new FileReader(input));
            bw = new BufferedWriter(new FileWriter(temp));

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                if (split[0] == id) continue;
                String res = "";
                for (String s: split) {
                    res += s + " ";
                }
                res += "\n";
                bw.write(res);
            }
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
        } catch (IOException e)  {
            e.printStackTrace();
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException s) {
            }
        }
    }

}
