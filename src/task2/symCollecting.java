package task2;

import java.io.FileInputStream;
import java.io.IOException;

public class symCollecting implements Runnable {
    String path = System.getProperty("user.dir")+"/src/task2/";

    @Override
    public void run() {
        try{
            FileInputStream in = new FileInputStream(path+"text.txt");
            int symb;
            while ((symb = in.read()) != -1){
                task2.add((char) symb);
                //System.out.print((char) symb);
            }
        }
        catch(IOException e){}
        System.out.println("Symbols map is full "+task2.getChars().size());
    }
}
