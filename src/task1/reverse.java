package task1;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.WRITE;

public class reverse {
    public static void main(String [] args){
        FileInputStream in;
        FileOutputStream out;
        String path = System.getProperty("user.dir")+"/src/task1/";
        LinkedList<Integer> buf = new LinkedList<Integer>();
        int c, pos = 0;
        try{
            in = new FileInputStream(path+"osi.txt");
            out = new FileOutputStream(path+"osiReverse.txt");
            while ((c=in.read())!=-1){
                if(c  =='\n') pos = 0;
                buf.add(pos, c);
                pos++;
            }
            for(int i = 0; i<buf.size(); i++){
                out.write(buf.get(i));
            }
        }
        catch(IOException e){
            System.out.println("Exception caught:");
            // вывод полной информации об исключении
            e.printStackTrace();
        }
        BufferedReader bufIn;
        try (BufferedReader reader =
                     Files.newBufferedReader(Paths.get(path + "osi.txt"),
                             Charset.defaultCharset()))
        {
            String line = null;
            LinkedList<String>  lines = new LinkedList<>();
            try (BufferedWriter writer =
                         Files.newBufferedWriter(Paths.get(path + "osiReverse.txt"),
                                 Charset.defaultCharset(), APPEND)) {
                while ((line = reader.readLine()) != null) {
                    lines.add(0, line);
                }
                for(int i = 0; i<lines.size(); i++){
                    writer.write(lines.get(i), 0, lines.get(i).length());
                    writer.write('\n');
                }
                System.out.println("File copied with BufferedReader");
            } catch (IOException e) {
                System.out.println("Exception caught (write):");
                System.err.format("IOException: %s%n", e);
            }
        } catch (IOException e) {
            System.out.println("Exception caught (read):");
            System.err.format("IOException: %s%n", e);
        }
    }
}
